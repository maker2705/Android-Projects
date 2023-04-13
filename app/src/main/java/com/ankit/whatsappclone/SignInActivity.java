package com.ankit.whatsappclone;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.security.FileIntegrityManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.ankit.whatsappclone.databinding.ActivitySignInBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;
FirebaseDatabase fDatabase;
FirebaseAuth mAuth;
ProgressDialog pDialog;
GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        binding=ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth=FirebaseAuth.getInstance();
        fDatabase=FirebaseDatabase.getInstance();
        pDialog=new ProgressDialog(SignInActivity.this);
        pDialog.setTitle("Signing In");
        pDialog.setMessage("Please wait,\n validating your credentials");

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        // Build a GoogleSignInClient with the options specified by gso.

        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);



        /*gSOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gSOptions);
        GoogleSignInAccount gAccount= GoogleSignIn.getLastSignedInAccount(this);
        if (gAccount!=null){
            finish();
            Intent intent=new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
        }*/
        /*ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()== Activity.RESULT_OK){
                            Intent data=result.getData();
                            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
                            try {task.getResult(ApiException.class);
                                finish();
                                Intent intent=new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(intent);

                            }catch(ApiException e){
                                Toast.makeText(SignInActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });*/
        /*binding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent=mGoogleSignInClient.getSignInIntent();
                activityResultLauncher.launch(signInIntent);

            }
        });*/
        /*BeginSignInRequest signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                .build();*/


        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.textEmail.getText().toString().isEmpty()&&!binding.textPassword.getText().toString().isEmpty())
                {

                    pDialog.show();
                    mAuth.signInWithEmailAndPassword(binding.textEmail.getText().toString(),binding.textPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                 pDialog.dismiss();
                                 if (task.isSuccessful()){
                                     Intent intent=new Intent(SignInActivity.this,MainActivity.class);
                                     startActivity(intent);
                                 }
                                 else{
                                     Toast.makeText(SignInActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                 }
                                }
                            });
                }
                else{
                    Toast.makeText(SignInActivity.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
                }

            }
        });
        if(mAuth.getCurrentUser()!=null){
            Intent intent=new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
        }

        binding.textClickSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
//        binding.btnGoogle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signIn();
//            }
//        });

    }

//    int RC_SIGN_IN = 65;
//    private void signIn(){
//        Intent signInIntent =mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent,RC_SIGN_IN);
//    }
//    @Override
//    public void onActivityResult(int requestCode,int resultCode, Intent data){
//        super.onActivityResult(requestCode,resultCode,data);
//        if (requestCode==RC_SIGN_IN){
//            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                GoogleSignInAccount account=task.getResult(ApiException.class);
//                Log.d("TAG","firebaseAuthWithGoogle:"+account.getId());
//                firebaseAuthWithGoogle(account.getIdToken());
//            }catch (ApiException e){
//                Log.w("TAG","Google Sign in Failed",e);
//            }
//        }
//    }

//    private void firebaseAuthWithGoogle(String idToken) {
//        AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            Log.d("TAG","signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            Intent intent=new Intent(SignInActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            Toast.makeText(SignInActivity.this, "Signed In with Google", Toast.LENGTH_SHORT).show();
//                        }else{
//                            Log.w("TAG","signInWithCredential: failure",task.getException());
//
//                        }
//                    }
//                });
//    }

}