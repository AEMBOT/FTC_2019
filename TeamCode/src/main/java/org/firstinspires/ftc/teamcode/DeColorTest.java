package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name = "DeColorTest", group = "DeLorean")
public class DeColorTest extends LinearOpMode {

    private Servo svFlipper;

    // Declare color sensor(s) here
    private ColorSensor csMain;

    // Used to specify direction for strafing, turning, or later arch screw intake
    public enum direction { UP, DOWN, LEFT, RIGHT }

    // Number of ticks per rotation for drive motors
    private final int REV_TICK_COUNT = 560;

    public void runOpMode() {
        // Init color sensor
        csMain = hardwareMap.get(ColorSensor.class, "csMain");
        svFlipper = hardwareMap.get(Servo.class, "svFlipper");

        waitForStart();

        while(opModeIsActive()) {
        telemetry.addData("Red: ", csMain.red());
        telemetry.addData("Green: ", csMain.green());
        telemetry.addData("Blue: ", csMain.blue());
        telemetry.addData("Is it yellow? ", isItYellow());

        if(isItYellow()) {
            svFlipper.setPosition(0.2);
            sleep(1000);
            svFlipper.setPosition(1);
        }

        telemetry.update();
        }
    }
    private boolean isItYellow(){
        //Declare boolean isYellow and initialize it to false
        boolean isYellow = false;

        //Senses yellow
        if (csMain.red() > csMain.green() && csMain.blue() < (2 * csMain.green())  / 3) {
            isYellow = true;
        }

        //Return boolean value isYellow
        return isYellow;
    }
}


