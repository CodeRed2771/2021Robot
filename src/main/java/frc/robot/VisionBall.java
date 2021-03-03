package frc.robot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Semaphore;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;    
import org.opencv.imgproc.Imgproc;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Point;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;   

public class VisionBall
{
    //Camera Resolution Constants
    private static final int CAMERA_HORIZONTAL_RESOLUTION = 640;
    private static final int CAMERA_VERTICAL_RESOLUTION = 480;

    //Camera FOV Constants 
    private static final float CAMERA_HORIZONTOL_FOV = 60.0f;
    private static final float CAMERA_VERTICAL_FOV = 36.9f; //Calculated Experimentally
    //Contour Filtering Constants
    private static final int BALL_MIN_WIDTH = 20;
    //Camera Physical Constants
    private static final float CAMERA_HEIGHT = 6.5f;
    private static final float CAMERA_TILT = 9.5f; 
    private static final int RELATIVE_CENTER_X = 320; //TODO - Get actual center x
    //Ball Physical Constants
    private static final float CENTER_BALL_HEIGHT = 3.5f;

    //Member Variables
    private static UsbCamera mBallTrackingCamera;

    private static CvSink mCameraFrameGrabber;

    private static CvSource mRawImageStream;
    private static CvSource mBinaryStream;
    private static CvSource mProcessedStream;

    private static Mat mUnprocessedFrame;
    private static Mat mBinaryFrame;
    private static Mat mProcessedFrame;
    
    private static ArrayList<MatOfPoint> mContours;

    public VisionBall()
    {

    }

    public static void SetUpBallVision()
    {
        //Start Camera Server and bind it to usb port 0
        mBallTrackingCamera = CameraServer.getInstance().startAutomaticCapture(0);
        //Set resolution of camera
        mBallTrackingCamera.setResolution(CAMERA_HORIZONTAL_RESOLUTION, CAMERA_VERTICAL_RESOLUTION);

        //Set up CV Sink to grab frames
        mCameraFrameGrabber = CameraServer.getInstance().getVideo();
        mCameraFrameGrabber.setSource(mBallTrackingCamera);

        //Setup Publishing Streams
        mRawImageStream = CameraServer.getInstance().putVideo("Raw Image", CAMERA_HORIZONTAL_RESOLUTION, CAMERA_VERTICAL_RESOLUTION);
        mBinaryStream = CameraServer.getInstance().putVideo("Binary Image", CAMERA_HORIZONTAL_RESOLUTION, CAMERA_VERTICAL_RESOLUTION);
        mProcessedStream = CameraServer.getInstance().putVideo("Processed Image", CAMERA_HORIZONTAL_RESOLUTION, CAMERA_VERTICAL_RESOLUTION);

        //Report Back Succesful Init
        System.out.println("Ball Tracking Vision Init Successful");

        //Initialize Contours List
        mContours = new ArrayList<>();

        //Initialize Frames
        mUnprocessedFrame = new Mat();
        mBinaryFrame = new Mat();
        mProcessedFrame = new Mat();
    }

    public static ArrayList<Point> GetBallLocations()
    {
        //Grab and process a frame
        SmartDashboard.putString("aaaaaaaaaaaaaaaaaaaaaaaaa", "0");
        GrabFrameFromServer();
        SmartDashboard.putString("aaaaaaaaaaaaaaaaaaaaaaaaa", "1");
        ProcessFrame();
        SmartDashboard.putString("aaaaaaaaaaaaaaaaaaaaaaaaa", "2");

        //Find all the Contours in the frame
        ArrayList<Rect> balls = FindContoursInFrame();
        SmartDashboard.putString("aaaaaaaaaaaaaaaaaaaaaaaaa", "3");

        //Find position of balls in relationship to camera - returned as polar coordinants
        ArrayList<Point> ballLocations = FindPositions(balls);
        SmartDashboard.putString("aaaaaaaaaaaaaaaaaaaaaaaaa", "4");

        //Sort Balls from nearest to furthest
        ballLocations = PrioritizeBalls(ballLocations);
        SmartDashboard.putString("aaaaaaaaaaaaaaaaaaaaaaaaa", "5");

        return ballLocations;
    }
    
    private static void GrabFrameFromServer()
    {
        mCameraFrameGrabber.grabFrame(mUnprocessedFrame);
        SmartDashboard.putNumber("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj", mUnprocessedFrame.cols());
        //Display raw image
        mRawImageStream.putFrame(mUnprocessedFrame); // failing at this step
        SmartDashboard.putString("bbbbbbbbbbbbbbbbbbbbbb", "1");
        //Save copy of raw image so that we can bound balls on it later
        Imgproc.cvtColor(mUnprocessedFrame, mProcessedFrame, Imgproc.COLOR_BGR2RGB);
        SmartDashboard.putString("bbbbbbbbbbbbbbbbbbbbbb", "2");
    }

    private static void ProcessFrame()
    {
        //Convert BGR to HSV color space
        Imgproc.cvtColor(mUnprocessedFrame, mUnprocessedFrame, Imgproc.COLOR_BGR2HSV);

        //Threshold the Image to HSV
        Core.inRange(mUnprocessedFrame, new Scalar(10, 140, 120), new Scalar(33, 255, 255), mBinaryFrame);

        //Erode and Dilate Image to help filter false positives
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(15, 15));
        Imgproc.dilate(mBinaryFrame, mBinaryFrame, element);
        Imgproc.erode(mBinaryFrame, mBinaryFrame, element);
        Imgproc.erode(mBinaryFrame, mBinaryFrame, element);

        //Display the filtered binary image
        mBinaryStream.putFrame(mBinaryFrame);
    }

    private static ArrayList<Rect> FindContoursInFrame()
    {
        //Get rid of old Contours
        mContours.clear();

        Imgproc.findContours(mBinaryFrame, mContours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        return ProcessContours();
    }

    private static ArrayList<Rect> ProcessContours()
    {
        //List for accepted rectangles
        ArrayList<Rect> trackedBalls = new ArrayList<>();

        Rect possibleBall = null;

        for(MatOfPoint contour : mContours)
        {
            possibleBall = Imgproc.boundingRect(contour);

            if(possibleBall.width > BALL_MIN_WIDTH)
            {
                trackedBalls.add(possibleBall);
                Imgproc.rectangle(mProcessedFrame,
                                  new Point(possibleBall.x, possibleBall.y), 
                                  new Point(possibleBall.x + possibleBall.width, possibleBall.y + possibleBall.height),
                                  new Scalar(0,255,0), 10);
            } 
        }

        mProcessedStream.putFrame(mProcessedFrame);

        return trackedBalls;
    }

    private static ArrayList<Point> FindPositions(ArrayList<Rect> balls)
    {
        float currentX = 0.0f;
        float currentY = 0.0f;

        float degreesFromTarget = 0.0f;
        float distanceFromTarget = 0.0f;
        float verticalDegreesFromTarget = 0.0f;

        ArrayList<Point> ballPolarCoordinants = new ArrayList<>(); 

        for(Rect rectangle : balls)
        {
            //Calculate Center Of Targets
            currentX = rectangle.x + (((float) rectangle.width) / 2.0f);
            currentY = rectangle.y + (((float) rectangle.height) / 2.0f);

            //Calculate angular displacement
            degreesFromTarget = (RELATIVE_CENTER_X - currentX) * (CAMERA_HORIZONTOL_FOV / ((float)CAMERA_HORIZONTAL_RESOLUTION));

            //Calculate how far the ball is
            verticalDegreesFromTarget = (((CAMERA_VERTICAL_RESOLUTION / 2) - currentY) * (CAMERA_VERTICAL_FOV / ((float)CAMERA_VERTICAL_RESOLUTION)));
            verticalDegreesFromTarget = CAMERA_TILT - verticalDegreesFromTarget;
            
            verticalDegreesFromTarget *= (verticalDegreesFromTarget < 0)? -1 : 1;

            distanceFromTarget = (CAMERA_HEIGHT - CENTER_BALL_HEIGHT) / ((float) Math.tan(Math.toRadians(verticalDegreesFromTarget)));

            System.out.println(distanceFromTarget + "Inches Away");

            ballPolarCoordinants.add(new Point(degreesFromTarget, distanceFromTarget));
        }

        return ballPolarCoordinants;
    }

    
    private static ArrayList<Point> PrioritizeBalls(ArrayList<Point> balls)
    {
        int indexOfSmallestElement = 0;

        for(int i = 0; i < balls.size(); i++)
        {
            indexOfSmallestElement = i;
            for(int j = i + 1; j < balls.size(); j++)
            {
                if(balls.get(j).y < balls.get(indexOfSmallestElement).y)
                {
                    indexOfSmallestElement = j;
                }
            }
            Collections.swap(balls, i, indexOfSmallestElement);
        }

        return balls;
    }
}