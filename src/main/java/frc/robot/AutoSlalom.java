/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoSlalom extends AutoBaseClass {

    public void start() {
        super.start();
    }

    public void stop() {
        super.stop();
    }

    @Override
    public void tick() {
        if (isRunning()) {
            Vision.setTargetForShooting();
            DriveAuto.tick();
            SmartDashboard.putNumber("Auto Step", getCurrentStep());

            switch (getCurrentStep()) {
            case 0:
                DriveAuto.driveInches(54,90, 1);
                setTimerAndAdvanceStep(2000);
                break;
            case 1:
                if (driveCompleted()) {
                    advanceStep();
                } 
                break;
            case 2:
                DriveAuto.driveInches(54, 0, 1);
                setTimerAndAdvanceStep(2000);
                break;
            case 3:
                if (driveCompleted()) {
                    advanceStep();
                } 
                break;
            case 4:
                DriveAuto.driveInches(85, 90, 1);
                setTimerAndAdvanceStep(3000);
                break;
            case 5:
                if (driveCompleted()) {
                    setStep(99);
                    advanceStep();
                }
                break;
            case 6:
                DriveAuto.driveInches(60, 45, 1);
                setTimerAndAdvanceStep(2000);
                break;
            case 7:
                if (driveCompleted()) {
                    advanceStep();
                }
                break;
            case 8:
                DriveAuto.driveInches(48, 0, 1);
                setTimerAndAdvanceStep(2000);
                break;
            case 9:
                if (driveCompleted()) {
                    advanceStep();
                }
                break;
            case 10:
                DriveAuto.driveInches(24, -90, 1);
                setTimerAndAdvanceStep(2000);
                break;
            case 11:
                if (driveCompleted()) {
                    advanceStep();
                }
                break;
            case 20:
            stop();
                break;
            case 21:
               
                break;
            case 22:
              
                break;
            }
        }
    }
}