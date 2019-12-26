package com.begroup.besoultion.drbooking.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.begroup.besoultion.drbooking.list.productList;
import com.begroup.besoultion.drbooking.view.productDetails;
import com.begroup.besoultion.renova.R;
import com.begroup.besoultion.drbooking.library.progressdialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class myproductsAdapter extends RecyclerView.Adapter<myproductsAdapter.productHolder> {
    Context context;
    ArrayList<productList> mylist;

    public myproductsAdapter(Context context, ArrayList<productList> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public myproductsAdapter.productHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.myproductitem,viewGroup,false);
        productHolder productHolder=new productHolder(view);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final myproductsAdapter.productHolder viewHolder, final int i) {
        Picasso.with(context).load(mylist.get(i).getProductImage()).into(viewHolder.productImage);
        viewHolder.productName.setText(mylist.get(i).getProducrName().toString());
        viewHolder.productPrice.setText(mylist.get(i).getProductPrice().toString());
        viewHolder.productitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressdialog progressdialog=new progressdialog();
                progressdialog.dialogProgress(context,R.layout.progress_card,"sss");
                Bundle b = new Bundle();
                Intent intent=new Intent(context,productDetails.class);
                int id=mylist.get(i).getId();
                String name = viewHolder.productName.getText().toString();
                String productPrice = viewHolder.productPrice.getText().toString();
                b.putString("id", String.valueOf(id));
                b.putString("productPrice", productPrice);
                b.putString("name", name);
                intent.putExtras(b);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }
    public class productHolder extends RecyclerView.ViewHolder {
        TextView productName,productPrice;
        ImageView productImage;
        LinearLayout productitem;
        public productHolder(@NonNull View itemView) {
            super(itemView);
            productName=(TextView)itemView.findViewById(R.id.productName);
            productPrice=(TextView)itemView.findViewById(R.id.price);
            productImage=(ImageView)itemView.findViewById(R.id.imgproducts);
            productitem=(LinearLayout)itemView.findViewById(R.id.productitem);

        }
    }
}
