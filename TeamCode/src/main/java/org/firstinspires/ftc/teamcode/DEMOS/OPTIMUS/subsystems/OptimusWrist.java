package org.firstinspires.ftc.teamcode.DEMOS.OPTIMUS.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class OptimusWrist extends SubsystemBase {
    Servo sW;

    public OptimusWrist(HardwareMap hardwareMap) {
        sW = hardwareMap.get(Servo.class, "sW");

    }
}