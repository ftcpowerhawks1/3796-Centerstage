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
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.ArrayList;
import java.util.List;

@TeleOp
@Disabled
public class Skelly extends LinearOpMode {
    //Vision Items
    public static final String TFOD_MODEL_FILE = "model.tflite";
    public List<String> labels = new ArrayList<>();
    public VisionPortal visionPortal;
    public AprilTagProcessor aprilTag;
    public TfodProcessor tfod;

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

    protected void initHardware(boolean isAuto) {
        //Subsystem inti
        intake = new Intake(hardwareMap);
        elevator = new Elevator(hardwareMap);

        intake.register();
        elevator.register();

        //Motor Init
        leftFront = hardwareMap.get(MotorEx.class, "Left Front");
        leftBack = hardwareMap.get(MotorEx.class, "Left Back");
        rightFront = hardwareMap.get(MotorEx.class, "Right Front");
        rightBack = hardwareMap.get(MotorEx.class, "Right Back");


    }

    @Override
    public void runOpMode() {




    }

    /**
    * Initialize AprilTag and Tensorflow.
    * */
    public void initVision() {

        labels.add("Cone");
        labels.add("No Cone");

        // Create the AprilTag processor.
        aprilTag = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                //.setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                //.setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                //.setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)

                // == CAMERA CALIBRATION ==
                // If you do not manually specify calibration parameters, the SDK will attempt
                // to load a predefined calibration for your camera.
                //.setLensIntrinsics(578.272, 578.272, 402.145, 221.506)

                // ... these parameters are fx, fy, cx, cy.

                .build();
        tfod = new TfodProcessor.Builder()

                // Use setModelAssetName() if the TF Model is built in as an asset.
                // Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                //.setModelAssetName(TFOD_MODEL_ASSET)
                .setModelFileName(TFOD_MODEL_FILE)

                .setModelLabels(labels)
                //.setIsModelTensorFlow2(true)
                .setIsModelQuantized(true)
                //.setModelInputSize(300)
                //.setModelAspectRatio(16.0 / 9.0)

                .build();
        tfod.setMinResultConfidence(0.75f);

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));


        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(aprilTag);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Disable or re-enable the aprilTag processor at any time.
        visionPortal.setProcessorEnabled(aprilTag, true);

    }   // end method initAprilTag()

}
