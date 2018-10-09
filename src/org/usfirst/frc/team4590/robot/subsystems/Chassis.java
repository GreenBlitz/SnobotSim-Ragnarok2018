package org.usfirst.frc.team4590.robot.subsystems;

import org.usfirst.frc.team4590.robot.CANRobotDrive;
import org.usfirst.frc.team4590.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Chassis extends Subsystem {

	private static Chassis instance;

	private CANRobotDrive m_robotDrive;

	public static Chassis getInstance() {
		if (instance == null) init();
		return instance;
	}

	public static void init() {
		instance = new Chassis();
	}

	private Chassis() {
		m_robotDrive = new CANRobotDrive();
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(null);
	}

	public void update() {
	}

	public void arcadeDrive(double moveValue, double rotateValue) {
		m_robotDrive.arcadeDrive(moveValue, rotateValue);
	}

	public void tankDrive(double leftValue, double rightValue) {
		m_robotDrive.tankDrive(leftValue, rightValue);
	}

	public void stop() {
		tankDrive(0, 0);
	}
}