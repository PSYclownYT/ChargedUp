package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmExtenderSubsystem;
import frc.robot.subsystems.ArmPneumaticsSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class ArmControls extends CommandBase{

  ArmSubsystem armSubsystem;
  WristSubsystem wristSubsystem;
  ArmExtenderSubsystem armExtenderSubsystem;
  ArmPneumaticsSubsystem armPneumaticsSubsystem;

  int x = 0;
  int y = 0;
  int w = 0;
  int phd = 0;

    public ArmControls(ArmSubsystem armSubsystem, WristSubsystem wristSubsystem, ArmExtenderSubsystem armExtenderSubsystem, ArmPneumaticsSubsystem armPneumaticsSubsystem){
        this.armSubsystem = armSubsystem;
        this.wristSubsystem = wristSubsystem;
        this.armExtenderSubsystem = armExtenderSubsystem;
        this.armPneumaticsSubsystem = armPneumaticsSubsystem;
        addRequirements(armSubsystem, wristSubsystem, armExtenderSubsystem);
      }


    @Override
    public void initialize() {
        armSubsystem.setBreakMode();
        armExtenderSubsystem.setBreakMode();
    }
    
    @Override
    public void execute() {
      double speed = Constants.armthings.armstopspeed;
      double speedw = Constants.armthings.wriststopspeed;//make later
      double speede = 0;
      boolean stopnow = false;

      XboxController controller = RobotContainer.controller;
      // left controller left and right
      SmartDashboard.putNumber("XBoxControllerAxis0", controller.getRawAxis(0));
      // left controller up and down 
      SmartDashboard.putNumber("XBoxControllerAxis1", controller.getRawAxis(1));
      // right controller left and right
      SmartDashboard.putNumber("XBoxControllerAxis2", controller.getRawAxis(2));
      // right contoller up and down
      SmartDashboard.putNumber("XBoxControllerAxis3", controller.getRawAxis(3));
  
      Point RightControllerPoint = new Point(controller.getRawAxis(0), controller.getRawAxis(1));
      Point LeftControllerPoint = new Point(controller.getRawAxis(2), controller.getRawAxis(3));

      if (RobotContainer.controller.getPOV() == -1) {
        y = 0;
        x = 0;
      }
      else if (RobotContainer.controller.getPOV() > 45 && RobotContainer.controller.getPOV() <= 135){y = 1;}//exe
      else if(RobotContainer.controller.getPOV() > 225 && RobotContainer.controller.getPOV() <= 315){y = 2;}

      else if (RobotContainer.controller.getPOV() > 135 && RobotContainer.controller.getPOV() <= 225) {x = 1;}//was both now noth (im a poet)
      else if (RobotContainer.controller.getPOV() > 315 || RobotContainer.controller.getPOV() < 45) {x = 2;}

      else{//does this do anything? no it doesnt
        y = 0;
        x = 0;
      }

      /*if (RobotContainer.controller.getLeftBumperPressed()) {x = 1;}
      else if (RobotContainer.controller.getRightBumperPressed()) {x = 2;}
      else if (RobotContainer.controller.getLeftBumperReleased() == true && RobotContainer.controller.getRightBumperReleased() == true){x = 0;}
      */
      
      /*
      if (RobotContainer.controller.getRawButtonPressed(1) == true){
        //speedw = Constants.armthings.wristspeed;
        w = 1;
      }//both
      else if(RobotContainer.controller.getRawButtonPressed(2) == true){
        //speedw = Constants.armthings.wristspeed * -1;
        w = 2;
      }
      else if (RobotContainer.controller.getRawButtonReleased(1) == true && RobotContainer.controller.getRawButtonReleased(2) == true){
        w = 0;
      }//one fish two fish red fish blue fish
      */

      if  (RobotContainer.controller.getRightTriggerAxis() > 0.2){w = 1;}
      if (RobotContainer.controller.getLeftTriggerAxis() > 0.2) {w = 2;}
      if (RobotContainer.controller.getLeftTriggerAxis() < 0.2 && RobotContainer.controller.getRightTriggerAxis() < 0.2){
        w = 0;
      }

      /*if (RobotContainer.controller.getAButtonReleased() && RobotContainer.controller.getBButtonReleased()){
        speedw = 0;
        w = 3;
      }*/


      if (RobotContainer.controller.getXButtonPressed()){
        armPneumaticsSubsystem.in();
      }
      if (RobotContainer.controller.getBButtonPressed()){
        armPneumaticsSubsystem.out();
      }


      //if(RobotContainer.controller.getBackButtonPressed()){Constants.armthings.morecontrol = true;}

      //if (Constants.armthings.morecontrol){speed = controlslb(speed);}

      if (x == 1){
        speed = Constants.armthings.armspeed;
        //speedw = Constants.armthings.wristspeed;
      }
      else if (x == 2){
        speed = Constants.armthings.armspeed * -1;
        //speedw = Constants.armthings.wristspeed * -1;
      }
      else if (x == 0) {
        speed = Constants.armthings.armstopspeed;
        //speedw = Constants.armthings.wriststopspeed;
      }

      if (y == 1) {
        speede = Constants.armthings.armexespeed;
      }
      else if (y == 2) {
        speede = Constants.armthings.armexespeed * -1;
      }
      else {speede = 0;}

      SmartDashboard.putNumber("aksdjhfaksjbhf", speed);


      if (w == 1) {speedw = Constants.armthings.wristspeed;}
      else if (w == 2) {speedw = Constants.armthings.wristspeed * -1;}

      if (RobotContainer.controller.getYButtonPressed()) {
        stopnow = true;
      }
      if (stopnow == true){
        x = 0;
        y = 0;
        w = 0;
        speed = 0;
        speede = 0;
        speedw = 0;
      }
      if (RobotContainer.controller.getRightTriggerAxis() > 0) {stopnow = false;}

      armSubsystem.movemotor(speed);
      wristSubsystem.wristWatch(speedw);
      armExtenderSubsystem.movemotor(speede);
        
    }

    /*double controlslb(double speed) {

      if (RobotContainer.controller.getLeftBumperPressed()){speed = speed * -1;}

      return 0;

    }*/
}
