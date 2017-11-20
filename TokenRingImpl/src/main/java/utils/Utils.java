package utils;

import conf.Settings;

public class Utils {

    public static synchronized void log(String s, boolean isSilent) {
        if (isSilent || s == null) {
            return;
        }
        System.out.println(s);
    }

    public static synchronized void log(String s) {
        log(s, Settings.SILENT_MODE);
    }

}
