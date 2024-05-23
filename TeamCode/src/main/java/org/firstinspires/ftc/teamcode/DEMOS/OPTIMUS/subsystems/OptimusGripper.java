package org.firstinspires.ftc.teamcode.DEMOS.OPTIMUS.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.DEMOS.demoBotPositions;

public class OptimusGripper extends SubsystemBase {

    Servo sG;

    public OptimusGripper(HardwareMap hardwareMap){
        sG = hardwareMap.get(Servo.class, "sG");
    }

    @Override
    public void periodic(){

    }

    public void openGripper(){sG.setPosition(demoBotPositions.opennedGripper);}

    public void closeGripper(){sG.setPosition(demoBotPositions.closedGripper);}
}
