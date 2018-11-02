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

        MotorLB = hardwareMap.get(DcMotor.class, "MotorLB");
        MotorRB = hardwareMap.get(DcMotor.class, "MotorRB");
        MotorUp = hardwareMap.get(DcMotor.class, "LiftUp");
        MotorDown = hardwareMap.get(DcMotor.class, "LiftDown");
        ServoFlipper = hardwareMap.get(Servo.class, "Flipper");

        DemoColorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");

        MotorRB.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            //Wheels
            MotorRB.setPower(gamepad1.right_stick_y);
            MotorLB.setPower(gamepad1.left_stick_y);

            if (gamepad2.y) {
                MoveLift(12.5, 1);
            }
            if (gamepad2.a) {
                MoveLift(-12.5, 1);
            }

            //Flipper code
            if(gamepad1.a) {
                // move to 0 degrees.
                ServoFlipper.setPosition(0);
            } else if (gamepad1.x || gamepad1.b) {
                // move to 90 degrees.
                ServoFlipper.setPosition(.5/*may be .5*/);
            } else if (gamepad1.y) {
                // move to 180 degrees.
                ServoFlipper.setPosition(1/*may be 1 */);
            }
            telemetry.addData("Servo Position", ServoFlipper.getPosition());
            telemetry.update();
        }
    }

    private final int REV_TICK_COUNT = 288;
    private void MoveLift(double rotations, double motorSpeed) {
        double ticks = rotations * REV_TICK_COUNT;

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

        MotorDown.setPower(motorSpeed);
        MotorUp.setPower(motorSpeed);

        while(MotorDown.isBusy() && MotorUp.isBusy() && opModeIsActive()) {
            idle();
        }

        MotorDown.setPower(0);
        MotorUp.setPower(0);
    }
}
