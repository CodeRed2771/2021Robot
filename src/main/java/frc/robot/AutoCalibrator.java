/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoCalibrator extends AutoBaseClass {

    private AutoAlign mAutoAlign = new AutoAlign();

    public void start(char robotPosition) {
        super.start(robotPosition);
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
                turnDegrees(90, 1);
                // driveInches(60, 0, 1, false, true);
                setTimerAndAdvanceStep(2000);
                break;
            case 1:
                if (driveCompleted()) {
                    advanceStep();
                }
                break;
            case 2:
                stop();
                break;
            }
        }
    }
}
