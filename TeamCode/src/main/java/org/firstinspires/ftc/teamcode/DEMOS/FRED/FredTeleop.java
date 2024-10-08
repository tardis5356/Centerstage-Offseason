package org.firstinspires.ftc.teamcode.DEMOS.FRED;
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
import org.firstinspires.ftc.teamcode.DEMOS.FRED.subsystems.FredArm;
import org.firstinspires.ftc.teamcode.DEMOS.FRED.subsystems.FredWrist;

@TeleOp(name = "FredTeleop", group = "Teleop")
//@Disabled
public class FredTeleop extends CommandOpMode {

    //Gamepads
    private GamepadEx drive, apendage;
    //Drive Motors
    private DcMotorEx mFL, mFR, mBL, mBR;

    private FredArm arm;

    private FredWrist wrist;

    @Override
    public void initialize () {
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        //define gamepads
        drive = new GamepadEx(gamepad1);
        apendage = new GamepadEx(gamepad2);

        //define intake

        //define drive motors slash map them
        mBL = hardwareMap.get(DcMotorEx.class, "mBL");
        mBR = hardwareMap.get(DcMotorEx.class, "mBR");
        mFL = hardwareMap.get(DcMotorEx.class, "mFL");
        mFR = hardwareMap.get(DcMotorEx.class, "mFR");


        arm = new FredArm(hardwareMap);

        //Triggers
        //        new Trigger(() -> apendage.getButton(GamepadKeys.Button.RIGHT_BUMPER) || apendage.getButton(GamepadKeys.Button.LEFT_BUMPER))
        //                .toggleWhenActive(gripper::closeGripper, gripper::openGripper);

        new Trigger(() -> apendage.getButton(GamepadKeys.Button.DPAD_UP))
                .whenActive(wrist::wristUp);
        //
        new Trigger(() -> apendage.getButton(GamepadKeys.Button.DPAD_DOWN))
                .whenActive(wrist::wristDown);
        //
        new Trigger(() -> opModeIsActive())
                .whenActive(wrist::innitializeWrist);
        //
        //        new Trigger(() -> apendage.getButton(GamepadKeys.Button.A))
        //                .whenActive(() -> arm.setTPos(50));
    }
       @Override
        public void run() {
                    super.run();

           mBR.setPower(drive.getLeftY() + drive.getLeftX() - drive.getRightX());
           mBL.setPower(drive.getLeftY() - drive.getLeftX() + drive.getRightX());
           mFR.setPower(drive.getLeftY() - drive.getLeftX() - drive.getRightX());
           mFL.setPower(drive.getLeftY() + drive.getLeftX() + drive.getRightX());
        }

}