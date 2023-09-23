package org.firstinspires.ftc.teamcode.subsystem;

import static org.firstinspires.ftc.teamcode.configVar.intakereversed;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake extends SubsystemBase {
    private HardwareMap hardwareMap;
    private DcMotorEx intakeMotor;



    public Intake(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");


    }

    @Override
    public void register() {
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

    public void stop() {
        setPower(0.0);
    }

    public void setPower(double power) {
        intakeMotor.setPower(power);
    }
}