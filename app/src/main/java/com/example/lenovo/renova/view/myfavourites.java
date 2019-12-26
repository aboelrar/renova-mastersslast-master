package com.example.lenovo.renova.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.renova.R;
import com.example.lenovo.renova.adapter.doctorAdapter;
import com.example.lenovo.renova.library.checkConnection;
import com.example.lenovo.renova.library.savedId;
import com.example.lenovo.renova.list.doctorlist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class myfavourites extends AppCompatActivity {
    RecyclerView favouriteList;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    savedId savedId;
    TextView title;
    ImageView back;
    public static final String favUrl="http://coderg.org/renova_en/User/getFavoriteDoctors/54732964/259743/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection checkConnection=new checkConnection();
        if(checkConnection.isOnline(this)==true) {
            setContentView(R.layout.activity_myfavourites);
            back = (ImageView) findViewById(R.id.back);
            title = (TextView) findViewById(R.id.products);
            savedId = new savedId();
            getAllData();
            onClick();
        }
        else{
            setContentView(R.layout.activity_no_connection);
            onclickonconnection();
        }
    }


    public void getAllData()
    {


        favouriteList=(RecyclerView)findViewById(R.id.favList);
        final ArrayList<doctorlist> arrayList=new ArrayList<doctorlist>();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, favUrl+savedId.getId(myfavourites.this), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals("1")) {
                        JSONArray jsonArray = response.getJSONArray("doctors");
                        for(int index=0;index<jsonArray.length();index++)
                        {
                            JSONObject jsonObject=jsonArray.getJSONObject(index);
                            arrayList.add(new doctorlist(jsonObject.getString("name"),jsonObject.getString("price"),jsonObject.getString("address"),jsonObject.getString("job_title"),jsonObject.getString("image"),jsonObject.getInt("id"), (float) jsonObject.getDouble("rating"),jsonObject.getInt("favorite"),jsonObject.getString("latitude"),jsonObject.getString("longitude")));
                        }
                        layoutManager=new LinearLayoutManager(myfavourites.this);
                        favouriteList.setLayoutManager(layoutManager);
                        adapter=new doctorAdapter(myfavourites.this,arrayList,R.layout.favouriteitem);
                        favouriteList.setAdapter(adapter);
                    }else if(response.getString("status").equals("2"))
                    {
                        Toast.makeText(myfavourites.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }else if(response.getString("status").equals("3"))
                    {
                        Toast.makeText(myfavourites.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(myfavourites.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(myfavourites.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(myfavourites.this);
        requestQueue.add(request);
    }

    public void onClick()
    {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("مفضلتى");
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
