package org.usfirst.frc.team4590.robot.subsystems;

import org.usfirst.frc.team4590.robot.RobotMap;
import org.usfirst.frc.team4590.robot.SmartTalon;
import org.usfirst.frc.team4590.robot.commands.shooter.FreeShooter;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Shoots the fuel.
 */
public class Shooter extends PIDSubsystem {

	private static double kP = 0.00007, kI = 0, kD = 0;
	
	private static final double TICKS_PER_RPM = 35.842;
	private SmartTalon talon1, talon2;
	private static Shooter instance;
	
	
	// Initialize your subsystem here
	private Shooter() {
		super("Shooter", kP, kI, kD);
		
		talon1 = new SmartTalon(RobotMap.SHOOTER_TALON_B);
		talon2 = new SmartTalon(RobotMap.SHOOTER_TALON_A);
		talon2.follow(talon1);
		resetEncoder();
		SmartDashboard.putNumber("Shooter__PID(P)", kP);
		SmartDashboard.putNumber("Shooter__PID(I)", kI);
		SmartDashboard.putNumber("Shooter__PID(D)", kD);
	}
	

	private void resetEncoder() {
		talon1.resetEncoder();
		talon2.resetEncoder();
	}

	public static final void init() {
		instance = new Shooter();
	}

	public static final Shooter getInstance() {
		return instance;
	}

	// getters for sensors
	public double getSpeed() {
		return talon1.getSensorCollection().getQuadratureVelocity() / TICKS_PER_RPM;
	}

	public double getDistance() {
		return talon1.getSensorCollection().getQuadratureVelocity() / TICKS_PER_RPM;
	}

	private double clamp(double min, double max, double val) {
		return val < min ? min : val > max ? max : val;
	}
	
	private double motorClamp(double val) {
		return clamp(-1, 1, val);
	}
	
	// motor methods
	public void setPower(double power) {
		talon1.set(motorClamp(power));
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new FreeShooter());
	}

	protected double returnPIDInput() {
		// Return your input value for the PID loop
		// e.g. a sensor, like a potentiometer:
		// yourPot.getAverageVoltage() / kYourMaxVoltage;
		return -getSpeed();
	}

	protected void usePIDOutput(double output) {
		// Use output to drive your system, like a motor
		// e.g. yourMotor.set(output);
		setPower(output);

	}
}