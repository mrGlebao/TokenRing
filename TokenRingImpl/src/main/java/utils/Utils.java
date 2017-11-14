package utils;

import conf.Settings;

public class Utils {

    public static synchronized void log(String s) {
        if(Settings.SILENT_MODE) {
            return;
        }
        System.out.println(s);
    }

}
