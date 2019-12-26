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
import com.example.lenovo.renova.library.savedId;

import org.json.JSONException;
import org.json.JSONObject;

public class changePassword extends AppCompatActivity {
 public static final String changePass="http://coderg.org/renova_en/User/update_password/54732964/259743/";
 EditText password,newPassword,reTypePassword;
    savedId savedId;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        password=(EditText)findViewById(R.id.password);
        newPassword=(EditText)findViewById(R.id.newpassword);
        reTypePassword=(EditText)findViewById(R.id.retypepassword);
        edit=(Button)findViewById(R.id.edit);
        onClick();

    }
    public void setChangePass ()
    {
       final progressdialog progressdialog=new progressdialog();
        savedId=new savedId();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, changePass + "/" + savedId.getId(changePassword.this) + "/" + password.getText().toString() + "/" + newPassword.getText().toString() + "/" + reTypePassword.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("1"))
                    {
                        Toast.makeText(changePassword.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        progressdialog.progressDialog(changePassword.this);
                        startActivity(new Intent(changePassword.this,profileMem.class));
                    }
                    else if(jsonObject.getString("status").equals("2"))
                    {
                        Toast.makeText(changePassword.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                        else if(jsonObject.getString("status").equals("3"))
                    {
                        Toast.makeText(changePassword.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    Toast.makeText(changePassword.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(changePassword.this, "No Connection", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(changePassword.this);
        requestQueue.add(stringRequest);
    }

    public void onClick()
    {
         edit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 setChangePass();
                 }
         });
    }
}
