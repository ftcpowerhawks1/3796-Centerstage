package org.firstinspires.ftc.teamcode.subsystem;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.configVar;


public class Elevator extends SubsystemBase {
    private final HardwareMap hardwareMap;
    private final DcMotorEx Elevatorleftmotor;
    private final DcMotorEx Elevatorrightmotor;

    private final ServoEx clawrotationchanger;
    private final ServoEx clawservo;

    public BasicPID controller;

    public boolean scored = false;

    public Elevator(HardwareMap hardwareMap, double Kp, double Ki, double Kd) {
        this.hardwareMap = hardwareMap;

        Elevatorleftmotor = hardwareMap.get(DcMotorEx.class, "Left Elevator");
        Elevatorrightmotor = hardwareMap.get(DcMotorEx.class, "Right Elevator");

        Elevatorleftmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Elevatorrightmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        clawrotationchanger = hardwareMap.get(ServoEx.class, "Claw Spin Servo");
        clawservo = hardwareMap.get(ServoEx.class, "Claw Servo");


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
        indexpixel();
        while (!scored) {
            Elevatorleftmotor.setPower(gotopos(100, Elevatorleftmotor.getCurrentPosition()));
            Elevatorrightmotor.setPower(gotopos(100, Elevatorrightmotor.getCurrentPosition()));
            if (clawrotationchanger.getPosition() - configVar.clawRotationScoringPos > 2) {
                clawtoscoringpos();
            }
        }
        scored = false;
    }
    public void elevatorRunToSet2() {
        indexpixel();
        while (!scored) {
            Elevatorleftmotor.setPower(gotopos(50, Elevatorleftmotor.getCurrentPosition()));
            Elevatorrightmotor.setPower(gotopos(50, Elevatorrightmotor.getCurrentPosition()));
            if (clawrotationchanger.getPosition() - configVar.clawRotationScoringPos > 2) {
                clawtoscoringpos();
            }
        }
        scored = false;
    }
    public void elevatorRunToSet1() {
        indexpixel();
        while (!scored) {
            Elevatorleftmotor.setPower(gotopos(30, Elevatorleftmotor.getCurrentPosition()));
            Elevatorrightmotor.setPower(gotopos(30, Elevatorrightmotor.getCurrentPosition()));
            if (clawrotationchanger.getPosition() - configVar.clawRotationScoringPos > 2) {
                clawtoscoringpos();
            }
        }
        scored = false;
    }

    public void elevatordown() {
        clawtoindexpos();
        while (5 < Math.abs(Elevatorleftmotor.getCurrentPosition() - 0)) {
            Elevatorleftmotor.setPower(gotopos(0, Elevatorleftmotor.getCurrentPosition()));
            Elevatorrightmotor.setPower(gotopos(0, Elevatorrightmotor.getCurrentPosition()));
        }
    }

    public void stop() {
        Elevatorleftmotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        Elevatorrightmotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        setPower(0.0);
    }

    public void setPower(double Power) {
        Elevatorleftmotor.setPower(Power);
        Elevatorrightmotor.setPower(Power);
    }

    /** Set claw ready to score the pixel **/

    public void clawtoscoringpos() {
        clawrotationchanger.turnToAngle(configVar.clawRotationScoringPos);
    }

    /** Set claw ready to pick up a pixel from the intake **/

    public void clawtoindexpos() {
        clawrotationchanger.turnToAngle(0);
        openclaw();
    }

    /** Open the claw **/

    public void openclaw() {
        clawservo.turnToAngle(120);
    }

    /** Close the claw **/

    public void indexpixel() {
        clawservo.turnToAngle(0);
    }

    /** Recheck we are in the right position and then score on the board. **/

    public void scoreonboard() {
        clawrotationchanger.turnToAngle(configVar.clawRotationScoringPos);
        openclaw();
        scored = true;
    }

    public double gotopos(double target, double state) {
        double output = controller.calculate(target, state);
        return output;
    }
}