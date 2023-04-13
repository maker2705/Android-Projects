package com.ankit.whatsappclone.Adapters;

import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ankit.whatsappclone.ChattingScreenActivity;
import com.ankit.whatsappclone.Models.Users;
import com.ankit.whatsappclone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    ArrayList<Users>list;

    android.content.Context context;


    public UsersAdapter(ArrayList<Users>list, android.content.Context context){
        this.list=list;
        this.context=context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_user_sample,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users = list.get(position);
        // Load the user's profile image using a library like Picasso or Glide
        // and set it to the image view
        Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.avatar3).into(holder.image);
        holder.userName.setText(users.getUserName());


        FirebaseDatabase.getInstance().getReference()
                .child("chats")
                    .child(FirebaseAuth.getInstance().getUid()+users.getUserID())
                        .orderByChild("timestamp")
                            .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if (snapshot.hasChildren()){
                           for (DataSnapshot snapshot1:snapshot.getChildren()){
                               holder.lastMessage.setText(snapshot1.child("message").getValue().toString());
                           }
                       }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, ChattingScreenActivity.class);
                intent.putExtra("userId",users.getUserID());
                intent.putExtra("profilePic",users.getProfilePic());
                intent.putExtra("userName",users.getUserName());
                context.startActivity(intent);
            }
        });
        // Set an onClickListener for the user's item view to open a chat activity

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView userName,lastMessage;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.profile_image);
            userName=itemView.findViewById(R.id.userNameList);
            lastMessage=itemView.findViewById(R.id.lastMessage);

        }
    }
}
