package org.firstinspires.ftc.teamcode.subsystem;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class SubsystemTemplate extends SubsystemBase {
    //define hardware here as private
    //private MotorEx test;

    public SubsystemTemplate(HardwareMap hardwareMap) {
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