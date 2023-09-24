package org.firstinspires.ftc.teamcode;

import android.annotation.SuppressLint;

import com.outoftheboxrobotics.photoncore.PhotonCore;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.subsystem.Elevator;
import org.firstinspires.ftc.teamcode.subsystem.Vision;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Vision;

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
        PhotonCore.enable();

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
