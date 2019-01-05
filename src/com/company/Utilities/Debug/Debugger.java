package com.company.Utilities.Debug;

import com.company.Settings;

public class Debugger {
    public static void out(String outText){
        if(Settings.IsDebugOn)
            System.out.println(outText);
    }
}
