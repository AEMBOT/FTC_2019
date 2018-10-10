/*
Demo TeleOP code created by Will Richards for FTC 2019(Old 2018 Bot)

!!!IMPORTANT!!! This code is a demo, the comments are excessive on purpose don't comment this much normally but defiantly comment your code because there are 4-5 of you
Comment large blocks of code that need description, or anything that needs a description
Also if you have alot of unorganized code somewhere insert //region [NAME HERE] at the start and //endregion at the end and it will allow you to collapse that specific section of code
*/


package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

//Declares This is a drive controlled TeleOp mode
//@Autonomous(name = "DemoAutoMode", group = "Demo") this is what a autonomous mode would look like
@TeleOp(name = "DemoTeleOpModeBasic", group = "Demo")
public class DemoOPModeBasic extends LinearOpMode {

    //All variables are declared with the modifier "private"
    //The following are the variables that will contain the drive motors and each of their respective data
    private DcMotor MotorLeftFront;
    private DcMotor MotorRightFront;
    private DcMotor MotorLeftBack;
    private DcMotor MotorRightBack;

    //Always separate each set of variable types... it just looks nicer
    private Servo DemoServo;

    private ColorSensor DemoColorSensor;

    //Where the majority of your code will go some variables are defined above this outside the runOpMode method
    public void runOpMode()
    {

        //The following assigns the corresponding motor variable to the  variable name on the phone which corresponds to a motor connection # on the REV hub
        //!!!IMPORTANT!!! If the names are changed on the phones such as "MotorLF" is changed to "MotorLeftFront" the code following must be updated
        //MotorLeftFront = hardwareMap.get(DcMotor.class, "MotorLF");
        //MotorRightFront = hardwareMap.get(DcMotor.class, "MotorRF");
        MotorLeftBack = hardwareMap.get(DcMotor.class, "MotorLB");
        MotorRightBack = hardwareMap.get(DcMotor.class, "MotorRB");

        //DemoServo = hardwareMap.get(Servo.class, "DemoServo");

        DemoColorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");
        //Needs to be reversed to move forward

        //MotorLeftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        //After initialization of variables has completed wait until the game starts(The play button is pressed)
        waitForStart();


        //Will loop until the stop button is pressed on the controller
        while (opModeIsActive())
        {
            //Should in theory work, however I haven't touched this code in a year so it might take some time to get this working again
            //Basically what this does is set the power of the motor to the y value on the stick corresponding to that side thus creating tank drive(In theory)
            //MotorRightFront.setPower(gamepad1.right_stick_y);
            MotorRightBack.setPower(-gamepad1.right_stick_y);
            //MotorLeftFront.setPower(gamepad1.left_stick_y);
            MotorLeftBack.setPower(gamepad1.left_stick_y);


            //Alternatively you can set the motor power using a float as so...
            //A motors power can be set to anything between 0 and 1, because it accepts a value(variable) type called a double(floats work similar)
            //if(gamepad1.dpad_up)
            //{
            //    //MotorRightFront.setPower(1);
            //    MotorRightBack.setPower(1);
            //    //MotorLeftFront.setPower(1);
            //    MotorLeftBack.setPower(1);
            //}


            //Will be called if A on controller 1 is pressed
            if(gamepad1.a)
            {
                //Works much like a System.out.println(); or a print(""); or a Console.writeln(""); only difference is it prints it on the RobotDriverStation
                telemetry.addData("Value of A: ", "Pushed");
            }

            //region Servo Controller
            //A servos position can range between 0 and 1 and works similar to the motors however it can be set to an exact spot as opposed to go, at this speed
            //The following code will increment the current servo position by 0.05 while it is pressed
            //if (gamepad1.right_bumper)
            //{
            //    DemoServo.setPosition(DemoServo.getPosition() + 0.05);
            //}
//
//
            ////This will lower the servo when left bumper is pressed
            //if(gamepad1.left_bumper)
            //{
            //    DemoServo.setPosition(DemoServo.getPosition() - 0.05);
            //}
            //endregion

        }
    }
}
