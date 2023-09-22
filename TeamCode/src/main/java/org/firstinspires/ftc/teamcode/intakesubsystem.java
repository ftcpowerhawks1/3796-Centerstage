package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.command.SubsystemBase;

public class intakesubsystem extends SubsystemBase {
    private final MotorEx intakeMotor = new MotorEx(hardwareMap, "testing");


    /**
     * Releases a starts intaking.
     */
    public void spinintake() {
        intakeMotor.setVelocity(80);
    }

}