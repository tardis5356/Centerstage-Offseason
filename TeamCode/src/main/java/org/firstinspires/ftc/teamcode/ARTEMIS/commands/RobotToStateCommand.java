package org.firstinspires.ftc.teamcode.ARTEMIS.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.ARTEMIS.subsystems.Gripper;

public class RobotToStateCommand extends SequentialCommandGroup {
    public RobotToStateCommand(Gripper gripper, String state) {
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
                        new WaitCommand(250)
                );
                break;
            case "deposit":
                // logic here
                break;
            case "dropPurple":
                // logic here
                break;
        }
    }
}
