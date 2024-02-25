package frc.team5115.Robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Config;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Mechanism;

public class MySubsystem extends SubsystemBase{
    final CANSparkMax sparkMax;
    final int id = 4;
    final String name = "front-left-motor";

    public MySubsystem() {
        sparkMax = new CANSparkMax(id, MotorType.kBrushless);
    }

    public Command getSysIdCommand(boolean forward, boolean dynamic, double timeout) {
        SysIdRoutine routine = generateSysIdRoutine(sparkMax, name, this);
        Direction direction = forward ? Direction.kForward : Direction.kReverse;
        
        if (dynamic) {
            return routine.dynamic(direction).withTimeout(timeout);
        } else {
            return routine.quasistatic(direction).withTimeout(timeout);
        }
    }

    private static SysIdRoutine generateSysIdRoutine(CANSparkMax motor, String name, Subsystem subsystem) {
        RelativeEncoder encoder = motor.getEncoder();
        // final double timeout = 5;
        // final double maxVoltage = 1.8;
        // final double rampRate = maxVoltage/timeout;
        // Config config = new Config(Units.Volts.of(rampRate).per(Units.Second), Units.Volts.of(maxVoltage), Units.Seconds.of(timeout));
        Config config = new Config();
        Mechanism mechanism = new Mechanism(
            voltage -> {
               motor.set(voltage.magnitude() / RobotController.getBatteryVoltage()); 
            }, log -> {
                log.motor(name)
                .voltage(Units.Volts.of(motor.get() * RobotController.getBatteryVoltage()))
                .angularPosition(Units.Rotations.of(encoder.getPosition()))
                .angularVelocity(Units.RPM.of(encoder.getVelocity()));
            }, subsystem
        );
        return new SysIdRoutine(config, mechanism);
    }
}
