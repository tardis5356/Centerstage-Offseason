package org.firstinspires.ftc.teamcode.ARTEMIS.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Lift extends SubsystemBase {
    DcMotorEx mLR, mLL;
    TouchSensor touchLift;
    double joystickPowerInput = 0;

    public Lift(HardwareMap hardwareMap){
        mLR = hardwareMap.get(DcMotorEx.class, "mLR");
        mLL = hardwareMap.get(DcMotorEx.class, "mLL");
        touchLift = hardwareMap.get(TouchSensor.class, "touchLift");
        mLR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mLL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mLL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mLL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mLR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mLL.setDirection(DcMotorSimple.Direction.REVERSE);
        mLR.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void ManualMode(double left, double right){
        joystickPowerInput = left + right * 0.5;
    }

    public void periodic(){
        // runs every loop
        double MotorPower = joystickPowerInput - 0.09;
        mLL.setPower(MotorPower);
        mLR.setPower(MotorPower);
    }
    

}
