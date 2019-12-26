package com.begroup.besoultion.drbooking.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.begroup.besoultion.drbooking.list.reserveList;
import com.begroup.besoultion.drbooking.view.confirmResercation;
import com.begroup.besoultion.renova.R;
import com.begroup.besoultion.drbooking.library.progressdialog;

import java.util.ArrayList;

public class reservationAdapter extends RecyclerView.Adapter<reservationAdapter.reservationHolder> {
    Context context;
    ArrayList<reserveList>mylist;

    public reservationAdapter(Context context, ArrayList<reserveList> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public reservationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.reservationitem,viewGroup,false);
        reservationHolder reservationHolder=new reservationHolder(view);
        return reservationHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull reservationHolder viewHolder, final int i) {
        viewHolder.date.setText(mylist.get(i).getDate().toString());
        viewHolder.from.setText(mylist.get(i).getFrom().toString());
        viewHolder.to.setText(mylist.get(i).getTo().toString());
        viewHolder.reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressdialog progressdialog=new progressdialog();
                progressdialog.dialogProgress(context,R.layout.progress_card,"sss");
                int id = mylist.get(i).getId();
                int userId=mylist.get(i).getUserId();
                Bundle b = new Bundle();
                Intent intent=new Intent(context,confirmResercation.class);
                b.putString("id", String.valueOf(id));
                b.putString("userId", String.valueOf(userId));
                intent.putExtras(b);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }
    public class reservationHolder extends RecyclerView.ViewHolder {
        TextView date,from,to,reservation;
        public reservationHolder(@NonNull View itemView) {
            super(itemView);
            date=(TextView)itemView.findViewById(R.id.date);
            from=(TextView)itemView.findViewById(R.id.from);
            to=(TextView)itemView.findViewById(R.id.to);
            reservation=(TextView)itemView.findViewById(R.id.reserve);
        }
    }
}
