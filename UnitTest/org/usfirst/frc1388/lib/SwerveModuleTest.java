package org.usfirst.frc1388.lib;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

class SwerveModuleTest {

	static SwerveModule swerveModule;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		swerveModule = new SwerveModule();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSteerOffset() {
		assertEquals(60, swerveModule.steerOffset(90,30), "Target = 90, Current = 30"); 
		assertEquals(-15, swerveModule.steerOffset(45,60), "Target = 45, Current = 60"); 
		assertEquals(-20, swerveModule.steerOffset(355,15), "Target = 355, Current = 15"); 
	}
	
	@Test
	void testNormalizeAngle() {
		assertEquals(  0,   SwerveModule.normalizeAngle(0),       "Target = 0");
		assertEquals(  1,   SwerveModule.normalizeAngle(1),       "Target = 1");
		assertEquals(100,   SwerveModule.normalizeAngle(100),     "Target = 100");
		assertEquals(359.9, SwerveModule.normalizeAngle(359.9),   "Target = 359.9");
		assertEquals(  0,   SwerveModule.normalizeAngle(360),     "Target = 360");
		assertEquals(140,   SwerveModule.normalizeAngle(500),     "Target = 500");
		assertEquals(  0,   SwerveModule.normalizeAngle(720),     "Target = 720");
		assertEquals(180,   SwerveModule.normalizeAngle(900),     "Target = 900");
		assertEquals(280,   SwerveModule.normalizeAngle(1000000), "Target = 1000000");
		assertEquals(359,   SwerveModule.normalizeAngle(-1),      "Target = -1");
		assertEquals(320,   SwerveModule.normalizeAngle(-400),    "Target = -400");
		assertEquals(180,   SwerveModule.normalizeAngle(-900),    "Target = -900");
		
		// Sweep through many values
		for (double angle = -1001.0; angle <= 1001.0; angle += 0.1) {
			double normalized = angle;
			while (normalized <    0.0) normalized += 360.0;
			while (normalized >= 360.0) normalized -= 360.0;
			assertEquals(normalized, SwerveModule.normalizeAngle(angle), "Target = " + angle);
		}
	}

}
