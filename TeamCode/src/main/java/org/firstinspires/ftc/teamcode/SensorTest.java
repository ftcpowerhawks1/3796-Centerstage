package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.TouchSensor;


@TeleOp(name = "TeleOp sensor test", group = "Pain (Basic)")
public class SensorTest extends configVar {
    private DigitalChannel greenLED;
    TouchSensor touch;


    @Override
    public void runOpMode() {
        // Get the LED colors and touch sensor from the hardwaremap
        greenLED = hardwareMap.get(DigitalChannel.class, "green");


        // Wait for the play button to be pressed
        waitForStart();

        // change LED mode from input to output
        greenLED.setMode(DigitalChannel.Mode.OUTPUT);

        // Loop while the Op Mode is running
        while (opModeIsActive()) {
            greenLED.setState(touch.isPressed());
        }
    }
}
