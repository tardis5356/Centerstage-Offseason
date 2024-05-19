package org.firstinspires.ftc.teamcode.ARTEMIS.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Wrist extends SubsystemBase {
    Servo sWP;

    public Wrist(HardwareMap hardwareMap) {
        sWP = hardwareMap.get(Servo.class, "sWGP");

    }

    @Override
    public void periodic() {
    }

    public void wristToDep() {
        sWP.setPosition(BotPositions.ARM_DEPOSIT_LOW);
    }

    public void wristToIntake() {
        sWP.setPosition(BotPositions.WRIST_TILT_INTAKE);
    }

    public void wristToTransition() {
        sWP.setPosition(BotPositions.WRIST_TILT_TRANSITION);
    }

}
