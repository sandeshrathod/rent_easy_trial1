package com.example.renteasytrial1st;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
Context context;
ArrayList<product> userArrayList;

    public MyAdapter(Context context, ArrayList<product> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
product product = userArrayList.get(position);
holder.pname.setText(product.pname);
        holder.deposit.setText(product.deposit);
        holder.Rent.setText(product.Rent);
        holder.pid.setText(product.pid);
       holder.btn_prod.setOnClickListener(v->
        {
            Intent intent=new Intent(context,prod_desc.class);
            intent.putExtra("pid", product.getpid());
            context.startActivity(intent);

        });
    }


    @Override
    public int getItemCount() {
        return userArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView pname,deposit,Rent,pid;
        Button btn_prod;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pname= itemView.findViewById(R.id.pro_name);
            deposit = itemView.findViewById(R.id.text_dpst);
            Rent= itemView.findViewById(R.id.rent);
            pid= itemView.findViewById(R.id.pdd);
        btn_prod = itemView.findViewById(R.id.pro_pallet_btn);


        }
    }
}