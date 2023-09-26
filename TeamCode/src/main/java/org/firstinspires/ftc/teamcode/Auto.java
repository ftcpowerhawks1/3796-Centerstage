package org.firstinspires.ftc.teamcode;

import com.outoftheboxrobotics.photoncore.PhotonCore;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@Autonomous(name = "Auto", group = "Pain (Pre-Release)")

public class Auto extends Skelly {

    /**
     * The variable to store our instance of the vision portal.
     */

    //Initialize Required Variables for AprilTag

    double leftCenterX, middleCenterX, rightCenterX;

    @Override
    public void runOpMode() {
        PhotonCore.enable();
        initHardware(true);
        vision.initVision(autopos);



        waitForStart();

        if (opModeIsActive()) {
            automain(possibleAutoPos.REDRIGHT);

        }

        // Save more CPU resources when camera is no longer needed.
        vision.visionPortal.close();

    }   // end method runOpMode()

    public void automain(possibleAutoPos pos) {
        autopos = pos;
        if (pos == possibleAutoPos.BLUELEFT) {
            telemetry.addData(">", "Blue Left");
        } else if (pos == possibleAutoPos.BLUERIGHT) {
            telemetry.addData(">", "Blue Right");
        } else if (pos == possibleAutoPos.REDLEFT) {
            telemetry.addData(">", "Red Left");
        } else if (pos == possibleAutoPos.REDRIGHT) {
            telemetry.addData(">", "Red Right");
        }
    }

}   // end class
