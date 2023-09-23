package org.firstinspires.ftc.teamcode.subsystem;

import static org.firstinspires.ftc.teamcode.configVar.*;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.configVar;

public class Elevator extends SubsystemBase {
    private HardwareMap hardwareMap;
    private MotorEx Elevatorleftmotor, Elevatorrightmotor;

    // Create a new ElevatorFeedforward with gains kS, kG, kV, and kA
    ElevatorFeedforward feedforward = new ElevatorFeedforward(
            elevatorKS, elevatorKG, elevatorKV, elevatorKA
    );

    public Elevator(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        Elevatorleftmotor = hardwareMap.get(MotorEx.class, "Left Elevator");
        Elevatorrightmotor = hardwareMap.get(MotorEx.class, "Right Elevator");

    }

    @Override
    public void register() {
        super.register();
    }

    @Override
    public void periodic() {

    }

    public void elevatorRunToSet3() {
        Elevatorleftmotor.setVelocity(feedforward.calculate(10, 20));
        Elevatorrightmotor.setVelocity(feedforward.calculate(10, 20));
    }
    public void elevatorRunToSet2() {
        Elevatorleftmotor.setVelocity(feedforward.calculate(10, 20));
        Elevatorrightmotor.setVelocity(feedforward.calculate(10, 20));
    }
    public void elevatorRunToSet1() {
        Elevatorleftmotor.setVelocity(feedforward.calculate(10, 20));
        Elevatorrightmotor.setVelocity(feedforward.calculate(10, 20));
    }

    public void stop() {
        setVel(0.0);
    }

    public void setVel(double vel) {
        Elevatorleftmotor.setVelocity(vel);
        Elevatorrightmotor.setVelocity(vel);
    }
}