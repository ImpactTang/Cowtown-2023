package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.arm.StowCmd;
import frc.robot.commands.intake.IntakeForwardCmd;
import frc.robot.commands.intake.IntakeHoldCmd;
import frc.robot.commands.routines.scoring.ScoreHighCmd;
import frc.robot.commands.routines.scoring.ScoreLowCmd;
import frc.robot.commands.routines.scoring.ScoreMidCmd;
import frc.robot.commands.swerve.SwerveJoystickCmd;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.WristSubsystem;


public class RobotContainer {

  private final SwerveSubsystem swerveSubsystem = new SwerveSubsystem();
  private final ArmRotationSubsystem armRotationSubsystem = new ArmRotationSubsystem();
  private final ArmExtensionSubsystem armExtensionSubsystem = new ArmExtensionSubsystem();
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  private final WristSubsystem wristSubsystem = new WristSubsystem();

  private final XboxController driveController = new XboxController(0);
  private final CommandXboxController cmdDriveController = new CommandXboxController(0);

  /* NOTE: BUTTON BOX BUTTONS START AT 1!!! */
  private final CommandJoystick buttonBox = new CommandJoystick(1);  

  private final JoystickButton robotCentric = new JoystickButton(driveController, XboxController.Button.kStart.value);

  public RobotContainer() {

    // Xbox Controller Driving
    swerveSubsystem.setDefaultCommand(new SwerveJoystickCmd(swerveSubsystem,
    () -> -driveController.getRawAxis(0), // Axis 0 = Left X Stick
    () -> -driveController.getRawAxis(1), // Axis 1 = Left Y Stick
    () -> driveController.getRawAxis(4), // Axis 2 = Right X Stick
    () -> robotCentric.getAsBoolean()));

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    cmdDriveController.leftBumper().onTrue(new InstantCommand(() -> swerveSubsystem.resetHeading()));

    cmdDriveController.x().onTrue(new IntakeForwardCmd(intakeSubsystem));
    cmdDriveController.x().onFalse(new IntakeHoldCmd(intakeSubsystem));

    cmdDriveController.rightBumper().onTrue(new ScoreHighCmd(armRotationSubsystem, armExtensionSubsystem, intakeSubsystem));
    cmdDriveController.y().onTrue(new ScoreMidCmd(armRotationSubsystem, armExtensionSubsystem, intakeSubsystem));
    cmdDriveController.b().onTrue(new ScoreLowCmd(armRotationSubsystem, armExtensionSubsystem, intakeSubsystem));
    cmdDriveController.a().onTrue(new StowCmd(armRotationSubsystem, armExtensionSubsystem, wristSubsystem));
    // buttonBox.button(ButtonBoxButtons.straightUpButton).onTrue(new StowCmd(armSubsystem));
  }
  
}