package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@Config
class configVar extends Skelly {
    public static volatile int kS = 0;
    public static volatile int kG = 0;
    public static volatile int kV = 0;
    public static volatile int kA = 0;
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

                    leftFront.setVelocity(frontLeftPower);
                    leftBack.setVelocity(backLeftPower);
                    rightFront.setVelocity(frontRightPower);
                    rightBack.setVelocity(backRightPower);

                }


                if (operator.wasJustPressed(GamepadKeys.Button.A)); {
                    elevatorRunToSet3();
                }

                if (operator.wasJustPressed(GamepadKeys.Button.B)); {
                    elevatorRunToSet2();
                }

                if (operator.wasJustPressed(GamepadKeys.Button.X)); {
                    elevatorRunToSet1();
                }




                telemetry.update();
                sleep(20);
            }
        }
    }
}

