/*
Demo autonomous code created by Will Richards for FTC 2019

The following code is to demo the use of color sensors in an auto period

!!!IMPORTANT!!! This code is a demo, the comments are excessive on purpose don't comment this much normally but defiantly comment your code
Comment large blocks of code that need description, or anything that needs a description... Be concise
Also if you have alot of unorganized code somewhere insert //region [NAME HERE] at the start and //endregion at the end and it will allow you to collapse that specific section of code
Try to keep use of regions to a minimal
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "ColorSensorDemo", group = "Demo")
public class ColorSensorDemo extends LinearOpMode {

    //Variables created for the two back motors
    private DcMotor MotorLeftBack;
    private DcMotor MotorRightBack;

    //Color sensor variables
    private ColorSensor ColorSensor;

    public void runOpMode(){

        //Initializes motor variables
        MotorLeftBack = hardwareMap.get(DcMotor.class, "MotorLB");
        MotorRightBack = hardwareMap.get(DcMotor.class, "MotorRB");

        //Init color sensor
        ColorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");

        waitForStart();

        //Turn on the LED (if it has one) at the start of the match
        //ColorSensor.enableLed(true);
        String color = "None";

        while (opModeIsActive()) {


            //Output the reads for the RGB values of the color sensor and update them while robot is running
            telemetry.addData("Red Value: ", ColorSensor.red());
            telemetry.addData("Green Value: ", ColorSensor.green());
            telemetry.addData("Blue Value: ", ColorSensor.blue());
            telemetry.addData("Color: ", color);
            telemetry.update();

            if(ColorSensor.blue() > 100 && ColorSensor.red() > 100 && ColorSensor.green() > 100){
                color = "White";
            }
            else if(ColorSensor.blue() < 100 && ColorSensor.blue() > 50){
                color = "Yellow";
            }
            else{
                color = "None";
            }

        }

        //Example use of ColorSensor.red() in a situation
        //Uses the color sensor to check if the red value is greater than 2 to verify it is actually red and not just a false reading
        /*
            if(ColorSensor.red() > 2){
                //Drive forward
            }

            else{
                //Do something else
            }
        */


    }
}
