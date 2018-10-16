package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "DemoAutoMode", group = "Demo")
public class DemoAutoMode extends LinearOpMode {


    //Variables created for the two back motors
    private DcMotor MotorLeftBack;
    private DcMotor MotorRightBack;

    //Creates a constant variable with the value of 288 or one revolution
    private final int REV_TICK_COUNT = 288;

    public void runOpMode(){

        //Initializes motor variables
        MotorLeftBack = hardwareMap.get(DcMotor.class, "MotorLB");
        MotorRightBack = hardwareMap.get(DcMotor.class, "MotorRB");

        //Sets the left motor to
        MotorLeftBack.setDirection(DcMotor.Direction.REVERSE);


        //So when "Init" is pressed on the control it stops the encoders and resets the encoders to their default value
        MotorLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Then it switches the encoders in a mode where it will drive the specified distance no matter what
        MotorLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        //Waits until the start button is pressed
        waitForStart();

        //Drives straight forward roughly 12 inches
        DriveToDistance(1, 1);
    }


    public void TurnToDegrees(double degrees, double motorSpeed){
        final double CONVERSION_FACTOR = 0.8;
        double steps = (degrees * CONVERSION_FACTOR);

        //Sets the number of ticks the motor needs to move while setting
        MotorLeftBack.setTargetPosition(-(int)steps);
        MotorRightBack.setTargetPosition((int)steps);

        //It then sets the power to one and moves forward
        MotorLeftBack.setPower(motorSpeed);
        MotorRightBack.setPower(motorSpeed);

        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() && MotorLeftBack.isBusy() && MotorRightBack.isBusy()) {
            idle();
        }

        //After it has moved the desired amount break the wheels
        MotorRightBack.setPower(0);
        MotorLeftBack.setPower(0);

    }

    public void DriveToDistance(double revCount, double motorSpeed){
        double distance = REV_TICK_COUNT * revCount;

        //!!! 1 revolution(288 ticks) equals 1ft !!! Then it tells the encoders how many times the wheel needs to spin based on the variable values assigned above
        MotorLeftBack.setTargetPosition((int)distance);
        MotorRightBack.setTargetPosition((int)distance);

        //It then sets the power to one and moves forward
        MotorLeftBack.setPower(motorSpeed);
        MotorRightBack.setPower(motorSpeed);

        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() && MotorLeftBack.isBusy() && MotorRightBack.isBusy()) {
            idle();
        }

        //After it has moved the desired amount break the wheels
        MotorRightBack.setPower(0);
        MotorLeftBack.setPower(0);
    }
}
