package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "DeTest", group = "DeLorean")
public class DeTest extends LinearOpMode {

    //Declares Motor Variables
    private DcMotor BackLeft;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor WheelTuckRight;
    private DcMotor WheelTuckLeft;

    //Declare color sensor(s) here

    //Used to specify direction for strafing/turning
    public enum Direction {RIGHT, LEFT}

    //Number of ticks per rotation for each motor type
    private final int REV_TICK_COUNT = 560;
    private final int LIFT_TICK_COUNT = 1120;

    public void runOpMode() throws InterruptedException {

        //Initialize motors and sensors
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        WheelTuckLeft = hardwareMap.get(DcMotor.class, "WheelTuckLeft");
        WheelTuckRight = hardwareMap.get(DcMotor.class, "WheelTuckRight");


        //ColorSensorL = hardwareMap.get(ColorSensor.class, "ColorSensorL");
        //ColorSensorR = hardwareMap.get(ColorSensor.class, "ColorSensorR");
        //May change to only 1 sensor because of height

        //Creates a local reference to VuforiaBase
        //VuforiaBase vuforiaBase = new VuforiaBase();

        //Reverse left motors so all motors rotate in the same direction at any given power level
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);

        double motorSpeed = 0.75;
        double turnSpeed = 0.75;

        //Wait for start button to be pressed
        waitForStart();

        Strafe(3, motorSpeed,Direction.RIGHT);

        DriveToDistance(12, motorSpeed);

        TurnOnTheSpot(180, motorSpeed, Direction.RIGHT);


    }
    private void Strafe(double distance, double motorSpeed, Direction strafeDirection){

        //Converts degrees into ticks
        double totalDistance = (REV_TICK_COUNT / 12.566) * distance; //totalDistance is never used. Remove it?

        //Resets motor encoders
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if(strafeDirection == Direction.RIGHT)
        {
            BackLeft.setTargetPosition((int)totalDistance);
            BackRight.setTargetPosition(-(int)totalDistance);
            FrontLeft.setTargetPosition(-(int)totalDistance);
            FrontRight.setTargetPosition((int)totalDistance);

            BackLeft.setPower(motorSpeed);
            BackRight.setPower(-motorSpeed);
            FrontLeft.setPower(-motorSpeed);
            FrontRight.setPower(motorSpeed);
        }
        else {
            BackLeft.setTargetPosition((int)totalDistance);
            BackRight.setTargetPosition(-(int)totalDistance);
            FrontLeft.setTargetPosition(-(int)totalDistance);
            FrontRight.setTargetPosition((int)totalDistance);

            BackLeft.setPower(motorSpeed);
            BackRight.setPower(-motorSpeed);
            FrontLeft.setPower(-motorSpeed);
            FrontRight.setPower(motorSpeed);
        }
        //Stalls until motors are done
        while (opModeIsActive() && BackLeft.isBusy() && BackRight.isBusy() && FrontLeft.isBusy() && FrontRight.isBusy()) {
            idle();
        }
        BackRight.setPower(0);
        BackLeft.setPower(0);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
    }
    private void TurnOnTheSpot(double degrees, double motorSpeed, Direction turnDirection){
        //Converts degrees into ticks
        final double CONVERSION_FACTOR = 5;
        double ticks = (degrees * CONVERSION_FACTOR);

        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if(turnDirection == Direction.RIGHT)
        {
            BackLeft.setTargetPosition((int)ticks );
            BackRight.setTargetPosition(-(int)ticks);
            FrontLeft.setTargetPosition((int)ticks );
            FrontRight.setTargetPosition(-(int)ticks);

            BackLeft.setPower(motorSpeed);
            BackRight.setPower(-motorSpeed);
            FrontLeft.setPower(motorSpeed);
            FrontRight.setPower(-motorSpeed);
        }
        else {
            BackLeft.setTargetPosition(-(int)ticks);
            BackRight.setTargetPosition((int)ticks);
            FrontLeft.setTargetPosition(-(int)ticks);
            FrontRight.setTargetPosition((int)ticks);

            BackLeft.setPower(-motorSpeed);
            BackRight.setPower(motorSpeed);
            FrontLeft.setPower(-motorSpeed);
            FrontRight.setPower(motorSpeed);
        }

        while (opModeIsActive() &&BackLeft.isBusy() && BackRight.isBusy() && FrontLeft.isBusy() && FrontRight.isBusy()) {
            idle();
        }



        BackRight.setPower(0);
        BackLeft.setPower(0);
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
    }

    private void Land(double rotations, double motorSpeedTuck){
        double totalRotation = REV_TICK_COUNT * rotations;

        WheelTuckLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        WheelTuckRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        WheelTuckLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        WheelTuckRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (motorSpeedTuck < 0) {

            WheelTuckLeft.setTargetPosition(-(int) totalRotation);
            sleep(1000);
            WheelTuckRight.setTargetPosition(-(int) totalRotation);
        }

        else{

            WheelTuckLeft.setTargetPosition((int) totalRotation);
            sleep(1000);
            WheelTuckRight.setTargetPosition((int) totalRotation);
        }

        while (opModeIsActive() && WheelTuckLeft.isBusy()) {

            idle();
        }
    }



    //region Duplicate turn function
    /*
    private void TurnToDegrees(double degrees, double motorSpeed, Direction turnDirection){
        //Converts degrees into ticks
        final double CONVERSION_FACTOR = 2.5;
        double ticks = (degrees * CONVERSION_FACTOR);
        sleep(100);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if(turnDirection == Direction.LEFT) {
            BackLeft.setTargetPosition((int)ticks);
            BackRight.setTargetPosition(-(int)ticks);
            FrontLeft.setTargetPosition((int)ticks);
            FrontRight.setTargetPosition(-(int)ticks);
            //It then sets the power of the motors accordingly to turn the robot to the right
            BackLeft.setPower(motorSpeed);
            BackRight.setPower(-motorSpeed);
        }
        //If false turn left
        else {
            //Sets the number of ticks the motor needs to turn left
            BackLeft.setTargetPosition(-(int)ticks);
            BackRight.setTargetPosition((int)ticks);
            //It then sets the power of the motors to turn left
            BackLeft.setPower(-motorSpeed);
            BackRight.setPower(motorSpeed);
        }
        //This will stall until the motors are done moving forward at which point this loop is broken and the code may proceed
        while (opModeIsActive() && BackLeft.isBusy() && BackRight.isBusy()) {
            idle();
        }
        //After it has moved the desired amount brake the wheels
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }
    */
    //endregion

    //This method can be called when you want the robot to drive a certain distance in INCHES at a certain speed
    private void DriveToDistance(double distance, double motorSpeed){
        //! 1 rev is 12.566 inches !
        double totalDistance = (REV_TICK_COUNT / 12.566) * distance;

        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if(motorSpeed < 0){
            BackLeft.setTargetPosition(-(int)totalDistance);
            BackRight.setTargetPosition(-(int)totalDistance);
            FrontLeft.setTargetPosition(-(int)totalDistance);
            FrontRight.setTargetPosition(-(int)totalDistance);
        }
        else {
            BackLeft.setTargetPosition((int)totalDistance);
            BackRight.setTargetPosition((int)totalDistance);
            FrontLeft.setTargetPosition((int)totalDistance);
            FrontRight.setTargetPosition((int)totalDistance);
        }

        BackLeft.setPower(motorSpeed);
        BackRight.setPower(motorSpeed);
        FrontLeft.setPower(motorSpeed);
        FrontRight.setPower(motorSpeed);

        while (opModeIsActive() &&BackLeft.isBusy() && BackRight.isBusy()) {
            idle();
        }

        BackRight.setPower(0);
        BackLeft.setPower(0);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
    }

    private boolean SenseYellow(ColorSensor sensor){
        boolean isYellow;

        //White
        if(sensor.blue() > 100 && sensor.red() > 100 && sensor.green() > 100){
            isYellow = false;
        }
        //Yellow
        else if(sensor.blue() < 100 && sensor.blue() > 50){
            isYellow = true;
        }
        //Invalid color
        else{
            isYellow = false;
        }
        return isYellow;
    }
    //Lift function here
}
