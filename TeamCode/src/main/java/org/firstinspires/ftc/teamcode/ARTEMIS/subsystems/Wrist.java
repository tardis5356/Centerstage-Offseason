package org.firstinspires.ftc.teamcode.ARTEMIS.subsystems;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
public class Wrist extends SubsystemBase {
    Servo sWP;
    double WRIST_TILT_INTAKE = 0.29;
    double WRIST_TILT_DEPOSIT_LOW = 0.69;
    double WRIST_TILT_TRANSITION = 0.17;

    public Wrist(HardwareMap hardwareMap){
        sWP = hardwareMap.get(Servo.class, "sWGP");

    }
    @Override
    public void periodic(){ ;
    }

    public void wristToDep(){sWP.setPosition(WRIST_TILT_DEPOSIT_LOW);

    }

    public void wristToIntake(){sWP.setPosition(WRIST_TILT_INTAKE);

    }

    public void wristToTransition(){sWP.setPosition(WRIST_TILT_TRANSITION);}

}
