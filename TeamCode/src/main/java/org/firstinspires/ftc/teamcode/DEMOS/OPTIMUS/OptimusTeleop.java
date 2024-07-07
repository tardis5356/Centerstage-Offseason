package org.firstinspires.ftc.teamcode.DEMOS.OPTIMUS;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DEMOS.OPTIMUS.subsystems.OptimusArm;
import org.firstinspires.ftc.teamcode.DEMOS.OPTIMUS.subsystems.OptimusGripper;
import org.firstinspires.ftc.teamcode.DEMOS.OPTIMUS.subsystems.OptimusWrist;

@TeleOp(name = "OptimusTeleOp", group = "Demos")
//@Disabled
public class OptimusTeleop extends CommandOpMode {

    //Gamepads
    private GamepadEx drive, apendage;
    //Drive Motors
    private DcMotorEx mL, mR;

    private OptimusGripper gripper;
    private OptimusArm arm;
    OptimusWrist wrist;

    @Override
    public void initialize() {

        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        //define gamepads
        drive = new GamepadEx(gamepad1);
        apendage = new GamepadEx(gamepad2);

        // define gripper slash map it
        gripper = new OptimusGripper(hardwareMap);

        //define drive motors slash map them
        mL = hardwareMap.get(DcMotorEx.class, "mL");
        mR = hardwareMap.get(DcMotorEx.class, "mR");

        arm = new OptimusArm(hardwareMap);

        wrist = new OptimusWrist(hardwareMap);

        //Triggers!!!

        new Trigger(() -> apendage.getButton(GamepadKeys.Button.RIGHT_BUMPER) || apendage.getButton(GamepadKeys.Button.LEFT_BUMPER))
                .toggleWhenActive(gripper::closeGripper, gripper::openGripper);

        new Trigger(() -> apendage.getButton(GamepadKeys.Button.DPAD_UP))
                .whenActive(wrist::wristUp);

        new Trigger(() -> apendage.getButton(GamepadKeys.Button.DPAD_DOWN))
                .whenActive(wrist::wristDown);

        new Trigger(() -> opModeIsActive())
                .whenActive(wrist::innitializeWrist);

        new Trigger(() -> apendage.getButton(GamepadKeys.Button.A))
                .whenActive(() -> arm.setTPos(50));
    }

    @Override
    public void run() {
        super.run();

        //trust the math i've done it many times
        mL.setPower((drive.getLeftY()/1.5)-(drive.getRightX()/2));
        mR.setPower((-drive.getLeftY()/1.5)-(drive.getRightX()/2));

        arm.ManualMode(apendage.getLeftY(), -apendage.getRightY());

    }
}
