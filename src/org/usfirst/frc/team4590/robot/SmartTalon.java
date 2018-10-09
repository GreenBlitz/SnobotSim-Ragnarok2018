package org.usfirst.frc.team4590.robot;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class SmartTalon extends TalonSRX {

	private boolean wasSet; 
	
	public SmartTalon(int deviceNumber) {
		super(deviceNumber);
	}
	
	private double m_lastValue = 0;

	public void set(double power) {
		m_lastValue = power;
		set(ControlMode.PercentOutput, power);
		wasSet = true;
	}
	
	public double getLastValue(){ 
		return m_lastValue;
	}
	
	public boolean wasSet() {
		return wasSet;
	}
	
	public void newIterration() {
		wasSet = false;
	}
	
	public ErrorCode resetEncoder() {
		ErrorCode ec = getSensorCollection().setQuadraturePosition(0, 100);
		if (ec != ErrorCode.OK) {
			System.err.println("error occured while reseting encoder '" + getHandle() + "': " + ec);
		}
		return getSensorCollection().getQuadraturePosition() == 0 ? ec : resetEncoder();
	}
}