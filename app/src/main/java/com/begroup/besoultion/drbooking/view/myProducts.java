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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.begroup.besoultion.drbooking.library.checkConnection;
import com.begroup.besoultion.drbooking.list.productList;
import com.begroup.besoultion.renova.R;
import com.begroup.besoultion.drbooking.adapter.myproductsAdapter;
import com.begroup.besoultion.drbooking.library.savedId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class myProducts extends AppCompatActivity {
RecyclerView myProductList;
RecyclerView.Adapter adapter;
RecyclerView.LayoutManager layoutManager;
public static final String productsURL = "http://drbookingsa.com/renova_en/user/getUserProducts/54732964/259743/";
ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection checkConnection=new checkConnection();
        if(checkConnection.isOnline(this)==true) {
            setContentView(R.layout.activity_my_products);
            myProducts();
            onBack();
        }
        else {
            setContentView(R.layout.activity_no_connection);
            onclickonconnection();
        }
    }
    public void myProducts()
    {
        myProductList=(RecyclerView)findViewById(R.id.myProductList);
        final ArrayList<productList>productList=new ArrayList<productList>();
        savedId savedId=new savedId();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, productsURL+savedId.getId(myProducts.this), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("1")) {
                         JSONArray jsonArray=response.getJSONArray("products");
                         for(int index=0;index<jsonArray.length();index++)
                         {
                             JSONObject jsonObject=jsonArray.getJSONObject(index);
                             String img=jsonObject.getString("image");
                             productList.add(new productList(jsonObject.getString("name"),jsonObject.getString("price"),img,jsonObject.getInt("id")));
                         }
                         layoutManager=new LinearLayoutManager(myProducts.this);
                         myProductList.setLayoutManager(layoutManager);
                         adapter=new myproductsAdapter(myProducts.this,productList);
                         myProductList.setAdapter(adapter);
                     }
                    else if(response.get("status").equals("2"))
                    {
                        Toast.makeText(myProducts.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if(response.getString("status").equals("3"))
                    {
                        Toast.makeText(myProducts.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("myproducts",e.getLocalizedMessage());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(myProducts.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        request.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(myProducts.this);
        requestQueue.add(request);
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

    //on Click to back
    private void  onBack()
    {
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
