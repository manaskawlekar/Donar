package com.example.donate;

import android.content.Intent;
import android.util.Log;
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

public class myadapter2 extends FirebaseRecyclerAdapter<model2,myadapter2.myviewholder>
{
    public myadapter2(@NonNull FirebaseRecyclerOptions<model2> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder2, int position, @NonNull model2 model2)
    {
        holder2.fullname.setText(model2.getFullname());
        holder2.email.setText(model2.getEmail());
        holder2.phone.setText(model2.getPhone());
        holder2.type.setText(model2.getType());

        Log.d("mResult", model2.getFullname());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow2,parent,false);
        return new myviewholder(view);
    }
    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView fullname,email,phone,type;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            fullname=(TextView) itemView.findViewById(R.id.fullname);
            email=(TextView) itemView.findViewById(R.id.email);
            phone=(TextView) itemView.findViewById(R.id.phone);
            type=(TextView) itemView.findViewById(R.id.type);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(itemView.getContext() ,donationpage.class);
                    intent.putExtra("name", fullname.getText().toString());
                    intent.putExtra("email", email.getText().toString());
                    intent.putExtra("phone", phone.getText().toString());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
