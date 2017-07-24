package com.controlderuta.guardianroute;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by eduin on 6/07/2017.
 */

public class AlarmToasrReceiver extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"ESTA ES MI ALARMA",Toast.LENGTH_LONG).show();


    }
}