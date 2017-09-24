package com.controlderuta.guardianroute;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;


public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i("tokenMain",""+token);
        RegisterToken(token);

    }

    private void RegisterToken(String refreshedToken) {
        Log.i("token",""+refreshedToken);
        String URL   = "http://192.168.0.34/mercandoya/php/registrarUsuario.php?nombreUsuario=Cesar%20Augusto%20Perez&correoUsuario=cesdddrrartt@mail.com&idPush="+refreshedToken;
        Log.i("url",URL);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest  = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("errorrespuesta",error.getMessage());

            }
        });

        queue.add(stringRequest);

    }
}
