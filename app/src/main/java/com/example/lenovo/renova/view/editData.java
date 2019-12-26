package com.example.lenovo.renova.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.lenovo.renova.library.progressdialog;
import com.example.lenovo.renova.library.savedId;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class editData extends AppCompatActivity {
    CircleImageView circleImageView;
    Bitmap SelectedPhoto;
    Button edit;
    EditText userName,phone,email;
    public static final String userProfile="http://coderg.org/renova_en/User/view/54732964/259743/";
    public static final String EditProfile="http://coderg.org/renova_en/user/editProfile/54732964/259743/";
    TextView changepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        userName=(EditText)findViewById(R.id.username);
        phone=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);
        editImage();
        onclick();
        getData();
    }
    public void editImage()
    {
        circleImageView=(CircleImageView)findViewById(R.id.editimg);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(i, "Select Your Photo"), 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
// if(resultCode == RESULT_OK) تعني ان كان قد تم الحصول على البيانات بدون مشاكل
        if (resultCode == RESULT_OK){
            if (requestCode == 1 ){
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                SelectedPhoto = BitmapFactory.decodeStream(imageStream );
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                SelectedPhoto.compress(Bitmap.CompressFormat.PNG,100,os);
                circleImageView.setImageBitmap(SelectedPhoto);
            }
}}
public void onclick()
{
    edit=(Button)findViewById(R.id.edit);
    edit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setData();
            startActivity(new Intent(editData.this,profileMem.class));
        }
    });
    changepassword=(TextView)findViewById(R.id.changepassword);
    changepassword.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(editData.this,changePassword.class));
        }
    });

}

    public void getData()
    {
        final ViewSwitcher viewSwitcher=(ViewSwitcher)findViewById(R.id.viewswitch);

        savedId savedId=new savedId();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, userProfile+savedId.getId(editData.this), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("1"))
                    {
                        viewSwitcher.showNext();
                        JSONObject jsonObject=response.getJSONObject("user");
                        userName.setText(jsonObject.getString("name"));
                        phone.setText(jsonObject.getString("phone"));
                        email.setText(jsonObject.getString("mail"));

                    }
                    else if(response.getString("status").equals("2"))
                    {
                        Toast.makeText(editData.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if(response.getString("status").equals("3"))
                    {
                        Toast.makeText(editData.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(editData.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(editData.this, "No Internet Access", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(editData.this);
        requestQueue.add(jsonObjectRequest);
    }

    public void setData()
    {
        final progressdialog progressdialog=new progressdialog();
        savedId savedId=new savedId();
        StringRequest jsonObjectRequest=new StringRequest(Request.Method.GET, EditProfile + savedId.getId(editData.this) + "/" + userName.getText().toString() + "/" + email.getText().toString() + "/" + phone.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("1"))
                    {
                        Toast.makeText(editData.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        progressdialog.progressDialog(editData.this);

                        }else if(jsonObject.getString("status").equals("2"))
                    {
                        Toast.makeText(editData.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if(jsonObject.getString("status").equals("3"))
                    {
                        Toast.makeText(editData.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(editData.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(editData.this, "No Connection", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(editData.this);
        requestQueue.add(jsonObjectRequest);
    }


}
