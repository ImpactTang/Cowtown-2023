package frc.robot.commands.routines.loading;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.arm.ExtendArmCmd;
import frc.robot.commands.arm.RotateArmCmd;
import frc.robot.commands.intake.IntakeForwardCmd;
import frc.robot.commands.wrist.RotateWristCmd;
import frc.robot.subsystems.ArmExtensionSubsystem;
import frc.robot.subsystems.ArmRotationSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class SingleSubstationCmd extends SequentialCommandGroup{

    public SingleSubstationCmd(ArmRotationSubsystem armRotationSubsystem, ArmExtensionSubsystem armExtensionSubsystem, WristSubsystem wristSubsystem, IntakeSubsystem intakeSubsystem){

        addCommands(
            new ParallelCommandGroup(
                new ExtendArmCmd(armExtensionSubsystem, Units.inchesToMeters(0.0)),
                new RotateArmCmd(armRotationSubsystem, Units.degreesToRadians(0.0)),
                new RotateWristCmd(wristSubsystem, Units.degreesToRadians(45.0))));
        addCommands(
            new IntakeForwardCmd(intakeSubsystem));

    }
    
}
