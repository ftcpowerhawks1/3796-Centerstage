package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.subsystem.Elevator;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Vision;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.ArrayList;
import java.util.List;

@TeleOp
@Disabled
public class Skelly extends LinearOpMode {

    //Drive Init
    public int DriverTolerance = 5;

    //Gamepads
    public GamepadEx primaryGamePad = new GamepadEx(gamepad1);
    public GamepadEx secondaryGamePad = new GamepadEx(gamepad2);

    //Motors
    MotorEx leftFront, leftBack, rightFront, rightBack;

    //Toggle Buttons
    public ToggleButtonReader intaketoggle = new ToggleButtonReader(secondaryGamePad, GamepadKeys.Button.RIGHT_BUMPER);

    //Subsystem Init
    protected Intake intake;
    protected Elevator elevator;
    protected Vision vision;

    protected void initHardware(boolean isAuto) {
        //Subsystem inti
        intake = new Intake(hardwareMap);
        elevator = new Elevator(hardwareMap);
        vision = new Vision(hardwareMap);

        intake.register();
        elevator.register();
        vision.register();

        //Motor Init
        leftFront = hardwareMap.get(MotorEx.class, "Left Front");
        leftBack = hardwareMap.get(MotorEx.class, "Left Back");
        rightFront = hardwareMap.get(MotorEx.class, "Right Front");
        rightBack = hardwareMap.get(MotorEx.class, "Right Back");


    }

    @Override
    public void runOpMode() {

    }
}
