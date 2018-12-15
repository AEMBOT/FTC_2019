package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name = "GearboxTest", group = "DeLorean")
public class GearboxTest extends LinearOpMode {
    private final int REV_TICK_COUNT = 560;

    private DcMotor motorL;
    private DcMotor motorR;
    private DcMotor screw;

    public void runOpMode() {
        motorL = hardwareMap.get(DcMotor.class, "motorL");
        motorR = hardwareMap.get(DcMotor.class, "motorR");
        screw = hardwareMap.get(DcMotor.class, "screw");

        waitForStart();

        while (opModeIsActive()) {
            motorR.setPower(gamepad1.left_stick_y);
            motorL.setPower(gamepad1.left_stick_y);

            if (gamepad1.a){
                screw.setPower(0.5);
            }
            if (gamepad1.b){
                screw.setPower(0);
            }


        }

    }
}
