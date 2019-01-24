package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "MainTeleOp", group = "DeLorean")
public class MainTeleOp extends LinearOpMode {
    private DcMotor dcBackLeft;
    private DcMotor dcBackRight;
    private DcMotor dcFrontLeft;
    private DcMotor dcFrontRight;
   // private DcMotor dcTuckLeft;
    private DcMotor dcIntake;
    private DcMotor dcConveyor;
    private DcMotor dcLift;

    //Declare any other motors (servos, etc.)
    private Servo svClaim;
    private Servo svConveyor;
    private Servo svSweeper;

    // Max turn speed variable
    private double speedLimit;

    //Declare sensors

    public void runOpMode() {
        //Motor
        dcBackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        dcBackRight = hardwareMap.get(DcMotor.class, "BackRight");
        dcFrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        dcFrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        //dcTuckLeft = hardwareMap.get(DcMotor.class, "WheelTuckLeft");
        dcIntake = hardwareMap.get(DcMotor.class, "Intake");
        dcConveyor = hardwareMap.get(DcMotor.class, "Conveyor");
        dcLift = hardwareMap.get(DcMotor.class, "Lift");

        //Servo
        svClaim = hardwareMap.get(Servo.class, "svClaim");
        svConveyor = hardwareMap.get(Servo.class, "svConveyor");
        svSweeper = hardwareMap.get(Servo.class, "svSweeper");

        //Reverse left motors so forward is the same for all motors
        dcBackLeft.setDirection(DcMotor.Direction.REVERSE);
        dcFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        //Declare strafeSpeed variable
        double strafeSpeed = .75;

        // Initialize speed limit
        speedLimit = 0.75;

        waitForStart();

        while (opModeIsActive()) {
            dcBackLeft.setPower(gamepad1.left_stick_y);
            dcBackRight.setPower(gamepad1.left_stick_y);
            dcFrontLeft.setPower(gamepad1.right_stick_y);
            dcFrontRight.setPower(gamepad1.right_stick_y);

            dcIntake.setPower(gamepad2.left_trigger);
            dcConveyor.setPower(gamepad2.right_trigger);
            dcLift.setPower(gamepad2.left_stick_y);


            if(gamepad1.a) {
                svSweeper.setPosition(1);
            }
            else {
                svSweeper.setPosition(0.5);
            }

            if(gamepad1.b) {
                svConveyor.setPosition(1);
            }
            else {
                svConveyor.setPosition(0.5);
            }

            if(gamepad1.x){
                svClaim.setPosition(1);
            }
            else {
                svClaim.setPosition(0);
            }

            if (gamepad1.dpad_left){
                dcFrontLeft.setPower(-1);
                dcBackLeft.setPower(1);
                dcFrontRight.setPower(-1);
                dcBackRight.setPower(1);
            }
            if (gamepad1.dpad_right){
                dcFrontLeft.setPower(1);
                dcBackLeft.setPower(-1);
                dcFrontRight.setPower(1);
                dcBackRight.setPower(-1);
            }

        }
    }
}