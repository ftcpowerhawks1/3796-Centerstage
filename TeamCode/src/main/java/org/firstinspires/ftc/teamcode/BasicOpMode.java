package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Config
class configVar extends Skelly {

    public static volatile int elevatorKS = 0;
    public static volatile int elevatorKG = 0;
    public static volatile int elevatorKV = 0;
    public static volatile int elevatorKA = 0;

    public static volatile int intakeVel = 50;
}

@TeleOp(name = "BasicTeleOp", group = "Pain (Basic)")
public class BasicOpMode extends configVar {

    @Override
    public void runOpMode() {

        waitForStart();

        if (opModeIsActive()) {
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


                if (operator.isDown(GamepadKeys.Button.A)) {
                    elevatorRunToSet3();
                }

                if (operator.isDown(GamepadKeys.Button.B)) {
                    elevatorRunToSet2();
                }

                if (operator.isDown(GamepadKeys.Button.Y)) {
                    elevatorRunToSet1();
                }


                if (intake.getState()) {
                    intakeMotor.setVelocity(intakeVel, DEGREES);
                } else {
                    intakeMotor.setVelocity(0, DEGREES);
                }




                telemetry.update();
                sleep(20);
            }
        }
    }
}

