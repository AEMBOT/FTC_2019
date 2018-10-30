package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name = "DeLoreanTeleOpModeMain", group = "DeLorean")
public class DeLoreanTeleOpModeMain extends LinearOpMode {
    //Declare DC motor variables
    private DcMotor MotorLB;
    private DcMotor MotorRB;
    private DcMotor MotorLF;
    private DcMotor MotorRF;

    //Declare any other motors (servos, etc.)

    //Declare sensors
    private ColorSensor ColorSensorR;
    private ColorSensor ColorSensorL;

    //Creates constant that contains the number of ticks in a full rotation of a REV motor
    private final int REV_TICK_COUNT = 288;

    public void runOpMode() {
        //Initialize DC motor variables
        MotorLB = hardwareMap.get(DcMotor.class, "MotorLB");
        MotorRB = hardwareMap.get(DcMotor.class, "MotorRB");
        MotorLF = hardwareMap.get(DcMotor.class, "MotorLF");
        MotorRF = hardwareMap.get(DcMotor.class, "MotorRF");

        //Initialize any other motors here

        //Initialize sensors
        ColorSensorR = hardwareMap.get(ColorSensor.class, "ColorSensorR");
        ColorSensorL = hardwareMap.get(ColorSensor.class, "ColorSensorL");

        //Reverse left motors so forward is the same for all motors
        MotorLB.setDirection(DcMotor.Direction.REVERSE);
        MotorLF.setDirection(DcMotor.Direction.REVERSE);

        //Wait for start button to be pressed
        waitForStart();

         while (opModeIsActive()) {
             //Set all motors' power to y-value of left/right stick accordingly
             MotorRB.setPower(gamepad1.right_stick_y);
             MotorLB.setPower(gamepad1.left_stick_y);
             MotorRF.setPower(gamepad1.right_stick_y);
             MotorLF.setPower(gamepad1.left_stick_y);

             //Other code goes here
         }
    }
}
