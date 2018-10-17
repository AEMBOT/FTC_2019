/*
Demo TeleOP code created by Will Richards for FTC 2019(Old 2018 Bot)

!!!IMPORTANT!!! This code is a demo, the comments are excessive on purpose don't comment this much normally but defiantly comment your code because there are 4-5 of you
Comment large blocks of code that need description, or anything that needs a description
Also if you have alot of unorganized code somewhere insert //region [NAME HERE] at the start and //endregion at the end and it will allow you to collapse that specific section of code
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
    private DcMotor MotorLeftBack;
    private DcMotor MotorRightBack;

    private DcMotor ArmMotor;

    //Always separate each set of variable types... it just looks nicer
    private ColorSensor DemoColorSensor;

    //Where the majority of your code will go some variables are defined above this outside the runOpMode method
    public void runOpMode()
    {

        //The following assigns the corresponding motor variable to the  variable name on the phone which corresponds to a motor connection # on the REV hub
        //!!!IMPORTANT!!! If the names are changed on the phones such as "MotorLF" is changed to "MotorLeftFront" the code following must be updated
        MotorLeftBack = hardwareMap.get(DcMotor.class, "MotorLB");
        MotorRightBack = hardwareMap.get(DcMotor.class, "MotorRB");
        ArmMotor = hardwareMap.get(DcMotor.class, "ArmMotor");

        DemoColorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");
        //Needs to be reversed to move forward

        MotorRightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        //After initialization of variables has completed wait until the game starts(The play button is pressed)
        waitForStart();


        //Will loop until the stop button is pressed on the controller
        while (opModeIsActive())
        {
            //Should in theory work, however I haven't touched this code in a year so it might take some time to get this working again
            //Basically what this does is set the power of the motor to the y value on the stick corresponding to that side thus creating tank drive(In theory)
            MotorRightBack.setPower(gamepad1.right_stick_y);
            MotorLeftBack.setPower(gamepad1.left_stick_y);


            //Will be called if A on controller 1 is pressed
            if(gamepad1.a)
            {
                //Works much like a System.out.println(); or a print(""); or a Console.writeln(""); only difference is it prints it on the RobotDriverStation
                telemetry.addData("Value of A: ", "Pushed");
                telemetry.update();
            }

        }
    }
}
