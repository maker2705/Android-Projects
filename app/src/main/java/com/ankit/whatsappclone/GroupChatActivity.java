package com.ankit.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ankit.whatsappclone.Adapters.ChatAdapter;
import com.ankit.whatsappclone.Models.MessageModel;

import com.ankit.whatsappclone.databinding.ActivityGroupChatBinding;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class GroupChatActivity extends AppCompatActivity {
ActivityGroupChatBinding binding;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding.backArrow.setOnClickListener(v -> {
            Intent intent =new Intent(GroupChatActivity.this, MainActivity.class);
            startActivity(intent);
        });

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final ArrayList<MessageModel> messageModels=new ArrayList<>();

        final String senderId= FirebaseAuth.getInstance().getUid();
        binding.userName.setText("Group Chat");

        final ChatAdapter adapter=new ChatAdapter(messageModels, this);
        binding.chatRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);


        database.getReference().child("Group Chat")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren())
                        {
                            MessageModel model=snapshot1.getValue(MessageModel.class);
                            model.setMessageId(snapshot1.getKey());
                            messageModels.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





        binding.send.setOnClickListener(v -> {
            final String message= binding.enterMessage.getText().toString();
            final MessageModel model=new MessageModel(senderId,message);
            model.setTimestamp(new Date().getTime());

            binding.enterMessage.setText("");
            database.getReference().child("Group Chat")
                    .push().setValue(model)
                    .addOnSuccessListener(unused -> Toast.makeText(GroupChatActivity.this, "Message sent", Toast.LENGTH_SHORT).show());
        });

    }
}