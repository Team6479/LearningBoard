package org.usfirst.frc.team6479.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot 
{
	//mode selected, either pneumatics or electronics
	String modeSelected;
	
	//motor controllers
	Spark spark;
	Victor victor;
	
	//sensors
	RangeFinderAnalog sonar;
	Encoder encoder;
	
	//counter for driver info, so code isnt called too much
	private int driverCounter;
	
	@Override
	public void robotInit() {	
		//init all motor controllers and sensors
		spark = new Spark(0);
		victor = new Victor(0);
		sonar = new RangeFinderAnalog(0);
		
		//encoder
		encoder = new Encoder(1, 2, false, Encoder.EncodingType.k4X);
		// set the time until when the robot is considered stopped, set in seconds
		encoder.setMaxPeriod(.05);
		// set distance per pulse to be 1 inch
		encoder.setDistancePerPulse((6 * Math.PI) / 360);
		
		//setup driver console
		SmartDashboard.putString("DB/String 0", "electronics");
		driverInfo();
		driverCounter = 0;
		
		//camera
		CameraServer.getInstance().startAutomaticCapture();
		
	}
	//method to show driver information
	public void driverInfo()
	{
		SmartDashboard.putString("DB/String 1", String.format("Spark: %.3f", spark.get()));
		SmartDashboard.putString("DB/String 2", String.format("Victor: %.3f", victor.get()));
		SmartDashboard.putString("DB/String 3", String.format("Sonar: %.3f\"", sonar.getDistanceInInches()));
		SmartDashboard.putString("DB/String 4", String.format("Encoder: %.3f\"", encoder.getDistance()));
	}
	@Override
	public void teleopInit() {
		// get the selected mode
		modeSelected = SmartDashboard.getString("DB/String 0", "electronics");

		// reset the encoder
		encoder.reset();
		
		//update driver info
		driverInfo();
		driverCounter = 0;
	}
	@Override
	public void teleopPeriodic() {
		//based on mode, do stuff
		switch(modeSelected)
		{
		case "electronics":
			//run victor at half speed backwards and spark at full speed
			victor.set(-0.5);
			victor.set(1.0);
		break;
		case "pneumatics":
			//do pneumatics stuff
		break;
		}
		
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

