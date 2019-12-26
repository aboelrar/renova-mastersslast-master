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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.renova.R;
import com.example.lenovo.renova.adapter.productAdapter;
import com.example.lenovo.renova.adapter.reservationAdapter;
import com.example.lenovo.renova.interfacee.MVP;
import com.example.lenovo.renova.library.checkConnection;
import com.example.lenovo.renova.list.productList;
import com.example.lenovo.renova.presenter.productPresrenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class products extends AppCompatActivity implements MVP.interfaces.View {
    RecyclerView productLists,productList;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    productPresrenter productPresrenter;
    public static final String productUrl="http://coderg.org/renova_en/product/getAll/54732964/259743";
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection checkConnection=new checkConnection();
        if(checkConnection.isOnline(this)==true) {
            setContentView(R.layout.activity_products);
            // productPresrenter=new productPresrenter(this);
            // productPresrenter.getData();
            getProductData();
            appBar();
        }
        else {
            setContentView(R.layout.activity_no_connection);
            onclickonconnection();
        }
    }

    @Override
    public void element() {
        productList=(RecyclerView)findViewById(R.id.productList);
    }

    @Override
    public void setviewdata(ArrayList data) {
        layoutManager=new LinearLayoutManager(products.this);
        productList.setLayoutManager(layoutManager);
        adapter=new productAdapter(products.this,data);
        productList.setAdapter(adapter);
    }
public void appBar()
{
  back=(ImageView)findViewById(R.id.back);
  back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          finish();
      }
  });
}

    public void getProductData()
    {
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        productLists=(RecyclerView)findViewById(R.id.productList);
       final ArrayList<com.example.lenovo.renova.list.productList>productList=new ArrayList<productList>();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, productUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("1"))
                    {
                        progressBar.setVisibility(View.GONE);
                        JSONArray jsonArray=response.getJSONArray("products");
                    for(int index=0;index<jsonArray.length();index++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(index);
                        String img=jsonObject.getString("image");
                        productList.add(new productList(jsonObject.getString("name"),jsonObject.getString("price"),img,jsonObject.getInt("id")));
                        }
                    layoutManager=new LinearLayoutManager(products.this);
                    productLists.setLayoutManager(layoutManager);
                    adapter=new productAdapter(products.this,productList);
                    productLists.setAdapter(adapter);}
                    else if(response.getString("status").equals("2"))
                    {
                        Toast.makeText(products.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if (response.getString("status").equals("3"))
                    {
                        Toast.makeText(products.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(products.this, "someThing Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(products.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(products.this);
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
}
