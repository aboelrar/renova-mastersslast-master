package com.begroup.besoultion.drbooking.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.begroup.besoultion.drbooking.library.checkConnection;
import com.begroup.besoultion.renova.R;
import com.begroup.besoultion.drbooking.library.progressdialog;
import com.begroup.besoultion.drbooking.library.savedId;

import org.json.JSONException;
import org.json.JSONObject;

public class profileMem extends AppCompatActivity {
    LinearLayout myproducts,myappoienments,editData,mywishlist;
    Button logout;
    TextView title;
    ImageView back;
    progressdialog progressdialog;
    public static final String userProfile="http://drbookingsa.com/renova_en/User/view/54732964/259743/";
    ImageView userImage;
    TextView userName,userNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection checkConnection=new checkConnection();
        if(checkConnection.isOnline(this)==true) {
        setContentView(R.layout.activity_profile_mem);
        title=(TextView)findViewById(R.id.products);
        title.setText("الصفحه الشخصيه");
        back=(ImageView)findViewById(R.id.back);
        userNumber=(TextView)findViewById(R.id.usernumber);
        userName=(TextView)findViewById(R.id.username);
        userImage=(ImageView) findViewById(R.id.userimage);
        bottomNav();
        onclick();
        getData();

        }
    else {
            setContentView(R.layout.activity_no_connection);
            onclickonconnection();
        }
    }

    public void bottomNav() {
        final progressdialog progressdialog=new progressdialog();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.products) {
                    progressdialog.dialogProgress(profileMem.this,R.layout.progress_card,"sss");
                    startActivity(new Intent(profileMem.this, products.class));

                    }else if(item.getItemId()==R.id.searchnav)
                {
                    progressdialog.dialogProgress(profileMem.this,R.layout.progress_card,"sss");
                    startActivity(new Intent(profileMem.this, searchActivity.class));


                }
                return true;
            }
        });
    }
    public void onclick()
    {
        final progressdialog progressdialogs=new progressdialog();
        myproducts=(LinearLayout)findViewById(R.id.myproducts);
        myproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profileMem.this,myProducts.class));
            }
        });
        logout=(Button)findViewById(R.id.logout);
        progressdialog=new progressdialog();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBoolean();
                progressdialog.progressDialog(profileMem.this);
                startActivity(new Intent(profileMem.this,Login.class));

            }
        });
        myappoienments=(LinearLayout)findViewById(R.id.myappoienments);
        myappoienments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressdialogs.dialogProgress(profileMem.this,R.layout.progress_card,"sss");
                startActivity(new Intent(profileMem.this,appoienments.class));
            }
        });
        editData=(LinearLayout)findViewById(R.id.editdata);
        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressdialogs.dialogProgress(profileMem.this,R.layout.progress_card,"sss");
                startActivity(new Intent(profileMem.this,editData.class));
            }
        });
        mywishlist=(LinearLayout)findViewById(R.id.mywishlist);
        mywishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressdialogs.dialogProgress(profileMem.this,R.layout.progress_card,"sss");
                startActivity(new Intent(profileMem.this,myfavourites.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void setBoolean()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("id",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("islogin",false);
        editor.commit();
    }

    public void getData()
    {
        final ViewSwitcher viewSwitcher=(ViewSwitcher)findViewById(R.id.viewswitch);
        savedId savedId=new savedId();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, userProfile+savedId.getId(profileMem.this), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("1"))
                    {
                        viewSwitcher.showNext();
                        JSONObject jsonObject=response.getJSONObject("user");
                        userName.setText(jsonObject.getString("name"));
                        userNumber.setText(jsonObject.getString("phone"));
                    //    Picasso.with(profileMem.this).load(jsonObject.getString("image")).into(userImage);
                    }
                    else if(response.getString("status").equals("2"))
                    {
                        Toast.makeText(profileMem.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if(response.getString("status").equals("3"))
                    {
                        Toast.makeText(profileMem.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("profile",e.getLocalizedMessage());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(profileMem.this, "No Internet Access", Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(profileMem.this);
        requestQueue.add(jsonObjectRequest);
    }


    public void onclickonconnection()
    {
        Button noConnection=(Button)findViewById(R.id.noconnection);
        noConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=getIntent();
                finish();
                startActivity(intent);
            }
        });

    }


}