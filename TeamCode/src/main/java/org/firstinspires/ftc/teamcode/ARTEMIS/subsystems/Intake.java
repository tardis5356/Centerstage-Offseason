package org.firstinspires.ftc.teamcode.ARTEMIS.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import dev.frozenmilk.dairy.calcified.Calcified;
import dev.frozenmilk.dairy.calcified.hardware.pwm.CalcifiedServo;

//@Calcified.Attach(automatedCacheHandling = true)
public class Intake extends SubsystemBase {
    Servo sI;
    DcMotorEx mI;
    ColorSensor colorLeft, colorRight;

    double INTAKE_DOWN_TELEOP = 0.48;
    double INTAKE_UP = 0.3;


    public Intake(HardwareMap hardwareMap) {
        sI = hardwareMap.get(Servo.class, "sI");
        mI = hardwareMap.get(DcMotorEx.class, "mI");
        colorLeft = hardwareMap.get(ColorSensor.class, "colorLeft");
        colorRight = hardwareMap.get(ColorSensor.class, "colorRight");

    }

    @Override
    public void periodic() {

    }

    public void setINTAKE_DOWN_TELEOP() {
        sI.setPosition(INTAKE_DOWN_TELEOP);
    }

    public void setINTAKE_UP() {
        sI.setPosition(INTAKE_UP);
    }

    public void setINTAKE_IN() {
        mI.setPower(1);
    }

    public void setINTAKE_OUT() {
        mI.setPower(-1);
    }

    public void setINTAKE_STOP() {
        mI.setPower(0);
    }

    public double getIntakeRightDist(){
        return ((DistanceSensor)colorRight).getDistance(DistanceUnit.MM);
    }
    public double getIntakeLeftDist(){
        return ((DistanceSensor)colorLeft).getDistance(DistanceUnit.MM);
    }

}