package org.firstinspires.ftc.teamcode.DEMOS.SHOCKWAVE;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.rev.RevSPARKMini;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "ShockwaveTeleOp", group = "Demos")
public class ShockwaveTeleop extends LinearOpMode {

    private DcMotor mBR, mBL, mFL, mFR;
    private Servo sG, sW;
    private CRServo mLift, mA;
    private TouchSensor aLim;
    private GamepadEx Driver, Manip;
    //Pos is position
    double armPos, actualPos, posDiff = 0, armSpeed;
    double extPos = 0, extActualPos, extDiff = 0, extSpeed;

    @Override
    public void runOpMode (){

        mBR = hardwareMap.get(DcMotor.class, "mBR");
        mBL = hardwareMap.get(DcMotor.class, "mBL");
        mFR = hardwareMap.get(DcMotor.class, "mFR");
        mFL = hardwareMap.get(DcMotor.class, "mFL");

        sG = hardwareMap.get(Servo.class, "sG");
        sW = hardwareMap.get(Servo.class, "sW");
        mA = hardwareMap.crservo.get("mLift");
        mLift = hardwareMap.crservo.get("mA");

        //DO NOT CHANGE THE CONFIG ON THE PHONE WITHOUT VICTOR, THANG, OR LINCOLN, DO NOT. THE PORTS THAT MLIFT AND MA ARE IN ARE FINE >:(

        aLim = hardwareMap.get(TouchSensor.class, "aLim");

        Driver = new GamepadEx(gamepad1);
        Manip = new GamepadEx(gamepad2);

        mBL.setDirection(DcMotor.Direction.REVERSE);
        mFL.setDirection(DcMotor.Direction.REVERSE);


        extActualPos = mFL.getCurrentPosition();
        extDiff = -extActualPos;

        waitForStart();
        while(opModeIsActive())   {

            extPos = extActualPos + extDiff;

            armSpeed = -Manip.getLeftY()/2;

            actualPos = mFR.getCurrentPosition();
            armPos = actualPos + posDiff;

            if (aLim.isPressed()){
                armPos = 0;
                posDiff = -actualPos;
                if (Manip.getLeftY() > 0){
                    armSpeed = 0;
                }
                else {
                    armSpeed = -Manip.getLeftY()/2;
                }
            }
            else if (armPos <= -3500) {
                if (Manip.getLeftY() < 0){
                    armSpeed = 0;
                }
                else {
                    armSpeed = -Manip.getLeftY()/2;
                }
            }

            if(Manip.isDown(GamepadKeys.Button.DPAD_DOWN)){
                extSpeed = -.25;
            } else if (Manip.isDown(GamepadKeys.Button.DPAD_UP)) {
                extSpeed = .25;
            }
            else if((!Manip.isDown(GamepadKeys.Button.DPAD_UP) && !Manip.isDown(GamepadKeys.Button.DPAD_DOWN)) || extPos == 0 ){
                extSpeed = 0;
            }

            mBR.setPower(Driver.getLeftY() + Driver.getLeftX() - Driver.getRightX());
            mBL.setPower(Driver.getLeftY() - Driver.getLeftX() + Driver.getRightX());
            mFR.setPower(Driver.getLeftY() - Driver.getLeftX() - Driver.getRightX());
            mFL.setPower(Driver.getLeftY() + Driver.getLeftX() + Driver.getRightX());

            mA.setPower(armSpeed);

            mLift.setPower(extSpeed);

            telemetry.addData("DigitalArmPosition", armPos);
            telemetry.addData("ActualArmPosition", actualPos);
            // Noticed that the y value had up on the stick be a positive value, which is opposite
            // of how it normally behaves where up is negative. My best hypothesis is that the
            // GamepadEx class flips the y value.
            telemetry.addData("ManipLeftY", Manip.getLeftY());
            telemetry.addData("extDigitalPos" , extPos);
            telemetry.update();


        }
    }
}
