package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@Autonomous(name = "Auto", group = "Pain (Pre-Release)")

public class Auto extends Skelly {

    /**
     * The variable to store our instance of the AprilTag processor.
     */

    /**
     * The variable to store our instance of the vision portal.
     */

    //Initialize Required Variables for AprilTag

    double leftCenterX, middleCenterX, rightCenterX;

    @Override
    public void runOpMode() {
        initHardware(true);
        vision.initVision();

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                //April Tag
                List<AprilTagDetection> aprilTagDetections = vision.aprilTag.getDetections();
                if (!aprilTagDetections.isEmpty()){
                    for (AprilTagDetection detection : aprilTagDetections) {
                        if (detection.id == vision.leftId) {
                            leftCenterX = detection.center.x;
                        } else if (detection.id == vision.middleId) {
                            middleCenterX = detection.center.x;
                        } else if (detection.id == vision.rightId) {
                            rightCenterX = detection.center.x;
                        }
                    }
                }

                //Tensorflow
                vision.telemetryAprilTag();
                telemetry.update();

                // Share the CPU.
                sleep(20);
            }
        }

        // Save more CPU resources when camera is no longer needed.
        vision.visionPortal.close();

    }   // end method runOpMode()


}   // end class
