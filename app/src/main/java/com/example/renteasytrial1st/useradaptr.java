package com.example.renteasytrial1st;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class useradaptr extends RecyclerView.Adapter<useradaptr.viewholder>{
    ArrayList<userbag> uvv;
    Context context;

    public useradaptr(ArrayList<userbag> uvv, Context context) {
        this.uvv = uvv;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_user,parent,false);
        return new viewholder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
userbag usrg=uvv.get(position);
holder.name.setText(usrg.getName());
        holder.email.setText(usrg.getName());
    }

    @Override
    public int getItemCount() {
        return uvv.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder{
TextView name,email;
        public viewholder(@NonNull View itemView) {
            super(itemView);
          name=itemView.findViewById(R.id.displayName);

            email=itemView.findViewById(R.id.displayEmail);
        }
    }
}
