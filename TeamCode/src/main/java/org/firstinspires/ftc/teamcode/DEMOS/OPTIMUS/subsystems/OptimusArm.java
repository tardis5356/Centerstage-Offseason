package org.firstinspires.ftc.teamcode.DEMOS.OPTIMUS.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class OptimusArm extends SubsystemBase {
    DcMotorEx mA;
    //Servo sW;
    PIDController oController;
    TouchSensor magneticLim;
    public static double tPos = 0;
    double stickInput = 0;
    double mPower = 0;

    public OptimusArm(HardwareMap hardwareMap) {
        mA = hardwareMap.get(DcMotorEx.class, "mA");

        magneticLim = hardwareMap.get(TouchSensor.class, "armLimit");

        mA.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        mA.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        mA.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        oController = new PIDController(0, 0, 0);
    }

    public void ManualMode(double left, double right) {
        stickInput = left + right * 0.5;
    }

    public void periodic() {
        // runs every loop
        if (stickInput != 0) {
            mPower = stickInput;
            tPos = -15;
        } else if (tPos == -15) {
            mPower = 0;
        } else if (tPos != -15) {
            mPower = getCurrentOPID();
        } else {
            mPower = 0;
        }

        mA.setPower(mPower);

    }

    public double getCurrentPosition() {
        return mA.getCurrentPosition();
    }

    public double getCurrentOPID() {
        return -oController.calculate(-mA.getCurrentPosition(), tPos);
    }
    public double getCurrentMotorPower() {
        return mPower;
    }



    public void setTPos(double positionInput) {
        tPos = positionInput;
    }

}