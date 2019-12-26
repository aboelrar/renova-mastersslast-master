package com.begroup.besoultion.drbooking.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.begroup.besoultion.drbooking.list.appoientmentList;
import com.begroup.besoultion.drbooking.view.doctorDetails;
import com.begroup.besoultion.drbooking.view.mapsforitem;
import com.begroup.besoultion.renova.R;
import com.begroup.besoultion.drbooking.library.progressdialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class appoienmentAdapter extends RecyclerView.Adapter<appoienmentAdapter.appoienmentHolder>{
    Context context;
    ArrayList<appoientmentList>mylist;

    public appoienmentAdapter(Context context, ArrayList<appoientmentList> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public appoienmentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.appoientmentitem,viewGroup,false);
        appoienmentHolder appoienmentHolder=new appoienmentHolder(view);
        return appoienmentHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final appoienmentHolder viewHolder, final int i) {
        viewHolder.title.setText(mylist.get(i).getDocName().toString());
        viewHolder.specialist.setText(mylist.get(i).getSpechlist().toString());
        viewHolder.from.setText(mylist.get(i).getFrom().toString());
        viewHolder.to.setText(mylist.get(i).getTo().toString());
        viewHolder.date.setText(mylist.get(i).getDate().toString());
        viewHolder.price.setText(mylist.get(i).getPrice().toString());
        viewHolder.Address.setText(mylist.get(i).getAddress().toString());
        Picasso.with(context).load(mylist.get(i).getImage()).into(viewHolder.circleImageView);
        final int id =mylist.get(i).getId();
        viewHolder.searchitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressdialog progressdialog=new progressdialog();
                progressdialog.dialogProgress(context,R.layout.progress_card,"sss");
                Bundle b = new Bundle();
                Intent intent=new Intent(context,doctorDetails.class);
                int id=mylist.get(i).getId();
                b.putString("id", String.valueOf(id));
                intent.putExtras(b);
                v.getContext().startActivity(intent);

            }
        });
        viewHolder.viewmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,mapsforitem.class);
                intent.putExtra("lat",mylist.get(i).getLat().toString());
                intent.putExtra("longt",mylist.get(i).getLng().toString());
                intent.putExtra("placeName",mylist.get(i).getDocName().toString());
                v.getContext().startActivity(intent);
            }
        });
        viewHolder.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressdialog progressdialog=new progressdialog();
                progressdialog.dialogProgress(context,R.layout.progress_card,"sss");
                Bundle b = new Bundle();
                Intent intent=new Intent(context,doctorDetails.class);
                int id=mylist.get(i).getId();
                b.putString("id", String.valueOf(id));
                intent.putExtras(b);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }
    public class appoienmentHolder extends RecyclerView.ViewHolder {
        TextView date,from,to,price,Address,title,specialist;
        LinearLayout searchitem,viewmap;
        CircleImageView  circleImageView;
        Button profile;
        public appoienmentHolder(@NonNull View itemView) {
            super(itemView);
            date=(TextView)itemView.findViewById(R.id.date);
            from=(TextView)itemView.findViewById(R.id.fromtime);
            specialist=(TextView)itemView.findViewById(R.id.speclist);
            to=(TextView)itemView.findViewById(R.id.totime);
            price=(TextView)itemView.findViewById(R.id.fees);
            Address=(TextView)itemView.findViewById(R.id.locaation);
            title=(TextView)itemView.findViewById(R.id.doctorName);
            searchitem=(LinearLayout)itemView.findViewById(R.id.searchitem);
            viewmap=(LinearLayout)itemView.findViewById(R.id.viewmap);
            circleImageView=(CircleImageView)itemView.findViewById(R.id.prfileImage);
            profile=(Button)itemView.findViewById(R.id.profile);
        }

    }
}
