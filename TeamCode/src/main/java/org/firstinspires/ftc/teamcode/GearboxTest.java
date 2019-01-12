package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "GearboxTest", group = "DeLorean")
public class GearboxTest extends LinearOpMode {
    private DcMotor motorL;
    private DcMotor motorR;

    public void runOpMode() {
        while (opModeIsActive()) {
            motorR.setPower(gamepad1.left_stick_y);
            motorL.setPower(gamepad1.left_stick_y);
        }

    }

}
