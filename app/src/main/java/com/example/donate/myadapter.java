package com.example.donate;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Locale;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder>
{
    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model)
    {
        holder.name.setText(model.getName());
        holder.NGOcode.setText(model.getNGOcode());
        holder.email.setText(model.getEmail());
        holder.phone.setText(model.getPhone());
        holder.type.setText(model.getType());
        holder.ngoId = model.getId();
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }
    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView name,NGOcode,email,phone,type;
        String ngoId="";
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.name);
            NGOcode=(TextView) itemView.findViewById(R.id.NGOcode);
            email=(TextView) itemView.findViewById(R.id.email);
            phone=(TextView) itemView.findViewById(R.id.phone);
            type=(TextView) itemView.findViewById(R.id.type);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(itemView.getContext() ,donationpage.class);
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("NGOcode", NGOcode.getText().toString());
                    intent.putExtra("email", email.getText().toString());
                    intent.putExtra("phone", phone.getText().toString());
                    intent.putExtra("ngoId", ngoId);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
