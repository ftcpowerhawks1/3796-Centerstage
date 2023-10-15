package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

@Config
public class configVar extends Skelly {

    //Intake Items
    public static volatile boolean intakereversed = false;
    public static volatile int intakeVel = 50;
    public static volatile int intakelowpos = 25;
    public static volatile int intakehighpos = 25;

    //Elevator Items
    public static volatile int clawRotationScoringPos = 60;
    public static volatile int elevatorSetThreePosition = 20;
    public static volatile int elevatorSetTwoPosition = 20;
    public static volatile int elevatorSetOnePosition = 20;

    //Vision Items
    public static int cameraCenter = 620;
    public static int apriltagtollerence = 5;
}

