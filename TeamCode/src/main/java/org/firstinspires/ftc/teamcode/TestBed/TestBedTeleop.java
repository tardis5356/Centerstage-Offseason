package org.firstinspires.ftc.teamcode.TestBed;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TestBedTeleop", group = "TestBed")
public class TestBedTeleop extends LinearOpMode {

    private GamepadEx driver1, driver2;
    private DcMotorEx mFL, mFR, mBL, mBR;
    double FB, LR, Rotation;


    @Override
    public void runOpMode(){
        driver1 = new GamepadEx(gamepad1);
        driver2 = new GamepadEx(gamepad2);

        mFL = hardwareMap.get(DcMotorEx.class, "mFL");
        mFR = hardwareMap.get(DcMotorEx.class, "mFR");
        mBL = hardwareMap.get(DcMotorEx.class, "mBL");
        mBR = hardwareMap.get(DcMotorEx.class, "mBR");

        //this motor physically runs opposite. For convenience, reverse direction.
        mBR.setDirection(DcMotorSimple.Direction.REVERSE);
        //mFR.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            FB = gamepad1.left_stick_y;
            LR = -gamepad1.left_stick_x;
            Rotation = -gamepad1.right_stick_x;

            //map motor power to vars (tb tested)
            //depending on the wheel, forward back, left right, and rotation's power may be different
            //think, if fb is positive, thus the bot should move forward, will the motor drive the bot forward if its power is positive.
            mFL.setPower(FB + LR + Rotation);
            mFR.setPower(FB - LR - Rotation);
            mBL.setPower(FB - LR + Rotation);
            mBR.setPower(FB + LR - Rotation);
        }
    }
}
