package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoBallAtHomeChallenge extends AutoBaseClass {

    AutoBallPickUp mAutoBallPickUp = new AutoBallPickUp();

    public AutoBallAtHomeChallenge() {
        
    }

    public void start() {
        super.start();
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
                mAutoBallPickUp.tick();
                advanceStep();
                break;
            case 1:
                mAutoBallPickUp.start();
                advanceStep();
                break;
            case 2:
                if (mAutoBallPickUp.ballCollected()) {
                advanceStep();
                }
                break;
            case 3:
                if (mAutoBallPickUp.getDegreesOffBall() == 0 && mAutoBallPickUp.distanceFromBall() == 0)
                {
                    setStep(50);
                }
                else if (mAutoBallPickUp.getDegreesOffBall() < -10)
                {
                    setStep(70);
                }
                else
                {
                    setStep(90);
                }
                break;
// ----------------------------------------------------------------------------------------------------------------------------------
// BLUE PATH
// ----------------------------------------------------------------------------------------------------------------------------------

// BOTH PATH A AND B
            case 50:
                driveInches(120, 0, 1);
                setTimerAndAdvanceStep(3000);
                break;
            case 51:
                if (driveCompleted())
                {
                    advanceStep();
                }
                break;
            case 52:
                turnDegrees(30, 1);
                setTimerAndAdvanceStep(1000);
                break;
            case 53:
                if (driveCompleted())
                {
                    advanceStep();
                }
                break;
            case 54:
                mAutoBallPickUp.start();
                advanceStep();
                break;
            case 55:
                if (mAutoBallPickUp.ballCollected())
                {
                    advanceStep();
                }
                break;
            case 56:
                turnToHeading(330, 1);
                setTimerAndAdvanceStep(2000);
                break;
            case 57:
                if (driveCompleted())
                {
                    advanceStep();
                }
                break;
            case 58:
                mAutoBallPickUp.start();
                advanceStep();
                break;
            case 59:
                if (mAutoBallPickUp.ballCollected())
                {
                    advanceStep();
                }
                break;
            case 60:
                turnToHeading(37, 1);
                setTimerAndAdvanceStep(2000);
                break;
            case 61:
                if (driveCompleted())
                {
                    advanceStep();
                }
                break;
            case 62:
                mAutoBallPickUp.start();
                advanceStep();
                break;
            case 63:
                if (mAutoBallPickUp.ballCollected())
                {
                    advanceStep();
                }
                break;
            case 64:
                turnToHeading(0, 1);
                setTimerAndAdvanceStep(1000);
                break;
            case 65: 
                if (driveCompleted())
                {
                    advanceStep();
                }
                break;
            case 66:
                driveInches(60, 0, 1);
                setTimerAndAdvanceStep(3000);
                break;
            case 67:
                if (driveCompleted())
                {
                    setStep(1000);
                }                
                break;
// -----------------------------------------------------------------------------------------------------------------------------------
// RED PATHS
// -----------------------------------------------------------------------------------------------------------------------------------

// PATH A
            case 70:
                turnToHeading(30, 1);
                setTimerAndAdvanceStep(2000);
                break;
            case 71:
                if (driveCompleted())
                {
                    advanceStep();
                }
                break;
            case 72:
                mAutoBallPickUp.start();
                advanceStep();
                break;
            case 73:
                if (mAutoBallPickUp.ballCollected())
                {
                    advanceStep();
                }
                break;
            case 74:
                turnToHeading(330, 1);
                setTimerAndAdvanceStep(2000);
                break;
            case 75:
                if (driveCompleted())
                {
                    advanceStep();
                }
            case 76:
                mAutoBallPickUp.start();
                advanceStep();
                break;
            case 77:
                if (mAutoBallPickUp.ballCollected())
                {
                    advanceStep();
                }
                break;
            case 78:
                turnToHeading(0, 1);
                setTimerAndAdvanceStep(2000);
                break;
            case 79:
                if (driveCompleted())
                {
                    advanceStep();
                }
                break;
            case 80:
                driveInches(126, 0, 1);
                setTimerAndAdvanceStep(6000);
                break;
            case 81:
                if (driveCompleted())
                {
                    setStep(1000);
                }
                break;

// PATH B
            
            case 90:
                mAutoBallPickUp.start();
                advanceStep();
                break;
            case 91:
                if (mAutoBallPickUp.ballCollected())
                {
                    advanceStep();
                }
                break;
            case 92:
                turnToHeading(290, 1);
                setTimerAndAdvanceStep(3000);
                break;
            case 93:
                if (driveCompleted())
                {
                    advanceStep();
                }
                break;
            case 94:
                mAutoBallPickUp.start();
                advanceStep();
                break;
            case 95:
                if (mAutoBallPickUp.ballCollected())
                {
                    advanceStep();
                }
                break;
            case 96:
                turnToHeading(0, 1);
                setTimerAndAdvanceStep(2000);
                break;
            case 97:
                if (driveCompleted())
                {
                    advanceStep();
                }
                break;
            case 98:
                driveInches(132, 0, 1);
                setTimerAndAdvanceStep(5000);
                break;
            case 99:
                if (driveCompleted())
                {
                    setStep(1000);
                }
                break;
                
// -----------------------------------------------------------------------------------------------------------------------------------
            case 1000:
                stop();
                break;
            }
        }
    }
}