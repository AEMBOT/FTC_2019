package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name = "DeLoreanTeleOpModeMain", group = "DeLorean")
public class DeLoreanTeleOpModeMain extends LinearOpMode {
    //Declare DC motor variables
    private DcMotor BackLeft;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor MotorWheelR;
    private DcMotor MotorWheelL;

    //Declare any other motors (servos, etc.)

    //Declare sensors
    //private ColorSensor ColorSensorR;
    //private ColorSensor ColorSensorL;

    //Creates constant that contains the number of ticks in a full rotation of a REV motor
    private final int REV_TICK_COUNT = 288;

    public void runOpMode() {
        //Initialize DC motor variables
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");

        //Initialize any other motors here

        //Initialize color sensors
        //ColorSensorR = hardwareMap.get(ColorSensor.class, "ColorSensorR");
        //ColorSensorL = hardwareMap.get(ColorSensor.class, "ColorSensorL");

        //Reverse left motors so forward is the same for all motors
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);

        //Wait for start button to be pressed
        waitForStart();

         while (opModeIsActive()) {
             //Set all motors' power to y-value of left/right stick accordingly
             BackRight.setPower(gamepad1.right_stick_y);
             BackLeft.setPower(gamepad1.left_stick_y);
             FrontRight.setPower(gamepad1.right_stick_y);
             FrontLeft.setPower(gamepad1.left_stick_y);

             if(gamepad1.dpad_left) {
                 Strafe(1, DeLoreanAutomodeMain.StrafeDirection.LEFT);
             }
             else if(gamepad1.dpad_right) {
                 Strafe(1, DeLoreanAutomodeMain.StrafeDirection.RIGHT);
             }
             else {
                 BackRight.setPower(0);
                 BackLeft.setPower(0);
                 FrontRight.setPower(0);
                 FrontLeft.setPower(0);
             }
         }
    }
    private void Strafe(double motorSpeed, DeLoreanAutomodeMain.StrafeDirection strafeDirection){

        //Converts degrees into ticks
        //double totalDistance = (REV_TICK_COUNT / 12.566) * distance;
        /*
        //Resets encoder values
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the encoders back up for accepting input
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        */
        //Checks if it is meant to turn right
        if(strafeDirection == DeLoreanAutomodeMain.StrafeDirection.RIGHT)
        {
            /*
            //Sets the number of ticks the motor needs to move
            BackLeft.setTargetPosition((int)distance);
            BackRight.setTargetPosition(-(int)distance);
            FrontLeft.setTargetPosition(-(int)distance);
            FrontRight.setTargetPosition((int)distance);
            */

            //It then sets the power of the motors accordingly to turn the robot to the right
            BackLeft.setPower(motorSpeed);
            BackRight.setPower(-motorSpeed);
            FrontLeft.setPower(-motorSpeed);
            FrontRight.setPower(motorSpeed);
        }

        //If false turn left
        else {
            /*
            //Sets the number of ticks the motor needs to turn left
            BackLeft.setTargetPosition((int)distance);
            BackRight.setTargetPosition(-(int)distance);
            FrontLeft.setTargetPosition(-(int)distance);
            FrontRight.setTargetPosition((int)distance);
            */

            //It then sets the power of the motors to turn left
            BackLeft.setPower(motorSpeed);
            BackRight.setPower(-motorSpeed);
            FrontLeft.setPower(-motorSpeed);
            FrontRight.setPower(motorSpeed);

        }


        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        /*
        while (opModeIsActive() && BackLeft.isBusy() && BackRight.isBusy() && FrontLeft.isBusy() && FrontRight.isBusy()) {

            idle();
        }
        */

        /*
        //After it has moved the desired amount brake the wheels
        BackRight.setPower(0);
        BackLeft.setPower(0);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        */
    }
}
