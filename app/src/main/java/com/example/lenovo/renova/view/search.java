package com.example.lenovo.renova.view;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.renova.R;
import com.example.lenovo.renova.adapter.doctorAdapter;
import com.example.lenovo.renova.adapter.tasneefAdapter;
import com.example.lenovo.renova.interfacee.MVP;
import com.example.lenovo.renova.interfacee.searchInterface;
import com.example.lenovo.renova.library.NetworkChangeReceiver;
import com.example.lenovo.renova.library.checkConnection;
import com.example.lenovo.renova.library.progressdialog;
import com.example.lenovo.renova.library.savedId;
import com.example.lenovo.renova.list.doctorlist;
import com.example.lenovo.renova.list.tasneefList;
import com.example.lenovo.renova.presenter.doctorPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Handler;

import static android.provider.CalendarContract.CalendarCache.URI;

public class search extends AppCompatActivity implements searchInterface.interfaces.View {
RecyclerView doctorList;
RecyclerView.Adapter adapter;
tasneefAdapter tasneefAdapter;
RecyclerView.LayoutManager layoutManager;
    Button noConnection;
    doctorPresenter doctorPresenter;
    private BroadcastReceiver mNetworkReceiver;
    savedId savedId;
    progressdialog progressdialog;
    public static final String searchUrl="http://coderg.org/renova_en/Search/index/54732964/259743/";
    TextView tasneef;
    ViewSwitcher viewSwitcher;
    checkConnection  checkConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchresult);


        // doctorPresenter=new doctorPresenter(this);
        //doctorPresenter.getData();
        tasneef=(TextView)findViewById(R.id.sortlist);
        viewSwitcher=(ViewSwitcher)findViewById(R.id.viewswitch);
        checkConnection=new checkConnection();
        if(checkConnection.isOnline(this)==true)
        {
            getSpinner();
            bottomNav();
            getAllData("rate");

        }
         else {
            setContentView(R.layout.activity_no_connection);
            onclickonconnection();

        }


    }

    @Override
    public void element() {
        doctorList=(RecyclerView)findViewById(R.id.doctorlist);

    }

    @Override
    public void setviewdata(ArrayList data) {
        layoutManager=new LinearLayoutManager(search.this);
        doctorList.setLayoutManager(layoutManager);
        adapter=new doctorAdapter(search.this,data,R.layout.searchitem);
        doctorList.setAdapter(adapter);
    }

    @Override
    public void setViewtasneefData(ArrayList datas) {

    }

    public void getSpinner()
    {

        progressdialog=new progressdialog();
        tasneef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog(search.this,R.layout.filter);

            }
        });
      /*  tasneef.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1)
                {
                    getAllData("price_desc");
                }else if(position==2)
                {
                    getAllData("price_asc");

                }else if(position==3)
                {
                    getAllData("rate");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tasneef.setAdapter(tasneefAdapter);*/
    }
   public String searchText()
   {
       Intent intent=getIntent();
       String searchName= intent.getStringExtra("text");
       return searchName;
   }

   public void getAllData(String filter)
   {
       final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar);
       progressBar.setVisibility(View.VISIBLE);
       savedId=new savedId();
       doctorList=(RecyclerView)findViewById(R.id.doctorlist);
       String searchurl=null;
       Intent intent=getIntent();
       String get=intent.getStringExtra("text");
       if(get.equals("0"))
       {
           viewSwitcher.showNext();
           searchurl="http://coderg.org/renova_en/Doctor/getAll/54732964/259743/"+savedId.getId(search.this);

       }
      else {
           searchurl = searchUrl + searchText() + "/" + filter + "/" + savedId.getId(search.this);
       }
       searchurl = searchurl.replaceAll(" ", "%20");
       final ArrayList<doctorlist>arrayList=new ArrayList<doctorlist>();
       JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, searchurl, null, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {
               try {
                   if (response.getString("status").equals("1")) {

                       progressBar.setVisibility(View.GONE);
                       JSONArray jsonArray = response.getJSONArray("doctors");
                       for(int index=0;index<jsonArray.length();index++)
                       {
                           JSONObject jsonObject=jsonArray.getJSONObject(index);
                           arrayList.add(new doctorlist(jsonObject.getString("name"),jsonObject.getString("price"),jsonObject.getString("address"),jsonObject.getString("job_title"),jsonObject.getString("image"),jsonObject.getInt("id"),(float) jsonObject.getDouble("rating"),jsonObject.getInt("favorite"),jsonObject.getString("latitude"),jsonObject.getString("longitude")));
                           }
                       layoutManager=new LinearLayoutManager(search.this);
                       doctorList.setLayoutManager(layoutManager);
                       adapter=new doctorAdapter(search.this,arrayList,R.layout.searchitem);
                       doctorList.setAdapter(adapter);
                   }else if(response.getString("status").equals("2"))
                   {
                       Toast.makeText(search.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                   }else if(response.getString("status").equals("3"))
                   {
                       Toast.makeText(search.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                   }
               } catch (JSONException e) {
                   Toast.makeText(search.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();               }
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(search.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
           }
       });
       RequestQueue requestQueue = Volley.newRequestQueue(search.this);
       requestQueue.add(request);
   }
    public void onClick()
    {


   }
    public void dialog(Context context, int resource )
    {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(resource);
        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.90);
        int height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.show();
       RadioButton mostRated=(RadioButton)dialog.findViewById(R.id.mostrated);
        mostRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllData("rate");
                dialog.dismiss();
            }
        });
        RadioButton lowestprice=(RadioButton)dialog.findViewById(R.id.lowestprice);
         lowestprice.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 getAllData("price_asc");
                 dialog.dismiss();
             }
         });
         RadioButton highestprice=(RadioButton)dialog.findViewById(R.id.highestprice);
         highestprice.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 getAllData("price_desc");
                 dialog.dismiss();

             }
         });
        Button close=(Button)dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void bottomNav() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.products) {
                    startActivity(new Intent(search.this, products.class));
                } else if (item.getItemId() == R.id.profile) {
                    startActivity(new Intent(search.this, profileMem.class));

                }else if(item.getItemId()==R.id.searchnav)
                {
                    startActivity(new Intent(search.this, searchActivity.class));

                }
                return true;
            }
        });
    }




   public void onclickonconnection()
   {
       noConnection=(Button)findViewById(R.id.noconnection);
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
