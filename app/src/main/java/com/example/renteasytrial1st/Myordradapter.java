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

public class Myordradapter extends RecyclerView.Adapter<Myordradapter.MyView>{
    Context context;
    ArrayList<ordr_prod> userArrayList;

    public Myordradapter(Context context, ArrayList<ordr_prod> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public Myordradapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.ordr_list,parent,false);
        return new MyView(v);

    }

    @Override
    public void onBindViewHolder(@NonNull Myordradapter.MyView holder, int position) {
        ordr_prod pr = userArrayList.get(position);
        holder.name.setText(pr.pname);
        holder.depo.setText(pr.deposit);
        holder.ren.setText(pr.Rent);

        Toast.makeText(context, "id: "+ pr.getReqstduid(), Toast.LENGTH_SHORT).show();
        holder.bttn.setOnClickListener(v->
        {
            Intent intent=new Intent(context,user_desc.class);
            intent.putExtra("pid", pr.getPid());
            context.startActivity(intent);

        });


    }


    @Override
    public int getItemCount() {
        return userArrayList.size();
    }


    public static class MyView extends RecyclerView.ViewHolder{
        TextView name,depo,ren;
        String uid;
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