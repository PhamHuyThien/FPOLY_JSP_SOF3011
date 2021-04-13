/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author MSII
 */
public class Utils {

    public static String toVnTime(int time) {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(Long.parseLong(time + "000")));
    }

    public static int getTime() {
        return (int) (new Date().getTime() / 1000);
    }
}
