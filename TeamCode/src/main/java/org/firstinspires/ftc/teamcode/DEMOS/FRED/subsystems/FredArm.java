package org.firstinspires.ftc.teamcode.DEMOS.FRED.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class FredArm extends SubsystemBase {
    DcMotorEx mA;
    //Servo sW;
    PIDController oController;
    TouchSensor magneticLim;
    public static double tPos = 0;
    double stickInput = 0;
    double mPower = 0;
    public FredArm(HardwareMap hardwareMap) {
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

    }
}
