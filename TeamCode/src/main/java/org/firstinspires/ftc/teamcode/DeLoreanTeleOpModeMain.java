package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name = "DeLoreanTeleOpModeMain", group = "DeLorean")
public class DeLoreanTeleOpModeMain extends LinearOpMode {
    private DcMotor BackLeft;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor MotorWheelTuckR;
    private DcMotor MotorWheelTuckL;
    private DcMotor tethookLift;

    //Declare any other motors (servos, etc.)
    private Servo svClaim;

    //Declare sensors
    //private ColorSensor ColorSensorR;
    //private ColorSensor ColorSensorL;

    public void runOpMode() {
        //Motor
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        MotorWheelTuckL = hardwareMap.get(DcMotor.class, "WheelTuckLeft");
        MotorWheelTuckR = hardwareMap.get(DcMotor.class, "WheelTuckRight");
        tethookLift = hardwareMap.get(DcMotor.class, "HookLift");

        //Servo
        svClaim = hardwareMap.get(Servo.class, "svClaim");

        //Reverse left motors so forward is the same for all motors
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);

        //Declare strafeSpeed variable
        double strafeSpeed = .75;
        boolean isServoRunning = true;

        waitForStart();

        while (opModeIsActive()) {
            //Driving NOT strafing
            BackRight.setPower(-gamepad1.right_stick_y * 0.75);
            BackLeft.setPower(-gamepad1.left_stick_y * 0.75);
            FrontRight.setPower(-gamepad1.right_stick_y * 0.75);
            FrontLeft.setPower(-gamepad1.left_stick_y * 0.75);

            // Mess with lift
            tethookLift.setPower(gamepad2.right_stick_y);

            //Strafe left
            if(gamepad1.dpad_left) {
                BackLeft.setPower(strafeSpeed * 0.75);
                BackRight.setPower(-strafeSpeed * 0.75);
                FrontLeft.setPower(-strafeSpeed * 0.75);
                FrontRight.setPower(strafeSpeed * 0.75);

            }
            //Strafe right
            if(gamepad1.dpad_right) {
                BackLeft.setPower(-strafeSpeed * 0.75);
                BackRight.setPower(strafeSpeed * 0.75);
                FrontLeft.setPower(strafeSpeed * 0.75);
                FrontRight.setPower(-strafeSpeed * 0.75);
            }
            //Changes position of claim servo based on driver controller A or B
            if(gamepad1.a) {
                svClaim.setPosition(1);
            }
            if(gamepad1.b) {
                svClaim.setPosition(0);
            }
            //Untuck Left Wheels
            if(gamepad2.left_trigger > 0) {
                MotorWheelTuckL.setPower(gamepad2.left_trigger);
            }
            //Tuck left wheels
            if(gamepad2.left_bumper) {
                MotorWheelTuckL.setPower(-1);
            }
            //Untuck right wheels
            if(gamepad2.right_trigger > 0) {
                MotorWheelTuckR.setPower(-gamepad2.right_trigger);
            }
            //Tuck left wheels
            if(gamepad2.right_bumper) {
                MotorWheelTuckR.setPower(1);
            }
            else {
                BackRight.setPower(0);
                BackLeft.setPower(0);
                FrontRight.setPower(0);
                FrontLeft.setPower(0);
                MotorWheelTuckR.setPower(0);
                MotorWheelTuckL.setPower(0);
            }
        }
    }
}