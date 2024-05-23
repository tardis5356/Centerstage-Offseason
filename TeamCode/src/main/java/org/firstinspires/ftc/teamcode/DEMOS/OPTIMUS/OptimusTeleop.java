package org.firstinspires.ftc.teamcode.DEMOS.OPTIMUS;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.DEMOS.OPTIMUS.subsystems.OptimusGripper;

@TeleOp(name = "OptimusTeleOp", group = "Demos")
//@Disabled
public class OptimusTeleop extends CommandOpMode {

    //Gamepads
    private GamepadEx drive, arm;
    //Drive Motors
    private DcMotorEx mL, mR;

    private OptimusGripper gripper;

    @Override
    public void initialize() {

        //define gamepads
        drive = new GamepadEx(gamepad1);
        arm = new GamepadEx(gamepad2);

        // define gripper slash map it
        gripper = new OptimusGripper(hardwareMap);

        //define drive motors slash map them
        mL = hardwareMap.get(DcMotorEx.class, "mL");
        mR = hardwareMap.get(DcMotorEx.class, "mR");



        //Triggers!!!

        new Trigger(() -> drive.getButton(GamepadKeys.Button.RIGHT_BUMPER) || drive.getButton(GamepadKeys.Button.LEFT_BUMPER))
                .toggleWhenActive(gripper::closeGripper, gripper::openGripper);
    }

    @Override
    public void run() {
        super.run();

        //trust the math i've done it many times
        mL.setPower((drive.getLeftY()/1.5)-(drive.getRightX()/2));
        mR.setPower((-drive.getLeftY()/1.5)-(drive.getRightX()/2));

    }
}
