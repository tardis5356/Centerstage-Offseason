package org.firstinspires.ftc.teamcode.ARTEMIS.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import dev.frozenmilk.dairy.calcified.Calcified;
import dev.frozenmilk.dairy.calcified.hardware.pwm.CalcifiedServo;

//@Calcified.Attach(automatedCacheHandling = true)
public class Gripper extends SubsystemBase {
    Servo gL, gR;
    ColorSensor colorLeft, colorRight;
//    CalcifiedServo gL, gR;

    public Gripper(HardwareMap hardwareMap){
        gL = hardwareMap.get(Servo.class, "sGL");
        gR = hardwareMap.get(Servo.class, "sGR");
        colorLeft = hardwareMap.get(ColorSensor.class, "colorLeft");
        colorRight = hardwareMap.get(ColorSensor.class, "colorRight");

//        gL = Calcified.getExpansionHub().getServo(3);
//        gR = Calcified.getExpansionHub().getServo(2);

        // Typecasting
//        double a = 1.1;
//        int b = (int) a;
    }

    @Override
    public void periodic(){
        // runs every loop
    }

    public void closeLeft(){
        gL.setPosition(BotPositions.GRIPPER_LEFT_CLOSED);
    }
    public void closeRight(){
        gR.setPosition(BotPositions.GRIPPER_RIGHT_CLOSED);
    }

    public void openLeft(){
        gL.setPosition(BotPositions.GRIPPER_LEFT_OPEN);
    }
    public void openRight(){
        gR.setPosition(BotPositions.GRIPPER_RIGHT_OPEN);
    }


}
