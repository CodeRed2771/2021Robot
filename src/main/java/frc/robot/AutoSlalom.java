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
                    driveInches(60, 90, 1);
                    setTimerAndAdvanceStep(2000);
                    break;
                case 1:
                    if (driveCompleted()) {
                        advanceStep();
                    } 
                    break;
                case 2:
                   driveInches( 130, 45, 1);
                    setTimerAndAdvanceStep(2000);
                    break;
                case 3:
                    if (driveCompleted()) {
                        advanceStep();
                    } 
                    break;
                case 4:
                    driveInches( 120, -45, 1);
                     setTimerAndAdvanceStep(2000);
                     break;
                case 5:
                     if (driveCompleted()) {
                         advanceStep();
                     } 
                     break;
                case 6:
                    driveInches( 130, -45, 1);
                    setTimerAndAdvanceStep(3000);
                    break;
                case 7:
                    if (driveCompleted()) {
                        advanceStep();
                    }
                    break;
                case 8:
                    driveInches( 60, 45, 1);
                    setTimerAndAdvanceStep(2000);
                    break;
                case 9:
                    if (driveCompleted()) {
                        advanceStep();
                    }
                    break;
                case 10:
                    driveInches( 90, -134, 1);
                    setTimerAndAdvanceStep(2000);
                    break;
                case 11:
                    if (driveCompleted()) {
                        advanceStep();
                    }
                    break;
                case 12:
                    driveInches( 90, 180, 1);
                    setTimerAndAdvanceStep(2000);
                    break;
                case 13:
                    if (driveCompleted()) {
                        advanceStep();
                    }
                    break;
                case 14:
                    driveInches( 90, 90, 1);
                    setTimerAndAdvanceStep(2000);
                    break;
                case 15:
                    if (driveCompleted()) {
                        advanceStep();
                    }
                    break;
                case 16:
                    driveInches( 150 , 180, 1);
                    setTimerAndAdvanceStep(2000);
                    break;
                case 17:
                    if (driveCompleted()) {
                        advanceStep();
                    }
                    break;
                case 18:
                    driveInches( 60, -225, 1);
                    setTimerAndAdvanceStep(2000);
                    break;
                case 19:
                    if (driveCompleted()) {
                        advanceStep();
                    }
                    break;
                case 20:
                    driveInches( 30, 180, 1);
                    setTimerAndAdvanceStep(2000);
                    break;
                case 21:
                    if (driveCompleted()) {
                        advanceStep();
                    }
                    break;
                case 22:
                    stop();
                    break;
            } 
        }
    }
}
        
