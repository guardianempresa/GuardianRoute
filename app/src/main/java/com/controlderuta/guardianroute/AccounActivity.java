package com.controlderuta.guardianroute;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;


public class AccounActivity extends AppCompatActivity {

    //Button logOutBtn;
    //private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;

    RadioButton rdiNotication, rdiToast;
    Button btnTime,btnRepeatLing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accoun);



     /*   logOutBtn=(Button)findViewById(R.id.logOutBtn); Esto es para ldesloguear

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(AccounActivity.this, LoginActivity.class);
                startActivity(intent);


            }
        });*/

        rdiNotication = (RadioButton) findViewById(R.id.rdiNotification);
        btnTime = (Button) findViewById(R.id.btnOneTime);
        btnRepeatLing = (Button) findViewById(R.id.btnRepeting);


        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    starAlarm();


            }
        });

        btnRepeatLing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    starAlarm();

            }
        });
    }

    private void starAlarm() {

    AlarmManager manager =(AlarmManager)getSystemService(Context.ALARM_SERVICE);
    Intent myIntent;
    PendingIntent pendingIntent;

    myIntent=new Intent(AccounActivity.this,AlarmNotificationReceiver.class);
    pendingIntent=PendingIntent.getBroadcast(this,0,myIntent,0);
        manager.set(AlarmManager.RTC_WAKEUP, 0,pendingIntent);

}
}




