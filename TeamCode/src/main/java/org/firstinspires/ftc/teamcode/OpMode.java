package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;

import android.util.Log;

import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.arcrobotics.ftclib.util.Timing;
import com.outoftheboxrobotics.photoncore.PhotonCore;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Vision;

import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "TeleOp", group = "Pain (Basic)")
public class OpMode extends configVar {


    protected Intake intake;
    protected Vision vision;
    public List<String> labels = new ArrayList<>();
    public VisionPortal visionPortal;
    public AprilTagProcessor aprilTag;
    public TfodProcessor tfod;
    public ToggleButtonReader intakeToggle = new ToggleButtonReader(secondaryGamePad, GamepadKeys.Button.A);

    @Override
    public void runOpMode() {
        PhotonCore.enable();
        waitForStart();

        initHardware(false);

        if (opModeIsActive()) {
            int aprilTagLeftId = 1;
            int aprilTagMiddleId = 2;
            int aprilTagRightId = 3;

            double aprilTagLeftCenterX = 0;
            double aprilTagMiddleCenterX = 0;
            double aprilTagRightCenterX = 0;

            new GamepadButton(secondaryGamePad, GamepadKeys.Button.DPAD_UP).whenPressed(intake::stop);
            new GamepadButton(secondaryGamePad, GamepadKeys.Button.LEFT_BUMPER).whenPressed(intake::intakelow);
            new GamepadButton(secondaryGamePad, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(intake::intakehigh);
            new GamepadButton(secondaryGamePad, GamepadKeys.Button.X).whenPressed(elevator::elevatorRunToSet1);
            new GamepadButton(secondaryGamePad, GamepadKeys.Button.Y).whenPressed(elevator::elevatorRunToSet2);
            new GamepadButton(secondaryGamePad, GamepadKeys.Button.B).whenPressed(elevator::elevatorRunToSet3);
            new GamepadButton(secondaryGamePad, GamepadKeys.Button.DPAD_DOWN).whenPressed(elevator::elevatordown);
            new GamepadButton(secondaryGamePad, GamepadKeys.Button.DPAD_RIGHT).whenPressed(elevator::scoreonboard);



            while (opModeIsActive()) {

                /* Custom Mecaunum Drive Code */
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

                if (intakeToggle.stateJustChanged()) {
                    if (intakeToggle.getState()) {
                        intake.intake();
                    } else {
                        intake.outtake();
                    }
                }

                //April Tag Stuff
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
        if (isStopRequested()) {
            stoprobot();

            stop();
        }
        }
    }
}

