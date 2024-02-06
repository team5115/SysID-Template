package frc.team5115.Robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TestRoutine extends SequentialCommandGroup {
    public TestRoutine(MySubsystem subsystem) {

        double dynamic_time = 3;
        double dynamic_max = 1.5;

        double quasi_time = 5;
        double quasi_max = 2;
        
        addCommands(
            subsystem.getSysIdCommand(true, true, dynamic_time, dynamic_max),
            subsystem.getSysIdCommand(false, true, dynamic_time, dynamic_max),
            subsystem.getSysIdCommand(true, false, quasi_time, quasi_max),
            subsystem.getSysIdCommand(false, false, quasi_time, quasi_max),
            new InstantCommand(this :: done)
        );
    }
    
    private void done() {
        System.out.println("FINISHED");
    }
}
