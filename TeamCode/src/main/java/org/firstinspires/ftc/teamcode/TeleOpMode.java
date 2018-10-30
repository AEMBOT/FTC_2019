/*
Demo TeleOP code created by Will Richards for FTC 2019

Bare bones driver controlled teleop code

!!!IMPORTANT!!! This code is a demo, the comments are excessive on purpose don't comment this much normally but defiantly comment your code
Comment large blocks of code that need description, or anything that needs a description... Be concise
Also if you have alot of unorganized code somewhere insert //region [NAME HERE] at the start and //endregion at the end and it will allow you to collapse that specific section of code
Try to keep use of regions to a minimal
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

//Declares a drive controlled TeleOp mode
//@Autonomous(name = "AutoModeMain", group = "Demo") this is what a autonomous mode would look like
@TeleOp(name = "TeleOpMode", group = "Main")
public class TeleOpMode extends LinearOpMode {

    //All variables are declared with the modifier "private"
    //The following are the variables that will contain the drive motors and each of their respective data

    //REV Motors
    private DcMotor MotorLB;
    private DcMotor MotorRB;
    private DcMotor MotorUp;
    private DcMotor MotorDown;

    //Servos
    private Servo ServoFlipper;

    //Sensors
    private ColorSensor DemoColorSensor;

    //Assigns Motors to match the configuration
    public void runOpMode()
    {

        //The following assigns the corresponding motor variable to the  variable name on the phone which corresponds to a motor connection # on the REV hub
        //!!!IMPORTANT!!! If the names are changed on the phones such as "MotorLF" is changed to "MotorLF" the code following must be updated
        MotorLB = hardwareMap.get(DcMotor.class, "MotorLB");
        MotorRB = hardwareMap.get(DcMotor.class, "MotorRB");
        MotorUp = hardwareMap.get(DcMotor.class, "LiftUp");
        MotorDown = hardwareMap.get(DcMotor.class, "LiftDown");
        ServoFlipper = hardwareMap.get(Servo.class, "Flipper");

        DemoColorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");
        //Needs to be reversed to move forward

        MotorRB.setDirection(DcMotorSimple.Direction.REVERSE);

        //After initialization of variables has completed wait until the game starts(The play button is pressed)
        waitForStart();


        //Will loop until the stop button is pressed on the controller
        while (opModeIsActive())
        {
            //Button Mapping to analog sticks

            //Wheels
            MotorRB.setPower(gamepad1.right_stick_y);
            MotorLB.setPower(gamepad1.left_stick_y);

            //lift
            MotorUp.setPower(gamepad2.left_stick_y / 2);
            MotorDown.setPower(gamepad2.left_stick_y / 2);

            //Why is this here Will?
            //Will be called if A on controller 1 is pressed
            /* if(gamepad1.a)
            {
                //Works much like a System.out.println(); or a print(""); or a Console.writeln(""); only difference is it prints it on the RobotDriverStation
                telemetry.addData("Value of A: ", "Pushed");
                telemetry.update();
            } */

            //Flipper code.
            if(gamepad1.a) {
                // move to 0 degrees.
                ServoFlipper.setPosition(0);
            } else if (gamepad1.x || gamepad1.b) {
                // move to 90 degrees.
                ServoFlipper.setPosition(90/*may be .5*/);
            } else if (gamepad1.y) {
                // move to 180 degrees.
                ServoFlipper.setPosition(180/*may be 1 */);
            }
            telemetry.addData("Servo Position", ServoFlipper.getPosition());

            //comment added so I can push
        }
    }
}
