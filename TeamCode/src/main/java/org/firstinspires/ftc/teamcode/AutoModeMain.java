/*
Demo autonomous code created by Will Richards for FTC 2019

The following code is a demo of controlling the robot during the auto period using encoders to control the distance of the robot

!!!IMPORTANT!!! This code is a demo, the comments are excessive on purpose don't comment this much normally but defiantly comment your code
Comment large blocks of code that need description, or anything that needs a description... Be concise
Also if you have alot of unorganized code somewhere insert //region [NAME HERE] at the start and //endregion at the end and it will allow you to collapse that specific section of code
Try to keep use of regions to a minimal
*/

package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "AutoModeMain", group = "Main")
public class AutoModeMain extends LinearOpMode {

    //Create motor variables
    private DcMotor MotorLB;
    private DcMotor MotorRB;
    private DcMotor MotorLiftUp;
    private DcMotor MotorLiftDown;
    private Servo FlipperMotor;

    //Create sensor variables
    private ColorSensor ColorSensor;

    //Declare TurnDirection enum
    public enum TurnDirection {RIGHT, LEFT}

    //Declare LiftDirection enum
    public enum LiftDirection {UP, DOWN}

    //Declare boolean hasFlipped for flipper
    public boolean hasFlipped;

    //Creates a constant variable with the value of 288 or one revolution
    private final int REV_TICK_COUNT = 288;

    public void runOpMode() throws InterruptedException {

        //Initializes motor variables
        MotorLB = hardwareMap.get(DcMotor.class, "MotorLB");
        MotorRB = hardwareMap.get(DcMotor.class, "MotorRB");
        MotorLiftUp = hardwareMap.get(DcMotor.class, "LiftUp");
        MotorLiftDown = hardwareMap.get(DcMotor.class, "LiftDown");

        //Initialize flipper servo
        FlipperMotor = hardwareMap.get(Servo.class, "Flipper");

        //Initialize sensor
        ColorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");

        //Creates a local reference to VuforiaBase
        //VuforiaBase vuforiaBase = new VuforiaBase();

        //Sets the left motor to
        MotorLB.setDirection(DcMotor.Direction.REVERSE);

        //Declare speed variables
        double motorSpeed = .5;
        double turnSpeed = motorSpeed * (2 / 3);

        hasFlipped = false;


        //Waits until the start button is pressed
        waitForStart();

        //Robot landing code will go here

        LandRobot(1); //Change number of rotations later (1 is just an estimate)

        //Drives straight forward roughly 20 inches
        DriveToDistance(20, motorSpeed);

        //Waits 2 seconds
        //Thread.sleep(2000);

        //Turns 60 degrees to the right
        TurnToDegrees(60, turnSpeed, TurnDirection.RIGHT);

        //Waits 2 seconds
        //Thread.sleep(2000);

        //Drives directly forward 33 inches
        //This part is inconsistent; Work in progress
        DriveToDistance(33, motorSpeed);

        //Waits 0.5 seconds
        Thread.sleep(500);

        //Turns to the left 40 degrees
        TurnToDegrees(40, turnSpeed, TurnDirection.RIGHT);

        //Backwards 18 inches
        DriveToDistance(18, -motorSpeed);

        //
        Thread.sleep(500);

        //See if color sensor senses yellow (gold) here
        if (SenseYellow() && !hasFlipped) {
            RunFlipper(0.8);
            //hasFlipped = true;
        }

        //pause to check for yellow
        Thread.sleep(500);

        //Backwards 14.5 Inches
        DriveToDistance(14.5, -motorSpeed);

        //Sense if yellow
        if (SenseYellow() && !hasFlipped) {
            RunFlipper(0.8);
            //hasFlipped = true;
        }

        //pause to check for yellow
        Thread.sleep(500);

        //Backwards 14.5 Inches
        DriveToDistance(14.5, -motorSpeed);

        //Sense if yellow
        if (SenseYellow() && !hasFlipped) {
            RunFlipper(0.8);
            //hasFlipped = true;
        }

        //Parking code will start here
        //Drive forward 2 inches
        DriveToDistance(2, motorSpeed);

        //Turn right 270 degrees (so as to not hit other samples of gold/silver)
        TurnToDegrees(270, motorSpeed, TurnDirection.RIGHT);

        //Drive 6 inches forward and park on crater
        DriveToDistance(6, motorSpeed);


        //pause to check for yellow
        //Thread.sleep(1000);

        //Reset has flipped value
        //hasFlipped = false;
        //endregion

        /*
        //Drive backward 24 inches
        DriveToDistance(24, -motorSpeed);

        //Turn right 75 degrees
        TurnToDegrees(75, motorSpeed, TurnDirection.RIGHT);

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
        TurnToDegrees(45, motorSpeed, TurnDirection.LEFT);

        //Drives Backwards 18 Inches
        DriveToDistance(18, -motorSpeed);

        //Turns 90 degrees Left
        TurnToDegrees(90, motorSpeed, TurnDirection.LEFT);

        //Forward 96 inches
        DriveToDistance(96, motorSpeed);

        //Claim Code will go here
        */
    }


    //This method can be called when you want the robot to turn to a set degrees value at a certain speed and direction
    private void TurnToDegrees(double degrees, double motorSpeed, TurnDirection turnDirection){
        //Converts degrees into ticks
        final double CONVERSION_FACTOR = 2.5;
        final double ticksToDegrees = 90 / 85;

        //Multiplies the number of degrees by the conversion factor to get the number of ticks for the specified degrees
        double ticks = (degrees * CONVERSION_FACTOR);
        double turnDegrees = ticks * ticksToDegrees;

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }

        //Resets encoder values
        MotorLB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the encoders back up for accepting input
        MotorLB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Checks if it is meant to turn right
        if(turnDirection == TurnDirection.RIGHT)
        {
            //Sets the number of ticks the motor needs to move
            MotorLB.setTargetPosition((int)turnDegrees );
            MotorRB.setTargetPosition(-(int)turnDegrees);

            //It then sets the power of the motors accordingly to turn the robot to the right
            MotorLB.setPower(motorSpeed);
            MotorRB.setPower(-motorSpeed);
        }

        //If false turn left
        else {

            //Sets the number of ticks the motor needs to turn left
            MotorLB.setTargetPosition(-(int)turnDegrees );
            MotorRB.setTargetPosition((int)turnDegrees);

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
    private void DriveToDistance(double distance, double motorSpeed){

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }

        //! 1 rev is 12.56 inches !
        double totalDistance = (REV_TICK_COUNT / 12.566) * distance;


        //Stops and resets encoders
        MotorLB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Then it switches the encoders in a mode where it will drive the specified distance no matter what
        MotorLB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Will check if the robot should be in reverse if so set encoder target accordingly
        if(motorSpeed < 0){

            //Sets the number of ticks to negative to allow for reverse
            MotorLB.setTargetPosition(-(int)totalDistance);
            MotorRB.setTargetPosition(-(int)totalDistance);
        }

        //Will run if robot is set to move forward
        else{

            //Roughly the same as the code above but this will move forward
            MotorLB.setTargetPosition((int)totalDistance);
            MotorRB.setTargetPosition((int)totalDistance);
        }


        //It then sets the power to the motors to allow for forward movement
        MotorLB.setPower(motorSpeed);
        MotorRB.setPower(motorSpeed);

        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() && MotorLB.isBusy() && MotorRB.isBusy()) {
            idle();
        }

        //After it has moved the desired amount brake the wheels
        MotorRB.setPower(0);
        MotorLB.setPower(0);
    }

    //This method will use the color sensor to sense if the ball is yellow or white and returns a boolean value accordingly
    private boolean SenseYellow(){
        boolean isYellow;

        //The color sensed was white
        if(ColorSensor.blue() > 100 && ColorSensor.red() > 100 && ColorSensor.green() > 100){
            isYellow = false;
            //return isYellow; Why are there two of these? - Zane
        }

        //The color sensed was yellow
        else if(ColorSensor.blue() < 100 && ColorSensor.blue() > 50){
            isYellow = true;
        }

        //It did not sense a valid color
        else{
            isYellow = false;
        }
        return isYellow;
    }
    private void RunFlipper(double position) {
        //Set flipper position to 90 degrees
        FlipperMotor.setPosition(position);

        //Set hasFlipped variable to true
        hasFlipped = true;

        //Reset flipper motor
        FlipperMotor.setPosition(0);
    }
    private void LandRobot(double rotations) {
        double ticks = rotations * REV_TICK_COUNT;

        //Reset encoders
        MotorLiftDown.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorLiftUp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Make it so motors run to position
        MotorLiftDown.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorLiftUp.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Set motor target position
        MotorLiftDown.setTargetPosition((int)ticks);
        MotorLiftUp.setTargetPosition((int)ticks);

        //Run motors until they reach target positon (ticks)
        MotorLiftDown.setPower(0.5);
        MotorLiftUp.setPower(0.5);

        while(MotorLiftDown.isBusy() && MotorLiftUp.isBusy() && opModeIsActive()) {
            idle();
        }
    }
}
