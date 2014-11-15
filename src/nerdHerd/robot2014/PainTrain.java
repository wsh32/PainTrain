/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nerdHerd.robot2014;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import nerdHerd.util.ThreeCimBallShifter;
/**
 *
 * @author Jordan
 */
public class PainTrain {
    private ThreeCimBallShifter m_leftGearbox, m_rightGearbox;
    private Intake m_intake;
    private Shooter m_shooter;
    private Compressor m_compressor;
    private Encoder m_encodeLeft, m_encodeRight;
    private ThreeCimBallShifter.GearNumber m_gear = ThreeCimBallShifter.GearNumber.kFirstGear;
    private boolean m_isEnabled = false;
    double m_holdValue = 0.0;
    
    
    public PainTrain(){
        m_leftGearbox   = new ThreeCimBallShifter(  new Victor(1),
                                                    new Victor(2),
                                                    new Victor(3),
                                                    new DoubleSolenoid (1,2) );
                
        m_rightGearbox  = new ThreeCimBallShifter(  new Victor(4),
                                                    new Victor(5),
                                                    new Victor(6));
        
        m_shooter       = new Shooter(7,8,7,8,9);
        m_intake        = new Intake( 3,
                                      5,
                                      10 );
        m_encodeLeft = new Encoder(2,3);
        m_encodeRight = new Encoder(5,6);
  
        m_compressor    = new Compressor(1,4);
        m_compressor.start();
    }
    
    public void init(){
        m_intake.init();
        m_encodeLeft.start();
        m_encodeLeft.reset();
        m_encodeRight.start();
        m_encodeRight.reset();
    }
    
    public void disable(){
        m_isEnabled = false;
    }
    
    public void enable(){
        m_isEnabled = true;
        m_intake.enable();
    }
    
    public void run(){
        if(m_isEnabled){
            m_leftGearbox.run();
            m_rightGearbox.run();
            m_intake.run();
            m_shooter.run();
//            check();
        }else{
            m_leftGearbox.set(0.0);
            m_rightGearbox.set(0.0);
            m_intake.disable();
            m_shooter.disable();
//            check();
        }
    }
    
    public void setLeft(double value){
        m_leftGearbox.set(value);
    }
    
    public void setRight(double value){
        m_rightGearbox.set(-value);
    }
    
    public void pullShooter(){
//        m_shooter.pullDown();
    }
    
    public void shoot(){
        if( m_intake.isRaised() ) {
           // dont shoot
        } else {
             m_shooter.shoot();
        }
    }
    
    public void shooterReset(){
        m_shooter.reset();
    }
    
//    public void holdPosition(boolean holdButton){
//        m_shooter.hold(holdButton); 
//    }
    
    public void setShotVal(double shotVal){
        m_shooter.setShotVal(shotVal);
    }
    
//    public void setHoldVal(double holdVal){
//        m_shooter.setHoldVal(holdVal);
//    }
    
    public void setThreshVal(double threshVal){
        m_shooter.setThreshVal(threshVal);
    }
    
    public void disableShooter(){
        m_shooter.disable();
    }
    
    public void test(boolean forward, boolean backward){
        m_shooter.test(forward, backward);
    }
    
    public void retractIntake(){
        m_intake.raiseArm();
    }
    
    public void extendIntake(){
        m_intake.lowerArm();
    }
    
    public void startIntake(double speed){
        m_intake.setWheelSpeed(speed);
    }
    
    public void stopIntake(){
        m_intake.stopWheels();
    }
    
    public void shift(ThreeCimBallShifter.GearNumber value){
        m_leftGearbox.shift(value);
    }
    public int getLeftGBEncoder(){
      return m_encodeLeft.get();
      
    }
    
    public int getRightGBEncoder(){
       return m_encodeRight.get();
    }
    
//    public int getShooterEncoder(){
//       return m_shooter.getEncoder();
//    }
//    
//    public double getShootTime(){
//       return m_shooter.getShootTime();
//    }
//    public double getRetractTime(){
//       return m_shooter.getRetractTime();
//    }
//    public void check(){
//        if(m_intake.isIntakeUp()){
//            m_shooter.disable();
//        }else if(m_shooter.isShooterOut()){
//            m_intake.disable();
//        }
//    }
}
