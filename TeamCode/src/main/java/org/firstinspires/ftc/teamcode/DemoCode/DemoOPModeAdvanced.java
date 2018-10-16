package org.firstinspires.ftc.teamcode.DemoCode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "DemoTeleOpModeAdvanced", group = "Demo")
public class DemoOPModeAdvanced extends OpMode {

    //All variables are declared with the modifier "private"
    //The following are the variables that will contain the drive motors and each of their respective data
    private DcMotor MotorLeftBack;
    private DcMotor MotorRightBack;

    private ColorSensor DemoColorSensor;

    //Code that runs when the init button is pressed (Eg. Assign all variables)
    public void init(){
        telemetry.addData("Robot Status: ", "Initializing");

        //The following assigns the corresponding motor variable to the  variable name on the phone which corresponds to a motor connection # on the REV hub
        //!!!IMPORTANT!!! If the names are changed on the phones such as "MotorLF" is changed to "MotorLeftFront" the code following must be updated
        MotorLeftBack = hardwareMap.get(DcMotor.class, "MotorLB");
        MotorRightBack = hardwareMap.get(DcMotor.class, "MotorRB");
        DemoColorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");

        //Needs to be reversed to move forward
        MotorRightBack.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Robot Status: ", "Initialized");
    }

    //Code that runs once as soon as the play button is pressed
    public void start() { telemetry.addData("Robot Status: ", "Started"); }

    //Code inside will loop from the time the play button is pressed to the time it is stopped, all movement code goes here
    public void loop(){
        //Should in theory work, however I haven't touched this code in a year so it might take some time to get this working again
        //Basically what this does is set the power of the motor to the y value on the stick corresponding to that side thus creating tank drive(In theory)

        MotorRightBack.setPower(gamepad1.right_stick_y);
        MotorLeftBack.setPower(gamepad1.left_stick_y);


        //Alternatively you can set the motor power using a float as so...
        //A motors power can be set to anything between 0 and 1, because it accepts a value(variable) type called a double(floats work similar)
        if(gamepad1.dpad_up)
        {
            MotorRightBack.setPower(1);
            MotorLeftBack.setPower(1);
        }


        //Will be called if A on controller 1 is pressed
        if(gamepad1.a)
        {
            //Works much like a System.out.println(); or a print(""); or a Console.writeln(""); only difference is it prints it on the RobotDriverStation
            telemetry.addData("Value of A: ", "Pushed");
        }
    }

    //Code that runs once the stop button is pushed(Eg. Break Wheels)
    public void stop(){

    }
}
