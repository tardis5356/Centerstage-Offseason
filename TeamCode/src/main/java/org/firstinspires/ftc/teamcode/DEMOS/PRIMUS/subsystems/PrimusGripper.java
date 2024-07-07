package org.firstinspires.ftc.teamcode.DEMOS.PRIMUS.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.DEMOS.demoBotPositions;

public class PrimusGripper extends SubsystemBase {

    Servo sR, sL;



    public PrimusGripper(HardwareMap hardwareMap){
        sR = hardwareMap.get(Servo.class, "sR");
        sL = hardwareMap.get(Servo.class, "sL");
    }

    @Override
    public void periodic(){

    }

    public void leftGripperOpen(){sL.setPosition(demoBotPositions.leftGripperOpen);}
    public void rightGripperOpen(){sR.setPosition(demoBotPositions.rightGripperOpen);}
    public void leftGripperClosed(){sL.setPosition(demoBotPositions.leftGripperClosed);}
    public void rightGripperClosed(){sR.setPosition(demoBotPositions.rightGripperClosed);}



}
