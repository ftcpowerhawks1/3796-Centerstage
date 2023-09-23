package org.firstinspires.ftc.teamcode.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.ArrayList;
import java.util.List;

public class SubsystemTemplate extends SubsystemBase {
    private HardwareMap hardwareMap;
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

}