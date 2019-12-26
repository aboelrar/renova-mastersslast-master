package com.example.lenovo.renova.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.renova.R;
import com.example.lenovo.renova.adapter.doctorAdapter;
import com.example.lenovo.renova.adapter.reservationAdapter;
import com.example.lenovo.renova.interfacee.MVP;
import com.example.lenovo.renova.library.checkConnection;
import com.example.lenovo.renova.library.savedId;
import com.example.lenovo.renova.list.reserveList;
import com.example.lenovo.renova.presenter.reservationPresenter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class doctorDetails extends AppCompatActivity implements MVP.interfaces.View{
RecyclerView reservationList;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    reservationPresenter reservationPresenter;
    TextView title,descripition;
    ImageView fav,doctorDetailsImg,back,map;
    LinearLayout viewMap;
    String id;
    savedId savedId;
    RatingBar ratingBar;
    Button ratingbutton;
    Boolean isfav=false;
    public static final String DoctorDetails="http://coderg.org/renova_en/Doctor/view/54732964/259743/";
    public static final String deleteFav="http://coderg.org/renova_en/User/deleteFavoriteDoctor/54732964/259743/";
    public static final String addFav="http://coderg.org/renova_en/User/addFavoriteDoctor/54732964/259743/";
    public static final String setRate="http://coderg.org/renova_en/User/addRate/54732964/259743/";
    TextView name,fees,address,speclist,jobtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection checkConnection=new checkConnection();
        if(checkConnection.isOnline(this)==true) {

        setContentView(R.layout.activity_doctor_details);
            map=(ImageView)findViewById(R.id.map);
            map.setImageResource(R.drawable.seemap);
        Bundle b = getIntent().getExtras();
        id = b.getString("id");
         name = (TextView) findViewById(R.id.name);
        descripition=(TextView)findViewById(R.id.descripition);
         fees = (TextView) findViewById(R.id.fees);
         address = (TextView) findViewById(R.id.address);
         speclist=(TextView)findViewById(R.id.speclist);
         jobtitle=(TextView)findViewById(R.id.jobtitle);
         back=(ImageView)findViewById(R.id.back);
         doctorDetailsImg=(ImageView) findViewById(R.id.doctorDetailsImg);
         title=(TextView)findViewById(R.id.products);
         ratingBar=(RatingBar)findViewById(R.id.ratings);
        ratingbutton=(Button)findViewById(R.id.ratingbutton);
         onClick();
         getDoctorDetails();
         bottomNav();
         isFav();}
         else {
            setContentView(R.layout.activity_no_connection);
            onclickonconnection();
        }
         }

    @Override
    public void element() {
        reservationList=(RecyclerView)findViewById(R.id.reservationList);
    }

    @Override
    public void setviewdata(ArrayList data) {
        layoutManager=new LinearLayoutManager(doctorDetails.this,LinearLayoutManager.HORIZONTAL,false);
        reservationList.setLayoutManager(layoutManager);
        adapter=new reservationAdapter(doctorDetails.this,data);

        reservationList.setAdapter(adapter);
    }

    public void bottomNav()
        {
            BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.nav);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(item.getItemId()==R.id.products)
                    {
                        startActivity(new Intent(doctorDetails.this,products.class));
                    }else if(item.getItemId()==R.id.profile)
                    {
                        startActivity(new Intent(doctorDetails.this,profileMem.class));

                    }
                    else if(item.getItemId()==R.id.searchnav)
                    {
                        startActivity(new Intent(doctorDetails.this, searchActivity.class));

                    }
                    return true;
                }
            });

    }

    public void isFav() {
        fav = (ImageView) findViewById(R.id.fav);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isfav == false) {
                    fav.setImageResource(R.drawable.redfavourite);
                    addDeleteFav(id,addFav);
                    isfav = true;
                } else {
                    fav.setImageResource(R.drawable.fav);
                    addDeleteFav(id,deleteFav);
                    isfav = false;
                }
            }
        });

    }
    public void getDoctorDetails()
    {
        final ViewSwitcher viewSwitcher=(ViewSwitcher)findViewById(R.id.viewswitch);
        reservationList=(RecyclerView)findViewById(R.id.reservationList);
        savedId=new savedId();
        final ArrayList<reserveList>arrayList=new ArrayList<reserveList>();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, DoctorDetails+id+"/"+savedId.getId(doctorDetails.this), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals("1"))
                    {
                        viewSwitcher.showNext();
                        JSONObject doctorObject=response.getJSONObject("doctor");
                        name.setText(doctorObject.getString("name"));
                        speclist.setText(doctorObject.getString("job_title"));
                        fees.setText(doctorObject.getString("price"));
                        address.setText(doctorObject.getString("address"));
                        jobtitle.setText(doctorObject.getString("job_title"));
                        ratingBar.setRating((float) doctorObject.getDouble("rating"));
                        descripition.setText(doctorObject.getString("description"));
                        Picasso.with(doctorDetails.this).load(doctorObject.getString("image")).into(doctorDetailsImg);
                        goToMap(doctorObject.getString("latitude"),doctorObject.getString("longitude"),doctorObject.getString("name"));

                         if(doctorObject.getInt("favorite")==1)
                         {
                             fav.setImageResource(R.drawable.redfavourite);
                             isfav = true;
                         }
                         JSONArray jsonArray = response.getJSONArray("dates");
                        for(int index=0;index<jsonArray.length();index++)
                        {
                            JSONObject jsonObject=jsonArray.getJSONObject(index);
                            arrayList.add(new reserveList(jsonObject.getString("reservation_date"),jsonObject.getString("from_time"),jsonObject.getString("to_time"),jsonObject.getInt("id"),jsonObject.getInt("id_user")));
                        }
                        layoutManager=new LinearLayoutManager(doctorDetails.this,LinearLayoutManager.HORIZONTAL,false);
                        reservationList.setLayoutManager(layoutManager);
                        adapter=new reservationAdapter(doctorDetails.this,arrayList);
                        reservationList.setAdapter(adapter);


                    }
                else if(response.getString("status").equals("2"))
                    {
                        Toast.makeText(doctorDetails.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if(response.getString("status").equals("3"))
                    {
                        Toast.makeText(doctorDetails.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(doctorDetails.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(doctorDetails.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(doctorDetails.this);
        requestQueue.add(request);
    }

    public void onClick()
    {
         title.setText("بيانات الدكتور");
         back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
        ratingbutton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dialog(doctorDetails.this,R.layout.ratings);
             }
         });

    }

    public void addDeleteFav(final String id,final String Url)
    {
        savedId savedId=new savedId();
        StringRequest addFav=new StringRequest(Request.Method.GET, Url+savedId.getId(doctorDetails.this)+"/"+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("1")) {
                        Toast.makeText(doctorDetails.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }else if(jsonObject.getString("status").equals("2"))
                    {
                        Toast.makeText(doctorDetails.this,""+jsonObject.getString("message") , Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(doctorDetails.this, "catch", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(doctorDetails.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(doctorDetails.this);
        requestQueue.add(addFav);
    }

    public void dialog(Context context, int resource ) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(resource);
        int width = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        int height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        RatingBar ratingBar=(RatingBar)dialog.findViewById(R.id.rating);
        final Button rate=(Button)dialog.findViewById(R.id.rate);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, final float rating, boolean fromUser) {
                TextView value=(TextView)dialog.findViewById(R.id.value);
                value.setText(""+rating);
                rate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRate(rating);
                        dialog.dismiss();

                    }
                });
            }
        });
        dialog.show();
    }
    public void setRate(float rate)
    {
        savedId savedId=new savedId();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, setRate + savedId.getId(doctorDetails.this) + "/" + id + "/" + rate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("1"))
                    {
                        Toast.makeText(doctorDetails.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if(jsonObject.getString("status").equals("2"))
                    {
                        Toast.makeText(doctorDetails.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                    else if(jsonObject.getString("status").equals("3"))
                    {
                        Toast.makeText(doctorDetails.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    Toast.makeText(doctorDetails.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(doctorDetails.this, "No Connection", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(doctorDetails.this);
        requestQueue.add(stringRequest);
    }

    public void goToMap(final String lat, final String longt, final String name)
    {
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(doctorDetails.this,mapsforitem.class);
                intent.putExtra("lat",lat);
                intent.putExtra("longt",longt);
                intent.putExtra("placeName",name);
                startActivity(intent);
            }
        });

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
