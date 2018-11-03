/*
Demo TeleOP code created by Will Richards for FTC 2019

Bare bones driver controlled teleop code

!!!IMPORTANT!!! This code is a demo, the comments are excessive on purpose don't comment this much normally but defiantly comment your code
Comment large blocks of code that need description, or anything that needs a description... Be concise
Also if you have alot of unorganized code somewhere insert //region [NAME HERE] at the start and //endregion at the end and it will allow you to collapse that specific section of code
Try to keep use of regions to a minimal
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleOpMode", group = "Main")
public class TeleOpMode extends LinearOpMode {

    //DC Motors
    private DcMotor MotorLB;
    private DcMotor MotorRB;
    private DcMotor MotorUp;
    private DcMotor MotorDown;

    //Servos
    private Servo ServoFlipper;

    //Sensors
    private ColorSensor DemoColorSensor;

    public void runOpMode()
    {
        //Initialize motors and sensors
        MotorLB = hardwareMap.get(DcMotor.class, "MotorLB");
        MotorRB = hardwareMap.get(DcMotor.class, "MotorRB");
        MotorUp = hardwareMap.get(DcMotor.class, "LiftUp");
        MotorDown = hardwareMap.get(DcMotor.class, "LiftDown");
        ServoFlipper = hardwareMap.get(Servo.class, "Flipper");

        DemoColorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");

        //Reverse right-side drive motor so both motors rotate in same direction
        MotorRB.setDirection(DcMotorSimple.Direction.REVERSE);

        //Wait for start button to be pressed
        waitForStart();

        while (opModeIsActive()) {
            //Controls drive motors with gamepad 1's joysticks
            MotorRB.setPower(gamepad1.right_stick_y);
            MotorLB.setPower(gamepad1.left_stick_y);

            //Lift code
            if (gamepad2.y) {
                //Pull lift towards robot
                MotorUp.setPower(-0.75);
                MotorDown.setPower(0.75);
            }
            if (gamepad2.a) {
                //Lower robot (raise lift)
                MotorUp.setPower(0.75);
                MotorDown.setPower(-0.75);
            }
            //Individual motor controlling (is this needed anymore?)
            if (gamepad2.b) {
                MotorDown.setPower(0.25);
            }
            if (gamepad2.x) {
                MotorDown.setPower(-0.25);
            }
            //Reset motor power if nothing is pressed
            else {
                MotorUp.setPower(0);
                MotorDown.setPower(0);
            }

            //Flipper code
            if(gamepad1.a) {
                // move to 0 degrees.
                ServoFlipper.setPosition(0);
            } else if (gamepad1.x || gamepad1.b) {
                // move to 90 degrees.
                ServoFlipper.setPosition(.5);
            } else if (gamepad1.y) {
                // move to 180 degrees.
                ServoFlipper.setPosition(1);
            }
            //Print Flipper servo position do driver station phone
            telemetry.addData("Servo Position", ServoFlipper.getPosition());
            telemetry.update();
        }
    }

    private final int REV_TICK_COUNT = 288;
    //Should we consider removing this from TeleOp? We don't call it at all.
    private void MoveLift(double motorSpeed) {
        //region This broke the lift
        /*
        MotorDown.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorUp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        MotorDown.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorUp.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        if(motorSpeed < 0){
            MotorDown.setTargetPosition(-(int) ticks);
            MotorUp.setTargetPosition(-(int) ticks);
        }
        else {
            MotorDown.setTargetPosition((int) ticks);
            MotorUp.setTargetPosition((int) ticks);
        }
        */
        //endregion
        MotorDown.setPower(motorSpeed);
        MotorUp.setPower(motorSpeed);
    }
}
