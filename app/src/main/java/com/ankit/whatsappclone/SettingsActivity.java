package com.ankit.whatsappclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.ankit.whatsappclone.Models.Users;
import com.ankit.whatsappclone.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {
ActivitySettingsBinding binding;
FirebaseAuth auth;
FirebaseDatabase database;
FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySettingsBinding.inflate(getLayoutInflater());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

    binding.backArrow.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(SettingsActivity.this,MainActivity.class);
            startActivity(intent);
        }

    });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.etStatus.getText().toString().equals("")&&!binding.textUsername.getText().toString().equals("")){
                    String status=binding.etStatus.getText().toString();
                    String userName=binding.textUsername.getText().toString();
                    HashMap<String, Object> obj=new HashMap<>();
                    obj.put("userName",userName);
                    obj.put("status",status);
                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                            .updateChildren(obj);
                    Toast.makeText(SettingsActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SettingsActivity.this, "Please Update your Username and Status", Toast.LENGTH_SHORT).show();
                }

            }
        });
    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Users users=snapshot.getValue(Users.class);
                            assert users != null;
                            Picasso.get()
                            .load(users.getProfilePic())
                                    .into(binding.profileImage);
                            binding.textUsername.setText(users.getUserName());
                            binding.etStatus.setText(users.getStatus());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");//* means all type of images, for e.g., png, jpg, jpeg,etc..
                startActivityForResult(intent,25);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (data.getData()!=null){
            Uri sFile=data.getData();
            binding.profileImage.setImageURI(sFile);
            final StorageReference reference=storage.getReference().child("Profile Image")
                    .child(FirebaseAuth.getInstance().getUid());
            reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .child("profilePicture").setValue(uri.toString());
                        }
                    });
                }
            });
        }
    }
}