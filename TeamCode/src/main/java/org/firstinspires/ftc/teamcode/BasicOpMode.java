package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@Config
class configVar extends Skelly {

    public static volatile int elevatorKS = 0;
    public static volatile int elevatorKG = 0;
    public static volatile int elevatorKV = 0;
    public static volatile int elevatorKA = 0;

    public static volatile int intakeVel = 50;

    public static int cameraCenter = 620;
    public static int apriltagtollerence = 5;
}

@TeleOp(name = "BasicTeleOp", group = "Pain (Basic)")
public class BasicOpMode extends configVar {

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

            Button setline3 = new GamepadButton(
                    operator, GamepadKeys.Button.A
            );
            Button setline2 = new GamepadButton(
                    operator, GamepadKeys.Button.A
            );
            Button setline1 = new GamepadButton(
                    operator, GamepadKeys.Button.A
            );

            Button autoalignleft = new GamepadButton(
                    driver, GamepadKeys.Button.A
            );
            Button autoalignmiddle = new GamepadButton(
                    driver, GamepadKeys.Button.A
            );
            Button autoalignright = new GamepadButton(
                    driver, GamepadKeys.Button.A
            );


            while (opModeIsActive()) {

                double rx = driver.getRightX();
                double x = driver.getLeftX() * 1.1; // Counteract imperfect strafing
                double y = driver.getLeftY();

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

                if (driver.isDown(GamepadKeys.Button.DPAD_LEFT)) {
                    while (Math.abs(aprilTagLeftCenterX - cameraCenter) > apriltagtollerence) {
                        break;
                    }
                }

                if (driver.isDown(GamepadKeys.Button.DPAD_UP)) {
                    while (Math.abs(aprilTagMiddleCenterX - cameraCenter) > apriltagtollerence) {
                        break;
                    }
                }

                if (driver.isDown(GamepadKeys.Button.DPAD_RIGHT)) {
                    while (Math.abs(aprilTagRightCenterX - cameraCenter) > apriltagtollerence) {
                        break;
                    }
                }

                //TODO Implement FTCLib Subsystems
                setline1.whenPressed(intakesubsystem::spinintake);




                telemetry.update();
                sleep(20);
            }
        }
    }
}

