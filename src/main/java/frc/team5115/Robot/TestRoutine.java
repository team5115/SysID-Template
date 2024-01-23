package frc.team5115.Robot;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TestRoutine extends SequentialCommandGroup {
    private static final double TIMEOUT = 10;

    public TestRoutine(MySubsystem subsystem) {
        addCommands(
            subsystem.getSysIdCommand(true, true, TIMEOUT),
            subsystem.getSysIdCommand(false, true, TIMEOUT),
            subsystem.getSysIdCommand(true, false, TIMEOUT),
            subsystem.getSysIdCommand(false, false, TIMEOUT)
        );
    }
}
