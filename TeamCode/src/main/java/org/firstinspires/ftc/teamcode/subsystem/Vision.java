package org.firstinspires.ftc.teamcode.subsystem;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.annotation.SuppressLint;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import org.firstinspires.ftc.teamcode.configVar;
import org.firstinspires.ftc.teamcode.Skelly.*;



import java.util.ArrayList;
import java.util.List;

public class Vision extends SubsystemBase {
    private final HardwareMap hardwareMap;
    public static final String TFOD_MODEL_FILE = "model.tflite";
    public List<String> labels = new ArrayList<>();
    public VisionPortal visionPortal;
    public AprilTagProcessor aprilTag;
    public TfodProcessor tfod;

    possibleAutoPos autoPos;

    public int blueLeftID = 1;
    public int blueMiddleID = 2;
    public int blueRightID = 3;

    public int redLeftID = 1;
    public int redMiddleID = 2;
    public int redRightID = 3;

    public double aprilTagLeftCenterX = 0;
    public double aprilTagMiddleCenterX = 0;
    public double aprilTagRightCenterX = 0;

    private List<AprilTagDetection> aprilTagDetections;

    private double returnvalue;

    public Vision(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

    }

    @Override
    public void register() {
        super.register();
    }

    @Override
    public void periodic() {

    }

    /**
     * Initialize AprilTag and Tensorflow.
     */

    public void initVision(possibleAutoPos pos) {

        autoPos = pos;
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

    /**
     * Add telemetry about AprilTag detections.
     */
    @SuppressLint("DefaultLocale")
    public void telemetryAprilTag() {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop

        // Add "key" information to telemetry
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");

    }   // end method telemetryAprilTag()

    /**
     * Return the double of the distance until the left tag is centered.
     */
    public double alignleft() {
        List<AprilTagDetection> aprilTagDetections = aprilTag.getDetections();
        if (autoPos == possibleAutoPos.BLUELEFT || autoPos == possibleAutoPos.BLUERIGHT) {
            if (!aprilTagDetections.isEmpty()) {
                for (AprilTagDetection detection : aprilTagDetections) {
                    if (detection.id == blueLeftID) {
                        aprilTagLeftCenterX = detection.center.x;
                        returnvalue = configVar.cameraCenter - aprilTagLeftCenterX;

                    }
                }
            } else {
                returnvalue = 99999;
            }
        } else {
            if (!aprilTagDetections.isEmpty()) {
                for (AprilTagDetection detection : aprilTagDetections) {
                    if (detection.id == redLeftID) {
                        aprilTagLeftCenterX = detection.center.x;
                        returnvalue = configVar.cameraCenter - aprilTagLeftCenterX;

                    }
                }
            } else {
                returnvalue = 99999;
            }
        }
        return returnvalue;
    }

    /**
     * Return the double of the distance until the middle tag is centered.
     */
    public double alignmiddle() {
        aprilTagDetections = aprilTag.getDetections();
        if (autoPos == possibleAutoPos.BLUELEFT || autoPos == possibleAutoPos.BLUERIGHT) {
            if (!aprilTagDetections.isEmpty()) {
                for (AprilTagDetection detection : aprilTagDetections) {
                    if (detection.id == blueMiddleID) {
                        aprilTagMiddleCenterX = detection.center.x;
                        returnvalue = configVar.cameraCenter - aprilTagMiddleCenterX;

                    }
                }
            } else {
                returnvalue = 99999;
            }
        } else {
            if (!aprilTagDetections.isEmpty()) {
                for (AprilTagDetection detection : aprilTagDetections) {
                    if (detection.id == redMiddleID) {
                        aprilTagMiddleCenterX = detection.center.x;
                        returnvalue = configVar.cameraCenter - aprilTagMiddleCenterX;

                    }
                }
            } else {
                returnvalue = 99999;
            }
        }
        return returnvalue;
    }


    /**
     * Return the double of the distance until the right tag is centered.
     */
    public double alignright() {
        if (autoPos == possibleAutoPos.BLUELEFT || autoPos == possibleAutoPos.BLUERIGHT) {
            aprilTagDetections = aprilTag.getDetections();
            if (!aprilTagDetections.isEmpty()) {
                for (AprilTagDetection detection : aprilTagDetections) {
                    if (detection.id == blueRightID) {
                        aprilTagRightCenterX = detection.center.x;
                        returnvalue = configVar.cameraCenter - aprilTagRightCenterX;

                    }
                }
            } else {
                returnvalue = 99999;
            }
        } else {
            aprilTagDetections = aprilTag.getDetections();
            if (!aprilTagDetections.isEmpty()) {
                for (AprilTagDetection detection : aprilTagDetections) {
                    if (detection.id == redRightID) {
                        aprilTagRightCenterX = detection.center.x;
                        returnvalue = configVar.cameraCenter - aprilTagRightCenterX;

                    }
                }
            } else {
                returnvalue = 99999;
            }
        }
        return returnvalue;
    }


    public void getapriltagcenter() {
        aprilTagDetections = aprilTag.getDetections();

        if (autoPos == possibleAutoPos.BLUELEFT || autoPos == possibleAutoPos.BLUERIGHT) {
            if (!aprilTagDetections.isEmpty()) {
                for (AprilTagDetection detection : aprilTagDetections) {
                    if (detection.id == blueLeftID) {
                        aprilTagLeftCenterX = detection.center.x;
                    } else if (detection.id == blueMiddleID) {
                        aprilTagMiddleCenterX = detection.center.x;
                    } else if (detection.id == blueRightID) {
                        aprilTagRightCenterX = detection.center.x;
                    }
                }
            }
        } else {
            if (!aprilTagDetections.isEmpty()) {
                for (AprilTagDetection detection : aprilTagDetections) {
                    if (detection.id == redLeftID) {
                        aprilTagLeftCenterX = detection.center.x;
                    } else if (detection.id == redMiddleID) {
                        aprilTagMiddleCenterX = detection.center.x;
                    } else if (detection.id == redRightID) {
                        aprilTagRightCenterX = detection.center.x;
                    }
                }
            }
        }
    }
}