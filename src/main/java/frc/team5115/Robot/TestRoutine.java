package frc.team5115.Robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TestRoutine extends SequentialCommandGroup {
    public TestRoutine(MySubsystem subsystem) {

        double dynamic_max_volts = 1.5;
        double quasi_max_volts = 2.5;
        
        addCommands(
            subsystem.getSysIdCommand(true, true, 3, dynamic_max_volts),
            subsystem.getSysIdCommand(false, true, 1.2, dynamic_max_volts),
            subsystem.getSysIdCommand(true, false, 5, quasi_max_volts),
            subsystem.getSysIdCommand(false, false, 5, quasi_max_volts),
            new InstantCommand(this :: done)
        );
    }
    
    private void done() {
        System.out.println("FINISHED");
    }
}
