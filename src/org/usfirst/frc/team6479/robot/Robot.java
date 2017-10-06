package org.usfirst.frc.team6479.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
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
	DoubleSolenoid dubsolenoid; 
	
	//Spike
	Relay spike;
	
	//Encoder encoder;
	
	//counter for driver info, so code isnt called too much
	private int driverCounter;
	
	@Override
	public void robotInit() {	
		//init all motor controllers and sensors
		victor1 = new Victor(0);
		victor2 = new Victor(1);
		
		//init Compressor
		compressor = new Compressor(1);
		
		//controller
		xbox = new XboxController(0);
		
		//Solenoid
		dubsolenoid = new DoubleSolenoid(4,5);
		
		//Spike
		spike = new Relay(3);
		
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
	}
	@Override
	public void teleopPeriodic() {
		
		double leftY = xbox.getRawAxis(1);
		double rightY = xbox.getRawAxis(5);
		
		if (xbox.getRawButton(3))
		{
			compressor.setClosedLoopControl(true);
		}
		
		if (xbox.getRawButton(2))
		{
			compressor.setClosedLoopControl(false);
		}
		
		//Set solenoid to on or off
		if(xbox.getRawButton(4)) {
			dubsolenoid.set(Value.kForward);
		}
		
		if(xbox.getRawButton(1)) {
			dubsolenoid.set(Value.kReverse);
		}
		
		if(xbox.getRawButton(7)) {
			spike.set(Relay.Value.kOn);
		}
		
		if (xbox.getRawButton(8)) {
			spike.set(Relay.Value.kOff);
		}
		
		//set a threshold for motors
		victor1.set(Math.abs(leftY) >= 0.008 ? leftY : 0);
		victor2.set(Math.abs(rightY) >= 0.008 ? rightY : 0);
		
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

