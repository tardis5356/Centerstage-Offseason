package org.firstinspires.ftc.teamcode.DEMOS.PRIMUS;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.rev.RevSPARKMini;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.DEMOS.OPTIMUS.subsystems.OptimusGripper;
import org.firstinspires.ftc.teamcode.DEMOS.PRIMUS.subsystems.PrimusGripper;

@TeleOp(name = "PrimusTeleOp", group = "Demos")
//@Disabled
public class PrimusTeleop extends CommandOpMode {

    //Drive Motors
    private GamepadEx drive, arm;
    private DcMotorEx mBL, mBR, mFR, mFL;
    private CRServo mArm;

    private PrimusGripper gripper;

    private TouchSensor aTouchLim;
    double mArmPower;
    double ArmPos;
    double PosDif;


    @Override
    public void initialize() {

        //define gamepads
        drive = new GamepadEx(gamepad1);
        arm = new GamepadEx(gamepad2);

        // define gripper slash map it
        gripper = new PrimusGripper(hardwareMap);

        //define drive motors slash map them
        mBL = hardwareMap.get(DcMotorEx.class, "mBL");
        mBR = hardwareMap.get(DcMotorEx.class, "mBR");
        mFL = hardwareMap.get(DcMotorEx.class, "mFL");
        mFR = hardwareMap.get(DcMotorEx.class, "mFR");
        mArm = hardwareMap.crservo.get("mArm");
        aTouchLim = hardwareMap.get(TouchSensor.class, "aTouch");

        new Trigger(() -> arm.getButton(GamepadKeys.Button.RIGHT_BUMPER))
                .toggleWhenActive(gripper::rightGripperClosed, gripper::rightGripperOpen);

        new Trigger(() -> arm.getButton(GamepadKeys.Button.LEFT_BUMPER))
                .toggleWhenActive(gripper::leftGripperClosed, gripper::leftGripperOpen);


    }

    @Override
    public void run() {
        super.run();
        ArmPos = mBR.getCurrentPosition() + PosDif;
        //trust the math i've done it many times
        mBL.setPower((-drive.getLeftY()/1.5)-(drive.getRightX()/1.5));
        mBR.setPower((drive.getLeftY()/1.5)-(drive.getRightX()/1.5));
        mFL.setPower((-drive.getLeftY()/1.5)-(drive.getRightX()/1.5));
        mFR.setPower((drive.getLeftY()/1.5)-(drive.getRightX()/1.5));

//        arm.ManualMode(apendage.getLeftY(), -apendage.getRightY());

        telemetry.addData("mBL_Power", mBL.getPower());
        telemetry.addData("mBR_Power", mBR.getPower());
        telemetry.addData("mFL_Power", mFL.getPower());
        telemetry.addData("mFR_Power", mFR.getPower());
        telemetry.addData("LeftY", drive.getLeftY());
        telemetry.addData("RightX", drive.getRightX());
        telemetry.update();

        if(aTouchLim.isPressed() == true) {
            ArmPos = 0;
            PosDif = ArmPos - mBR.getCurrentPosition();
                if(arm.getRightY() < 0){
                mArmPower = 0;
                }
                else{
                mArmPower = arm.getRightY();
                }
        }
        else if (ArmPos < -300) {
            if(arm.getRightY() > 0){
                mArmPower = 0;
            }
            else{
                mArmPower = arm.getRightY();
            }
        }
        else {
            mArmPower = arm.getRightY();
        }

        mArm.setPower(mArmPower);
    }
}
