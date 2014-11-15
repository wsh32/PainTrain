/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/**
 * @author Mr. Mallory
 * @version 20140922
 */
package nerdHerd.robot2014;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import nerdHerd.util.Thresholder;
import nerdHerd.util.InputValues;

/**
 * The VM is configured to automatically run this class,and to call the
 * functions corresponding to each mode,as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project,you must also update the manifest file in the resource
 * directory.
 */
public class Shooter {

    Victor m_motor1, m_motor2, m_motor3;
    Encoder m_encode;
    Timer m_shootTimer, m_retractTimer;
    boolean m_shooting = false;
    boolean m_retracting = false;
    boolean m_holding;
    double m_power = 0;
    double m_shotValue, m_holdValue = 0;
    double error = 0;
    double m_pHold = .025;
    double m_pShoot = 0.02;
    double m_shootTime = 1;
    double m_retractTime = 5.0;
    double m_thresholdValue; // max m_power, not used
    boolean m_pleaseShoot, m_holdButton, m_wasPressed, m_wasReleased;
    final double retractPower = 0.2;
    boolean m_shooterReset = true;
   

    public Shooter(int encoderA, int encoderB, int victor1, int victor2, int victor3) {
        m_encode = new Encoder(encoderA, encoderB);
        m_encode.start();
        m_encode.reset();
        m_motor1 = new Victor(victor1);
        m_motor2 = new Victor(victor2);
        m_motor3 = new Victor(victor3);
        m_shootTimer = new Timer();
        m_retractTimer = new Timer();

    }

    public void run() {

        if (false == m_shooting && false == m_retracting) { //resting    
            if (m_pleaseShoot && m_shooterReset) {     
                // shoot button is pressed and it was previously released
                m_shooting = true;
                m_shooterReset = false;
                m_shootTimer.reset();
                m_shootTimer.start();
                System.out.println("SHOOT CALLED");
                Shoot();
            } else {
                System.out.println("HOLD");
            }
        } else if (true == m_shooting && false == m_retracting){  //shooting
            Shoot();
            System.out.println("SHOOT");   
        } else if (false == m_shooting && true == m_retracting) { //retracting
            Retract();
            System.out.println("RETRACT");
        } else {   // should never happen
            System.out.println("WHAT IS HAPPENING");
            SmartDashboard.putString("Shooter Error Message", "WHAT IS HAPPENING");
        }
        System.out.println("Button reset" + m_shooterReset);
        System.out.println(flip(m_encode.get()));
        System.out.println("THRESH" + m_thresholdValue);
        m_motor1.set(m_power);
        m_motor2.set(m_power);
        m_motor3.set(m_power);

        SmartDashboard.putBoolean("shooting", m_shooting);
        SmartDashboard.putBoolean("retracting", m_retracting);

    }

    public void shoot() {
        m_pleaseShoot = true;
    }

//    public void hold(boolean holdButton) {
//        m_holdButton = holdButton;
//    }
    
    public void setShotVal(double shotVal){
        m_shotValue = shotVal;
    }
    
//    public void setHoldVal(double holdVal){
//        m_holdValue = holdVal;
//    }
    
    public void setThreshVal(double threshVal){
        m_thresholdValue = threshVal;
    }
        
    public void reset(){
        m_shooterReset = true;
        m_pleaseShoot = false;
    }
    
    public int flip(int input){
        input = input * -1;
        return input;
    }
    
    private void Shoot() {

        if (flip(m_encode.get()) >= m_shotValue || m_shootTimer.get() > m_shootTime) { //Stop and Retract
            m_shooting = false;
            m_retracting = true;
            m_shootTimer.stop();
            m_shootTimer.reset();
            m_retractTimer.reset();
            m_retractTimer.start();
            Retract();
        } else {
            error = m_shotValue - flip(m_encode.get());
            m_power = -Threshold(m_pShoot * error);
        }
    }

    private void Retract() {
        if (flip(m_encode.get()) <= m_holdValue || m_retractTimer.get() > m_retractTime) { //Stop and Rest
            m_power = 0;
            m_shooting = false;
            m_retracting = false;
            m_retractTimer.stop();
            m_retractTimer.reset();
        } else { // m_encode.get > 0 && m_retractTimer.get() < m_retractTime
            m_power = retractPower;
        }
    }

//    private void holdPosition() {
//        error = m_holdValue - m_encode.get();
//        m_power = -Threshold(m_pHold * error);
//    }
    
    private double Threshold(double value) {
        if (value > 1.0) {
            return m_thresholdValue;
        } else if (value < -1) {
            return -m_thresholdValue;
        } else {
            return value;
        }
    }
    
    public void test(boolean forward, boolean backward){
        if(forward){
            m_motor1.set(1.0);
            m_motor2.set(1.0);
            m_motor3.set(1.0);
        } else if (backward){
            m_motor1.set(-1.0);
            m_motor2.set(-1.0);
            m_motor3.set(-1.0);
        } else {
            m_motor1.set(0);
            m_motor2.set(0);
            m_motor3.set(0);
        }
    }

    public void disable() {
        m_shooting = false;
        m_retracting = false;
//        m_holding = false;
    }
}