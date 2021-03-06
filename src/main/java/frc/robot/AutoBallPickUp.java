package frc.robot;

import java.util.ArrayList;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.opencv.core.Point;

public class AutoBallPickUp extends AutoBaseClass {

    private double angleOffset = 0;
    private static boolean ballCollected = false;

    private static  ArrayList<Point> BallLocations = new ArrayList<>();

    public AutoBallPickUp () {

    }

    public boolean ballCollected () {
        return ballCollected;
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
                ballCollected = false;
                BallLocations.clear();
                BallLocations = VisionBall.GetBallLocations();
                advanceStep();
                break;
            case 1:
                turnDegrees(BallLocations.get(0).y/2, 0.5);
                //calculate strafe distance and direction
                // double Distance = BallLocations.get(0).y * Math.sin(Math.toRadians(BallLocations.get(0).x));
                // double Degrees = (Distance < 0) ? -90 : 90;
                // // driveInches(Math.abs(Distance), Degrees, .8);
                // SmartDashboard.putNumber("Strafe Distance:", Distance);
                //command serve base
                setTimerAndAdvanceStep(2000);
                break;
            case 2:
                if (driveCompleted())
                {
                    advanceStep();
                }
                break;
            case 3:
                driveInches(-BallLocations.get(0).x, 0, 0.5);
                setTimerAndAdvanceStep(3000);
                break;
            case 4:
                if (driveCompleted())
                {
                    ballCollected = true;
                    advanceStep();
                }
                break;
            case 5:
                stop();
                break;
            }
        }
    }
}
