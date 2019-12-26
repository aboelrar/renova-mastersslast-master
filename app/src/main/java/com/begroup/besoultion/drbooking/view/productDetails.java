package com.begroup.besoultion.drbooking.view;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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
import com.begroup.besoultion.drbooking.library.checkConnection;
import com.begroup.besoultion.renova.R;
import com.begroup.besoultion.drbooking.library.progressdialog;
import com.begroup.besoultion.drbooking.library.savedId;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import me.relex.circleindicator.CircleIndicator;

public class productDetails extends AppCompatActivity {

    CircleIndicator circleIndicator;
    ImageView back,fav,imgBack;
     String id;
    TextView productDetails,name,price,used,description,titleName,usedName;
    public static final String productUrl="http://drbookingsa.com/renova_en/product/view/54732964/259743/";
    public static final String makeOrder="http://drbookingsa.com/renova_en/product/buyProduct/54732964/259743/";
Boolean IsFav=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection checkConnection=new checkConnection();
        if(checkConnection.isOnline(this)==true) {
            setContentView(R.layout.activity_product_details);
            Bundle b = getIntent().getExtras();
            id = b.getString("id");
            name = (TextView) findViewById(R.id.title);
            price = (TextView) findViewById(R.id.price);
            used = (TextView) findViewById(R.id.used);
            description = (TextView) findViewById(R.id.description);
            titleName = (TextView) findViewById(R.id.def);
            usedName = (TextView) findViewById(R.id.useful);
            imgBack = (ImageView) findViewById(R.id.viewpager);
            getProductDetails();
            buy();
            appBar();
        }
        else {
            setContentView(R.layout.activity_no_connection);
            onclickonconnection();
        }

    }


    public void buy()
    {
        Button buy=(Button)findViewById(R.id.buy);
        final savedId savedId=new savedId();

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(savedId.getUserBoolean(productDetails.this)==false)
                {
                    startActivity(new Intent(productDetails.this,Login.class));
                }
                else {
                    makeOrder();
                    startActivity(new Intent(productDetails.this,successfulpayment.class));

                                          }
            }
        });
    }
    public void appBar()
    {
        productDetails=(TextView)findViewById(R.id.products);
        productDetails.setText("تفاصيل المنتج");
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




    public void getProductDetails()
    {
        final ViewSwitcher viewSwitcher=(ViewSwitcher)findViewById(R.id.viewswitch);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, productUrl+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("1"))
                    {
                        viewSwitcher.showNext();
                        JSONObject jsonObject1=jsonObject.getJSONObject("product");
                        name.setText(jsonObject1.getString("name"));
                        price.setText(jsonObject1.getString("price"));
                        used.setText(jsonObject1.getString("description"));
                        description.setText(jsonObject1.getString("description"));
                        titleName.setText(jsonObject1.getString("name"));
                        usedName.setText(jsonObject1.getString("name"));
                        Picasso.with(productDetails.this).load(jsonObject1.getString("image")).into(imgBack);
                    }else if(jsonObject.getString("status").equals("2"))
                    {
                        Toast.makeText(productDetails.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if(jsonObject.getString("status").equals("3"))
                    {
                        Toast.makeText(productDetails.this, ""+response.equals("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("productdetails",e.getLocalizedMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(productDetails.this, "No Internet Connection", Toast.LENGTH_SHORT).show();                }

        });
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(productDetails.this);
        requestQueue.add(stringRequest);
    }

     public void progressDialog()
     {
         final progressdialog progressdialog=new progressdialog();
         final Dialog dialog = new Dialog(productDetails.this);
         dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
         dialog.setContentView(R.layout.payment);
         int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
         int height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
         dialog.getWindow().setLayout(width, height);
         dialog.show();
         Button close=(Button)dialog.findViewById(R.id.continueshopping);
         close.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 makeOrder();
                 progressdialog.progressDialog(com.begroup.besoultion.drbooking.view.productDetails.this);
             startActivity(new Intent(productDetails.this,successfulpayment.class));
             }
         });
     }

    public void makeOrder()
    {
        savedId savedId=new savedId();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, makeOrder + savedId.getId(this) + "/" + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("1")){
                    Toast.makeText(productDetails.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                        showNotification(""+response.getString("message"));
                }
                else if(response.getString("status").equals("2"))
                    {
                        Toast.makeText(productDetails.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if(response.getString("status").equals("3"))
                    {
                        Toast.makeText(productDetails.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("makeorder",e.getLocalizedMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(productDetails.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        request.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(productDetails.this);
        requestQueue.add(request);
    }

    private void showNotification(String message) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.antinal)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(this, successfulpayment.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(productDetails.this.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());


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
