// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//hi

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final double kMaxSpeed = 0.5;

  private final WPI_TalonSRX m_frontLeft = new WPI_TalonSRX(3);
  private final WPI_TalonSRX m_rearLeft = new WPI_TalonSRX(4);
  private final WPI_TalonSRX m_frontRight = new WPI_TalonSRX(2);
  private final WPI_TalonSRX m_rearRight = new WPI_TalonSRX(1);

  private final MecanumDrive m_driveTrain = new MecanumDrive(m_frontLeft, m_rearLeft, m_frontRight, m_rearRight);
  private final XboxController m_controller = new XboxController(0);

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_frontLeft.setInverted(true);
    m_rearRight.setInverted(true);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double ySpeed = deadband(m_controller.getY(Hand.kLeft), 0.2);
    ySpeed = -Math.pow(ySpeed, 2) * Math.copySign(kMaxSpeed, ySpeed);
    double xSpeed = deadband(m_controller.getX(Hand.kLeft), 0.2);
    xSpeed = Math.pow(xSpeed, 2) * Math.copySign(kMaxSpeed, xSpeed);
    double zRotation = deadband(m_controller.getX(Hand.kRight), 0.2);
    zRotation = Math.pow(zRotation, 2) * Math.copySign(kMaxSpeed, zRotation);
    m_driveTrain.driveCartesian(ySpeed, xSpeed, zRotation);
    System.out.printf("y: %.2f\tx: %.2f\tz: %.2f%n", ySpeed, xSpeed, zRotation);
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
  }

  // 11/4 delete >:)

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }

  private double deadband(double input, double deadbandRange) {
    if ((input > -deadbandRange) && (input < deadbandRange)) {
      return 0;
    } else {
      return input - (Math.copySign(deadbandRange, input)) / (1 - deadbandRange);
    }
  }
}
