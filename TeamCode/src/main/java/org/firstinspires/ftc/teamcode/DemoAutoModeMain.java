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

@Autonomous(name = "AutoModeMain", group = "Demo")
public class DemoAutoModeMain extends LinearOpMode {

    //Variables created for the two back motors
    private DcMotor MotorLeftBack;
    private DcMotor MotorRightBack;

    private ColorSensor ColorSensor;

    public enum TurnDirection {RIGHT, LEFT}

    //Creates a constant variable with the value of 288 or one revolution
    private final int REV_TICK_COUNT = 288;

    public void runOpMode() throws InterruptedException {

        //Initializes motor variables
        MotorLeftBack = hardwareMap.get(DcMotor.class, "MotorLB");
        MotorRightBack = hardwareMap.get(DcMotor.class, "MotorRB");
        ColorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");

        //Sets the left motor to
        MotorLeftBack.setDirection(DcMotor.Direction.REVERSE);

        //Declare speed variable (double)
        double motorSpeed = 0.5;

        boolean hasFlipped = false;

        //Waits until the start button is pressed
        waitForStart();

        //Robot landing code will go here

        //Drives straight forward roughly 20 inches
        DriveToDistance(20, motorSpeed);

        //Waits 2 seconds
        Thread.sleep(2000);

        //Turns 60 degrees to the right
        TurnToDegrees(60, motorSpeed / 2, TurnDirection.RIGHT);

        //Waits 2 seconds
        Thread.sleep(2000);

        //Drives directly forward 34 inches
        DriveToDistance(34,motorSpeed);

        //Waits 2 seconds
        Thread.sleep(2000);

        //Turns to the left 30 degrees
        TurnToDegrees(30, motorSpeed , TurnDirection.RIGHT);

        //Backwards 18 degrees
        DriveToDistance(18, -motorSpeed);

        //See if color sensor senses yellow (gold) here
        if(SenseYellow() && !hasFlipped){
            //Flip Code
            hasFlipped = true;
        }

        //Backwards 14.5 Inches
        DriveToDistance(14.5, -motorSpeed);

        //Sense if yellow
        if(SenseYellow() && !hasFlipped){
            //Flip Code here
            hasFlipped = true;
        }

        //Backwards 14.5 Inches
        DriveToDistance(14.5, -motorSpeed);

        //Sense if yellow
        if(SenseYellow() && !hasFlipped){
            //Flip Code here
            hasFlipped = true;
        }

        //Reset has flipped value
        hasFlipped = false;
        //endregion

        //Drive Forward 24 inches
        DriveToDistance(24, motorSpeed);
        //Turn right 30 degrees
        TurnToDegrees(30, motorSpeed, TurnDirection.RIGHT);
        //Drive Forward 26 inches
        DriveToDistance(26, motorSpeed);


        //Sense Yellow
        if(SenseYellow() && !hasFlipped){
            //Flip Code here
            hasFlipped = true;
        }

        //Drives to next object, 14.5 inches
        DriveToDistance(14.5, motorSpeed);

        //Sense Yellow
        if(SenseYellow() && !hasFlipped){
            //Flipper code
            hasFlipped = true;
        }
        //Drives to next object, 14.5 inches
        DriveToDistance(14.5, motorSpeed);

        //Sense Yellow
        if(SenseYellow() && !hasFlipped){
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
    }


    //This method can be called when you want the robot to turn to a set degrees value at a certain speed and direction
    private void TurnToDegrees(double degrees, double motorSpeed, TurnDirection turnDirection){
        //Converts degrees into ticks
        final double CONVERSION_FACTOR = 2.5;

        //Multiplies the number of degrees by the conversion factor to get the number of ticks for the specified degrees
        double ticks = (degrees * CONVERSION_FACTOR);

        //Resets encoder values
        MotorLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the encoders back up for accepting input
        MotorLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Checks if it is meant to turn right
        if(turnDirection == TurnDirection.RIGHT)
        {
            //Sets the number of ticks the motor needs to move
            MotorLeftBack.setTargetPosition((int)ticks );
            MotorRightBack.setTargetPosition(-(int)ticks);

            //It then sets the power of the motors accordingly to turn the robot to the right
            MotorLeftBack.setPower(motorSpeed);
            MotorRightBack.setPower(-motorSpeed);
        }

        //If false turn left
        else {

            //Sets the number of ticks the motor needs to turn left
            MotorLeftBack.setTargetPosition(-(int)ticks );
            MotorRightBack.setTargetPosition((int)ticks);

            //It then sets the power of the motors to turn left
            MotorLeftBack.setPower(-motorSpeed);
            MotorRightBack.setPower(motorSpeed);
        }


        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() && MotorLeftBack.isBusy() && MotorRightBack.isBusy()) {
            idle();
        }

        //After it has moved the desired amount brake the wheels
        MotorRightBack.setPower(0);
        MotorLeftBack.setPower(0);

    }

    //This method can be called when you want the robot to drive a certain distance in INCHES at a certain speed
    private void DriveToDistance(double distance, double motorSpeed){

        //! 1 rev is 12.56 inches !
        double totalDistance = (REV_TICK_COUNT / 12.566) * distance;


        //Stops and resets encoders
        MotorLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Then it switches the encoders in a mode where it will drive the specified distance no matter what
        MotorLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Will check if the robot should be in reverse if so set encoder target accordingly
        if(motorSpeed < 0){

            //Sets the number of ticks to negative to allow for reverse
            MotorLeftBack.setTargetPosition(-(int)totalDistance);
            MotorRightBack.setTargetPosition(-(int)totalDistance);
        }

        //Will run if robot is set to move forward
        else{

            //Roughly the same as the code above but this will move forward
            MotorLeftBack.setTargetPosition((int)totalDistance);
            MotorRightBack.setTargetPosition((int)totalDistance);
        }


        //It then sets the power to the motors to allow for forward movement
        MotorLeftBack.setPower(motorSpeed);
        MotorRightBack.setPower(motorSpeed);

        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() && MotorLeftBack.isBusy() && MotorRightBack.isBusy()) {
            idle();
        }

        //After it has moved the desired amount brake the wheels
        MotorRightBack.setPower(0);
        MotorLeftBack.setPower(0);
    }

    //This method will use the color sensor to sense if the ball is yellow or white and returns a boolean value accordingly
    private boolean SenseYellow(){
        boolean isYellow;

        //The color sensed was white
        if(ColorSensor.blue() > 100 && ColorSensor.red() > 100 && ColorSensor.green() > 100){
            isYellow = false;
            return isYellow;
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
}
