package org.firstinspires.ftc.teamcode.subsystem;

import static org.firstinspires.ftc.teamcode.configVar.intakereversed;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake extends SubsystemBase {
    private HardwareMap hardwareMap;
    private DcMotorEx intakeMotor;
    private ServoEx intakeHeightChanger;
    private int intakehighpos = 25;
    private int intakelowpos = 25;
    private int startingintakepos = 0;



    public Intake(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        intakeHeightChanger = hardwareMap.get(ServoEx.class, "Intake Height Servo");

    }

    @Override
    public void register() {
        intakeHeightChanger.setPosition(startingintakepos);
        super.register();
    }

    @Override
    public void periodic() {

    }

    public void intake() {
        if (intakereversed) {
            setPower(1.0);
        } else {
            setPower(-1.0);
        }
    }

    public void outtake() {
        if (intakereversed) {
            setPower(-1.0);
        } else {
            setPower(1.0);
        }
    }

    public void intakehigh() {
        intakeHeightChanger.setPosition(intakehighpos);
    }

    public void intakelow() {
        intakeHeightChanger.setPosition(intakelowpos);
    }

    public void overideIntakePos(int Pos) {
        intakeHeightChanger.setPosition(Pos);
    }


    public void stop() {
        setPower(0.0);
    }

    public void setPower(double power) {
        intakeMotor.setPower(power);
    }
}