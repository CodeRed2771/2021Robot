package frc.robot;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.opencv.core.Point;

public class AutoBallPickUp extends AutoBaseClass {

    private double angleOffset = 0;
    private static boolean ballCollected = false;
    private static double degreesOffOfBall = 0;
    private static double distanceFromBall = 0;



    private static  ArrayList<Point> BallLocations = new ArrayList<>();

    public AutoBallPickUp () {

    }

    public boolean ballCollected () {
        return ballCollected;
    }

    public static double getDegreesOffBall ()
    {
        return degreesOffOfBall;
    }

    public static double distanceFromBall()
    {
        return distanceFromBall;
    }

    public void start() {
        super.start();
    }

    public void start(boolean autoShoot){
        super.start(autoShoot);
    }

    public void stop() {
        super.stop();
    }

    @Override
    public void tick() {
        if (isRunning()) {
            DriveAuto.tick();
            SmartDashboard.putNumber("Auto Step", getCurrentStep());
            switch (getCurrentStep()) {
            case 0:
                //get ball list
                Intake.moveIntakeDown();
                setTimerAndAdvanceStep(2000);
                break;
            case 1:
                advanceStep();
                break;
            case 2:
                ballCollected = false;
                BallLocations.clear();
                BallLocations = VisionBall.GetBallLocations();
                advanceStep();
                break;
            case 3:
                try 
                {
                    if (BallLocations.get(0).y > 120)
                    {
                        degreesOffOfBall = 0;
                        distanceFromBall = 0;
                        setStep(7);
                    }
                    else
                    {
                        turnDegrees(-BallLocations.get(0).x, 0.5);
                        degreesOffOfBall = -BallLocations.get(0).x;
                        setTimerAndAdvanceStep(2000);
                    }
                } 
                catch (ArrayIndexOutOfBoundsException e)
                {
                    degreesOffOfBall = 0;
                    distanceFromBall = 0;
                    setStep(7);
                }
                //command serve base
                break;
            case 4:
                if (driveCompleted())
                {
                    advanceStep();
                }
                break;
            case 5:
                Intake.runIntakeForwards();
                driveInches(BallLocations.get(0).y, 0, 0.5);
                distanceFromBall = BallLocations.get(0).y;
                setTimerAndAdvanceStep(3000);
                break;
            case 6:
                if (driveCompleted())
                {
                    ballCollected = true;
                    advanceStep();
                }
                break;
            case 7:
                stop();
                break;
            }
        }
    }
}
