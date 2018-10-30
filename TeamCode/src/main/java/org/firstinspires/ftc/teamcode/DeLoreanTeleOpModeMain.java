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
    private DcMotor MotorLB;
    private DcMotor MotorRB;
    private DcMotor MotorLF;
    private DcMotor MotorRF;
    private DcMotor MotorWheelR;
    private DcMotor MotorWheelL;

    //Declare any other motors (servos, etc.)

    //Declare sensors
    private ColorSensor ColorSensorR;
    private ColorSensor ColorSensorL;

    //Creates constant that contains the number of ticks in a full rotation of a REV motor
    private final int REV_TICK_COUNT = 288;

    public void runOpMode() {
        //Initialize DC motor variables
        MotorLB = hardwareMap.get(DcMotor.class, "MotorLB");
        MotorRB = hardwareMap.get(DcMotor.class, "MotorRB");
        MotorLF = hardwareMap.get(DcMotor.class, "MotorLF");
        MotorRF = hardwareMap.get(DcMotor.class, "MotorRF");

        //Initialize any other motors here
        //
        //        //Initialize sensors
        ColorSensorR = hardwareMap.get(ColorSensor.class, "ColorSensorR");
        ColorSensorL = hardwareMap.get(ColorSensor.class, "ColorSensorL");

        //Reverse left motors so forward is the same for all motors
        MotorLB.setDirection(DcMotor.Direction.REVERSE);
        MotorLF.setDirection(DcMotor.Direction.REVERSE);

        //Wait for start button to be pressed
        waitForStart();

         while (opModeIsActive()) {
             //Set all motors' power to y-value of left/right stick accordingly
             MotorRB.setPower(gamepad1.right_stick_y);
             MotorLB.setPower(gamepad1.left_stick_y);
             MotorRF.setPower(gamepad1.right_stick_y);
             MotorLF.setPower(gamepad1.left_stick_y);

             if(gamepad1.dpad_left) {
                 Strafe(1, DeLoreanAutomodeMain.StrafeDirection.LEFT);
             }
             else if(gamepad1.dpad_right) {
                 Strafe(1, DeLoreanAutomodeMain.StrafeDirection.RIGHT);
             }
             else {
                 MotorRB.setPower(0);
                 MotorLB.setPower(0);
                 MotorRF.setPower(0);
                 MotorLF.setPower(0);
             }
         }
    }
    private void Strafe(double motorSpeed, DeLoreanAutomodeMain.StrafeDirection strafeDirection){

        //Converts degrees into ticks
        //double totalDistance = (REV_TICK_COUNT / 12.566) * distance;
        /*
        //Resets encoder values
        MotorLB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorLF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the encoders back up for accepting input
        MotorLB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorLF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        */
        //Checks if it is meant to turn right
        if(strafeDirection == DeLoreanAutomodeMain.StrafeDirection.RIGHT)
        {
            /*
            //Sets the number of ticks the motor needs to move
            MotorLB.setTargetPosition((int)distance);
            MotorRB.setTargetPosition(-(int)distance);
            MotorLF.setTargetPosition(-(int)distance);
            MotorRF.setTargetPosition((int)distance);
            */

            //It then sets the power of the motors accordingly to turn the robot to the right
            MotorLB.setPower(motorSpeed);
            MotorRB.setPower(-motorSpeed);
            MotorLF.setPower(-motorSpeed);
            MotorRF.setPower(motorSpeed);
        }

        //If false turn left
        else {
            /*
            //Sets the number of ticks the motor needs to turn left
            MotorLB.setTargetPosition((int)distance);
            MotorRB.setTargetPosition(-(int)distance);
            MotorLF.setTargetPosition(-(int)distance);
            MotorRF.setTargetPosition((int)distance);
            */

            //It then sets the power of the motors to turn left
            MotorLB.setPower(motorSpeed);
            MotorRB.setPower(-motorSpeed);
            MotorLF.setPower(-motorSpeed);
            MotorRF.setPower(motorSpeed);

        }


        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        /*
        while (opModeIsActive() && MotorLB.isBusy() && MotorRB.isBusy() && MotorLF.isBusy() && MotorRF.isBusy()) {

            idle();
        }
        */

        /*
        //After it has moved the desired amount brake the wheels
        MotorRB.setPower(0);
        MotorLB.setPower(0);
        MotorRF.setPower(0);
        MotorLF.setPower(0);
        */
    }
}
