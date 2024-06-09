package org.firstinspires.ftc.teamcode.DEMOS.PRIMUS;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.DEMOS.OPTIMUS.subsystems.OptimusGripper;
import org.firstinspires.ftc.teamcode.DEMOS.PRIMUS.subsystems.PrimusGripper;

@TeleOp(name = "PrimusTeleOp", group = "Demos")
//@Disabled
public class PrimusTeleop extends CommandOpMode {

    //Drive Motors
    private GamepadEx drive, arm;
    private DcMotorEx mL, mR;

    private PrimusGripper gripper;


    @Override
    public void initialize() {

        //define gamepads
        drive = new GamepadEx(gamepad1);
        arm = new GamepadEx(gamepad2);

        // define gripper slash map it
        gripper = new PrimusGripper(hardwareMap);

        //define drive motors slash map them
        mL = hardwareMap.get(DcMotorEx.class, "mL");
        mR = hardwareMap.get(DcMotorEx.class, "mR");

    }
}
