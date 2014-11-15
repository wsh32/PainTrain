/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nerdHerd.util;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 * @author Klaus Wunderlich
 */
public class InputValues {
    
    public static double shotVal(){
        double in;
        in = (DriverStation.getInstance().getAnalogIn(1)) * 100;
        return in;
    }
    
    public static double holdVal(){
        double in;
        in = (DriverStation.getInstance().getAnalogIn(2)) * 100;
        return in;
    }
    
    public static double threshValue(){
        double in;
        in = (DriverStation.getInstance().getAnalogIn(3)) * 100;
        return in;
    }
    
}

//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package nerdHerd.util;
//
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.DriverStation;
//
///**
// *
// * @author M0nkey
// */
//public class InputValues {
//    
//    public InputValues(){ 
//    }
//    
//    public double shotVal(){
//        return (DriverStation.getInstance().getAnalogIn(1)) * 100;
//    }
//    
//    public double holdVal(){
//        return (DriverStation.getInstance().getAnalogIn(2)) * 100;
//    }
//    
//}
