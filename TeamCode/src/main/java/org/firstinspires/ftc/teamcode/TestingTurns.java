package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Testing Turns", group = "Testing")
public class TestingTurns extends LinearOpMode {
    // Declares Motor Variables
    private DcMotor dcBackLeft;
    private DcMotor dcBackRight;
    private DcMotor dcFrontLeft;
    private DcMotor dcFrontRight;

    // Used to specify direction for turning
    public enum direction {
        LEFT, RIGHT
    }

    // Number of ticks per rotation for drive motors
    private final int REV_TICK_COUNT = 560;

    public void runOpMode() {
        // Map variables to robot hardware (via config profile on phone)
        dcBackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        dcBackRight = hardwareMap.get(DcMotor.class, "BackRight");
        dcFrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        dcFrontRight = hardwareMap.get(DcMotor.class, "FrontRight");

        waitForStart();

        turnToDegreesNew(90, 0.5, direction.RIGHT);
    }
    private void turnToDegreesNew(int degrees, double power, direction turnDirection) {
        // Conversion factor to go from motor rotations to degrees of robot rotation (distance between wheel sets / wheel circumference)
        // This conversion factor is the number of ticks required for a 360 degree robot turn
        final double ROTATIONS_TO_DEGREES = (22.25 / (Math.PI * 4)) * REV_TICK_COUNT;

        // Convert degrees of robot rotation to motor rotations
        double finalTicks = (ROTATIONS_TO_DEGREES / 360) * degrees;

        // Reset all motor encoders
        dcBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        dcBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set target positions depending on direction and run motors to those points
        switch(turnDirection) {
            case LEFT:
                dcFrontRight.setTargetPosition(-(int) finalTicks);
                dcFrontLeft.setTargetPosition((int) finalTicks);
                dcBackRight.setTargetPosition(-(int) finalTicks);
                dcBackLeft.setTargetPosition((int) finalTicks);

                dcFrontRight.setPower(-power);
                dcFrontLeft.setPower(power);
                dcBackRight.setPower(-power);
                dcBackLeft.setPower(power);
            case RIGHT:
                dcFrontRight.setTargetPosition((int) finalTicks);
                dcFrontLeft.setTargetPosition(-(int) finalTicks);
                dcBackRight.setTargetPosition((int) finalTicks);
                dcBackLeft.setTargetPosition(-(int) finalTicks);

                dcFrontRight.setPower(power);
                dcFrontLeft.setPower(-power);
                dcBackRight.setPower(power);
                dcBackLeft.setPower(-power);
        }

        // Wait for motors to finish rotating, and also only run while op mode is active
        while(opModeIsActive() && dcBackLeft.isBusy() && dcBackRight.isBusy() && dcFrontRight.isBusy() && dcFrontLeft.isBusy()){
            idle();
        }

        // Turn off all motors
        dcFrontRight.setPower(0);
        dcFrontLeft.setPower(0);
        dcBackRight.setPower(0);
        dcBackLeft.setPower(0);
    }
}