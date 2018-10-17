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

    public void runOpMode() throws InterruptedException {

        //Initializes motor variables
        MotorLeftBack = hardwareMap.get(DcMotor.class, "MotorLB");
        MotorRightBack = hardwareMap.get(DcMotor.class, "MotorRB");

        //Sets the left motor to
        MotorLeftBack.setDirection(DcMotor.Direction.REVERSE);

        //Waits until the start button is pressed
        waitForStart();

        //Drives straight forward roughly 12 inches
        DriveToDistance(1, 1);

        Thread.sleep(2000);

        //SHOULD turn 90 degrees to the right
        TurnToDegrees(90, 1, true);

        Thread.sleep(2000);

        DriveToDistance(1,-1);

        Thread.sleep(2000);

        TurnToDegrees(90,1,false);


    }


    private void TurnToDegrees(double degrees, double motorSpeed, boolean turnRight){
        //Converts degrees into ticks
        final double CONVERSION_FACTOR = 1.25 * 2;

        double ticks = (degrees * CONVERSION_FACTOR);

        MotorLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Then it switches the encoders in a mode where it will drive the specified distance no matter what
        MotorLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if(turnRight)
        {
            //Sets the number of ticks the motor needs to move while setting
            MotorLeftBack.setTargetPosition((int)ticks );
            MotorRightBack.setTargetPosition(-(int)ticks);

            //It then sets the power to one and moves forward
            MotorLeftBack.setPower(motorSpeed);
            MotorRightBack.setPower(-motorSpeed);
        }
        else {
            //Sets the number of ticks the motor needs to move while setting
            MotorLeftBack.setTargetPosition(-(int)ticks );
            MotorRightBack.setTargetPosition((int)ticks);

            //It then sets the power to one and moves forward
            MotorLeftBack.setPower(-motorSpeed);
            MotorRightBack.setPower(motorSpeed);
        }


        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() && MotorLeftBack.isBusy() && MotorRightBack.isBusy()) {
            idle();
        }

        //After it has moved the desired amount break the wheels
        MotorRightBack.setPower(0);
        MotorLeftBack.setPower(0);

    }

    private void DriveToDistance(double revCount, double motorSpeed){
        double distance = REV_TICK_COUNT * revCount;

        MotorLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        //Then it switches the encoders in a mode where it will drive the specified distance no matter what
        MotorLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if(motorSpeed < 0){
            //!!! 1 revolution(288 ticks) equals 1ft !!! Then it tells the encoders how many times the wheel needs to spin based on the variable values assigned above
            MotorLeftBack.setTargetPosition(-(int)distance);
            MotorRightBack.setTargetPosition(-(int)distance);
        }
        else{
            //!!! 1 revolution(288 ticks) equals 1ft !!! Then it tells the encoders how many times the wheel needs to spin based on the variable values assigned above
            MotorLeftBack.setTargetPosition((int)distance);
            MotorRightBack.setTargetPosition((int)distance);
        }


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
