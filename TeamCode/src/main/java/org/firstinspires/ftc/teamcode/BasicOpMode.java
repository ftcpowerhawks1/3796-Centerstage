package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.Elevator;
import org.firstinspires.ftc.teamcode.subsystem.Intake;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@TeleOp(name = "BasicTeleOp", group = "Pain (Basic)")
public class BasicOpMode extends configVar {
    protected Intake intake;
    @Override
    public void runOpMode() {

        waitForStart();

        initVision();

        if (opModeIsActive()) {
            int aprilTagLeftId = 1;
            int aprilTagMiddleId = 2;
            int aprilTagRightId = 3;

            double aprilTagLeftCenterX = 0;
            double aprilTagMiddleCenterX = 0;
            double aprilTagRightCenterX = 0;

            new GamepadButton(secondaryGamePad, GamepadKeys.Button.A).whenPressed(intake::intake);
            new GamepadButton(secondaryGamePad, GamepadKeys.Button.A).whenPressed(elevator::elevatorRunToSet1);
            new GamepadButton(secondaryGamePad, GamepadKeys.Button.A).whenPressed(elevator::elevatorRunToSet2);
            new GamepadButton(secondaryGamePad, GamepadKeys.Button.A).whenPressed(elevator::elevatorRunToSet3);



            while (opModeIsActive()) {

                double rx = primaryGamePad.getRightX();
                double x = primaryGamePad.getLeftX() * 1.1; // Counteract imperfect strafing
                double y = primaryGamePad.getLeftY();

                if (rx > DriverTolerance || x > DriverTolerance || y > DriverTolerance) {
                    double denominator = Math.max(Math.abs(rx) + Math.abs(x) + Math.abs(y), 1);
                    double frontLeftPower = (rx - x + y) / denominator;
                    double backLeftPower = (rx + x + y) / denominator;
                    double frontRightPower = (rx - x - y) / denominator;
                    double backRightPower = (rx + x - y) / denominator;

                    leftFront.setVelocity(frontLeftPower, DEGREES);
                    leftBack.setVelocity(backLeftPower, DEGREES);
                    rightFront.setVelocity(frontRightPower, DEGREES);
                    rightBack.setVelocity(backRightPower, DEGREES);

                }

                List<AprilTagDetection> aprilTagDetections = aprilTag.getDetections();
                if (!aprilTagDetections.isEmpty()){
                    for (AprilTagDetection detection : aprilTagDetections) {
                        if (detection.id == aprilTagLeftId){
                            aprilTagLeftCenterX = detection.center.x;
                        } else if (detection.id == aprilTagMiddleId) {
                            aprilTagMiddleCenterX = detection.center.x;
                        } else if (detection.id == aprilTagRightId) {
                            aprilTagRightCenterX = detection.center.x;
                        }
                    }
                }



                telemetry.update();
                sleep(20);
            }
        }
    }
}

