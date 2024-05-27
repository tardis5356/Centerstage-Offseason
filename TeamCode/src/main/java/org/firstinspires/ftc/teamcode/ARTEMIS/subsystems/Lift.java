package org.firstinspires.ftc.teamcode.ARTEMIS.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@Config
public class Lift extends SubsystemBase {
    DcMotorEx mLR, mLL;
    TouchSensor touchLift;

    PIDController controller;

    public static double targetPosition = 0;
    double joystickPowerInput = 0;
    double motorPower = 0;

    public Lift(HardwareMap hardwareMap) {
        mLR = hardwareMap.get(DcMotorEx.class, "mLR");
        mLL = hardwareMap.get(DcMotorEx.class, "mLL");

        touchLift = hardwareMap.get(TouchSensor.class, "touchLift");

        mLR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        mLL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        mLL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mLL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mLR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        mLL.setDirection(DcMotorSimple.Direction.REVERSE);
        mLR.setDirection(DcMotorSimple.Direction.REVERSE);

        controller = new PIDController(0.02, 0, 0);
    }

    public void ManualMode(double left, double right) {
        joystickPowerInput = left + right * 0.5;
    }

    public void periodic() {
        // runs every loop
        if (joystickPowerInput != 0) {
            motorPower = joystickPowerInput - 0.09;
            targetPosition = -15;
        } else if (targetPosition == -10) {
            motorPower = 0;
        } else if (targetPosition != -15) {
            motorPower = -0.09 + getCurrentPID();
        } else {
            motorPower = -0.09;
        }

        mLL.setPower(motorPower);
        mLR.setPower(motorPower);
    }

    public double getCurrentPosition() {
        return mLL.getCurrentPosition();
    }

    public double getCurrentPID() {
        return -controller.calculate(-mLL.getCurrentPosition(), targetPosition);
    }

    public double getCurrentMotorPower() {
        return motorPower;
    }
}
