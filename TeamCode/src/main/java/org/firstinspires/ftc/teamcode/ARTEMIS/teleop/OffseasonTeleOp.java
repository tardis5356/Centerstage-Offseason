package org.firstinspires.ftc.teamcode.ARTEMIS.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.ARTEMIS.commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.ARTEMIS.commands.RobotToStateCommand;
import org.firstinspires.ftc.teamcode.ARTEMIS.subsystems.Arm;
import org.firstinspires.ftc.teamcode.ARTEMIS.subsystems.Gripper;
import org.firstinspires.ftc.teamcode.ARTEMIS.subsystems.Intake;
import org.firstinspires.ftc.teamcode.ARTEMIS.subsystems.Wrist;

import dev.frozenmilk.dairy.calcified.Calcified;

//@Disabled
@Calcified.Attach(automatedCacheHandling = true)
@TeleOp(name = "Offseason TeleOp", group = "Offseason")
public class OffseasonTeleOp extends CommandOpMode {

    private GamepadEx driver, manipulator;

    private Gripper gripper;

    private Arm arm;

    private Wrist wrist;
    double FB, LR, Rotation;
    DcMotorEx mBL, mBR, mFL, mFR;

    private Intake intake;

    @Override
    public void initialize() {
        driver = new GamepadEx(gamepad1);
        manipulator = new GamepadEx(gamepad2);

        gripper = new Gripper(hardwareMap);
        arm = new Arm(hardwareMap);
        wrist = new Wrist(hardwareMap);
        intake = new Intake(hardwareMap);

        //init and set up drive motors
        mFL = hardwareMap.get(DcMotorEx.class, "mFL");
        mFR = hardwareMap.get(DcMotorEx.class, "mFR");
        mBL = hardwareMap.get(DcMotorEx.class, "mBL");
        mBR = hardwareMap.get(DcMotorEx.class, "mBR");

        //this motor physically runs opposite. For convenience, reverse direction.
        mBR.setDirection(DcMotorSimple.Direction.REVERSE);
        mFR.setDirection(DcMotorSimple.Direction.REVERSE);

        /*
        A opens right
        B opens left
        X closes right
        Y closes left
        */

        //opens both grippers
        new Trigger(() -> driver.getButton(GamepadKeys.Button.A))
                .whenActive(
                        new SequentialCommandGroup(
                                new InstantCommand(gripper::openRight),
                                new InstantCommand(gripper::openLeft)
                        ));
//        new Trigger(() -> driver.getButton(GamepadKeys.Button.B))
//                .whenActive(() -> { // lets you call multiple lines of code, but has more formatting
//                    gripper.openLeft();
////                    gripper.openRight();
//                });

//        new Trigger(() -> driver.getButton(GamepadKeys.Button.X))
//                .whenActive(()-> gripper.closeRight());

        //toggles left gripper
        new Trigger(() -> driver.getButton(GamepadKeys.Button.X))
                .toggleWhenActive(gripper::closeLeft, gripper::openLeft);
        //closes both grippers
        new Trigger(() -> driver.getButton(GamepadKeys.Button.Y))
                .whenActive(
                        new SequentialCommandGroup(
                                new InstantCommand(gripper::closeRight),
                                new InstantCommand(gripper::openLeft)
                        ));

                                // shortcut for calling methods from subsystem
//                                new WaitCommand(500),
//                                new InstantCommand(() -> {
//                                    gripper.openLeft(); // can run multiple lines
//                                    gripper.openRight();
//                                }),
//                                new WaitCommand(1000),
//                                new ParallelCommandGroup(
//                                        new InstantCommand(gripper::closeLeft),
//                                        new InstantCommand(gripper::closeRight)
//                                )

        //toggles right gripper
        new Trigger(() -> driver.getButton(GamepadKeys.Button.B))
                .toggleWhenActive(gripper::closeRight, gripper::openRight);

        //to intake trigger
        new Trigger(() -> driver.getButton(GamepadKeys.Button.START))
                .whenActive(new RobotToStateCommand(gripper, wrist, arm, intake, "intake"));

        //to deposite trigger
        new Trigger(() -> driver.getButton(GamepadKeys.Button.BACK))
                .whenActive(new RobotToStateCommand(gripper, wrist, arm, intake,"deposit"));

//        run intake
        new Trigger(() -> driver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)>0.05)
                .whenActive(new IntakeCommand(gripper, intake));

        new Trigger(()-> (intake.getIntakeLeftDist())<15)
                .whenActive((gripper::closeLeft));

        new Trigger(()-> (intake.getIntakeRightDist())<15)
                .whenActive((gripper::closeRight));

        new Trigger(()-> (intake.getIntakeRightDist())<15 && intake.getIntakeLeftDist()<15)
                .whenActive(new SequentialCommandGroup(
                        new WaitCommand(2000),
                        (new RobotToStateCommand(gripper, wrist, arm, intake, "transition"))
                ));

    }

    @Override
    public void run() {
        super.run();

//        if(driver.getButton(GamepadKeys.Button.B)){
//            // do stuff here
//            gripper.openRight();
//        }
        FB = gamepad1.left_stick_y;
        LR = -gamepad1.left_stick_x;
        Rotation = -gamepad1.right_stick_x;

        mFL.setPower(FB + LR + Rotation);
        mFR.setPower(FB - LR - Rotation);
        mBL.setPower(FB - LR + Rotation);
        mBR.setPower(FB + LR - Rotation);

    }
}
