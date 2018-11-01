/*
Code Stolen From Will Richards by Troy Lopez for the Delorean bot.
Still a work in progress, need more motor definitions.
Over commented
Made for 2019 FTC
 */
package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "DeloreanAutomodeMain", group = "DeLorean")
public class DeLoreanAutomodeMain extends LinearOpMode {

    //Variables created for the four motors and wheel tucking motors
    private DcMotor BackLeft;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor FrontBack;
    //Declare wheel tucking motors

    //Declare color sensors

    public enum Direction {RIGHT, LEFT}

    private final int REV_TICK_COUNT = 560;
    private final int LIFT_TICK_COUNT = 1120;

    public void runOpMode() throws InterruptedException {

        //Initializes motor variables
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontBack = hardwareMap.get(DcMotor.class, "FrontRight");

        //ColorSensorL = hardwareMap.get(ColorSensor.class, "ColorSensorL");
        //ColorSensorR = hardwareMap.get(ColorSensor.class, "ColorSensorR");

        //Creates a local reference to VuforiaBase
        //VuforiaBase vuforiaBase = new VuforiaBase();

        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);

        double motorSpeed = 1;

        double turnSpeed = motorSpeed * (2 / 3);

        waitForStart();

        //Flipper R&L refers to the flippers to re-locate the cube and their respective sides
        //Strafe function might not work: consider testing
        //Robot landing code will go here (drop each wheel set at different times)

        Strafe(2, motorSpeed, Direction.RIGHT);

        //Drives up to left cube set.
        DriveToDistance (36, motorSpeed);

        //region
        //If cube is in position 1 or 2 from left, strafe right 8
        //If not, strafe left 8
        //endregion

        //Approaches claim site
        DriveToDistance(20, motorSpeed);

        //Drop team marker (motor run, then back)

        Strafe(15, motorSpeed, Direction.RIGHT);

        TurnOnTheSpot(135, turnSpeed, Direction.RIGHT);

        DriveToDistance(40, motorSpeed);

        TurnToDegrees(45, turnSpeed, Direction.RIGHT);

        DriveToDistance(25, motorSpeed);

        TurnToDegrees(90, motorSpeed, Direction.LEFT);

    }

    //Strafe The Robot In The Specified Direction
    private void Strafe(double distance, double motorSpeed, Direction strafeDirection){

        //Converts degrees into ticks
        double totalDistance = (REV_TICK_COUNT / 12.566) * distance; //totalDistance is never used. Remove it?

        //Resets encoder values
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the encoders back up for accepting input
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Checks if it is meant to turn right
        if(strafeDirection == Direction.RIGHT)
        {
            //Sets the number of ticks the motor needs to move
            BackLeft.setTargetPosition((int)totalDistance);
            BackRight.setTargetPosition(-(int)totalDistance);
            FrontLeft.setTargetPosition(-(int)totalDistance);
            FrontBack.setTargetPosition((int)totalDistance);

            //It then sets the power of the motors accordingly to turn the robot to the right
            BackLeft.setPower(motorSpeed);
            BackRight.setPower(-motorSpeed);
            FrontLeft.setPower(-motorSpeed);
            FrontBack.setPower(motorSpeed);
        }

        //If false turn left
        else {

            //Sets the number of ticks the motor needs to turn left
            BackLeft.setTargetPosition((int)totalDistance);
            BackRight.setTargetPosition(-(int)totalDistance);
            FrontLeft.setTargetPosition(-(int)totalDistance);
            FrontBack.setTargetPosition((int)totalDistance);

            //It then sets the power of the motors to turn left
            BackLeft.setPower(motorSpeed);
            BackRight.setPower(-motorSpeed);
            FrontLeft.setPower(-motorSpeed);
            FrontBack.setPower(motorSpeed);

        }


        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() &&BackLeft.isBusy() && BackRight.isBusy() && FrontLeft.isBusy() && FrontBack.isBusy()) {
            idle();
        }

        //After it has moved the desired amount brake the wheels
        BackRight.setPower(0);
        BackLeft.setPower(0);
        FrontBack.setPower(0);
        FrontLeft.setPower(0);
    }

    //This method is called when you want the robot to turn on the spot in a specified direction
    private void TurnOnTheSpot(double degrees, double motorSpeed, Direction turnDirection){

        //Converts degrees into ticks
        final double CONVERSION_FACTOR = 5;
        final double ticksToDegrees = 85 / 90; //changes with DeLorean? - Zane

        //Multiplies the number of degrees by the conversion factor to get the number of ticks for the specified degrees
        double ticks = (degrees * CONVERSION_FACTOR);
        double turnDegrees = ticks * ticksToDegrees;

        //Resets encoder values
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the encoders back up for accepting input
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Checks if it is meant to turn right
        if(turnDirection == Direction.RIGHT)
        {
            //Sets the number of ticks the motor needs to move
            BackLeft.setTargetPosition((int)turnDegrees );
            BackRight.setTargetPosition(-(int)turnDegrees);
            FrontLeft.setTargetPosition((int)turnDegrees );
            FrontBack.setTargetPosition(-(int)turnDegrees);

            //It then sets the power of the motors accordingly to turn the robot to the right
            BackLeft.setPower(motorSpeed);
            BackRight.setPower(-motorSpeed);
            FrontLeft.setPower(motorSpeed);
            FrontBack.setPower(-motorSpeed);
        }

        //If false turn left
        else {

            //Sets the number of ticks the motor needs to turn left
            BackLeft.setTargetPosition(-(int)turnDegrees);
            BackRight.setTargetPosition((int)turnDegrees);
            FrontLeft.setTargetPosition(-(int)turnDegrees);
            FrontBack.setTargetPosition((int)turnDegrees);

            //It then sets the power of the motors to turn left
            BackLeft.setPower(-motorSpeed);
            BackRight.setPower(motorSpeed);
            FrontLeft.setPower(-motorSpeed);
            FrontBack.setPower(motorSpeed);
        }


        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() &&BackLeft.isBusy() && BackRight.isBusy() && FrontLeft.isBusy() && FrontBack.isBusy()) {
            idle();
        }

        //After it has moved the desired amount brake the wheels
        BackRight.setPower(0);
        BackLeft.setPower(0);
        FrontBack.setPower(0);
        FrontLeft.setPower(0);
    }

    //This method can be called when you want the robot to turn to a set degrees value at a certain speed and direction
    private void TurnToDegrees(double degrees, double motorSpeed, Direction turnDirection){
        //Converts degrees into ticks
        final double CONVERSION_FACTOR = 2.5;
        final double ticksToDegrees = 90 / 85; //Again, changes with Mecanum wheels? - Zane

        //Multiplies the number of degrees by the conversion factor to get the number of ticks for the specified degrees
        double ticks = (degrees * CONVERSION_FACTOR);
        double turnDegrees = ticks * ticksToDegrees;

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }

        //Resets encoder values
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the encoders back up for accepting input
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Checks if it is meant to turn right
        if(turnDirection == Direction.LEFT) {
            //Sets the number of ticks the motor needs to move
            BackLeft.setTargetPosition((int)turnDegrees );
            BackRight.setTargetPosition(-(int)turnDegrees);

            //It then sets the power of the motors accordingly to turn the robot to the right
            BackLeft.setPower(motorSpeed);
            BackRight.setPower(-motorSpeed);
        }

        //If false turn left
        else {

            //Sets the number of ticks the motor needs to turn left
            BackLeft.setTargetPosition(-(int)turnDegrees );
            BackRight.setTargetPosition((int)turnDegrees);

            //It then sets the power of the motors to turn left
            BackLeft.setPower(-motorSpeed);
            BackRight.setPower(motorSpeed);
        }


        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() &&BackLeft.isBusy() && BackRight.isBusy()) {
            idle();
        }

        //After it has moved the desired amount brake the wheels
        BackRight.setPower(0);
        BackLeft.setPower(0);

    }

    //This method can be called when you want the robot to drive a certain distance in INCHES at a certain speed
    private void DriveToDistance(double distance, double motorSpeed){

        //! 1 rev is 12.56 inches !
        double totalDistance = (REV_TICK_COUNT / 12.566) * distance;


        //Stops and resets encoders
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Then it switches the encoders in a mode where it will drive the specified distance no matter what
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Will check if the robot should be in reverse if so set encoder target accordingly
        if(motorSpeed < 0){

            //Sets the number of ticks to negative to allow for reverse
            BackLeft.setTargetPosition(-(int)totalDistance);
            BackRight.setTargetPosition(-(int)totalDistance);
            FrontLeft.setTargetPosition(-(int)totalDistance);
            FrontBack.setTargetPosition(-(int)totalDistance);
        }

        //Will run if robot is set to move forward
        else {

            //Roughly the same as the code above but this will move forward
            BackLeft.setTargetPosition((int)totalDistance);
            BackRight.setTargetPosition((int)totalDistance);
            FrontLeft.setTargetPosition((int)totalDistance);
            FrontBack.setTargetPosition((int)totalDistance);
        }


        //It then sets the power to the motors to allow for forward movement
        BackLeft.setPower(motorSpeed);
        BackRight.setPower(motorSpeed);
        FrontLeft.setPower(motorSpeed);
        FrontBack.setPower(motorSpeed);

        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() &&BackLeft.isBusy() && BackRight.isBusy()) {
            idle();
        }

        //After it has moved the desired amount brake the wheels
        BackRight.setPower(0);
        BackLeft.setPower(0);
        FrontLeft.setPower(0);
        FrontBack.setPower(0);
    }

    //This method will use the color sensor to sense if the ball is yellow or white and returns a boolean value accordingly
    private boolean SenseYellow(ColorSensor sensor){
        boolean isYellow;

        //The color sensed was white
        if(sensor.blue() > 100 && sensor.red() > 100 && sensor.green() > 100){
            isYellow = false;
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
    //Lift function here
}
