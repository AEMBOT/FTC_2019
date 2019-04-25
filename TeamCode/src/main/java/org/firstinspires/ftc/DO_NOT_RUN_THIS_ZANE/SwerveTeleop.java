/**
 * Program written by Zane Othman and Troy Lopez for DeLorean 4.0 as teleop
 * to be used at qualifiers
 * 8 January, 2019
 */
package org.firstinspires.ftc.DO_NOT_RUN_THIS_ZANE;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;


@TeleOp(name = "SwerveTeleop", group = "DeLorean")
public class SwerveTeleop extends LinearOpMode {
    private DcMotor dcBackLeft;
    private DcMotor dcBackRight;
    private DcMotor dcFrontLeft;
    private DcMotor dcFrontRight;
   // private DcMotor dcTuckLeft;
    private DcMotor dcIntake;
    private DcMotor dcConveyor;
    private DcMotor dcLift;
    private DcMotor dcHook;

    //Declare any other motors (servos, etc.)
    private Servo svFrontRight;
    private Servo svFrontLeft;
    private Servo svBackRight;
    private Servo svBackLeft;
    private Servo svClaim;
    private CRServo svConveyor;
    private CRServo svSweeper;
    private Servo svLock;

    // Max turn speed variable
    private double speedLimit;

    //Declare sensors

    public void runOpMode() {
        //Motor
        dcBackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        dcBackRight = hardwareMap.get(DcMotor.class, "BackRight");
        dcFrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        dcFrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        dcIntake = hardwareMap.get(DcMotor.class, "Intake");
        dcConveyor = hardwareMap.get(DcMotor.class, "Conveyor");
        dcLift = hardwareMap.get(DcMotor.class, "Lift");
        dcHook = hardwareMap.get(DcMotor.class, "Hook");
        svFrontRight = hardwareMap.get(Servo.class, "FrontRight");
        //Servo
        svFrontRight = hardwareMap.get(Servo.class, "FrontRight");
        svFrontLeft = hardwareMap.get(Servo.class, "FrontLeft");
        svBackRight = hardwareMap.get(Servo.class, "BackRight");
        svBackLeft = hardwareMap.get(Servo.class, "BackLeft");
        svClaim = hardwareMap.get(Servo.class, "svClaim");
        svConveyor = hardwareMap.get(CRServo.class, "svConveyor");
        svSweeper = hardwareMap.get(CRServo.class, "svSweeper");
        svLock = hardwareMap.get(Servo.class, "svLock");

        //Reverse left motors so forward is the same for all motors
        dcBackRight.setDirection(DcMotor.Direction.REVERSE);
        dcFrontRight.setDirection(DcMotor.Direction.REVERSE);

        //TODO: Implement speed limit for drivers
        // Initialize speed limit
        speedLimit = 0.7;

        waitForStart();

        while (opModeIsActive()) {
            dcBackLeft.setPower(speedLimit * gamepad1.left_stick_y);
            dcBackRight.setPower(speedLimit * gamepad1.left_stick_y);
            dcFrontLeft.setPower(speedLimit * gamepad1.left_stick_y);
            dcFrontRight.setPower(speedLimit * gamepad1.left_stick_y);
            if(gamepad1.right_stick_y > 0.1){
                svFrontRight.setPosition(gamepad1.right_stick_y);
                svFrontLeft.setPosition(gamepad1.right_stick_y);
                svBackRight.setPosition(gamepad1.right_stick_y);
                svBackLeft.setPosition(gamepad1.right_stick_y);
            }
            dcIntake.setPower(-gamepad2.right_trigger);
            dcIntake.setPower(gamepad2.left_trigger);
            dcLift.setPower(-gamepad2.right_stick_y);
            dcHook.setPower(gamepad2.left_stick_y);

            if(gamepad2.left_bumper) {
                dcConveyor.setPower(0.75);
            }
            else if(gamepad2.right_bumper) {
                dcConveyor.setPower(-0.75);
            }
            else {
                dcConveyor.setPower(0);
            }

            if(gamepad1.right_trigger > 0) {
                svSweeper.setPower(-1);
            }
            else if(gamepad1.left_trigger > 0) {
                svSweeper.setPower(1);
            }
            else {
                svSweeper.setPower(0);
            }

            if(gamepad1.right_bumper) {
                dcIntake.setPower(-1);
                svConveyor.setPower(-1);
            }
            else if(gamepad1.left_bumper) {
                dcIntake.setPower(1);
                svConveyor.setPower(1);
            }
            /*
            else if(gamepad1.left_bumper){
            svConveyor.setPosition(0.7);
            }
            else if(gamepad1.right_bumper){
            svConveyor.setPosition(0.5);
            else{
            svConveyor.setPosition(0);
            }
             */
            else {
                dcIntake.setPower(0);
                svConveyor.setPower(0);
            }

            if(gamepad1.x){
                svClaim.setPosition(0);
            }
            else {
                svClaim.setPosition(1);
            }

            if(gamepad2.x) {
                svLock.setPosition(1);
            }
            else if(gamepad2.b) {
                svLock.setPosition(0);
            }
        }
    }
}