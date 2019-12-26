package com.begroup.besoultion.drbooking.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.begroup.besoultion.renova.R;
import com.begroup.besoultion.drbooking.library.progressdialog;
import com.begroup.besoultion.drbooking.library.savedId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
TextView goToSignUp;
String id;
EditText Email,Password;
Button signIn;
    savedId savedId;
    TextView forgetPassword;
    progressdialog progressdialog;
    public static final String LoginUrl="http://drbookingsa.com/renova_en/User/login/54732964/259743/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signIn=(Button)findViewById(R.id.signin);
        forgetPassword=(TextView)findViewById(R.id.forgetpass);
        progressdialog=new progressdialog();
        goToProducts();
        goToSignUp();
        CheckLogin();
        doToForgetPassword();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void goToSignUp()
    {
        goToSignUp=(TextView)findViewById(R.id.gotosignup);
        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,signup.class));

            }
        });
    }

    public void EditText()
    {
        Email=(EditText)findViewById(R.id.username);
        Password=(EditText)findViewById(R.id.password);

    }


    public void CheckLogin()
    {

        EditText();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest Login=new StringRequest(Request.Method.GET, LoginUrl+Email.getText().toString()+"/"+Password.getText().toString(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.getString("status").equals("1"))
                            {
                                Toast.makeText(Login.this,"successful login", Toast.LENGTH_SHORT).show();
                                JSONObject jsonObject1=jsonObject.getJSONObject("user_data");
                                id = jsonObject1.getString("id");
                                progressdialog.progressDialog(Login.this);
                                goToMain(id);
                                id();
                                finish();
                            }else if(jsonObject.getString("status").equals("2"))
                            {
                                Toast.makeText(Login.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                Email.setError("Error");
                            }
                            else if (jsonObject.getString("status").equals("3"))
                            {
                                Toast.makeText(Login.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e("login",e.getLocalizedMessage());

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this,"No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
                )
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("mail", Email.getText().toString());
                        params.put("password", Password.getText().toString());

                        return params;
                    }};
                Login.setShouldCache(false);
                RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
                requestQueue.add(Login);

            }

        });

    }


    public void id()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("id",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("islogin",true);
        editor.putString("id", id);
        editor.commit();
    }
    public void goToMain(String id)
    {
        Intent intent=new Intent(Login.this,searchActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void goToProducts()
    {
        savedId=new savedId();
        if(savedId.getUserBoolean(this)==true)
        {
            startActivity(new Intent(Login.this,searchActivity.class));
            finish();
        }
    }

    public void doToForgetPassword(){
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,forgetPassword.class));
                }
        });
    }

}
