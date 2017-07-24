package com.controlderuta.guardianroute;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.RingtonePreference;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

/**
 * Created by eduin on 6/07/2017.
 */

public class AlarmNotificationReceiver extends BroadcastReceiver {



    public void onReceive(Context context, Intent intent) {//Constructor de la notificacion, Icono, tiempo, tituylo y Notificacion
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);



        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.marcadorpadre)
                .setContentTitle("Guadian")
                .setContentText("Tu ruta esta muy cerca, corre para que no te deje")
                .setDefaults(Notification.DEFAULT_LIGHTS| RingtonePreference.DEFAULT_ORDER)
                .setContentInfo("info");

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());
    }


}