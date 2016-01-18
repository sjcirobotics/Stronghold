package org.usfirst.frc.team5590.robot.commands;

import org.usfirst.frc.team5590.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**This command sets the left track motor speed to 0.3 
 * and right track motor speed to -0.3
 *To spin the robot right
 */
public class SpinRight extends Command {

    public SpinRight() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires (Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.stop();
    	Robot.drivetrain.rotateRight(0.3);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stop();
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
