/*
Demo autonomous code created by Will Richards for FTC 2019

The following code is a demo of controlling the robot during the auto period using encoders to control the distance of the robot

!!!IMPORTANT!!! This code is a demo, the comments are excessive on purpose don't comment this much normally but defiantly comment your code
Comment large blocks of code that need description, or anything that needs a description... Be concise
Also if you have alot of unorganized code somewhere insert //region [NAME HERE] at the start and //endregion at the end and it will allow you to collapse that specific section of code
Try to keep use of regions to a minimal
*/

package org.firstinspires.ftc.teamcode;


import android.media.tv.TvInputService;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "AutoModeMain", group = "Main")
public class AutoModeMain extends LinearOpMode {

    private DcMotor MotorLB;
    private DcMotor MotorRB;
    private DcMotor MotorLiftUp;
    private DcMotor MotorLiftDown;
    private Servo FlipperMotor;

    private ColorSensor ColorSensor;

    public enum Direction {RIGHT, LEFT}

    //Declare boolean hasFlipped for flipper
    public boolean hasFlipped;

    //Creates a constant variable with the value of 288 or one revolution
    private final int REV_TICK_COUNT = 288;

    public void runOpMode() {

        MotorLB = hardwareMap.get(DcMotor.class, "MotorLB");
        MotorRB = hardwareMap.get(DcMotor.class, "MotorRB");
        MotorLiftUp = hardwareMap.get(DcMotor.class, "LiftUp");
        MotorLiftDown = hardwareMap.get(DcMotor.class, "LiftDown");
        FlipperMotor = hardwareMap.get(Servo.class, "Flipper");

        ColorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");

        //Creates a local reference to VuforiaBase
        //VuforiaBase vuforiaBase = new VuforiaBase();

        MotorLB.setDirection(DcMotor.Direction.REVERSE);

        double motorSpeed = 1;
        double turnSpeed = 0.75;

        hasFlipped = false;

        waitForStart();

        //LandRobot(5);

        //Should set flipper parallel to robot
        //FlipperMotor.setPosition(0.2);
        FlipperMotor.setPosition(-0.2);

        DriveToDistance(44, motorSpeed);

        //region Removed when AutoMode was simplified
        //TurnToDegrees(60, turnSpeed, Direction.RIGHT);
        //DriveToDistance(33, turnSpeed);
        //TurnToDegrees(45, turnSpeed, Direction.RIGHT);
        //DriveToDistance(16, -motorSpeed);

        //while (opModeIsActive()) {
        //    if(!SenseYellow()) {
        //        DriveToDistance(0.1, motorSpeed);
        //    }
        //    else {
        //        break;
        //    }
        //}
        //endregion

        //region For loop replaced
        /*
        if (SenseYellow() && !hasFlipped) {
            RunFlipper(0.8);
        }

        DriveToDistance(14.5, -motorSpeed);

        if (SenseYellow() && !hasFlipped) {
            RunFlipper(0.8);
        }

        DriveToDistance(14.5, -motorSpeed);

        if (SenseYellow() && !hasFlipped) {
            RunFlipper(0.8);
        }
        */
        //endregion

        //Park on crater
        //DriveToDistance(2, motorSpeed);
        //TurnToDegrees(270, motorSpeed, Direction.RIGHT);
        //DriveToDistance(6, motorSpeed);

        //region Commented Code

        //pause to check for yellow
        //Thread.sleep(1000);

        //Reset has flipped value
        //hasFlipped = false;

        /*
        //Drive backward 24 inches
        DriveToDistance(24, -motorSpeed);

        //Turn right 75 degrees
        TurnToDegrees(75, motorSpeed, Direction.RIGHT);

        //Drive Forward 24 inches
        DriveToDistance(24, motorSpeed);


        //Sense Yellow
        if (SenseYellow() && !hasFlipped) {

            //Flip Code here
            hasFlipped = true;
        }

        //Drives to next object, 14.5 inches
        DriveToDistance(14.5, motorSpeed);

        //Sense Yellow
        if (SenseYellow() && !hasFlipped) {
            //Flipper code
            hasFlipped = true;
        }
        //Drives to next object, 14.5 inches
        DriveToDistance(14.5, motorSpeed);

        //Sense Yellow
        if (SenseYellow() && !hasFlipped) {
            //Flipper code
            hasFlipped = true;
        }

        //Forward 26
        DriveToDistance(26, motorSpeed);

        //Turn Left 45 degrees
        TurnToDegrees(45, motorSpeed, Direction.LEFT);

        //Drives Backwards 18 Inches
        DriveToDistance(18, -motorSpeed);

        //Turns 90 degrees Left
        TurnToDegrees(90, motorSpeed, Direction.LEFT);

        //Forward 96 inches
        DriveToDistance(96, motorSpeed);

        //Claim Code will go here
        */
        //endregion
    }


    //This method can be called when you want the robot to turn to a set degrees value at a certain speed and direction
    private void TurnToDegrees(double degrees, double motorSpeed, Direction turnDirection) {
        //Converts degrees into ticks
        final double CONVERSION_FACTOR = 2.5;
        //final double ticksToDegrees = 85 / 90;

        //Multiplies the number of degrees by the conversion fa-ctor to get the number of ticks for the specified degrees
        double ticks = (degrees * CONVERSION_FACTOR);
        //double turnDegrees = ticks * ticksToDegrees;

        //Waits for 50 milliseconds
        sleep(50);


        //Resets encoder values
        MotorLB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the encoders back up for accepting input
        MotorLB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Checks if it is meant to turn right
        if(turnDirection == Direction.RIGHT)
        {
            //Sets the number of ticks the motor needs to move
            MotorLB.setTargetPosition((int)ticks);
            MotorRB.setTargetPosition(-(int)ticks);

            //It then sets the power of the motors accordingly to turn the robot to the right
            MotorLB.setPower(motorSpeed);
            MotorRB.setPower(-motorSpeed);
        }

        //If false turn left
        else {

            //Sets the number of ticks the motor needs to turn left
            MotorLB.setTargetPosition(-(int)ticks);
            MotorRB.setTargetPosition((int)ticks);

            //It then sets the power of the motors to turn left
            MotorLB.setPower(-motorSpeed);
            MotorRB.setPower(motorSpeed);
        }


        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() && MotorLB.isBusy() && MotorRB.isBusy()) {
            idle();
        }

        //After it has moved the desired amount brake the wheels
        MotorRB.setPower(0);
        MotorLB.setPower(0);

    }

    //This method can be called when you want the robot to drive a certain distance in INCHES at a certain speed
    private void DriveToDistance(double distance, double motorSpeed) {
        //1 rev is 12.566 inches
        double totalDistance = (REV_TICK_COUNT / 12.566) * distance;

            //Reset motor encoders
            MotorLB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            MotorRB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            MotorLB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            MotorRB.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        if(motorSpeed < 0){
            //Negative for reversing
            MotorLB.setTargetPosition(-(int)totalDistance);
            MotorRB.setTargetPosition( -(int)totalDistance);
            for(int i = 0; i < 5; i++){
                sleep(50);
                MotorLB.setPower(MotorLB.getPower() +  0.1);
                MotorRB.setPower(MotorRB.getPower() +  0.1);
            }
        }
        else {
            MotorLB.setTargetPosition((int)totalDistance);
            MotorRB.setTargetPosition((int)totalDistance);
        }

        for(int i = 0; i < 5; i++){
            sleep(50);
            MotorLB.setPower(MotorLB.getPower() + 0.1);
            MotorRB.setPower(MotorRB.getPower() + 0.1);
        }

        while (opModeIsActive() && MotorLB.isBusy() && MotorRB.isBusy()) { //Do we need opModeIsActive? It already invokes the idle() function
            idle();
        }

        //After it has moved the desired amount brake the wheels
        MotorRB.setPower(0);
        MotorLB.setPower(0);
    }

    private boolean SenseYellow(){
        boolean isYellow;

        telemetry.addData("Blue: ", ColorSensor.blue());
        telemetry.addData("Green: ", ColorSensor.green());
        telemetry.addData("Red: ", ColorSensor.red());
        telemetry.update();
        //Sensed yellow
        if(ColorSensor.blue() - 15 < ColorSensor.red() && ColorSensor.blue() - 15 < ColorSensor.green()){
            isYellow = true;
        }
        //Invalid color
        else{
            isYellow = false;
        }
        return isYellow;
    }

    private void RunFlipper(double position) {
        FlipperMotor.setPosition(position);

        hasFlipped = true;

        //Reset flipper motor to parallel to robot
        FlipperMotor.setPosition(0.3);
    }

    //region LandRobot function

    private void LandRobot(double rotations) {
        double ticks = rotations * REV_TICK_COUNT;

        //Reset encoders
        MotorLiftDown.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorLiftUp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Make it so motors run to position
        MotorLiftDown.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorLiftUp.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Set motor target position
        MotorLiftDown.setTargetPosition(-(int)ticks);
        MotorLiftUp.setTargetPosition((int)ticks);

        //Run motors until they reach target position (ticks)
        MotorLiftDown.setPower(0.5);
        MotorLiftUp.setPower(0.5);

        while(opModeIsActive() && MotorLiftDown.isBusy() && MotorLiftUp.isBusy()) {
            idle();
        }

        MotorLiftDown.setPower(0);
        MotorLiftUp.setPower(0);
    }

    //endregion
}
