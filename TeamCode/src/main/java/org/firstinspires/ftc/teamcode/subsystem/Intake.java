package org.firstinspires.ftc.teamcode.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.configVar;

public class Intake extends SubsystemBase {
    private final DcMotorEx intakeMotor;
    private final ServoEx intakeHeightChanger;

    public Intake(HardwareMap hardwareMap) {

        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        intakeHeightChanger = hardwareMap.get(ServoEx.class, "Intake Height Servo");

    }

    @Override
    public void register() {
        int startingintakepos = 0;
        intakeHeightChanger.setPosition(startingintakepos);
        super.register();
    }

    @Override
    public void periodic() {

    }

    public void intake() {
        if (configVar.intakereversed) {
            setPower(1.0);
        } else {
            setPower(-1.0);
        }
    }

    public void outtake() {
        if (configVar.intakereversed) {
            setPower(-1.0);
        } else {
            setPower(1.0);
        }
    }

    public void intakeHigh() {
        overideIntakePos(configVar.intakehighpos);
    }

    public void intakeLow() {
        overideIntakePos(configVar.intakelowpos);
    }

    public void overideIntakePos(int Pos) {
        intakeHeightChanger.turnToAngle(Pos);
    }


    public void stop() {
        intakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        setPower(0.0);
    }

    public void setPower(double power) {
        intakeMotor.setPower(power);
    }
}