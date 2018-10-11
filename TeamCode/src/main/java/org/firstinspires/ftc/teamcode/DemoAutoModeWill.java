package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "DemoAutoMode", group = "Demo")
@Disabled
public class DemoAutoModeWill extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    //All variables are declared with the modifier "private"
    //The following are the variables that will contain the drive motors and each of their respective data
    private DcMotor MotorLeftBack;
    private DcMotor MotorRightBack;

    private  final double     COUNTS_PER_MOTOR_REV    = 2240 ;    // eg: TETRIX Motor Encoder
    private final double     DRIVE_GEAR_REDUCTION    = 0.0;     // This is < 1.0 if geared UP
    private final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    private final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    private final double     DRIVE_SPEED             = 1;
    private final double     TURN_SPEED              = 0.5;

    public void runOpMode(){

        MotorLeftBack = hardwareMap.get(DcMotor.class, "MotorLB");
        MotorRightBack = hardwareMap.get(DcMotor.class, "MotorRB");


        //All drive code goes here
        MotorLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        //drive forward
        encoderDrive(DRIVE_SPEED, 48, -48, 5.0);
    }

    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = MotorLeftBack.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = MotorRightBack.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            MotorLeftBack.setTargetPosition(newLeftTarget);
            MotorRightBack.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            MotorLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            MotorRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            MotorLeftBack.setPower(Math.abs(speed));
            MotorRightBack.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (MotorLeftBack.isBusy() && MotorRightBack.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        MotorLeftBack.getCurrentPosition(),
                        MotorRightBack.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            MotorLeftBack.setPower(0);
            MotorRightBack.setPower(0);

            // Turn off RUN_TO_POSITION
            MotorLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            MotorRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
}