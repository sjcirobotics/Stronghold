package org.usfirst.frc.team5590.robot.commands.shooter;

import org.usfirst.frc.team5590.robot.Robot;
import org.usfirst.frc.team5590.robot.subsystems.Shooter.Position;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterEvade extends Command {

	private static final double DEGREE_POSITION = -90;

    public ShooterEvade() {
    	requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooter.setPosition(DEGREE_POSITION);   	
		Robot.shooter.shooterPosition = Position.EVADE;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
