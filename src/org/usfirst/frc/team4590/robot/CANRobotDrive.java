package org.usfirst.frc.team4590.robot;

import static org.usfirst.frc.team4590.robot.RobotMap.*;

public class CANRobotDrive {

    private SmartTalon mLeftFront,
            mLeftRear,
            mRightFront,
            mRightRear;

    private double mOutputScale = 1;
    private double mPowerLimit = 1;
    private int mLeftFrontInverted = 1, mLeftRearInverted = 1, mRightFrontInverted = 1, mRightRearInverted = 1;

    public CANRobotDrive() {
        mLeftFront = new SmartTalon(CHASSIS_TALON_LEFT_FRONT);
        mLeftRear = new SmartTalon(CHASSIS_TALON_LEFT_REAR);

        mRightFront = new SmartTalon(CHASSIS_TALON_RIGHT_FRONT);
        mRightRear = new SmartTalon(CHASSIS_TALON_RIGHT_REAR);
    }

    public static enum TalonID {
        LEFT_FRONT, RIGHT_FRONT, LEFT_REAR, RIGHT_REAR
    }

    public SmartTalon getTalon(TalonID id) {
        switch (id) {
            case LEFT_FRONT:
                return mLeftFront;
            case RIGHT_FRONT:
                return mRightFront;
            case LEFT_REAR:
                return mLeftRear;
            case RIGHT_REAR:
                return mRightRear;
        }
        return null;
    }

    public void setInvetedMotor(TalonID id, boolean inverted) {
        switch (id) {
            case LEFT_FRONT:
                mLeftFrontInverted = inverted ? -1 : 1;
                break;
            case RIGHT_FRONT:
                mLeftRearInverted = inverted ? -1 : 1;
                break;
            case LEFT_REAR:
                mRightFrontInverted = inverted ? -1 : 1;
                break;
            case RIGHT_REAR:
                mRightRearInverted = inverted ? -1 : 1;
                break;
        }
    }

    public void setOutputScale(double maxOutput) {
        mOutputScale = maxOutput;
    }

    public void setPowerLimit(double powerLimit) {
        mPowerLimit = powerLimit;
    }


    public void arcadeDrive(double moveValue, double rotateValue) {
        double leftMotorSpeed;
        double rightMotorSpeed;

        moveValue = limit(-moveValue);
        rotateValue = limit(rotateValue);

        moveValue = Math.copySign(moveValue * moveValue, moveValue);
        rotateValue = Math.copySign(rotateValue * rotateValue, rotateValue);

        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                leftMotorSpeed = -Math.max(-moveValue, rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            } else {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }
        setLeftRightMotorOutputs(leftMotorSpeed, rightMotorSpeed);
    }

    public void tankDrive(double leftValue, double rightValue) {
        leftValue = limit(-leftValue);
        rightValue = limit(-rightValue);

        leftValue = Math.copySign(leftValue * leftValue, leftValue);
        rightValue = Math.copySign(rightValue * rightValue, rightValue);

        setLeftRightMotorOutputs(leftValue, rightValue);
    }

    private double limit(double value) {
        if (value > 0)
            return Math.min(value, mPowerLimit);
        return Math.max(value, -mPowerLimit);
    }

    public void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
        mLeftFront.set(mLeftFrontInverted * limit(leftOutput) * mOutputScale);
        mLeftRear.set(mLeftRearInverted * limit(leftOutput) * mOutputScale);
        mRightFront.set(mRightFrontInverted * limit(rightOutput) * mOutputScale);
        mRightRear.set(mRightRearInverted * limit(rightOutput) * mOutputScale);
    }

}