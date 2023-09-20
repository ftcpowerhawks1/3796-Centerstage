package org.firstinspires.ftc.teamcode;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.internal.ui.GamepadUser;

@TeleOp(name = "BasicTeleOp", group = "Pain (Basic)")
public class BasicOpMode extends Skelly {

    @Override
    public void runOpMode() {
        DcMotorEx rf, lf, rb, lb, armLow, armHigh;

        Servo claw;

        GamepadEx driver = new GamepadEx(gamepad1);

        rf = hardwareMap.get (DcMotorEx.class, "Right Front Motor");
        lf = hardwareMap.get (DcMotorEx.class, "Left Front Motor");
        rb = hardwareMap.get (DcMotorEx.class, "Right Back Motor");
        lb = hardwareMap.get (DcMotorEx.class, "Left Back Motor");

        armLow = hardwareMap.get(DcMotorEx.class, "Lower Arm Motor");
        armHigh = hardwareMap.get(DcMotorEx.class, "Upper Arm Motor");

        claw = hardwareMap.get(Servo.class, "Claw Servo");//Servo //CH-S0

        armLow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armHigh.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                armLow.setTargetPositionTolerance(2);
                double y = gamepad1.right_stick_x; // Remember, Y stick value is reversed
                double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
                double rx = gamepad1.left_stick_y;

                // Denominator is the largest motor power (absolute value) or 1
                // This ensures all the powers maintain the same ratio,
                // but only if at least one is out of the range [-1, 1]
                double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                double frontLeftPower = (y - x + rx) / denominator;
                double backLeftPower = (y + x + rx) / denominator;
                double frontRightPower = (y - x - rx) / denominator;
                double backRightPower = (y + x - rx) / denominator;

                lf.setPower(frontLeftPower);
                lb.setPower(backLeftPower);
                rf.setPower(frontRightPower);
                rb.setPower(backRightPower);
                telemetry.addLine("Arm Low:" + armLow.getCurrentPosition());
                telemetry.addLine("Arm High: " + armHigh.getCurrentPosition());

                telemetry.update();

                if (gamepad1.a) {
                    armHigh.setDirection(DcMotorSimple.Direction.REVERSE);

                    armHigh.setPower(0.25);
                }
                if (gamepad1.b) {
                    armHigh.setDirection(DcMotorSimple.Direction.FORWARD);

                    armHigh.setPower(0.25);
                }

                if (gamepad1.y) {
                    armLow.setDirection(DcMotorSimple.Direction.REVERSE);

                    armLow.setPower(0.25);
                }

                if (gamepad1.x) {
                    armLow.setDirection(DcMotorSimple.Direction.FORWARD);

                    armLow.setPower(0.25);
                }

                if (gamepad1.dpad_up) {
                    armHigh.setDirection(DcMotorSimple.Direction.REVERSE);

                    armHigh.setPower(0.1);

                    armLow.setDirection(DcMotorSimple.Direction.REVERSE);
                    armLow.setPower(0.1);
                }
                if (gamepad1.dpad_down) {
                    armHigh.setDirection(DcMotorSimple.Direction.FORWARD);

                    armHigh.setPower(0.1);

                    armLow.setDirection(DcMotorSimple.Direction.FORWARD);
                    armLow.setPower(0.1);
                }

                if (!gamepad1.dpad_down && !gamepad1.dpad_up && !gamepad1.x && !gamepad1.y &&
                        !gamepad1.b && !gamepad1.a) {
                    armHigh.setPower(0);
                    armLow.setPower(0);
                    armHigh.setDirection(DcMotorSimple.Direction.FORWARD);
                    armLow.setDirection(DcMotorSimple.Direction.FORWARD);
                }

                if (gamepad1.right_bumper){
                    claw.setPosition(0);
                } else if (gamepad1.left_bumper){
                    claw.setPosition(1);
                }




                sleep(20);
            }
        }
    }
}

