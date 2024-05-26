package org.firstinspires.ftc.teamcode.ARTEMIS.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.ARTEMIS.subsystems.Gripper;
import org.firstinspires.ftc.teamcode.ARTEMIS.subsystems.Arm;
import org.firstinspires.ftc.teamcode.ARTEMIS.subsystems.Wrist;

import java.util.concurrent.TimeUnit;

public class RobotToStateCommand extends SequentialCommandGroup {
    public String robotState;
    public RobotToStateCommand(Gripper gripper, Wrist wrist, Arm arm, String state) {
        switch (state) {
            case "intake":
                // logic here
                if(robotState == "intake"){
                    return;
                }
                robotState = "intake";

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
                if(robotState == "deposit"){
                return;
                }
                robotState = "deposit";

                addCommands(
                        new ParallelCommandGroup(
                                new InstantCommand(gripper::closeLeft),
                                new InstantCommand(gripper::closeRight)
                        ),
                        /*code that allows input in seconds TimeUnit.SECONDS.toMillis((long) 1.5)*/
                        new WaitCommand(500),
                        new InstantCommand(arm::goTransfer),
                        new WaitCommand(1500),
                        new InstantCommand(wrist::wristToTransition),
                        new WaitCommand(1500),
                        new InstantCommand(arm::goDeposit),
                        new WaitCommand(1500),
                        new InstantCommand(wrist::wristToDep)

                );

                break;
            case "dropPurple":
                // logic here
                break;
        }
    }
}
