    package com.begroup.besoultion.drbooking.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.begroup.besoultion.drbooking.library.checkConnection;
import com.begroup.besoultion.drbooking.list.appoientmentList;
import com.begroup.besoultion.renova.R;
import com.begroup.besoultion.drbooking.adapter.appoienmentAdapter;
import com.begroup.besoultion.drbooking.adapter.doctorAdapter;
import com.begroup.besoultion.drbooking.interfacee.MVP;
import com.begroup.besoultion.drbooking.library.savedId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class appoienments extends AppCompatActivity implements MVP.interfaces.View{
   RecyclerView appoientmentLists;
    RecyclerView.Adapter adapter;
    ImageView back;
    TextView title;
    RecyclerView.LayoutManager layoutManager;

    com.begroup.besoultion.drbooking.presenter.appoienmentsPresenter appoienmentsPresenter;
    public static final String appoienmentsList="http://drbookingsa.com/renova_en/User/view_reservations/54732964/259743/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection checkConnection=new checkConnection();
        if(checkConnection.isOnline(this)==true) {
            setContentView(R.layout.activity_appoienments);
            title = (TextView) findViewById(R.id.products);
            getAllData();
            onClick();
        }
        else {
                setContentView(R.layout.activity_no_connection);
                onclickonconnection();
        }

    }

    @Override
    public void element() {
        appoientmentLists=(RecyclerView)findViewById(R.id.appoienmentslist);
    }

    @Override
    public void setviewdata(ArrayList data) {
        layoutManager=new LinearLayoutManager(appoienments.this);
        appoientmentLists.setLayoutManager(layoutManager);
        adapter=new doctorAdapter(appoienments.this,data,R.layout.appoientmentitem);
        appoientmentLists.setAdapter(adapter);
    }

    public void getAllData()
    {
    final    ArrayList<appoientmentList>arrayList=new ArrayList<appoientmentList>();
        appoientmentLists=(RecyclerView)findViewById(R.id.appoienmentslist);
        savedId savedId=new savedId();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, appoienmentsList + savedId.getId(appoienments.this), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("1"))
                    {
                        JSONArray jsonArray=response.getJSONArray("reservations");
                        for (int index=0;index<jsonArray.length();index++)
                        {
                            JSONObject jsonObject=jsonArray.getJSONObject(index);
                            arrayList.add(new appoientmentList(jsonObject.getString("doctor"),jsonObject.getString("job_title"),jsonObject.getString("price"),jsonObject.getString("place"),jsonObject.getString("timeFrom"),jsonObject.getString("timeTo"),jsonObject.getString("date"),jsonObject.getInt("doctor_id"), (float) jsonObject.getDouble("rate"),jsonObject.getString("latitude"),jsonObject.getString("longitude"),jsonObject.getString("image")));
                        }
                        layoutManager=new LinearLayoutManager(appoienments.this);
                        appoientmentLists.setLayoutManager(layoutManager);
                        adapter=new appoienmentAdapter(appoienments.this,arrayList);
                        appoientmentLists.setAdapter(adapter);
                    }else if(response.getString("status").equals("2"))
                    {
                        Toast.makeText(appoienments.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }else if(response.getString("status").equals("3"))
                    {
                        Toast.makeText(appoienments.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    Log.e("appoienments",e.getLocalizedMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(appoienments.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        request.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(appoienments.this);
        requestQueue.add(request);
    }

    public void onClick()
    {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("حجوزاتى");
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
