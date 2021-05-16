    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.DecimalFormat;

/**
 *
 * @author Anthony G.S
 */
public class Utility {
    
    public static int random(){
        return 1+(int)Math.floor(Math.random()*99);
    }

    public static String format(double n) {
        return new DecimalFormat("###,###.##").format(n);
    }
}
