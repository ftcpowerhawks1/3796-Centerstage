package org.firstinspires.ftc.teamcode;

import android.annotation.SuppressLint;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@TeleOp(name = "Double Vision", group = "Pain (Beta)")

public class DoubleVision extends Skelly {

    //Initialize Required Variables for AprilTag
    int aprilTagLeftId = 1;
    int aprilTagMiddleId = 2;
    int aprilTagRightId = 3;

    double aprilTagLeftCenterX, aprilTagMiddleCenterX, aprilTagRightCenterX;

    //Initialize Required Variables for Tensorflow
    int tensorflowLeftBorder = 20;
    int tensorflowRightBorder = 120;

    @Override
    public void runOpMode() {

        initVision();


        telemetry.addData(">", "Hope And Pray That it works");

        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                //AprilTag
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
                //Tensorflow
                List<Recognition> tfodRecognitions = tfod.getRecognitions();
                if (!tfodRecognitions.isEmpty()) {
                    for (Recognition detections : tfodRecognitions) {
                        double x = (detections.getLeft() + detections.getRight()) / 2 ;

                        //Left
                        if (x <= tensorflowLeftBorder) {
                            telemetry.addData(">", "Place on left");
                        }
                        //Center
                        if (x >= tensorflowLeftBorder && x <= tensorflowRightBorder) {
                            telemetry.addData(">", "Place in Middle");
                        }
                        //Right
                        if (x <= tensorflowRightBorder) {
                            telemetry.addData(">", "Place on Right");
                        }

                    }
                }



                telemetryAprilTag();
                telemetry.update();


                // Share the CPU.
                sleep(20);
            }
        }

        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();

    }   // end method runOpMode()




    /**
     * Add telemetry about AprilTag detections.
     */
    @SuppressLint("DefaultLocale")
    private void telemetryAprilTag() {

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

}   // end class
