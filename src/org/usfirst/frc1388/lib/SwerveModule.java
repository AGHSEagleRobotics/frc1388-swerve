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
	
	public SwerveModule() {}//for unit test, FIND BETTER WAY
	
	/**
	 * sets the pwr of the drive motor
	 * @param pwr motor power
	 */
	public void setDrivePwr(double pwr) {
		m_steerMotor.set(pwr);
	}
	
	//gets angle
	public double getAngle() {
		double volt = m_steerEncoder.getVoltage();
		double angle = volt * 360/5; // 5 volts max = 360 degrees
		return angle;
	}
	 
	//TODO: make it slow down the closer to the target it is
	//TODO: option 90 degrees reverse the wheel pwr
	/**
	 * moves the motor in the correct direction with the correct pwr
	 * @param targetAngle angle that we are trying to get to 
	 */
	
	public void setSteerAngle(double targetAngle) {
		double offset = steerOffset(targetAngle, getAngle());
		steerMotorToOffset(offset, .5);
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
		
		offset = ((targetAngle - currentAngle));//gives the difference of the angles
		absOffset = Math.abs(offset);

		if(absOffset <= 180)//not crossing the zero boundary
			return offset;
		else {
			offset = (absOffset % 180) - 180;//getting the difference when crossing zero

			if(targetAngle < currentAngle)//finds what way the zero is crossed
				return -offset;
			else
				return offset;
		}
	}
	
	/**
	 * setting the angles of the steer motors at a given speed, using the threshold 
	 * @param offset the angle that we are turning to 
	 * @param speed how fast the motor will actually turn
	 * @return difference between current angle and angle change
	 */
	public double steerMotorToOffset(double offset, double speed) {
		
		if(offset > k_steer_threshold) {
			m_steerMotor.set(speed);
		} else if (offset < -k_steer_threshold) {
			m_steerMotor.set(-speed);
		}
		m_steerMotor.set(0);
		return steerOffset(offset, getAngle());
	}
	
	/**
	 * setting the angles of the steer motors at max speed (1.0), using the threshold
	 * @param offset the angle that we are turning to
	 * @return difference between current angle and angle changed 
	 */
	public double steerMotorToOffset(double offset) {
		return steerMotorToOffset(offset, 1.0);
	}
}
