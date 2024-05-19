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

import org.firstinspires.ftc.teamcode.ARTEMIS.subsystems.Gripper;

import dev.frozenmilk.dairy.calcified.Calcified;

//@Disabled
@Calcified.Attach(automatedCacheHandling = true)
@TeleOp(name = "Offseason TeleOp", group = "Offseason")
public class OffseasonTeleOp extends CommandOpMode {

    private GamepadEx driver, manipulator;

    private Gripper gripper;

    @Override
    public void initialize() {
        driver = new GamepadEx(gamepad1);
        manipulator = new GamepadEx(gamepad2);

        gripper = new Gripper(hardwareMap);

        /*
        A opens right
        B opens left
        X closes right
        Y closes left
        */

        new Trigger(() -> driver.getButton(GamepadKeys.Button.A))
                .whenActive(gripper::openRight); //only lets you call one method, but it's shorter to write
//        new Trigger(() -> driver.getButton(GamepadKeys.Button.B))
//                .whenActive(() -> { // lets you call multiple lines of code, but has more formatting
//                    gripper.openLeft();
////                    gripper.openRight();
//                });

//        new Trigger(() -> driver.getButton(GamepadKeys.Button.X))
//                .whenActive(()-> gripper.closeRight());

        new Trigger(() -> driver.getButton(GamepadKeys.Button.X))
                .toggleWhenActive(gripper::closeLeft, gripper::openLeft);
        new Trigger(() -> driver.getButton(GamepadKeys.Button.Y))
                .whenActive(
                        new SequentialCommandGroup(
                                new InstantCommand(gripper::closeLeft), // shortcut for calling methods from subsystem
                                new WaitCommand(500),
                                new InstantCommand(() -> {
                                    gripper.openLeft(); // can run multiple lines
                                    gripper.openRight();
                                }),
                                new WaitCommand(1000),
                                new ParallelCommandGroup(
                                        new InstantCommand(gripper::closeLeft),
                                        new InstantCommand(gripper::closeRight)
                                )

                        ));
        new Trigger(() -> driver.getButton(GamepadKeys.Button.B))
                .toggleWhenActive(gripper::closeRight, gripper::openRight);
    }

    @Override
    public void run() {
        super.run();

//        if(driver.getButton(GamepadKeys.Button.B)){
//            // do stuff here
//            gripper.openRight();
//        }

    }
}
