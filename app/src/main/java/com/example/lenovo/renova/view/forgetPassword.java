package com.example.lenovo.renova.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.renova.R;
import com.example.lenovo.renova.library.progressdialog;

import org.json.JSONException;
import org.json.JSONObject;

public class forgetPassword extends AppCompatActivity {
 Button send;
 public static final String forgetPasswordUrl="http://coderg.org/renova_en/User/forget_pass/54732964/259743/";
 EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        send=(Button)findViewById(R.id.send);
        email=(EditText)findViewById(R.id.email);
        onClick();
    }

    public void onClick()
    {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPassword();
            }
        });
    }

    public void forgetPassword()
    {
        final progressdialog progressdialog=new progressdialog();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, forgetPasswordUrl + email.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("1"))
                    {
                        progressdialog.progressDialog(forgetPassword.this);
                        Toast.makeText(forgetPassword.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(forgetPassword.this,Login.class));

                    }
                    else if(jsonObject.getString("status").equals("2"))
                    {
                        Toast.makeText(forgetPassword.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                    else if(jsonObject.getString("status").equals("3"))
                    {
                        Toast.makeText(forgetPassword.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    Toast.makeText(forgetPassword.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();                }
                    }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(forgetPassword.this, "No Connection", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(forgetPassword.this);
        requestQueue.add(stringRequest);
    }
}
