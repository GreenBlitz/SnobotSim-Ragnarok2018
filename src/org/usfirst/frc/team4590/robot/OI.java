package org.usfirst.frc.team4590.robot;

import org.usfirst.frc.team4590.robot.SmartJoystick.JoystickAxis;

public class OI {

	private static OI instance;

	private SmartJoystick mainJS, sideJS;

	public static OI getInstance() {
		if (instance == null) instance = new OI();
		return instance;
	}

	public static void init() {
		instance = new OI();
	}

	private OI() {
		mainJS = new SmartJoystick(RobotMap.JOYSTICK_MAIN);
		mainJS.setAxisInverted(JoystickAxis.LEFT_Y, false);
		mainJS.setAxisInverted(JoystickAxis.RIGHT_Y, true);
		
		sideJS = new SmartJoystick(RobotMap.JOYSTICK_SECOND);
		sideJS.setAxisInverted(JoystickAxis.LEFT_Y, true);
		sideJS.setAxisInverted(JoystickAxis.RIGHT_Y, true);
	
	}
	
	public SmartJoystick getMainJS() {
		return mainJS;
	}
	
	public SmartJoystick getSideJS() {
		return sideJS;
	}
}