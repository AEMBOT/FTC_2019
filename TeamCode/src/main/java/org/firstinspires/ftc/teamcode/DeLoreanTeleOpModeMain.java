package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name = "DeLoreanTeleOpModeMain", group = "DeLorean")
public class DeLoreanTeleOpModeMain extends LinearOpMode {
    private DcMotor BackLeft;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor MotorWheelR;
    private DcMotor MotorWheelL;

    //Declare any other motors (servos, etc.)

    //Declare sensors
    //private ColorSensor ColorSensorR;
    //private ColorSensor ColorSensorL;

    public void runOpMode() {
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");

        //ColorSensorR = hardwareMap.get(ColorSensor.class, "ColorSensorR");
        //ColorSensorL = hardwareMap.get(ColorSensor.class, "ColorSensorL");

        //Reverse left motors so forward is the same for all motors
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

         while (opModeIsActive()) {
             BackRight.setPower(gamepad1.right_stick_y);
             BackLeft.setPower(gamepad1.left_stick_y);
             FrontRight.setPower(gamepad1.right_stick_y);
             FrontLeft.setPower(gamepad1.left_stick_y);

             if(gamepad1.dpad_left) {
                 Strafe(1, DeLoreanAutomodeMain.Direction.LEFT);
             }
             else if(gamepad1.dpad_right) {
                 Strafe(1, DeLoreanAutomodeMain.Direction.RIGHT);
             }
             else {
                 BackRight.setPower(0);
                 BackLeft.setPower(0);
                 FrontRight.setPower(0);
                 FrontLeft.setPower(0);
                 MotorWheelR.setPower(0);
                 MotorWheelL.setPower(0);
             }
         }
    }
    private void Strafe(double motorSpeed, DeLoreanAutomodeMain.Direction strafeDirection) {

        //region Unnecessary code from AutoMode
        //Converts degrees into ticks
        //double totalDistance = (REV_TICK_COUNT / 12.566) * distance;
        /*
        //Resets encoder values
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the encoders back up for accepting input
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        */
        //endregion

        if(strafeDirection == DeLoreanAutomodeMain.Direction.RIGHT) {
            BackLeft.setPower(motorSpeed);
            BackRight.setPower(-motorSpeed);
            FrontLeft.setPower(-motorSpeed);
            FrontRight.setPower(motorSpeed);
        }

        //Otherwise strafe left
        else {
            BackLeft.setPower(motorSpeed);
            BackRight.setPower(-motorSpeed);
            FrontLeft.setPower(-motorSpeed);
            FrontRight.setPower(motorSpeed);

        }

        //region Unnecessary code from AutoMode

        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        /*
        while (opModeIsActive() && BackLeft.isBusy() && BackRight.isBusy() && FrontLeft.isBusy() && FrontRight.isBusy()) {

            idle();
        }
        */

        /*
        //After it has moved the desired amount brake the wheels
        BackRight.setPower(0);
        BackLeft.setPower(0);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        */
        //endregion
    }
}
