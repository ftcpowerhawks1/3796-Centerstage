package org.firstinspires.ftc.teamcode.subsystem;

import static org.firstinspires.ftc.teamcode.configVar.*;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.PIDEx;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficientsEx;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.configVar;

public class Elevator extends SubsystemBase {
    private HardwareMap hardwareMap;
    private DcMotorEx Elevatorleftmotor, Elevatorrightmotor;

    public BasicPID controller;

    public Elevator(HardwareMap hardwareMap, double Kp, double Ki, double Kd) {
        this.hardwareMap = hardwareMap;

        Elevatorleftmotor = hardwareMap.get(DcMotorEx.class, "Left Elevator");
        Elevatorrightmotor = hardwareMap.get(DcMotorEx.class, "Right Elevator");

        Elevatorleftmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Elevatorrightmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        PIDCoefficients coefficients = new PIDCoefficients(Kp,Ki,Kd);
        BasicPID controller = new BasicPID(coefficients);

    }

    @Override
    public void register() {
        super.register();
    }

    @Override
    public void periodic() {

    }

    public void elevatorRunToSet3() {
        while (5 != Elevatorleftmotor.getCurrentPosition() - 100) {
            Elevatorleftmotor.setPower(gotopos(100, Elevatorleftmotor.getCurrentPosition()));
            Elevatorrightmotor.setPower(gotopos(100, Elevatorrightmotor.getCurrentPosition()));
        }
    }
    public void elevatorRunToSet2() {
        while (5 != Elevatorleftmotor.getCurrentPosition() - 50) {
            Elevatorleftmotor.setPower(gotopos(50, Elevatorleftmotor.getCurrentPosition()));
            Elevatorrightmotor.setPower(gotopos(50, Elevatorrightmotor.getCurrentPosition()));
        }
    }
    public void elevatorRunToSet1() {
        while (5 != Elevatorleftmotor.getCurrentPosition() - 30) {
            Elevatorleftmotor.setPower(gotopos(30, Elevatorleftmotor.getCurrentPosition()));
            Elevatorrightmotor.setPower(gotopos(30, Elevatorrightmotor.getCurrentPosition()));
        }
    }

    public void elevatordown() {
        while (5 != Elevatorleftmotor.getCurrentPosition() - 0) {
            Elevatorleftmotor.setPower(gotopos(0, Elevatorleftmotor.getCurrentPosition()));
            Elevatorrightmotor.setPower(gotopos(0, Elevatorrightmotor.getCurrentPosition()));
        }
    }

    public void stop() {
        setVel(0.0);
    }

    public void setVel(double vel) {
        Elevatorleftmotor.setVelocity(vel);
        Elevatorrightmotor.setVelocity(vel);
    }


    public double gotopos(double target, double state) {
        double output = controller.calculate(target, state);
        return output;
    }
}