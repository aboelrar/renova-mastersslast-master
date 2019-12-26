package com.begroup.besoultion.drbooking.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.begroup.besoultion.drbooking.list.doctorlist;
import com.begroup.besoultion.drbooking.view.doctorDetails;
import com.begroup.besoultion.drbooking.view.mapsforitem;
import com.begroup.besoultion.renova.R;
import com.begroup.besoultion.drbooking.library.progressdialog;
import com.begroup.besoultion.drbooking.library.savedId;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class doctorAdapter extends RecyclerView.Adapter<doctorAdapter.doctorHolder> {
    Context context;
    ArrayList<doctorlist>mylist;
    int resource;
    public static final String deleteFav="http://drbookingsa.com/renova_en/User/deleteFavoriteDoctor/54732964/259743/";
    public static final String addFav="http://drbookingsa.com/renova_en/User/addFavoriteDoctor/54732964/259743/";
    savedId savedId;
    String id;

    public doctorAdapter(Context context, ArrayList<doctorlist> mylist,int resource) {
        this.context = context;
        this.mylist = mylist;
        this.resource=resource;
    }

    @NonNull
    @Override
    public doctorHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(resource,viewGroup,false);
        doctorHolder doctorHolder=new doctorHolder(view);
        return doctorHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final doctorHolder viewHolder, final int i) {

     viewHolder.name.setText(mylist.get(i).getName().toString());
     viewHolder.spechlist.setText(mylist.get(i).getSpeclist().toString());
     viewHolder.fees.setText(mylist.get(i).getFees().toString());
     viewHolder.address.setText(mylist.get(i).getLocation().toString());
      viewHolder.ratings.setRating(mylist.get(i).getNumStars());
      Picasso.with(context).load(mylist.get(i).getProfileImage()).into(viewHolder.profileImg);

      viewHolder.viewmap.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(context,mapsforitem.class);
              intent.putExtra("lat",mylist.get(i).getLat().toString());
              intent.putExtra("longt",mylist.get(i).getLng().toString());
              intent.putExtra("placeName",mylist.get(i).getName().toString());
              v.getContext().startActivity(intent);
          }
      });

             viewHolder.profile.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
             Bundle b = new Bundle();
             Intent intent=new Intent(context,doctorDetails.class);
             String name = viewHolder.name.getText().toString();
             String fees = viewHolder.fees.getText().toString();
             String address = viewHolder.address.getText().toString();
             int id=mylist.get(i).getId();

             b.putString("fees", fees);
             b.putString("name", name);
             b.putString("address", address);
             b.putString("id", String.valueOf(id));
             doctorId(String.valueOf(id));

             intent.putExtras(b);
             v.getContext().startActivity(intent);
         }
     });
     viewHolder.searchitem.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             progressdialog progressdialog=new progressdialog();
             progressdialog.dialogProgress(context,R.layout.progress_card,"sss");
             Bundle b = new Bundle();
             Intent intent=new Intent(context,doctorDetails.class);
             String name = viewHolder.name.getText().toString();
             String fees = viewHolder.fees.getText().toString();
             String address = viewHolder.address.getText().toString();
             int id=mylist.get(i).getId();

             b.putString("fees", fees);
             b.putString("name", name);
             b.putString("address", address);
             b.putString("id", String.valueOf(id));
             doctorId(String.valueOf(id));

             intent.putExtras(b);
             v.getContext().startActivity(intent);

         }
     });
            savedId=new savedId();
        if(mylist.get(i).getFavnum()==1) {
            viewHolder.fav.setImageResource(R.drawable.redfavourite);
            mylist.get(i).setFav(true);
        }
            viewHolder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mylist.get(i).getFav()==false){
                    final   int id =mylist.get(i).getId();
                    viewHolder.fav.setImageResource(R.drawable.redfavourite);
                    addDeleteFav(String.valueOf(id),addFav);
                    mylist.get(i).setFav(true);
                }else{
                    final   int id =mylist.get(i).getId();
                    viewHolder.fav.setImageResource(R.drawable.fav);
                    addDeleteFav(String.valueOf(id),deleteFav);
                    mylist.get(i).setFav(false);
                }
            }
        });}

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class doctorHolder extends RecyclerView.ViewHolder {
        TextView name,address,fees,spechlist;
        ImageView profileImg,fav;
        LinearLayout searchitem,viewmap;
        RatingBar ratings;
        Button profile;
        public doctorHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.doctorName);
            address=(TextView)itemView.findViewById(R.id.locaation);
            fees=(TextView)itemView.findViewById(R.id.fees);
            spechlist=(TextView)itemView.findViewById(R.id.speclist);
            profileImg=(ImageView)itemView.findViewById(R.id.prfileImage);
            searchitem=(LinearLayout)itemView.findViewById(R.id.searchitem);
            fav=(ImageView)itemView.findViewById(R.id.fav);
            profile=(Button) itemView.findViewById(R.id.profile);
            ratings=(RatingBar)itemView.findViewById(R.id.ratings);
            viewmap=(LinearLayout)itemView.findViewById(R.id.viewmap);
        }
    }

    public void addDeleteFav(final String id,final String Url)
    {
        Log.e("addfav",""+Url+savedId.getId(context)+"/"+id);
        StringRequest addFav=new StringRequest(Request.Method.GET, Url+savedId.getId(context)+"/"+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("1")) {
                        Toast.makeText(context, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }else if(jsonObject.getString("status").equals("2"))
                    {
                        Toast.makeText(context,""+jsonObject.getString("message") , Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(context, "catch", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            }
        });
        addFav.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(addFav);
    }
    public void doctorId(String id)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("doctorid",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("doctorid", id);
        editor.commit();
    }
}
