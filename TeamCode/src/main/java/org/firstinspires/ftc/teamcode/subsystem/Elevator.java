package org.firstinspires.ftc.teamcode.subsystem;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.configVar;

public class Elevator extends SubsystemBase {
    private final HardwareMap hardwareMap;
    private final DcMotorEx elevatorLeftMotor;
    private final DcMotorEx elevatorRightMotor;

    private final ServoEx clawRotationChanger;
    private final ServoEx clawServo;

    public TouchSensor touch;

    public BasicPID controller;

    public boolean scored = false;

    public Elevator(HardwareMap hardwareMap, double Kp, double Ki, double Kd) {
        this.hardwareMap = hardwareMap;

        elevatorLeftMotor = hardwareMap.get(DcMotorEx.class, "Left Elevator");
        elevatorRightMotor = hardwareMap.get(DcMotorEx.class, "Right Elevator");

        elevatorLeftMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        elevatorRightMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        clawRotationChanger = hardwareMap.get(ServoEx.class, "Claw Spin Servo");
        clawServo = hardwareMap.get(ServoEx.class, "Claw Servo");

        touch = hardwareMap.get(TouchSensor.class, "Limit");



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
        scored = false;
        while (!scored) {
            elevatorLeftMotor.setPower(goToPos(100, elevatorLeftMotor.getCurrentPosition()));
            elevatorRightMotor.setPower(goToPos(100, elevatorRightMotor.getCurrentPosition()));
            if (clawRotationChanger.getPosition() - configVar.clawRotationScoringPos > 2) {
                clawtoscoringpos();
            }
        }
        scored = false;
    }
    public void elevatorRunToSet2() {
        indexpixel();
        scored = false;
        while (!scored) {
            elevatorLeftMotor.setPower(goToPos(configVar.elevatorSetTwoPosition, elevatorLeftMotor.getCurrentPosition()));
            elevatorRightMotor.setPower(goToPos(configVar.elevatorSetTwoPosition, elevatorRightMotor.getCurrentPosition()));
            if (clawRotationChanger.getPosition() - configVar.clawRotationScoringPos > 2) {
                clawtoscoringpos();
            }
        }
        scored = false;
    }
    public void elevatorRunToSet1() {
        indexpixel();
        scored = false;
        while (!scored) {
            elevatorLeftMotor.setPower(goToPos(configVar.elevatorSetOnePosition, elevatorLeftMotor.getCurrentPosition()));
            elevatorRightMotor.setPower(goToPos(configVar.elevatorSetOnePosition, elevatorRightMotor.getCurrentPosition()));
            if (clawRotationChanger.getPosition() - configVar.clawRotationScoringPos > 2) {
                clawtoscoringpos();
            }
        }
        scored = false;
    }
    public void elevatorDown() {
        clawtoindexpos();
        elevatorLeftMotor.setPower(0);
        elevatorRightMotor.setPower(0);
    }
    public void stop() {
        elevatorLeftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        elevatorRightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        setPower(0.0);
    }
    public void setPower(double Power) {
        elevatorLeftMotor.setPower(Power);
        elevatorRightMotor.setPower(Power);
    }
    /** Set claw ready to score the pixel **/
    public void clawtoscoringpos() {
        clawRotationChanger.turnToAngle(configVar.clawRotationScoringPos);
    }
    /** Set claw ready to pick up a pixel from the intake **/
    public void clawtoindexpos() {
        clawRotationChanger.turnToAngle(0);
        openclaw();
    }
    /** Open the claw **/
    public void openclaw() {
        clawServo.turnToAngle(120);
        scored = true;
    }
    /** Close the claw **/
    public void indexpixel() {
        clawServo.turnToAngle(0);
    }
    /** Recheck we are in the right position and then score on the board. **/
    public void scoreOnBoard() {
        clawRotationChanger.turnToAngle(configVar.clawRotationScoringPos);
        openclaw();
        scored = true;
    }
    public double goToPos(double target, double state) {
        return controller.calculate(target, state);
    }
}