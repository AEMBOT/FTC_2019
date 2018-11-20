package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name = "DeLoreanTeleOpModeMain", group = "DeLorean")
public class DeLoreanTeleOpModeMain extends LinearOpMode {
    private DcMotor BackLeft;
    private DcMotor BackRight;
    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor MotorWheelTuckR;
    private DcMotor MotorWheelTuckL;
    private Servo Flipper;

    //Declare any other motors (servos, etc.)
    private Servo intakeServo;
    private Servo Flipper;

    //Declare sensors
    //private ColorSensor ColorSensorR;
    //private ColorSensor ColorSensorL;

    public void runOpMode() {
         //Motor
         BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
         BackRight = hardwareMap.get(DcMotor.class, "BackRight");
         FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
         FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
         MotorWheelTuckL = hardwareMap.get(DcMotor.class, "WheelTuckLeft");
         MotorWheelTuckR = hardwareMap.get(DcMotor.class, "WheelTuckRight");

         //Servo
         //intakeServo = hardwareMap.get(Servo.class, "Sweeper");
         Flipper = hardwareMap.get(Servo.class, "Flipper" );
         //Sensors
         //ColorSensorR = hardwareMap.get(ColorSensor.class, "ColorSensorR");
         //ColorSensorL = hardwareMap.get(ColorSensor.class, "ColorSensorL");

         //Reverse left motors so forward is the same for all motors
         BackLeft.setDirection(DcMotor.Direction.REVERSE);
         FrontLeft.setDirection(DcMotor.Direction.REVERSE);

         //Declare strafeSpeed variable
         double strafeSpeed = .75;
         boolean isServoRunning = true;

         waitForStart();

         while (opModeIsActive()) {
             //Driving NOT strafing
             BackRight.setPower(-gamepad1.right_stick_y);
             BackLeft.setPower(-gamepad1.left_stick_y);
             FrontRight.setPower(-gamepad1.right_stick_y);
             FrontLeft.setPower(-gamepad1.left_stick_y);

             //Strafe left
             if(gamepad1.dpad_left) {
                 BackLeft.setPower(strafeSpeed);
                 BackRight.setPower(-strafeSpeed);
                 FrontLeft.setPower(-strafeSpeed);
                 FrontRight.setPower(strafeSpeed);

             }
             //Strafe right
             if(gamepad1.dpad_right) {
                 BackLeft.setPower(-strafeSpeed);
                 BackRight.setPower(strafeSpeed);
                 FrontLeft.setPower(strafeSpeed);
                 FrontRight.setPower(-strafeSpeed);
             }

             /*
             //Tuck wheels into robot
             if(gamepad2.left_bumper) {
                 MotorWheelTuckL.setPower(-0.25);
                 //encoder value tells it when to reduce power to .20 to hold position
                 sleep(500);
                 MotorWheelTuckR.setPower(0.25);
                 sleep(500);
                 //encoder value tells it when to reduce power to .20 to hold position
             }
             //Untuck wheels
             if(gamepad2.right_bumper) {
                 MotorWheelTuckL.setPower(0.25);
                 //encoder value tells it when to reduce power to .20 to hold position
                 sleep(500);
                 MotorWheelTuckL.setPower(0.2);
                 MotorWheelTuckR.setPower(-0.25);
                 sleep(500);
                 MotorWheelTuckR.setPower(-0.2);
                 //encoder value tells it when to reduce power to .20 to hold position

             }
             */
             //Changes position of servo based on driver controller A or B
             if(gamepad1.a) {
                 Flipper.setPosition(0.2);
             }
             if(gamepad1.b) {
                 Flipper.setPosition(0.6);
             }
             //Untuck Left Wheels
             if(gamepad2.left_trigger > 0) {//y
                 MotorWheelTuckL.setPower(-gamepad2.left_trigger);
             }
             //Tuck left wheels
             if(gamepad2.left_bumper) {//a
                 MotorWheelTuckL.setPower(1);
             }
             //Untuck right wheels
             if(gamepad2.right_trigger > 0) {//x
                 MotorWheelTuckR.setPower(-gamepad2.right_trigger);
             }
             //Tuck left wheels
             if(gamepad2.right_bumper) {//b
                 MotorWheelTuckR.setPower(1);
             }

             //region When A is pressed toggle servo running and stopping
             /* if(gamepad2.a){
                 isServoRunning = !isServoRunning;

                 if(isServoRunning){
                     Flipper.setPosition(1);
                 }
                 else{
                     Flipper.setPosition(0.5);
                 }
             }
             */
             /*
              if(gamepad2.a){
                isServoRunning = !isServoRunning;

                 if(isServoRunning){
                     intakeServo.setPosition(1);
                 }
                 else{
                     intakeServo.setPosition(0);
                 }
             }
             */
             //endregion

             //region Removed for simplicity
             /* Removed for simplicity
             if(gamepad2.y) {
                 MotorWheelTuckR.setPower(0.5);
             }
             if(gamepad2.a) {
                 MotorWheelTuckR.setPower(-0.5);
             }
             */
             //endregion

             else {
                 BackRight.setPower(0);
                 BackLeft.setPower(0);
                 FrontRight.setPower(0);
                 FrontLeft.setPower(0);
                 MotorWheelTuckR.setPower(0);
                 MotorWheelTuckL.setPower(0);
             }
         }
    }
}