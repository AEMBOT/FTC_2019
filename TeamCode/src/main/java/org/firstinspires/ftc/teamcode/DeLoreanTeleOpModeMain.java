package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "DeTeleOp", group = "DeLorean")
public class DeLoreanTeleOpModeMain extends LinearOpMode {
    private DcMotor dcBackLeft;
    private DcMotor dcBackRight;
    private DcMotor dcFrontLeft;
    private DcMotor dcFrontRight;
    //private DcMotor MotorWheelTuckR;
    private DcMotor dcTuckLeft;
    private DcMotor tetHookLift;
    private DcMotor dcScrew;

    //Declare any other motors (servos, etc.)
    private Servo svClaim;

    // Max turn speed variable
    private double speedLimit;

    //Declare sensors

    public void runOpMode() {
        //Motor
        dcBackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        dcBackRight = hardwareMap.get(DcMotor.class, "BackRight");
        dcFrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        dcFrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        dcTuckLeft = hardwareMap.get(DcMotor.class, "WheelTuckLeft");
        //MotorWheelTuckR = hardwareMap.get(DcMotor.class, "WheelTuckRight");
        tetHookLift = hardwareMap.get(DcMotor.class, "HookLift");
        dcScrew = hardwareMap.get(DcMotor.class, "screw");

        //Servo
        svClaim = hardwareMap.get(Servo.class, "svClaim");

        //Reverse left motors so forward is the same for all motors
        dcBackLeft.setDirection(DcMotor.Direction.REVERSE);
        dcFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        //Declare strafeSpeed variable
        double strafeSpeed = .75;

        // Initialize speed limit
        speedLimit = 0.75;

        waitForStart();

        while (opModeIsActive()) {
            //Driving NOT strafing
            dcBackRight.setPower(-gamepad1.right_stick_y * speedLimit);
            dcBackLeft.setPower(-gamepad1.left_stick_y * speedLimit);
            dcFrontRight.setPower(-gamepad1.right_stick_y * speedLimit);
            dcFrontLeft.setPower(-gamepad1.left_stick_y * speedLimit);

            // Move lift up or down
            tetHookLift.setPower(gamepad2.right_stick_y);

            //Strafe left
            if(gamepad1.dpad_left) {
                dcBackLeft.setPower(strafeSpeed * speedLimit);
                dcBackRight.setPower(-strafeSpeed * speedLimit);
                dcFrontLeft.setPower(-strafeSpeed * speedLimit);
                dcFrontRight.setPower(strafeSpeed * speedLimit);

            }
            //Strafe right
            if(gamepad1.dpad_right) {
                dcBackLeft.setPower(-strafeSpeed * speedLimit);
                dcBackRight.setPower(strafeSpeed * speedLimit);
                dcFrontLeft.setPower(strafeSpeed * speedLimit);
                dcFrontRight.setPower(-strafeSpeed * speedLimit);
            }

            //Changes position of claim servo based on driver controller A or B
            if(gamepad1.a) {
                svClaim.setPosition(1);
            }
            if(gamepad1.b) {
                svClaim.setPosition(0);
            }

            //TODO: Redo tucking code for Week 3 competition

            //region Old Tucking Code
            //Untuck Left Wheels
            /*
            if(gamepad2.left_trigger > 0) {
                dcTuckLeft.setPower(gamepad2.left_trigger);
            }
            //Tuck left wheels
            if(gamepad2.left_bumper) {
                dcTuckLeft.setPower(-1);
            }

            if (gamepad2.a){
                dcScrew.setPower(1);
            }
            if (gamepad2.b){
                dcScrew.setPower(0);
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
                dcBackRight.setPower(0);
                dcBackLeft.setPower(0);
                dcFrontRight.setPower(0);
                dcFrontLeft.setPower(0);
                MotorWheelTuckR.setPower(0);
                dcTuckLeft.setPower(0);
            }
            */
            //endregion
        }
    }
}