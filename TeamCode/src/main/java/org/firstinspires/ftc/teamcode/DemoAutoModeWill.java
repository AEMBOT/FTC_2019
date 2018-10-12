package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "DemoAutoModeWill", group = "Demo")
public class DemoAutoModeWill extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    //All variables are declared with the modifier "private"
    //The following are the variables that will contain the drive motors and each of their respective data
    private DcMotor MotorLeftBack;
    private DcMotor MotorRightBack;

    //Change based on how many times
    private double revCount = 0.5;
    private double distance = 288 * revCount;

    public void runOpMode(){

        MotorLeftBack = hardwareMap.get(DcMotor.class, "MotorLB");
        MotorRightBack = hardwareMap.get(DcMotor.class, "MotorRB");
        MotorLeftBack.setDirection(DcMotor.Direction.REVERSE);


        //All drive code goes here
        MotorLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        //!!! 1 revolution(288 ticks) equals 1ft
        MotorLeftBack.setTargetPosition((int)distance);
        MotorRightBack.setTargetPosition((int)distance);

        MotorLeftBack.setPower(1);
        MotorRightBack.setPower(1);


        while (opModeIsActive() && MotorLeftBack.isBusy() && MotorRightBack.isBusy()) {
            idle();
        }

        MotorRightBack.setPower(0);
        MotorLeftBack.setPower(0);
    }
}