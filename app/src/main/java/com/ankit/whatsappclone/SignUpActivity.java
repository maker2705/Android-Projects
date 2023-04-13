package com.ankit.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ankit.whatsappclone.Models.Users;
import com.ankit.whatsappclone.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//to hide the ActionBar
       // setContentView(R.layout.activity_sign_up);
        binding= ActivitySignUpBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot()); //binding code for the Views

        FirebaseAuth mAuth = FirebaseAuth.getInstance();//instantiating of mAuth object
            FirebaseDatabase database=FirebaseDatabase.getInstance();//instantiating of database object
        ProgressDialog progressDialog=new ProgressDialog(SignUpActivity.this);
            progressDialog.setTitle("Creating Account");
                progressDialog.setMessage("We are creating your account");
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.textUsername.getText().toString().isEmpty()
                            && !binding.textEmail.getText().toString().isEmpty()
                                && !binding.textPassword.getText().toString().isEmpty())
                {
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(
                            binding.textEmail.getText().toString(),
                            binding.textPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()){
                                Users user =new Users(binding.textUsername.getText().toString(),
                                        binding.textEmail.getText().toString(),
                                        binding.textPassword.getText().toString()
                                        );
                                String id=task.getResult().getUser().getUid();
                                database.getReference().child("Users").child(id).setValue(user);
                                Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(SignUpActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Enter Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.AlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}