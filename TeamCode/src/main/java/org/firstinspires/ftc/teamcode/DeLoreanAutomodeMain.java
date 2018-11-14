/*
Code Stolen From Will Richards by Troy Lopez for the Delorean bot.
Made for 2019 FTC
 */
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "DeLoreanAutomodeMain", group = "DeLorean")
public class DeLoreanAutomodeMain extends LinearOpMode {

    //Declares Motor Variables
    private DcMotor BackLeft;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor WheelTuckRight;
    private DcMotor WheelTuckLeft;
    //private DcMotor ArchScrew;
    //private DcMotor LiftScrew;
    //private Servo IntakeServo;

    //Declare color sensor(s) here
    private ColorSensor ColorSensor;

    //Used to specify direction for strafing/turning
    public enum Direction {UP, DOWN, LEFT, RIGHT}

    //Number of ticks per rotation for drive motors
    private final int REV_TICK_COUNT = 560;

    public void runOpMode() {
        //Initialize motors and sensors
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        WheelTuckLeft = hardwareMap.get(DcMotor.class, "WheelTuckLeft");
        WheelTuckRight = hardwareMap.get(DcMotor.class, "WheelTuckRight");

        //Also initialize intake servos and motors

        //Has to be fixed (not configured on phone)
        ColorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");

        //Creates a local reference to VuforiaBase
        //VuforiaBase vuforiaBase = new VuforiaBase();

        //Reverse left motors so all motors rotate in the same direction at any given power level
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);

        //Sets up speeds for different actions
        double motorSpeed = 0.75;
        double turnSpeed = 1;
        double tuckSpeed = 0.75;

        //Wait for start button to be pressed
        waitForStart();

        UntuckWheels(-0.5, tuckSpeed);

        //Strafe function was fixed (in theory)
        Strafe(2, motorSpeed, Direction.RIGHT);

        //region Old DeLorean Automode
        //Drives up to left cube set
        DriveToDistance (36, motorSpeed);

        //Approaches claim site
        DriveToDistance(20, motorSpeed);

        //Drop team marker (motor run, then back)

        //Begin new and improved DeLorean Automode

        //Drive to claim site and drop marker
        DriveToDistance(66, motorSpeed);
        //Drop marker
        DriveToDistance(34, -motorSpeed);

        //Best range of color sensor is <2cm away from target (close)
        //Check if object is the yellow cube
        if (SenseYellow(ColorSensor)) {
            //Hit it
            TurnOnTheSpot(90, 1, Direction.RIGHT);
            DriveToDistance(14.5, motorSpeed);
        }
        else {
            Strafe(14.5,motorSpeed,Direction.LEFT);

            //Checks if object on far left from the center of the maps Point ov view is yellow
            if (SenseYellow(ColorSensor)) {
                //pickup
            }
            //If not go to the far right one and pick up gold
            else {
               Strafe(29,motorSpeed,Direction.RIGHT);
                //Pick up cube
            }
        }

        //Begin approach to other cube set
        TurnOnTheSpot( 90, turnSpeed, Direction.RIGHT);
        DriveToDistance(30, motorSpeed);

        //Checks three objects for yellow and parks on edge of crater
        Strafe(20,motorSpeed,Direction.RIGHT);
        if (SenseYellow(ColorSensor)) {
            //pickup
            DriveToDistance(5, motorSpeed);
        }
        //Checks second object if yellow (third is automatic if 1+2 aren't yellow)
        else {

            Strafe(motorSpeed,14.5,Direction.RIGHT);

            if (SenseYellow(ColorSensor)) {
                //pickup
                DriveToDistance(5, motorSpeed);
            }
            else {
                Strafe(14.5, motorSpeed, Direction.RIGHT);
                //pickup
                DriveToDistance(5, motorSpeed);
            }

        }
    }
    //Intake function
    private void IntakeObject (double motorPower, double intakeTime, double rotations, Direction liftDirection) {
        /* Function pseudocode
         *
         * Reset lift and screw encoders and set to run to position (possibly don't do if using intakeTime)
         * Check which direction to move lift with liftDirection parameter
         * Start running IntakeServo as a continuous rotation servo ("servoName".setPower(x))
         */
    }
    private void Strafe (double distance, double motorSpeed, Direction strafeDirection){

        //Converts degrees into ticks
        double totalDistance = (REV_TICK_COUNT / 12.566) * distance; //totalDistance is never used. Remove it?

        //Resets motor encoders
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Check which direction to strafe
        if(strafeDirection == Direction.RIGHT)
        {
            BackLeft.setTargetPosition((int)totalDistance);
            BackRight.setTargetPosition(-(int)totalDistance);
            FrontLeft.setTargetPosition(-(int)totalDistance);
            FrontRight.setTargetPosition((int)totalDistance);

            BackLeft.setPower(motorSpeed);
            BackRight.setPower(-motorSpeed);
            FrontLeft.setPower(-motorSpeed);
            FrontRight.setPower(motorSpeed);
        }
        else {
            BackLeft.setTargetPosition((int)totalDistance);
            BackRight.setTargetPosition(-(int)totalDistance);
            FrontLeft.setTargetPosition(-(int)totalDistance);
            FrontRight.setTargetPosition((int)totalDistance);

            BackLeft.setPower(motorSpeed);
            BackRight.setPower(-motorSpeed);
            FrontLeft.setPower(-motorSpeed);
            FrontRight.setPower(motorSpeed);
        }

        //Stalls until motors are done
        while (opModeIsActive() && BackLeft.isBusy() && BackRight.isBusy() && FrontLeft.isBusy() && FrontRight.isBusy()) {
            idle();
        }

        //Brake all motors
        BackRight.setPower(0);
        BackLeft.setPower(0);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
    }
    private void TurnOnTheSpot(double degrees, double motorSpeed, Direction turnDirection){
        //Converts degrees into ticks
        final double CONVERSION_FACTOR = 5;
        double ticks = (degrees * CONVERSION_FACTOR);

        //Reset encoders and make motors run to # of ticks
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Check which direction to turn
        if(turnDirection == Direction.RIGHT)
        {
            BackLeft.setTargetPosition((int)ticks );
            BackRight.setTargetPosition(-(int)ticks);
            FrontLeft.setTargetPosition((int)ticks );
            FrontRight.setTargetPosition(-(int)ticks);

            BackLeft.setPower(motorSpeed);
            BackRight.setPower(-motorSpeed);
            FrontLeft.setPower(motorSpeed);
            FrontRight.setPower(-motorSpeed);
        }
        else {
            BackLeft.setTargetPosition(-(int)ticks);
            BackRight.setTargetPosition((int)ticks);
            FrontLeft.setTargetPosition(-(int)ticks);
            FrontRight.setTargetPosition((int)ticks);

            BackLeft.setPower(-motorSpeed);
            BackRight.setPower(motorSpeed);
            FrontLeft.setPower(-motorSpeed);
            FrontRight.setPower(motorSpeed);
        }

        //Wait until turning is done
        while (opModeIsActive() &&BackLeft.isBusy() && BackRight.isBusy() && FrontLeft.isBusy() && FrontRight.isBusy()) {
            idle();
        }

        //Stop motors
        BackRight.setPower(0);
        BackLeft.setPower(0);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
    }
    private void UntuckWheels(double rotations, double tuckSpeed){
        final int TUCK_TICK_COUNT = 1120;
        double totalRotations = TUCK_TICK_COUNT * rotations;

        //Reset encoders and make motors run to # of ticks
        WheelTuckLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        WheelTuckRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        WheelTuckLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        WheelTuckRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //region Tucking code not necessary
        /*
        //Check which direction to move wheels
        if (speedTuck < 0) {
            WheelTuckLeft.setTargetPosition(-(int)totalRotations);
            sleep(1000);
            WheelTuckRight.setTargetPosition(-(int)totalRotations);
        }
        else {
            WheelTuckLeft.setTargetPosition((int)totalRotations);
            sleep(1000);
            WheelTuckRight.setTargetPosition((int)totalRotations);
        }
        */
        //endregion

        //Define target position and run motors
        WheelTuckLeft.setTargetPosition((int)totalRotations);
        WheelTuckRight.setTargetPosition(-(int)totalRotations);

        WheelTuckLeft.setPower(tuckSpeed);
        WheelTuckRight.setPower(-tuckSpeed);

        //Wait until wheels finish tucking
        while (opModeIsActive() && WheelTuckLeft.isBusy()) {
            idle();
        }

        //Keep wheels down and don't let robot collapse
        WheelTuckLeft.setPower(0.2);
        WheelTuckRight.setPower(-0.2);
    }
    //Drives distance in INCHES
    private void DriveToDistance(double distance, double motorSpeed){
        //Convert inches to ticks
        double totalDistance = (REV_TICK_COUNT / 12.566) * distance;

        //Reset encoders and make motors run for ticks
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Check whether to drive forward or backward
        if(motorSpeed < 0){
            BackLeft.setTargetPosition(-(int)totalDistance);
            BackRight.setTargetPosition(-(int)totalDistance);
            FrontLeft.setTargetPosition(-(int)totalDistance);
            FrontRight.setTargetPosition(-(int)totalDistance);
        }
        else {
            BackLeft.setTargetPosition((int)totalDistance);
            BackRight.setTargetPosition((int)totalDistance);
            FrontLeft.setTargetPosition((int)totalDistance);
            FrontRight.setTargetPosition((int)totalDistance);
        }

        //Run motors
        BackLeft.setPower(motorSpeed);
        BackRight.setPower(motorSpeed);
        FrontLeft.setPower(motorSpeed);
        FrontRight.setPower(motorSpeed);

        //Wait for moving to finish
        while (opModeIsActive() && BackLeft.isBusy() && BackRight.isBusy()) {
            idle();
        }

        //Stop motors
        BackRight.setPower(0);
        BackLeft.setPower(0);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
    }
    //Function to sense yellow that returns boolean
    private boolean SenseYellow(ColorSensor sensor){
        //Declare boolean isYellow and initialize it to false
        boolean isYellow = false;

        //Senses yellow
        if (sensor.blue() < 100 && sensor.blue() > 50) {
            isYellow = true;
        }

        //Return boolean value isYellow
        return isYellow;
    }
}