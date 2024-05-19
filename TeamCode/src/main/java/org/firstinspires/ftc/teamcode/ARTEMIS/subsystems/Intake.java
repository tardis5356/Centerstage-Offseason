package org.firstinspires.ftc.teamcode.ARTEMIS.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import dev.frozenmilk.dairy.calcified.Calcified;
import dev.frozenmilk.dairy.calcified.hardware.pwm.CalcifiedServo;

//@Calcified.Attach(automatedCacheHandling = true)
public class Intake extends SubsystemBase {
    Servo sI;
    MotorEx mI;

    double INTAKE_DOWN_TELEOP = 0.48;
    double INTAKE_UP = 0.3;

    public Intake (HardwareMap hardwareMap){
        sI = hardwareMap.get(Servo.class, "sI");
        mI = hardwareMap.get(MotorEx.class, "mI");
    }

    @Override
    public void periodic(){ ;
    }

    public void setINTAKE_UP(){sI.setPosition(INTAKE_DOWN_TELEOP);

    }

    public void setINTAKE_DOWN_TELEOP(){sI.setPosition(INTAKE_UP);}

}
