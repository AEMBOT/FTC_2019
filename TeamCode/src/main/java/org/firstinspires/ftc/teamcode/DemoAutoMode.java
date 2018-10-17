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

        //Waits 2 seconds
        Thread.sleep(2000);

        //Turns 90 degrees to the right
        TurnToDegrees(90, 1, true);

        //Waits 2 seconds
        Thread.sleep(2000);

        //Drives directly backwards one revolution or 12 inches
        DriveToDistance(1,-1);

        //Waits 2 seconds
        Thread.sleep(2000);

        //Turns to the left 90 degrees
        TurnToDegrees(90,1,false);
    }


    private void TurnToDegrees(double degrees, double motorSpeed, boolean turnRight){
        //Converts degrees into ticks
        final double CONVERSION_FACTOR = 2.5;

        //Multiplies the number of degrees by the conversion factor to get the number of ticks for the specified degrees
        double ticks = (degrees * CONVERSION_FACTOR);

        //Resets encoder values
        MotorLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the encoders back up for accepting input
        MotorLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Checks if it is meant to turn right
        if(turnRight)
        {
            //Sets the number of ticks the motor needs to move
            MotorLeftBack.setTargetPosition((int)ticks );
            MotorRightBack.setTargetPosition(-(int)ticks);

            //It then sets the power of the motors accordingly to turn the robot to the right
            MotorLeftBack.setPower(motorSpeed);
            MotorRightBack.setPower(-motorSpeed);
        }

        //If false turn left
        else {

            //Sets the number of ticks the motor needs to turn left
            MotorLeftBack.setTargetPosition(-(int)ticks );
            MotorRightBack.setTargetPosition((int)ticks);

            //It then sets the power of the motors to turn left
            MotorLeftBack.setPower(-motorSpeed);
            MotorRightBack.setPower(motorSpeed);
        }


        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() && MotorLeftBack.isBusy() && MotorRightBack.isBusy()) {
            idle();
        }

        //After it has moved the desired amount brake the wheels
        MotorRightBack.setPower(0);
        MotorLeftBack.setPower(0);

    }

    private void DriveToDistance(double revCount, double motorSpeed){
        double distance = REV_TICK_COUNT * revCount;

        //Stops and resets encoders
        MotorLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Then it switches the encoders in a mode where it will drive the specified distance no matter what
        MotorLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Will check if the robot should be in reverse if so set encoder target accordingly
        if(motorSpeed < 0){

            //Sets the number of ticks to negative to allow for reverse
            MotorLeftBack.setTargetPosition(-(int)distance);
            MotorRightBack.setTargetPosition(-(int)distance);
        }

        //Will run if robot is set to move forward
        else{

            //Roughly the same as the code above but this will move forward
            MotorLeftBack.setTargetPosition((int)distance);
            MotorRightBack.setTargetPosition((int)distance);
        }


        //It then sets the power to the motors to allow for forward movement
        MotorLeftBack.setPower(motorSpeed);
        MotorRightBack.setPower(motorSpeed);

        //This will stall until the motors are done moving forward at which point this loop is broken and thus the loop is broken and the code may proceed
        while (opModeIsActive() && MotorLeftBack.isBusy() && MotorRightBack.isBusy()) {
            idle();
        }

        //After it has moved the desired amount brake the wheels
        MotorRightBack.setPower(0);
        MotorLeftBack.setPower(0);
    }
}
