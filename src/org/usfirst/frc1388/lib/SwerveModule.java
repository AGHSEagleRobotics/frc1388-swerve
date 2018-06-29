package org.usfirst.frc1388.lib;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SpeedController;

public class SwerveModule {
	
	private SpeedController m_driveMotor;
	private SpeedController m_steerMotor;
	private AnalogInput m_steerEncoder;
	
	public SwerveModule(SpeedController driveMotor, SpeedController steerMotor, AnalogInput steerEncoder ) {
		m_driveMotor = driveMotor;
		m_steerMotor = steerMotor;
		m_steerEncoder = steerEncoder;
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

	
	
}
