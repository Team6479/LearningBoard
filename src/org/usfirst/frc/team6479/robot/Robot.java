package org.usfirst.frc.team6479.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot 
{
	
	//motor controllers
	Victor victor1;
	Victor victor2;
	
	
	//xbox
	XboxController xbox;
	
	//Compressor
	Compressor compressor;
	
	//Solenoid
	Solenoid solenoid; 
	
	//Encoder encoder;
	
	//counter for driver info, so code isnt called too much
	private int driverCounter;
	
	@Override
	public void robotInit() {	
		//init all motor controllers and sensors
		victor1 = new Victor(0);
		victor2 = new Victor(1);
		
		//init Compressor
		compressor = new Compressor(0);
		//Set compressor to off by default
		compressor.setClosedLoopControl(false);
		
		//controller
		xbox = new XboxController(0);
		
		//Solenoid
		solenoid = new Solenoid(0);
		
		//sonar = new RangeFinderAnalog(0);
		
		//encoder
		//encoder = new Encoder(1, 2, false, Encoder.EncodingType.k4X);
		// set the time until when the robot is considered stopped, set in seconds
		//encoder.setMaxPeriod(.05);
		// set distance per pulse to be 1 inch
		//encoder.setDistancePerPulse((6 * Math.PI) / 360);
		
		driverInfo();
		driverCounter = 0;
		
		
	}
	//method to show driver information
	public void driverInfo()
	{
		SmartDashboard.putString("DB/String 1", String.format("Victor 1: %.3f", victor1.get()));
		SmartDashboard.putString("DB/String 2", String.format("Victor 2: %.3f", victor2.get()));
		//SmartDashboard.putString("DB/String 4", String.format("Encoder: %.3f\"", encoder.getDistance()));
	}
	@Override
	public void teleopInit() {

		// reset the encoder
		//encoder.reset();
		
		//update driver info
		driverInfo();
		driverCounter = 0;
		
		//Set Compressor to on
		compressor.setClosedLoopControl(true);
	}
	@Override
	public void teleopPeriodic() {
		
		double leftY = xbox.getRawAxis(1);
		double rightY = xbox.getRawAxis(5);
		
		victor1.set(leftY);
		victor2.set(rightY);
		
		//do pneumatics stuff
		solenoid.set(true);
		
		//update driver info of appropriate in cycle
		driverCounter++;
		if (driverCounter == 3)
		{
			driverInfo();
			driverCounter = 0;
		}
	}
	@Override
	public void testPeriodic() {	
	}
}

