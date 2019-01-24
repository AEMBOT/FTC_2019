/**
 * Program written by Zane Othman for DeLorean 4.0 as one of two
 * variants of automode to be used at qualifiers
 * 8 January, 2019
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "DepotAutoMode", group = "DeLorean")
@Disabled
public class DepotAutoMode extends LinearOpMode {
    // Declares Motor Variables
    private DcMotor dcBackLeft;
    private DcMotor dcBackRight;
    private DcMotor dcFrontLeft;
    private DcMotor dcFrontRight;
    private DcMotor dcTuckRight;
    private DcMotor dcTuckLeft;
    private DcMotor dcIntake;
    private DcMotor dcConveyor;
    private DcMotor dcLift;

    // Declare servos
    private Servo svClaim;
    private Servo svConveyor;
    private Servo svSweeper;

    // Declare color sensor(s) here
    private ColorSensor csMain;

    // Used to specify direction for strafing, turning, or later arch screw intake
    public enum direction { UP, DOWN, LEFT, RIGHT }

    // Number of ticks per rotation for drive motors
    private final int REV_TICK_COUNT = 560;

    public void runOpMode() {
        // Map variables to robot hardware (via config profile on phone)
        dcBackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        dcBackRight = hardwareMap.get(DcMotor.class, "BackRight");
        dcFrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        dcFrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        dcIntake = hardwareMap.get(DcMotor.class, "Intake");
        dcConveyor = hardwareMap.get(DcMotor.class, "Conveyor");
        dcLift = hardwareMap.get(DcMotor.class, "Lift");

        //Servo
        svClaim = hardwareMap.get(Servo.class, "svClaim");
        svConveyor = hardwareMap.get(Servo.class, "svConveyor");
        svSweeper = hardwareMap.get(Servo.class, "svSweeper");

        // Reverse motors on one side so all rotate in same direction
        dcBackLeft.setDirection(DcMotor.Direction.REVERSE);
        dcFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        //Sets up speeds for different actions
        double motorSpeed = 0.7;
        double turnSpeed = .8;
        double tuckSpeed = 0.75;
        double strafeSpeed = 1;

        waitForStart();

        // Code that does stuff goes here

    }
    private void strafe(double distance, double motorSpeed, direction strafeDirection){
        //Converts degrees into ticks

        double totalDistance = (REV_TICK_COUNT / (Math.PI * 4)) * distance;

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

    private void turnOnTheSpot(double degrees, double turnSpeed, direction turnDirection){
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
    private void landWheels(double rotations, double strafeRotations, double tuckSpeed, double strafeSpeed){
        final int TUCK_TICK_COUNT = 1120;
        double totalRotations = TUCK_TICK_COUNT * rotations;
        double strafeRotaions = TUCK_TICK_COUNT * strafeRotations;

        //Reset encoders and make motors run to # of ticks
        dcTuckLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcTuckRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        dcTuckLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcTuckRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //region Tucking code not necessary
        /*
        //Check which direction to move wheels
        if (speedTuck < 0) {
            dcTuckLeft.setTargetPosition(-(int)totalRotations);
            sleep(1000);
            dcTuckRight.setTargetPosition(-(int)totalRotations);
        }
        else {
            dcTuckLeft.setTargetPosition((int)totalRotations);
            sleep(1000);
            dcTuckRight.setTargetPosition((int)totalRotations);
        }
        */
        //endregion

        //Define target position and run motors
        dcTuckLeft.setTargetPosition((int)totalRotations);
        dcTuckRight.setTargetPosition(-(int)totalRotations);
        dcFrontRight.setTargetPosition(-(int)strafeRotations);
        dcBackRight.setTargetPosition(-(int)strafeRotations);
        dcFrontLeft.setTargetPosition((int)strafeRotations);
        dcBackLeft.setTargetPosition((int)strafeRotations);

        dcTuckLeft.setPower(tuckSpeed);
        dcTuckRight.setPower(-tuckSpeed);
        dcFrontLeft.setPower(strafeSpeed);
        dcFrontRight.setPower(-strafeSpeed);
        dcBackLeft.setPower(strafeSpeed);
        dcBackRight.setPower(-strafeSpeed);
        //Wait until wheels finish tucking
        while (opModeIsActive() && dcTuckLeft.isBusy()) {
            idle();
        }

        //Keep wheels down and don't let robot collapse
        dcTuckLeft.setPower(0.2);
        dcTuckRight.setPower(-0.2);
    }
    //Drives distance in INCHES
    private void driveInches(double distance, double motorSpeed){
        //Convert inches to ticks
        double totalDistance = (REV_TICK_COUNT / 12.566) * distance;

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
    //Function to sense yellow that returns boolean
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
