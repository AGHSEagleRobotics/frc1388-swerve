package org.usfirst.frc1388.lib;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SpeedController;

public class SwerveModule {
	
	private SpeedController m_driveMotor;
	private SpeedController m_steerMotor;
	private AnalogInput m_steerEncoder;
	private final double k_steer_threshold = 2;
	
	public SwerveModule(SpeedController driveMotor, SpeedController steerMotor, AnalogInput steerEncoder ) {
		m_driveMotor = driveMotor;
		m_steerMotor = steerMotor;
		m_steerEncoder = steerEncoder;
	}
	
	public SwerveModule() {
		
	}
	
	//gets angle
	public double getAngle() {
		double volt = m_steerEncoder.getVoltage();
		double angle = volt * 360/5; // 5 volts max = 360 degrees
		return angle;
	}
	
	//TODO: add threshold 
	//TODO: make it slow down the closer to the target it is
	//TODO: able to identify what angle is closer, as you cross zero
	//TODO: option 90 degrees reverse the wheel pwr
	public void setSteerAngle(double targetAngle) {
		if(targetAngle > getAngle()) {
			m_steerMotor.set(.5);
		}
		else if(targetAngle < getAngle()){
			m_steerMotor.set(-0.5);
		}
		m_steerMotor.set(0);
	}
	
	static double normalizeAngle(double inputAngle) {
		double normAngle = inputAngle % 360;
		if(normAngle < 0) {
			normAngle += 360;
		} 
		return normAngle;
	}

	/**
	 * finds the shortest distance based off the current angle and target angle 
	 * @param targetAngle the angle that we are trying to get to 
	 * @param currentAngle the angle that we are at
	 * @return offset of the current angle and the target angle
	 */
	public double steerOffset(double targetAngle, double currentAngle){
		double offset;
		double absOffset;
		
		offset = ((targetAngle - currentAngle));
		absOffset = Math.abs(offset);
		
		if(absOffset <= 90)
				return offset;
		else 
			offset = (absOffset % 180) - 180;
			if(targetAngle < currentAngle)
				return -offset;
			else
				return offset;
	}
	
	/**
	 * setting the angles of the steer motors at a given speed, using the threshold 
	 * @param angleChange the angle that we are turning to 
	 * @param speed how fast the motor will actually turn
	 * @return difference between current angle and angle change
	 */
	public double steerMotorTo(double angleChange, double speed) {
		
		if(angleChange > k_steer_threshold) {
			m_steerMotor.set(speed);
		} else if (angleChange < -k_steer_threshold) {
			m_steerMotor.set(-speed);
		}
		
		return steerOffset(angleChange, getAngle());
	}
	
	/**
	 * setting the angles of the steer motors at max speed (1.0), using the threshold
	 * @param angleChange the angle that we are turning to
	 * @return difference between current angle and angle changefj  s 
	 */
	public double steerMotorTo(double angleChange) {
		return steerMotorTo(angleChange, 1.0);
	}
}
