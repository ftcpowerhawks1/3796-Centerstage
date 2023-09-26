package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.outoftheboxrobotics.photoncore.PhotonCore;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.Elevator;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Vision;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@TeleOp
@Disabled
public class Skelly extends LinearOpMode {
    //Auto Pos storage
    public enum possibleAutoPos {
        BLUELEFT,
        BLUERIGHT,
        REDLEFT,
        REDRIGHT
    }
    possibleAutoPos autopos;

    //Drive Init
    public int DriverTolerance = 5;

    //Gamepads
    public GamepadEx primaryGamePad = new GamepadEx(gamepad1);
    public GamepadEx secondaryGamePad = new GamepadEx(gamepad2);

    //Motors
    MotorEx leftFront, leftBack, rightFront, rightBack;

    //Toggle Buttons

    //Subsystem Init
    protected Intake intake;
    protected Elevator elevator;
    protected Vision vision;



    protected void initHardware(boolean isAuto) {
        //Subsystem init
        intake = new Intake(hardwareMap);
        elevator = new Elevator(hardwareMap, 0, 0, 0);
        vision = new Vision(hardwareMap);

        intake.register();
        elevator.register();
        vision.register();

        //Motor Init
        leftFront = hardwareMap.get(MotorEx.class, "Left Front");
        leftBack = hardwareMap.get(MotorEx.class, "Left Back");
        rightFront = hardwareMap.get(MotorEx.class, "Right Front");
        rightBack = hardwareMap.get(MotorEx.class, "Right Back");

        leftFront.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);

        if (isAuto) {
            leftFront.setRunMode(MotorEx.RunMode.RawPower);
            leftBack.setRunMode(MotorEx.RunMode.RawPower);
            rightFront.setRunMode(MotorEx.RunMode.RawPower);
            rightBack.setRunMode(MotorEx.RunMode.RawPower);
        } else {
            leftFront.setRunMode(MotorEx.RunMode.VelocityControl);
            leftBack.setRunMode(MotorEx.RunMode.VelocityControl);
            rightFront.setRunMode(MotorEx.RunMode.VelocityControl);
            rightBack.setRunMode(MotorEx.RunMode.VelocityControl);
        }



    }

    @Override
    public void runOpMode() {
        PhotonCore.enable();

        while (opModeIsActive()) {
            //April Tag


            //Tensorflow
            vision.telemetryAprilTag();
            telemetry.update();

            // Share the CPU.
            sleep(20);
        }
    }

    public void stopRobot() {
        intake.stop();
        elevator.stop();

        leftFront.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.FLOAT);
        leftBack.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.FLOAT);
        rightFront.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.FLOAT);
        rightBack.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.FLOAT);

        leftFront.stopMotor();
        leftBack.stopMotor();
        rightFront.stopMotor();
        rightBack.stopMotor();
    }
}
