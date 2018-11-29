/*
Demo autonomous code created by Will Richards for FTC 2019
The following code is to demo the use of color sensors in an auto period
!!!IMPORTANT!!! This code is a demo, the comments are excessive on purpose don't comment this much normally but defiantly comment your code
Comment large blocks of code that need description, or anything that needs a description... Be concise
Also if you have alot of unorganized code somewhere insert //region [NAME HERE] at the start and //endregion at the end and it will allow you to collapse that specific section of code
Try to keep use of regions to a minimal
*/

package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "ColorSensorDemo", group = "Demo")

public class ColorSensorDemo extends LinearOpMode {

    //Variables created for the two back motors
    private DcMotor MotorLB;
    private DcMotor MotorRB;

    private Servo FlipperServo;

    //Color sensor variables
    private ColorSensor ColorSensor;

    public void runOpMode() {

        //Initializes motor variables
        FlipperServo = hardwareMap.get(Servo.class, "Flipper");

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

            if (ColorSensor.red() > ColorSensor.green() && ColorSensor.blue() < (2 * ColorSensor.green()) / 3) {
                color = "Yellow";
                FlipperServo.setPosition(0.8);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                FlipperServo.setPosition(0);
            } else {
                color = "None";
                idle();
            }

        }


    }
}