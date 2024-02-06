package frc.team5115.Robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
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

    static final byte INTAKE_MOTOR_ID = 32;
    static final byte ARM_LEFT_MOTOR_ID = 3;
    static final byte ARM_RIGHT_MOTOR_ID = 33;
    static final byte SHOOTER_CLOCKWISE_MOTOR_ID = 27;
    static final byte SHOOTER_COUNTERCLOCKWISE_MOTOR_ID = 20;
    static final byte CLIMBER_LEFT_MOTOR_ID = 30;
    static final byte CLIMBER_RIGHT_MOTOR_ID = 31;

    final CANSparkMax leftMotor;
    final CANSparkMax rightMotor;
    final I2CHandler bno;

    public MySubsystem(I2CHandler bno) {
        this.bno = bno;
        leftMotor = new CANSparkMax(ARM_LEFT_MOTOR_ID, MotorType.kBrushless);
        rightMotor = new CANSparkMax(ARM_RIGHT_MOTOR_ID, MotorType.kBrushless);
        leftMotor.setInverted(true);
        rightMotor.setInverted(false);
    }

    public Command getSysIdCommand(boolean forward, boolean dynamic, double timeout, double maxVoltage) {
        String name = "both-arms-together";
        SysIdRoutine routine = generateSysIdRoutine(leftMotor, rightMotor, name, this, bno, timeout, maxVoltage);
        Direction direction = forward ? Direction.kForward : Direction.kReverse;
        
        if (dynamic) {
            return routine.dynamic(direction).withTimeout(10);
        } else {
            return routine.quasistatic(direction).withTimeout(10);
        }
    }

    private static SysIdRoutine generateSysIdRoutine(CANSparkMax motor1, CANSparkMax motor2, String name, Subsystem subsystem, I2CHandler bno, double timeout, double maxVoltage) {
        RelativeEncoder encoder1 = motor1.getEncoder();
        RelativeEncoder encoder2 = motor2.getEncoder();
        final double rampRate = maxVoltage/timeout;
        Config config = new Config(Units.Volts.of(rampRate).per(Units.Second), Units.Volts.of(maxVoltage), Units.Seconds.of(timeout));
        // Config config = new Config();
        Mechanism mechanism = new Mechanism(
            voltage -> {
                motor1.set(voltage.magnitude() / RobotController.getBatteryVoltage());
                motor2.set(voltage.magnitude() / RobotController.getBatteryVoltage());
            }, log -> {
                // double totalPosition = encoder1.getPosition() + encoder2.getPosition();
                double totalVelocity = encoder1.getVelocity() + encoder2.getVelocity();

                log.motor(name)
                .voltage(Units.Volts.of(motor1.get() * RobotController.getBatteryVoltage()))
                .angularPosition(Units.Degrees.of(bno.getPitch()))
                .angularVelocity(Units.RPM.of(totalVelocity/2.0));
            }, subsystem
        );
        return new SysIdRoutine(config, mechanism);
    }
}
