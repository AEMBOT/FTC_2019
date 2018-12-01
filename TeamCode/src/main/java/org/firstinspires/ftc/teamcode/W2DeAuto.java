package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "W2DeAuto", group = "DeLorean")
public class W2DeAuto extends LinearOpMode {
    // Declares Motor Variables
    private DcMotor dcBackLeft;
    private DcMotor dcBackRight;
    private DcMotor dcFrontLeft;
    private DcMotor dcFrontRight;
    private DcMotor dcTuckRight;
    private DcMotor dcTuckLeft;

    // Declare servos
    private Servo svFlipper;
    private Servo svClaim;

    // Declare color sensor(s) here
    private ColorSensor csMain;

    // Used to specify direction for strafing, turning, or later arch screw intake
    public enum direction { UP, DOWN, LEFT, RIGHT }

    // Used to skip future scannings
    private boolean sensedGold;

    // Number of ticks per rotation for drive motors
    private final int REV_TICK_COUNT = 560;

    public void runOpMode() {
        // Map variables to robot hardware (via config profile on phone)
        dcBackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        dcBackRight = hardwareMap.get(DcMotor.class, "BackRight");
        dcFrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        dcFrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        dcTuckLeft = hardwareMap.get(DcMotor.class, "WheelTuckLeft");
        dcTuckRight = hardwareMap.get(DcMotor.class, "WheelTuckRight");

        //Servos
        svFlipper = hardwareMap.get(Servo.class, "Flipper");
        svClaim = hardwareMap.get(Servo.class, "Claim");

        // Sensors
        csMain = hardwareMap.get(ColorSensor.class, "ColorSensor");

        // Reverse motors on one side so all rotate in same direction
        dcBackLeft.setDirection(DcMotor.Direction.REVERSE);
        dcFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        //Sets up speeds for different actions
        double motorSpeed = 0.7;
        double turnSpeed = 0.8;
        double tuckSpeed = 0.75;
        // double strafeSpeed = 1; Impractical; we only use it once

        waitForStart();

        liftWheels(0.3, tuckSpeed);
        landWheels(0.5, tuckSpeed);

        strafe(2.25, motorSpeed, direction.RIGHT);

        svFlipper.setPosition(0.4);

        turnDegrees(15, turnSpeed, direction.RIGHT);

        driveInches(39.5, motorSpeed);

        if(isItYellow()) {
            hitGold();
            sensedGold = true;
        }
        turnDegrees(110, turnSpeed, direction.LEFT);
        driveInches(14.5, motorSpeed);
        if (isItYellow() && !sensedGold) {
            hitGold();
            sensedGold = true;
        }
        driveInches(14.5, motorSpeed);
        if(!sensedGold) {
            hitGold();
        }

        // Go to claim site
        turnDegrees(20, turnSpeed, direction.RIGHT);
        driveInches(30, motorSpeed);

        // Drop claim piece
        svClaim.setPosition(0.8);
        svClaim.setPosition(0);

        // Park on crater
        turnDegrees(110, turnSpeed, direction.RIGHT);
        driveInches(60, motorSpeed);

        //region Pseudocode
        /* Pseudocode
         * 1. Land
         * 2. Turn and approach right mineral of first set
         * START IF/ELSEIF/ELSE STATEMENT
         * 3. Scan it & intake if gold
         *    a. If it is, intake via Archimedes screw & skip 4 + 5
         *   4. Turn and move above next (center) mineral & scan
         *     a. Intake if yellow & skip 5
         *   5. Approach last mineral & intake if other two weren't yellow
         * END
         * 6. Turn toward and approach claim site
         * 7. Drop claim piece (and gold if picked up)
         * 8. Turn towards crater and drive
         * 9. Park on (NOT IN) crater
         */
        //endregion
    }
    private void hitGold() {
        svFlipper.setPosition(0.7);
        svFlipper.setPosition(0.4);
    }
    private void strafe(double inches, double motorSpeed, direction strafeDirection){
        //Converts degrees into ticks

        double totalDistance = (REV_TICK_COUNT / (Math.PI * 4)) * inches;

        //Resets motor encoders
        dcBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        dcBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Check which direction to strafe
        if(strafeDirection == direction.RIGHT)
        {
            dcBackLeft.setTargetPosition((int)totalDistance);
            dcBackRight.setTargetPosition(-(int)totalDistance);
            dcFrontLeft.setTargetPosition(-(int)totalDistance);
            dcFrontRight.setTargetPosition((int)totalDistance);

            dcBackLeft.setPower(motorSpeed);
            dcBackRight.setPower(-motorSpeed);
            dcFrontLeft.setPower(-motorSpeed);
            dcFrontRight.setPower(motorSpeed);
        }
        else {
            dcBackLeft.setTargetPosition((int)totalDistance);
            dcBackRight.setTargetPosition(-(int)totalDistance);
            dcFrontLeft.setTargetPosition(-(int)totalDistance);
            dcFrontRight.setTargetPosition((int)totalDistance);

            dcBackLeft.setPower(motorSpeed);
            dcBackRight.setPower(-motorSpeed);
            dcFrontLeft.setPower(-motorSpeed);
            dcFrontRight.setPower(motorSpeed);
        }

        //Stalls until motors are done
        while (opModeIsActive() && dcBackLeft.isBusy() && dcBackRight.isBusy() && dcFrontLeft.isBusy() && dcFrontRight.isBusy()) {
            idle();
        }

        //Brake all motors
        dcBackRight.setPower(0);
        dcBackLeft.setPower(0);
        dcFrontRight.setPower(0);
        dcFrontLeft.setPower(0);

    }

    private void turnDegrees(double degrees, double turnSpeed, direction turnDirection){
        //Converts degrees into ticks
        final double CONVERSION_FACTOR = 5;
        double ticks = (degrees * CONVERSION_FACTOR);

        //Reset encoders and make motors run to # of ticks
        dcBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        dcBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Check which direction to turn
        if(turnDirection == direction.RIGHT)
        {
            dcBackLeft.setTargetPosition((int)ticks );
            dcBackRight.setTargetPosition(-(int)ticks);
            dcFrontLeft.setTargetPosition((int)ticks );
            dcFrontRight.setTargetPosition(-(int)ticks);

            dcBackLeft.setPower(turnSpeed);
            dcBackRight.setPower(-turnSpeed);
            dcFrontLeft.setPower(turnSpeed);
            dcFrontRight.setPower(-turnSpeed);
        }
        else {
            dcBackLeft.setTargetPosition(-(int)ticks);
            dcBackRight.setTargetPosition((int)ticks);
            dcFrontLeft.setTargetPosition(-(int)ticks);
            dcFrontRight.setTargetPosition((int)ticks);

            dcBackLeft.setPower(-turnSpeed);
            dcBackRight.setPower(turnSpeed);
            dcFrontLeft.setPower(-turnSpeed);
            dcFrontRight.setPower(turnSpeed);
        }

        //Wait until turning is done
        while (opModeIsActive() && dcBackLeft.isBusy() && dcBackRight.isBusy() && dcFrontLeft.isBusy() && dcFrontRight.isBusy()) {
            idle();
        }

        //Stop motors
        dcBackRight.setPower(0);
        dcBackLeft.setPower(0);
        dcFrontRight.setPower(0);
        dcFrontLeft.setPower(0);


    }

    private void liftWheels (double rotations, double tuckSpeed){

        final int TUCK_TICK_COUNT = 1120;
        double totalRotations = TUCK_TICK_COUNT * rotations;

        dcTuckLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcTuckLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcTuckLeft.setTargetPosition((int)totalRotations);
        dcTuckLeft.setPower(tuckSpeed);

    }

    private void landWheels(double rotations, double tuckSpeed){
        final int TUCK_TICK_COUNT = 1120;
        double totalRotations = TUCK_TICK_COUNT * rotations;

        //Reset encoders and make motors run to # of ticks
        dcTuckLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcTuckRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        dcTuckLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcTuckRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Define target position and run motors
        dcTuckLeft.setTargetPosition((int)totalRotations);
        dcTuckRight.setTargetPosition(-(int)totalRotations);

        dcTuckLeft.setPower(tuckSpeed);
        dcTuckRight.setPower(-tuckSpeed);

        //Wait until wheels finish tucking
        while (opModeIsActive() && dcTuckLeft.isBusy() && dcTuckRight.isBusy()) {
            idle();
        }

        //Keep wheels down and don't let robot collapse
        /**
         * dcTuckLeft.setPower(0.2);
         * dcTuckRight.setPower(-0.2);
         */

    }

    //Drives distance in INCHES
    private void driveInches(double inches, double motorSpeed){
        //Convert inches to ticks
        double totalDistance = (REV_TICK_COUNT / 12.566) * inches;

        //Reset encoders and make motors run for ticks
        dcBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        dcBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Check whether to drive forward or backward
        if(motorSpeed < 0){
            dcBackLeft.setTargetPosition(-(int)totalDistance);
            dcBackRight.setTargetPosition(-(int)totalDistance);
            dcFrontLeft.setTargetPosition(-(int)totalDistance);
            dcFrontRight.setTargetPosition(-(int)totalDistance);
        }
        else {
            dcBackLeft.setTargetPosition((int)totalDistance);
            dcBackRight.setTargetPosition((int)totalDistance);
            dcFrontLeft.setTargetPosition((int)totalDistance);
            dcFrontRight.setTargetPosition((int)totalDistance);
        }

        //Run motors
        dcBackLeft.setPower(motorSpeed);
        dcBackRight.setPower(motorSpeed);
        dcFrontLeft.setPower(motorSpeed);
        dcFrontRight.setPower(motorSpeed);

        //Wait for moving to finish
        while (opModeIsActive() && dcBackLeft.isBusy() && dcBackRight.isBusy()) {
            idle();
        }

        //Stop motors
        dcBackRight.setPower(0);
        dcBackLeft.setPower(0);
        dcFrontLeft.setPower(0);
        dcFrontRight.setPower(0);
    }

    //Function to sense yellow that returns a boolean
    private boolean isItYellow(){
        //Declare boolean isYellow and initialize it to false
        boolean isYellow = false;

        //Senses yellow
        if (csMain.red() > csMain.green() && csMain.blue() < (2 * csMain.green())  / 3) {
            isYellow = true;
        }

        //Return boolean value isYellow
        return isYellow;
    }
}