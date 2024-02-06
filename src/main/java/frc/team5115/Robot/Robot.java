package frc.team5115.Robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    MySubsystem subsystem;
    I2CHandler bno;

    @Override
    public void robotInit() {
        bno = new I2CHandler();
        subsystem = new MySubsystem(bno);
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
        bno.updatePitch();
    }
}
