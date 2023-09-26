package org.firstinspires.ftc.teamcode.subsystem;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.ArrayList;
import java.util.List;

public class SubsystemTemplate extends SubsystemBase {
    private final HardwareMap hardwareMap;
    //define hardware here as private
    //private MotorEx test;

    public SubsystemTemplate(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        //init hardware here
    }

    @Override
    public void register() {
        super.register();
    }

    @Override
    public void periodic() {

    }
    //put any subsystem specific functions here.


    public void stop() {
        //Turn all motors in subsystem to float
        telemetry.addData("Stoped", "Stoped");

        //Set power to 0
    }
}