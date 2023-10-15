package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;

import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Vision;



@TeleOp(name = "TeleOp", group = "Pain (Basic)")
public class OpMode extends configVar {


    Intake intake;
    Vision vision;
    public ToggleButtonReader intakeToggle = new ToggleButtonReader(secondaryGamePad, GamepadKeys.Button.A);

    @Override
    public void runOpMode() {
        waitForStart();

        initHardware(false);

        if (opModeIsActive()) {

            new GamepadButton(secondaryGamePad, GamepadKeys.Button.DPAD_UP).whenPressed(intake::stop);
            new GamepadButton(secondaryGamePad, GamepadKeys.Button.LEFT_BUMPER).whenPressed(intake::intakeLow);
            new GamepadButton(secondaryGamePad, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(intake::intakeHigh);
            new GamepadButton(secondaryGamePad, GamepadKeys.Button.X).whenPressed(elevator::elevatorRunToSet1);
            new GamepadButton(secondaryGamePad, GamepadKeys.Button.Y).whenPressed(elevator::elevatorRunToSet2);
            new GamepadButton(secondaryGamePad, GamepadKeys.Button.B).whenPressed(elevator::elevatorRunToSet3);
            new GamepadButton(secondaryGamePad, GamepadKeys.Button.DPAD_DOWN).whenPressed(elevator::elevatorDown);
            new GamepadButton(secondaryGamePad, GamepadKeys.Button.DPAD_RIGHT).whenPressed(elevator::scoreOnBoard);


            while (opModeIsActive() && !isStopRequested()) {

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


                telemetry.update();
                sleep(20);
            }
        if (isStopRequested()) {
            stopRobot();

            stop();
        }
        }
    }
}

