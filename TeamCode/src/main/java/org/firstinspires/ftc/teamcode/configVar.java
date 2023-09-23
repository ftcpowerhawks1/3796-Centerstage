package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

@Config
public class configVar extends Skelly {

    //Intake Items
    public static volatile boolean intakereversed = false;
    public static volatile int intakeVel = 50;

    //Elevator Items
    public static volatile int elevatorKS = 0;
    public static volatile int elevatorKG = 0;
    public static volatile int elevatorKV = 0;
    public static volatile int elevatorKA = 0;

    //Vision Items
    public static int cameraCenter = 620;
    public static int apriltagtollerence = 5;
}

