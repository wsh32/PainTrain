/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nerdHerd.util;

/**
 *
 * @author student
 */
public class Thresholder {
    
    public static double threshold(double value){
          if (value > 1.0) {
            return 1.0;
        } else if (value < -1) {
            return -1.0;
        } else {
            return value;
        }
    }
}
