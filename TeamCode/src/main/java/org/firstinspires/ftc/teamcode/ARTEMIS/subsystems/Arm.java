package org.firstinspires.ftc.teamcode.ARTEMIS.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm extends SubsystemBase {
    Servo sAL, sAR;

    public Arm(HardwareMap hardwareMap){
        sAL = hardwareMap.get(Servo.class, "sAL");
        sAR = hardwareMap.get(Servo.class, "sAR");

    }

    @Override

    public void periodic(){
        // runs every loop
    }

    public void goIntake() {
        sAL.setPosition(BotPositions.ARM_INTAKE);
        sAR.setPosition(BotPositions.ARM_INTAKE);
    }
    public void goTransfer() {
        sAL.setPosition(BotPositions.ARM_TRANSITION_POSITION);
        sAR.setPosition(BotPositions.ARM_TRANSITION_POSITION);
    }
    public void goDeposit() {
        sAL.setPosition(BotPositions.ARM_DEPOSIT_LOW);
        sAR.setPosition(BotPositions.ARM_DEPOSIT_LOW);
    }
}
