/*
Code Stolen From Will Richards by Troy Lopez for the Delorean bot.
As of 10/25 code is written for babybot motor lay-out. This will need to change.
This is currently skeleton code since we need to have motors defined before we can write
code for that and frankly as of 10/25 i have no ide how to do that.
 */
package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "DeloreanAutomodeMain", group = "Main")
public class DeLoreanAutomodeMain extends LinearOpMode {

    //Variables created for the two back motors
    private DcMotor MotorLeftBack;
    private DcMotor MotorRightBack;
    private DcMotor MotorLeftFront;
    private DcMotor MotorRightFront;

    private ColorSensor ColorSensor;

    public enum TurnDirection {RIGHT, LEFT}
    public enum StrafeDirection {RIGHT, LEFT}

    //Creates a constant variable with the value of 288 or one revolution
    private final int REV_TICK_COUNT = 288;

    public void runOpMode() throws InterruptedException {

        //Initializes motor variables
        //We'll need to Int the 4 new motors for landing and full wheel drive
        MotorLeftBack = hardwareMap.get(DcMotor.class, "MotorLB");
        MotorRightBack = hardwareMap.get(DcMotor.class, "MotorRB");
        MotorLeftFront = hardwareMap.get(DcMotor.class, "MotorLF");
        MotorRightFront = hardwareMap.get(DcMotor.class, "MotorRF");

        ColorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");

        //Creates a local reference to VuforiaBase
        //VuforiaBase vuforiaBase = new VuforiaBase();

        //Sets the motor to reverse
        MotorLeftBack.setDirection(DcMotor.Direction.REVERSE);
        MotorLeftFront.setDirection(DcMotor.Direction.REVERSE);

        //Declare motor speed variable
        double motorSpeed = 1;

        //Defines turn speed to be half of regular motor speed (may change)
        double turnSpeed = motorSpeed / 2;

        //Waits until the start button is pressed
        waitForStart();

        //STARTS

        //Robot landing code will go here (drop wheel set 1, then Wheel set 2)

        //Strafe 2 inches left

        //Drives up to left cube set.
        DriveToDistance (36, motorSpeed);

        //Color sense for both sensors, L & R

        /*Start if situation
        // L'wood: If either sensor is yellow, flip corresponding flipper out and back

        //If neither sensor detects yellow, move right
        //Strafe right 15
        //Right flipper flip+back
        //End If situation
        */

        //Claim Code here

        /* Another complicated if function-thing, Courtesy L'wood
        If the cube was in position 1 or 2 from the left, strafe right 8.
        If not, strafe left 8
         */

        //Approach claim site
        DriveToDistance(20, motorSpeed);

        //Drop Marker (motor run, then back)

        //strafe right 15

        //this will need to rotate across the center axis
        TurnToDegrees(135, turnSpeed, AutoModeMain.TurnDirection.RIGHT);

        //drives towards crater straight
        DriveToDistance(40, motorSpeed);

        //Turns 45 RIGHT
        TurnToDegrees(45, turnSpeed, AutoModeMain.TurnDirection.RIGHT);

        //25 Straight
        DriveToDistance(25, motorSpeed);

        //Turns 90 left
        TurnToDegrees(90, motorSpeed, AutoModeMain.TurnDirection.LEFT);

    }

    //Strafe The Robot In The Specified Direction
    private void Strafe(double distance, double motorSpeed, StrafeDirection strafeDirection){

        //Converts degrees into ticks
        double totalDistance = (REV_TICK_COUNT / 12.566) * distance;

        //Resets encoder values
        MotorLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the encoders back up for accepting input
        MotorLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorLeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Checks if it is meant to turn right
        if(strafeDirection == StrafeDirection.RIGHT)
        {
            //Sets the number of ticks the motor needs to move
            MotorLeftBack.setTargetPosition((int)distance );
            MotorRightBack.setTargetPosition(-(int)distance);
            MotorLeftFront.setTargetPosition(-(int)distance );
            MotorRightFront.setTargetPosition((int)distance);

            //It then sets the power of the motors accordingly to turn the robot to the right
            MotorLeftBack.setPower(motorSpeed);
            MotorRightBack.setPower(-motorSpeed);
            MotorLeftFront.setPower(-motorSpeed);
            MotorRightFront.setPower(motorSpeed);
        }

        //If false turn left
        else {

            //Sets the number of ticks the motor needs to turn left
            MotorLeftBack.setTargetPosition((int)distance);
            MotorRightBack.setTargetPosition(-(int)distance);
            MotorLeftFront.setTargetPosition(-(int)distance);
            MotorRightFront.setTargetPosition((int)distance);

            //It then sets the power of the motors to turn left
            MotorLeftBack.setPower(motorSpeed);
            MotorRightBack.setPower(-motorSpeed);
            MotorLeftFront.setPower(-motorSpeed);
            MotorRightFront.setPower(motorSpeed);

        }


        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() && MotorLeftBack.isBusy() && MotorRightBack.isBusy() && MotorLeftFront.isBusy() && MotorRightFront.isBusy()) {
            idle();
        }

        //After it has moved the desired amount brake the wheels
        MotorRightBack.setPower(0);
        MotorLeftBack.setPower(0);
        MotorRightFront.setPower(0);
        MotorLeftFront.setPower(0);
    }

    //This method is called when you want the robot to turn on the spot in a specified direction
    private void TurnOnTheSpot(double degrees, double motorSpeed, TurnDirection turnDirection){

        //Converts degrees into ticks
        final double CONVERSION_FACTOR = 5;
        final double ticksToDegrees = 90 / 85;

        //Multiplies the number of degrees by the conversion factor to get the number of ticks for the specified degrees
        double ticks = (degrees * CONVERSION_FACTOR);
        double turnDegrees = ticks * ticksToDegrees;

        //Resets encoder values
        MotorLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the encoders back up for accepting input
        MotorLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorLeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Checks if it is meant to turn right
        if(turnDirection == TurnDirection.RIGHT)
        {
            //Sets the number of ticks the motor needs to move
            MotorLeftBack.setTargetPosition((int)turnDegrees );
            MotorRightBack.setTargetPosition(-(int)turnDegrees);
            MotorLeftFront.setTargetPosition((int)turnDegrees );
            MotorRightFront.setTargetPosition(-(int)turnDegrees);

            //It then sets the power of the motors accordingly to turn the robot to the right
            MotorLeftBack.setPower(motorSpeed);
            MotorRightBack.setPower(-motorSpeed);
            MotorLeftFront.setPower(motorSpeed);
            MotorRightFront.setPower(-motorSpeed);
        }

        //If false turn left
        else {

            //Sets the number of ticks the motor needs to turn left
            MotorLeftBack.setTargetPosition(-(int)turnDegrees);
            MotorRightBack.setTargetPosition((int)turnDegrees);
            MotorLeftFront.setTargetPosition(-(int)turnDegrees);
            MotorRightFront.setTargetPosition((int)turnDegrees);

            //It then sets the power of the motors to turn left
            MotorLeftBack.setPower(-motorSpeed);
            MotorRightBack.setPower(motorSpeed);
            MotorLeftFront.setPower(-motorSpeed);
            MotorRightFront.setPower(motorSpeed);
        }


        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() && MotorLeftBack.isBusy() && MotorRightBack.isBusy() && MotorLeftFront.isBusy() && MotorRightFront.isBusy()) {
            idle();
        }

        //After it has moved the desired amount brake the wheels
        MotorRightBack.setPower(0);
        MotorLeftBack.setPower(0);
        MotorRightFront.setPower(0);
        MotorLeftFront.setPower(0);
    }

    //This method can be called when you want the robot to turn to a set degrees value at a certain speed and direction
    private void TurnToDegrees(double degrees, double motorSpeed, AutoModeMain.TurnDirection turnDirection){
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
        MotorLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the encoders back up for accepting input
        MotorLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Checks if it is meant to turn right
        if(turnDirection == AutoModeMain.TurnDirection.RIGHT)
        {
            //Sets the number of ticks the motor needs to move
            MotorLeftBack.setTargetPosition((int)turnDegrees );
            MotorRightBack.setTargetPosition(-(int)turnDegrees);

            //It then sets the power of the motors accordingly to turn the robot to the right
            MotorLeftBack.setPower(motorSpeed);
            MotorRightBack.setPower(-motorSpeed);
        }

        //If false turn left
        else {

            //Sets the number of ticks the motor needs to turn left
            MotorLeftBack.setTargetPosition(-(int)turnDegrees );
            MotorRightBack.setTargetPosition((int)turnDegrees);

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
        MotorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Then it switches the encoders in a mode where it will drive the specified distance no matter what
        MotorLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorLeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Will check if the robot should be in reverse if so set encoder target accordingly
        if(motorSpeed < 0){

            //Sets the number of ticks to negative to allow for reverse
            MotorLeftBack.setTargetPosition(-(int)totalDistance);
            MotorRightBack.setTargetPosition(-(int)totalDistance);
            MotorLeftFront.setTargetPosition(-(int)totalDistance);
            MotorRightFront.setTargetPosition(-(int)totalDistance);
        }

        //Will run if robot is set to move forward
        else{

            //Roughly the same as the code above but this will move forward
            MotorLeftBack.setTargetPosition((int)totalDistance);
            MotorRightBack.setTargetPosition((int)totalDistance);
            MotorLeftFront.setTargetPosition((int)totalDistance);
            MotorRightFront.setTargetPosition((int)totalDistance);
        }


        //It then sets the power to the motors to allow for forward movement
        MotorLeftBack.setPower(motorSpeed);
        MotorRightBack.setPower(motorSpeed);
        MotorLeftFront.setPower(motorSpeed);
        MotorRightFront.setPower(motorSpeed);

        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() && MotorLeftBack.isBusy() && MotorRightBack.isBusy()) {
            idle();
        }

        //After it has moved the desired amount brake the wheels
        MotorRightBack.setPower(0);
        MotorLeftBack.setPower(0);
        MotorLeftFront.setPower(0);
        MotorRightFront.setPower(0);
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
