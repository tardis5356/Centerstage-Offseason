package org.firstinspires.ftc.teamcode.ARTEMIS.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.ARTEMIS.subsystems.Gripper;
import org.firstinspires.ftc.teamcode.ARTEMIS.subsystems.Arm;
import org.firstinspires.ftc.teamcode.ARTEMIS.subsystems.Wrist;

public class RobotToStateCommand extends SequentialCommandGroup {
    public RobotToStateCommand(Gripper gripper, Wrist wrist, Arm arm, String state) {
        switch (state) {
            case "intake":
                // logic here
                addCommands(
                        new ParallelCommandGroup( // this parallel group isn't actually necessary because the servos return true immediately
                                new InstantCommand(gripper::openLeft),
                                new InstantCommand(gripper::openRight)
                        ),
//                        new InstantCommand(() -> { // this performs the same as above code
//                            gripper.openRight();
//                            gripper.openLeft();
//                        })
                        new InstantCommand(wrist::wristToTransition),
                        new WaitCommand(250),
                        new InstantCommand(arm::goTransfer),
                        new WaitCommand(1000),
                        new InstantCommand(wrist::wristToIntake),
                        new InstantCommand(arm::goIntake)
                );
                break;
            case "deposit":
                // logic here
                addCommands(
                    new ParallelCommandGroup(
                            new InstantCommand(gripper::closeLeft),
                            new InstantCommand(gripper::closeRight)
                    ),
                new WaitCommand(250),
                new InstantCommand(wrist::wristToDep)
                );
                break;
            case "dropPurple":
                // logic here
                break;
        }
    }
}
