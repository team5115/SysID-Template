package frc.team5115.Robot;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    MySubsystem subsystem;

    @Override
    public void robotInit() {
        subsystem = new MySubsystem();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }
 
    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void testInit () {
        TestRoutine routine = new TestRoutine(subsystem);
        routine.schedule();
    }

    @Override
    public void testPeriodic () {
    }
}
