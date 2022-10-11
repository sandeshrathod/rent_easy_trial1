package com.example.renteasytrial1st;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class rqstadpter  extends RecyclerView.Adapter<rqstadpter.MyView>
{
    ArrayList<rqstpr> arr;
    Context context;

    public rqstadpter(ArrayList<rqstpr> arr, Context context) {
        this.arr = arr;
        this.context = context;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.ordr_list,parent,false);
        return new MyView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        rqstpr pr = arr.get(position);
        holder.name.setText(pr.pname);
        holder.depo.setText(pr.deposit);
        holder.ren.setText(pr.Rent);

        holder.bttn.setOnClickListener(v->
        {

            Intent intent=new Intent(context,billing.class);
            intent.putExtra("pid", pr.pid);
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public static class MyView extends RecyclerView.ViewHolder{
        TextView name,depo,ren;
        String pid;
        Button bttn;


        public MyView (@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.pro_namee);
            depo = itemView.findViewById(R.id.rentt2);
            ren= itemView.findViewById(R.id.rentt);
            bttn= itemView.findViewById(R.id.proregg_btn);




        }
    }
}
