/*Badger 4/9/2014
This code is for paintrain without the shooter mechanism.
This code will utilize the intake and drive system.
This is based on having all talons
*/
package nerdHerd.robot2014;

import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.KinectStick;
import nerdHerd.util.NerdyBot;
import nerdHerd.util.NerdyTimer;
import nerdHerd.util.ThreeCimBallShifter;
//import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import nerdHerd.util.ChezyButton;
import edu.wpi.first.wpilibj.Encoder;

public class Main extends NerdyBot{
    Joystick leftJoystick, rightJoystick, articJoystick;
    NerdyTimer autonomousTimer = new NerdyTimer(2.0), autonomousShooterTimer = new NerdyTimer(5.0);
    PainTrain myRobot;
    Timer timer;
    double holdVal, shotVal, threshVal;
    double leftSpeed = 0.0, rightSpeed = 0.0;
    boolean isRunningAutonomous = false;
    

    public void robotInit(){
        
        holdVal = (DriverStation.getInstance().getAnalogIn(1)) * 100;
        shotVal = (DriverStation.getInstance().getAnalogIn(2)) * 100;
        threshVal = (DriverStation.getInstance().getAnalogIn(3)) * 100;
        
        myRobot         = new PainTrain();
        leftJoystick    = new Joystick(1);
        rightJoystick   = new Joystick(2);
        articJoystick   = new Joystick(3);

        autonomousTimer.start();
        timer           = new Timer();
        //leftStick = new KinectStick(1);
        //rightStick = new KinectStick(2);
        autonomousShooterTimer.start();
//        filer           = new Filer();


    }

    public void autonomousPeriodic() {
        myRobot.setLeft(-0.5);
        myRobot.setRight(-0.5);
        if(timer.get() >= 2.0){
            myRobot.setLeft(0.0);
            myRobot.setRight(0.0);
            timer.stop();
        }
    }
    
    public void autonomousContinous(){
       myRobot.run();
//       report('A');
    }
    
    public void autonomousInit(){
        myRobot.enable();
        myRobot.shift(ThreeCimBallShifter.GearNumber.kFirstGear);
        timer.start();
        timer.reset();
    }
    
    public void teleopInit() {
        myRobot.enable();
//        filer.connect();
//        filer.println("Time , EncoderA, EncoderB, ShootEncoder, Shoot, Retract");
        timer.start();
        timer.reset();

    }

    public void teleopPeriodic() {
        double leftSpeed           =  leftJoystick.getY();
        double rightSpeed          =  rightJoystick.getY();
        boolean shiftUp     =  leftJoystick.getRawButton(3);  //Shift up
        boolean shiftDown   =  leftJoystick.getRawButton(2);  //Shift down for what
        boolean intakeIn    =  articJoystick.getRawButton(5); //Retract Intake
        boolean intakeOut   =  articJoystick.getRawButton(3); //Extend Intake
        boolean intake      =  articJoystick.getRawButton(6); //Spin wheels in
        boolean outTake     =  articJoystick.getRawButton(4); //Spin wheels out
        boolean shootButton =  articJoystick.getRawButton(1); //Shoot
        boolean shooterReset = articJoystick.getRawButton(7); //Reset shooter
        double shotVal      = (DriverStation.getInstance().getAnalogIn(2)) * 100;
        double holdVal      = (DriverStation.getInstance().getAnalogIn(1)) * 100;
        double threshVal    = (DriverStation.getInstance().getAnalogIn(3))/5;
        
        
        if(intakeIn){
            myRobot.retractIntake();
        }else if(intakeOut){
            myRobot.extendIntake();
        }

        if(intake){
            myRobot.startIntake(1.0);
        }else if(outTake){
            myRobot.startIntake(-1.0);
        }else{
            myRobot.stopIntake();
        }
        
        
        if(shiftUp){
            myRobot.shift(ThreeCimBallShifter.GearNumber.kSecondGear);
        }else if(shiftDown){
            myRobot.shift(ThreeCimBallShifter.GearNumber.kFirstGear);
        }
        
      if(shootButton) {
          myRobot.shoot();
      } else if(shooterReset){
           myRobot.shooterReset();
       }
        myRobot.setLeft(leftSpeed);
        myRobot.setRight(rightSpeed);
        
        myRobot.setShotVal(shotVal);
//        myRobot.setHoldVal(holdVal);
        myRobot.setThreshVal(threshVal);
        
        System.out.println("shootButton \t" + shootButton);
//        System.out.print("holdButton \t" + holdButton);
        
        myRobot.run();
        
        //need encoder values
                
//        report('T');
    }
    
    public void teleopContinous(){
        
    }
    
    public void testPeriodic(){         //PROJECT MOTORTEST 
      boolean forward =  articJoystick.getRawButton(1); //Shoot
      boolean backward = articJoystick.getRawButton(7); //Reset shooter       
      myRobot.test(forward, backward);
    }
    
    public void disabledInit(){
        myRobot.disable();
        
    }
    
    public void disabled(){
        myRobot.run();
//        report('D');
//        filer.close();
    }
    
//    private void report(char robotState)
//    {                
//        double time = timer.get();
//        filer.println(time + "," +robotState + ","
//                + myRobot.getLeftGBEncoder() + "," 
//                + myRobot.getRightGBEncoder() + "," 
//                + myRobot.getShooterEncoder() + ","
//                + myRobot.getShootTime() + ","
//                + myRobot.getRetractTime() + ","
//        );
//        SmartDashboard.putDouble("Encoder Left", myRobot.getLeftGBEncoder());
//        SmartDashboard.putDouble("Encoder Right", myRobot.getRightGBEncoder());
//        SmartDashboard.putDouble("Encoder Shooter", myRobot.getShooterEncoder());
    }


