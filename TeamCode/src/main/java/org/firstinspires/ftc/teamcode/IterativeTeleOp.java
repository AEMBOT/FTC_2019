package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "IterativeTeleOp", group = "DeLorean")
@Disabled
public class IterativeTeleOp extends OpMode {
    //Declaration of motors, sensors and runtime variable
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor dcBackLeft = null;
    private DcMotor dcBackRight = null;
    private DcMotor dcFrontLeft = null;
    private DcMotor dcFrontRight = null;

    private DcMotor dcTuckLeft = null;
    private DcMotor dcTuckRight = null;

    private Servo servoFlipper = null;

    private ColorSensor csMain = null;

    private double strafeSpeed;

    //Run when "Init" is pressed on driver station phone
    public void init() {
        //Map variables to actual hardware on robot
        dcBackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        dcBackRight = hardwareMap.get(DcMotor.class, "BackRight");
        dcFrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        dcFrontRight = hardwareMap.get(DcMotor.class, "FrontRight");

        servoFlipper = hardwareMap.get(Servo.class, "Flipper");

        csMain = hardwareMap.get(ColorSensor.class, "ColorSensor");

        //Declare speed variables
        double strafeSpeed = 0.75;

        //Update telemetry with initialized message
        telemetry.addData("Status", "Initialized");
    }

    //Run right when start button pressed on driver station phone
    public void start() { telemetry.addData("Status", "Running"); }

    //Equivalent to while(opModeIsActive) loop in linear op modes
    public void loop() {
        //Driving NOT strafing
        dcBackRight.setPower(-gamepad1.right_stick_y);
        dcBackLeft.setPower(-gamepad1.left_stick_y);
        dcFrontLeft.setPower(-gamepad1.right_stick_y);
        dcFrontRight.setPower(-gamepad1.left_stick_y);

        //Strafe left
        if(gamepad1.dpad_left) {
            dcBackLeft.setPower(strafeSpeed);
            dcBackRight.setPower(-strafeSpeed);
            dcFrontLeft.setPower(-strafeSpeed);
            dcFrontRight.setPower(strafeSpeed);

        }
        //Strafe right
        if(gamepad1.dpad_right) {
            dcBackLeft.setPower(-strafeSpeed);
            dcBackRight.setPower(strafeSpeed);
            dcFrontLeft.setPower(strafeSpeed);
            dcFrontRight.setPower(-strafeSpeed);
        }

        //Tuck wheels into robot
        if(gamepad1.left_bumper) {
            dcTuckLeft.setPower(-0.75);
            sleep(1000);
            dcTuckRight.setPower(0.75);
            sleep(1000);
        }
        //Untuck wheels
        if(gamepad1.right_bumper) {
            dcTuckLeft.setPower(0.75);
            sleep(1000);
            dcTuckRight.setPower(-0.75);
            sleep(1000);
            dcTuckRight.setPower(-0.2);

        }
        else {
            dcBackRight.setPower(0);
            dcBackLeft.setPower(0);
            dcFrontRight.setPower(0);
            dcFrontLeft.setPower(0);
            dcTuckRight.setPower(0);
            dcTuckLeft.setPower(0);
        }
    }
    private void sleep(long milliseconds) {
        runtime.reset();
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
