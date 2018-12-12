package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "GearboxTest", group = "DeLorean")
public class GearboxTest extends LinearOpMode {

    // Declares Motor Variables
    private DcMotor motorL;
    private DcMotor motorR;


    // Number of ticks per rotation for drive motors
    private final int REV_TICK_COUNT = 560;

    public void runOpMode() {

        motorL = hardwareMap.get(DcMotor.class, "motorL");
        motorR = hardwareMap.get(DcMotor.class, "motorR");

        while(opModeIsActive()){
            motorL.setPower(0.2);
            motorR.setPower(0.2);
        }


    }
}


