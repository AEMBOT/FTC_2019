/**
 * Program written by Troy Lopez and Zane OG for DeLorean 4.0 for Teleop to be used at qualifiers
 * 8 January, 2019
 */
package org.firstinspires.ftc.teamcode;

//Imports FTC code frames, auto fill, etc.
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "MainTeleOp", group = "DeLorean")
public class MainTeleOp extends LinearOpMode {
    //This makes the motor variables.
    private DcMotor dcBackLeft;
    private DcMotor dcBackRight;
    private DcMotor dcFrontLeft;
    private DcMotor dcFrontRight;
    // This Motor doesn't exist with the new chassis; it's here to show process
    // private DcMotor dcTuckLeft;
    private DcMotor dcIntake;
    private DcMotor dcConveyor;
    private DcMotor dcLift;

    //Declares any other motors (servos, etc.)
    private Servo svClaim;
    private Servo svConveyor;
    private Servo svSweeper;

    //Max turn speed variable
    //This was added because our drivers were crashing and to fast :(
    private double speedLimit;

    //Sensors are declared here
    //We don't have sensors! Thanks Mechanical :)

    //This is what happens during initialization, before start.
    public void runOpMode() {
        //Motors are assigned to variables here
        dcBackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        dcBackRight = hardwareMap.get(DcMotor.class, "BackRight");
        dcFrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        dcFrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        dcIntake = hardwareMap.get(DcMotor.class, "Intake");
        dcConveyor = hardwareMap.get(DcMotor.class, "Conveyor");
        dcLift = hardwareMap.get(DcMotor.class, "Lift");

        //Servos also are assigned here
        svClaim = hardwareMap.get(Servo.class, "svClaim");
        svConveyor = hardwareMap.get(Servo.class, "svConveyor");
        svSweeper = hardwareMap.get(Servo.class, "svSweeper");

        //Reverses left motors so that forward is the same for all motors
        dcBackLeft.setDirection(DcMotor.Direction.REVERSE);
        dcFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        //TODO: Implement speed limit for drivers
        // This "TO DO" was done in a newer version, but is here to show process

        // Initialize speed limit
        speedLimit = 0.75;

        //Hopefully this next line is self explanatory. Everything is paused until start is pressed.
        waitForStart();

        //This maps each function to a motor, so drivers can drive.
        while (opModeIsActive()) {
            //dcMotors are the motors, gamepad.___ is the button.
            dcBackLeft.setPower(gamepad1.left_stick_y);
            dcBackRight.setPower(gamepad1.right_stick_y);
            dcFrontLeft.setPower(gamepad1.left_stick_y);
            dcFrontRight.setPower(gamepad1.right_stick_y);

            dcIntake.setPower(gamepad2.left_trigger);
            dcConveyor.setPower(gamepad2.right_trigger);
            dcLift.setPower(gamepad2.left_stick_y);

            //When a is pressed a servo runs.
            if(gamepad1.a) {
                svSweeper.setPosition(1);
            }
            else {
                svSweeper.setPosition(0.5);
            }

            //When b is pressed a servo runs.
            if(gamepad1.b) {
                svConveyor.setPosition(1);
            }
            else {
                svConveyor.setPosition(0.5);
            }

            //When x is pressed a servo runs.
            if(gamepad1.x){
                svClaim.setPosition(1);
            }
            else {
                svClaim.setPosition(0);
            }
            //Useless strafe code.
            /*
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
            */
        }
    }
}