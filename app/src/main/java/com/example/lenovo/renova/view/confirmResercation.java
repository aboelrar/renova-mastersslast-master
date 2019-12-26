package com.example.lenovo.renova.view;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.renova.R;
import com.example.lenovo.renova.library.savedId;

import org.json.JSONException;
import org.json.JSONObject;

public class confirmResercation extends AppCompatActivity {
    public static final String confirmUrl="http://coderg.org/renova_en/User/make_reservation/54732964/259743/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_resercation);
        confirmResevation();
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(confirmResercation.this, searchActivity.class));
                finish();
            }
        }.start();
    }

    public void confirmResevation()
    {
        Bundle b = getIntent().getExtras();
        String  reservationid = b.getString("id");
        String  userId = b.getString("userId");
        savedId savedId=new savedId();

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, confirmUrl+savedId.getId(confirmResercation.this)+"/"+savedId.getDoctorId(confirmResercation.this)+"/"+reservationid, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("1"))
                    {
                        Intent intent = new Intent(confirmResercation.this, search.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(confirmResercation.this,0,intent,0);
                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(confirmResercation.this)
                                        .setSmallIcon(R.drawable.antinal)
                                        .setContentTitle("تم بنجاح")
                                        .setContentText(response.getString("message"))
                                        .setContentIntent(pendingIntent).setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(0, mBuilder.build());
                        Toast.makeText(confirmResercation.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if(response.getString("status").equals("2"))
                    {
                        Toast.makeText(confirmResercation.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if (response.getString("status").equals("3"))
                    {
                        Toast.makeText(confirmResercation.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(confirmResercation.this, "Something went Error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(confirmResercation.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(confirmResercation.this);
        requestQueue.add(request);
    }
}
