// Should not exist in reality, good example

package org.firstinspires.ftc.teamcode.ARTEMIS.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.ARTEMIS.subsystems.Gripper;
import org.firstinspires.ftc.teamcode.ARTEMIS.subsystems.Intake;

public class IntakeCommand extends SequentialCommandGroup {
    public IntakeCommand(Gripper gripper, Intake intake){
        addCommands(
                new InstantCommand(gripper::openRight),
                new InstantCommand(gripper::openLeft),
                new InstantCommand(intake::setINTAKE_DOWN_TELEOP),
                new InstantCommand(intake::setINTAKE_IN),
                new WaitCommand(5000),
                new InstantCommand(intake::setINTAKE_OUT),
                new WaitCommand(1000),
                new InstantCommand(intake::setINTAKE_UP),
                new WaitCommand(1000),
                new InstantCommand(intake::setINTAKE_STOP)
        );

    }

}
