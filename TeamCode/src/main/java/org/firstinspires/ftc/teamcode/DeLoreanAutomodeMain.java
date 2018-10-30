/*
Code Stolen From Will Richards by Troy Lopez for the Delorean bot.
Still a work in progress, need more motor definitions.
 */
package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "DeloreanAutomodeMain", group = "DeLorean")
public class DeLoreanAutomodeMain extends LinearOpMode {

    //Variables created for the two back motors
    private DcMotor MotorLB;
    private DcMotor MotorRB;
    private DcMotor MotorLF;
    private DcMotor MotorRF;

    private ColorSensor ColorSensorL;
    private ColorSensor ColorSensorR;

    public enum TurnDirection {RIGHT, LEFT}
    public enum StrafeDirection {RIGHT, LEFT}

    //Creates a constant variable with the value of 288 or one revolution
    private final int REV_TICK_COUNT = 288;

    public void runOpMode() throws InterruptedException {

        //Initializes motor variables
        //We'll need to Int the 4 new motors for landing and full wheel drive
        MotorLB = hardwareMap.get(DcMotor.class, "LeftBack");
        MotorRB = hardwareMap.get(DcMotor.class, "RightBack");
        MotorLF = hardwareMap.get(DcMotor.class, "FrontLeft");
        MotorRF = hardwareMap.get(DcMotor.class, "FrontRight");

        ColorSensorL = hardwareMap.get(ColorSensor.class, "ColorSensorL");
        ColorSensorR = hardwareMap.get(ColorSensor.class, "ColorSensorR");

        //Creates a local reference to VuforiaBase
        //VuforiaBase vuforiaBase = new VuforiaBase();

        //Sets the motor to reverse
        MotorLB.setDirection(DcMotor.Direction.REVERSE);
        MotorLF.setDirection(DcMotor.Direction.REVERSE);

        //Declare motor speed variable
        double motorSpeed = 1;

        //Defines turn speed to be half of regular motor speed (may change)
        double turnSpeed = motorSpeed / 2;

        //Waits until the start button is pressed
        waitForStart();

        //STARTS
        //Flipper R&L refers to the flippers to re-locate the cube and their respective sides
        //Robot landing code will go here (drop each wheel set at different times)

        //Strafe 2 inches left to unhook latch from landwer
        Strafe(2, motorSpeed, StrafeDirection.RIGHT);

        //Drives up to left cube set.
        DriveToDistance (36, motorSpeed);

       // Senses if either is yellow, if neither is then moves
        if(SenseYellow(ColorSensorL)) {
            //flipperL runs

        }
        else if(SenseYellow(ColorSensorR)){
            //FlipperR runs
        }
        else {
            Strafe(15, motorSpeed, StrafeDirection.RIGHT);
            //FlipperR runs
        }



        //Claim Code here?

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
        MotorLB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorLF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the encoders back up for accepting input
        MotorLB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorLF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRF.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Checks if it is meant to turn right
        if(strafeDirection == StrafeDirection.RIGHT)
        {
            //Sets the number of ticks the motor needs to move
            MotorLB.setTargetPosition((int)distance );
            MotorRB.setTargetPosition(-(int)distance);
            MotorLF.setTargetPosition(-(int)distance );
            MotorRF.setTargetPosition((int)distance);

            //It then sets the power of the motors accordingly to turn the robot to the right
            MotorLB.setPower(motorSpeed);
            MotorRB.setPower(-motorSpeed);
            MotorLF.setPower(-motorSpeed);
            MotorRF.setPower(motorSpeed);
        }

        //If false turn left
        else {

            //Sets the number of ticks the motor needs to turn left
            MotorLB.setTargetPosition((int)distance);
            MotorRB.setTargetPosition(-(int)distance);
            MotorLF.setTargetPosition(-(int)distance);
            MotorRF.setTargetPosition((int)distance);

            //It then sets the power of the motors to turn left
            MotorLB.setPower(motorSpeed);
            MotorRB.setPower(-motorSpeed);
            MotorLF.setPower(-motorSpeed);
            MotorRF.setPower(motorSpeed);

        }


        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() && MotorLB.isBusy() && MotorRB.isBusy() && MotorLF.isBusy() && MotorRF.isBusy()) {
            idle();
        }

        //After it has moved the desired amount brake the wheels
        MotorRB.setPower(0);
        MotorLB.setPower(0);
        MotorRF.setPower(0);
        MotorLF.setPower(0);
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
        MotorLB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorLF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the encoders back up for accepting input
        MotorLB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorLF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRF.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Checks if it is meant to turn right
        if(turnDirection == TurnDirection.RIGHT)
        {
            //Sets the number of ticks the motor needs to move
            MotorLB.setTargetPosition((int)turnDegrees );
            MotorRB.setTargetPosition(-(int)turnDegrees);
            MotorLF.setTargetPosition((int)turnDegrees );
            MotorRF.setTargetPosition(-(int)turnDegrees);

            //It then sets the power of the motors accordingly to turn the robot to the right
            MotorLB.setPower(motorSpeed);
            MotorRB.setPower(-motorSpeed);
            MotorLF.setPower(motorSpeed);
            MotorRF.setPower(-motorSpeed);
        }

        //If false turn left
        else {

            //Sets the number of ticks the motor needs to turn left
            MotorLB.setTargetPosition(-(int)turnDegrees);
            MotorRB.setTargetPosition((int)turnDegrees);
            MotorLF.setTargetPosition(-(int)turnDegrees);
            MotorRF.setTargetPosition((int)turnDegrees);

            //It then sets the power of the motors to turn left
            MotorLB.setPower(-motorSpeed);
            MotorRB.setPower(motorSpeed);
            MotorLF.setPower(-motorSpeed);
            MotorRF.setPower(motorSpeed);
        }


        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() && MotorLB.isBusy() && MotorRB.isBusy() && MotorLF.isBusy() && MotorRF.isBusy()) {
            idle();
        }

        //After it has moved the desired amount brake the wheels
        MotorRB.setPower(0);
        MotorLB.setPower(0);
        MotorRF.setPower(0);
        MotorLF.setPower(0);
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
        MotorLB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the encoders back up for accepting input
        MotorLB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Checks if it is meant to turn right
        if(turnDirection == AutoModeMain.TurnDirection.RIGHT)
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

        //! 1 rev is 12.56 inches !
        double totalDistance = (REV_TICK_COUNT / 12.566) * distance;


        //Stops and resets encoders
        MotorLB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorLF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Then it switches the encoders in a mode where it will drive the specified distance no matter what
        MotorLB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorLF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRF.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Will check if the robot should be in reverse if so set encoder target accordingly
        if(motorSpeed < 0){

            //Sets the number of ticks to negative to allow for reverse
            MotorLB.setTargetPosition(-(int)totalDistance);
            MotorRB.setTargetPosition(-(int)totalDistance);
            MotorLF.setTargetPosition(-(int)totalDistance);
            MotorRF.setTargetPosition(-(int)totalDistance);
        }

        //Will run if robot is set to move forward
        else{

            //Roughly the same as the code above but this will move forward
            MotorLB.setTargetPosition((int)totalDistance);
            MotorRB.setTargetPosition((int)totalDistance);
            MotorLF.setTargetPosition((int)totalDistance);
            MotorRF.setTargetPosition((int)totalDistance);
        }


        //It then sets the power to the motors to allow for forward movement
        MotorLB.setPower(motorSpeed);
        MotorRB.setPower(motorSpeed);
        MotorLF.setPower(motorSpeed);
        MotorRF.setPower(motorSpeed);

        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() && MotorLB.isBusy() && MotorRB.isBusy()) {
            idle();
        }

        //After it has moved the desired amount brake the wheels
        MotorRB.setPower(0);
        MotorLB.setPower(0);
        MotorLF.setPower(0);
        MotorRF.setPower(0);
    }

    //This method will use the color sensor to sense if the ball is yellow or white and returns a boolean value accordingly
    private boolean SenseYellow(ColorSensor sensor){
        boolean isYellow;

        //The color sensed was white
        if(sensor.blue() > 100 && sensor.red() > 100 && sensor.green() > 100){
            isYellow = false;
            //return isYellow; | Again, why do we need two "return isYellow;" statements? - Zane
        }

        //The color sensed was yellow
        else if(sensor.blue() < 100 && sensor.blue() > 50){
            isYellow = true;
        }

        //It did not sense a valid color
        else{
            isYellow = false;
        }
        return isYellow;
    }
}
