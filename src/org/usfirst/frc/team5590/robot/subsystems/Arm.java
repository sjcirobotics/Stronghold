package org.usfirst.frc.team5590.robot.subsystems;
import org.usfirst.frc.team5590.robot.Robot;
import org.usfirst.frc.team5590.robot.commands.arm.ManualArmControl;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {

	private static final int ARM_ROTATIONAL_PWM = 2;

	/**
	 * DIO Ports for Encoder
	 */
	private static int ROTATIONAL_ENCODER_SIGNAL_A = 7;
	private static int ROTATIONAL_ENCODER_SIGNAL_B = 8;
	
	private static int ANALOG_SAFETY_SWITCH_PORT = 2;
	
	private static SpeedController rotationalSpeedController;
	private static Encoder         rotationalEncoder;
	private static AnalogInput     safetySwitch;
	
	public void initDefaultCommand() {
		setDefaultCommand(new ManualArmControl());
	}
	
	public void resetArm() {
		this.rotate(0, -1);
		while (safetySwitch.getVoltage() < .8) {
			rotationalSpeedController.set(-0.1);
			System.out.println("Reseting Arm");
		}
		rotationalEncoder.reset();
		System.out.println("&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*");
	}

	public static void initializeControllers() {	
		rotationalSpeedController = new TalonSRX(ARM_ROTATIONAL_PWM);
		rotationalEncoder = new Encoder(ROTATIONAL_ENCODER_SIGNAL_A, ROTATIONAL_ENCODER_SIGNAL_B,
				false, EncodingType.k2X);
		 safetySwitch = new AnalogInput(ANALOG_SAFETY_SWITCH_PORT);
	}

	public void setPosition(double degrees){
    	if (degrees > this.getDegrees()) {
    		System.out.println("Rotating");
    		rotate(this.getDistance(degrees), 1);
    	} else {
    		rotate(this.getDistance(degrees) ,-1);
    	}
	}
	
	/**
	 * This method takes in a raw distance and direction parameter, and rotates
	 * the arm at a speed of 0.5. The direction is determined by -1, and 1.
	 * 
	 * As the arm reaches 90% of its desired rotation distance, the arm will then
	 * slow down to a speed of 0.2 until desired destination is reached. This will 
	 * reduce error from the values on the encoder.
	 * 
	 * @param rawDistance is the desired rotational value
	 * @param direction -1 rotates arm clockwise, 1 rotates arm counterclockwise
	 */
	public void rotate(double rawDistance, double direction) {
		double speedControlApex = rawDistance * 0.1;
		if (rawDistance == 0){
			speedControlApex = rotationalEncoder.getDistance() * 0.1;
		}
		while ((rotationalEncoder.getDistance()*direction) < (rawDistance * direction)) {
			if (safetySwitch.getVoltage() > 1.0) {
				break;
			}
			if (Math.abs(rotationalEncoder.getDistance() - rawDistance) < speedControlApex){
				rotationalSpeedController.set(0.2*direction);
			} else {
				rotationalSpeedController.set(0.5*direction);
			}
		}
		System.out.println("Current Location: " + rotationalEncoder.getDistance() + " Final Location: " + rawDistance);
		rotationalSpeedController.set(0.0);
	}

	public double getDegrees(){
		return .27355*rotationalEncoder.getDistance();
	}
	
	public double getDistance(double degrees){
		return 3.6555*degrees;
	}

	public void updateRotation(){
		if (Math.abs(Robot.oi.logitechController.getMainStickY()) > 0.5){
			rotationalSpeedController.set(Robot.oi.logitechController.getMainStickY());
		} else {
			rotationalSpeedController.set(0);
		}
	}
}
