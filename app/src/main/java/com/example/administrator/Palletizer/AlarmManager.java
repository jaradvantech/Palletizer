package com.example.administrator.Palletizer;


import android.content.Context;
import android.util.Log;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.administrator.Palletizer.Palletizer.EMERGENCY_ALARMS;
import static com.example.administrator.Palletizer.Palletizer.LIMIT_ALARMS;
import static com.example.administrator.Palletizer.Palletizer.OTHER_ALARMS;
import static com.example.administrator.Palletizer.Palletizer.TOTAL_ALARMS;

public class AlarmManager {

    private ArrayList<AlarmObject> newAlarms;
    private boolean[] lastCheckedAlarmBits;
    boolean[] newAlarmTypes;


    AlarmManager() {
        newAlarmTypes = new boolean[3]; //0=emergency, 1=limits, 2=misc
        lastCheckedAlarmBits = new boolean[TOTAL_ALARMS];
    }

    /* RBS
     *Check for new alarms
     * Will return an array of AlarmObject with the new alarms
     */
    public ArrayList<AlarmObject> detectNewAlarms() {
        newAlarms = new ArrayList<>();
        boolean[] currentAlarmBits = Palletizer.getAlarmBits();

        for(int i=0; i<3; i++)  {newAlarmTypes[i] = false; }

        //Get current time for AlarmObject timestamp
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss dd-MM-yy ");
        String strDate = mdformat.format(calendar.getTime());

        //Check (rising edge) every alarm bit, grouped by cathegory
        int i, limiter = EMERGENCY_ALARMS;
        for(i=0; i<limiter; i++)
            if((lastCheckedAlarmBits[i] == false) && (currentAlarmBits[i] == true)) {
                newAlarms.add(new AlarmObject("Emergency", strDate, Integer.toString(i)));
                newAlarmTypes[0] = true;
            }

        limiter += LIMIT_ALARMS;
        for(; i<limiter; i++)
            if((lastCheckedAlarmBits[i] == false) && (currentAlarmBits[i] == true)) {
                newAlarms.add(new AlarmObject("Mechanical limit", strDate, Integer.toString(i)));
                newAlarmTypes[1] = true;
            }

        limiter = TOTAL_ALARMS;
        for(; i<limiter; i++)
            if((lastCheckedAlarmBits[i] == false) && (currentAlarmBits[i] == true)) {
                newAlarms.add(new AlarmObject("Misc.", strDate, Integer.toString(i)));
                newAlarmTypes[2] = true;
            }

        //Update values.
        lastCheckedAlarmBits = currentAlarmBits;

        return newAlarms;
    }

    /* RBS
     * This is used to update the icons on the menu bar.
     */
    public boolean[] getNewAlarmTypes() {
        return newAlarmTypes;
    }
}
