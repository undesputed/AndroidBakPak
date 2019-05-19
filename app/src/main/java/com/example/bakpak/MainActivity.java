package com.example.bakpak;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button btn_login, btn_resetpassword,btn_signup;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    Toolbar toolbar;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            Toast.makeText(getApplicationContext(),"You are already Logged in", Toast.LENGTH_SHORT).show();
        }

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);
        btn_resetpassword = findViewById(R.id.btn_reset_password);
        btn_signup = findViewById(R.id.btn_signup);
        progressDialog = new ProgressDialog(this);

//        toolbar.setTitle("Login");

        firebaseAuth = FirebaseAuth.getInstance();

        btn_resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Reset Password", Toast.LENGTH_SHORT).show();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Sign-up your Account",Toast.LENGTH_SHORT).show();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progressBar.setVisibility(View.VISIBLE);
                progressDialog.setMessage("Logging In...");
                progressDialog.show();
                String user = email.getText().toString();
                final String pass = password.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressBar.setVisibility(View.GONE);
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Login Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
                        }else{
                            Toast.makeText(MainActivity.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

//    public void sign_in(){
//        List<AuthUI.IdpConfig> providers = Arrays.asList(
//                new AuthUI.IdpConfig.EmailBuilder().build(),
//                new AuthUI.IdpConfig.PhoneBuilder().build(),
//                new AuthUI.IdpConfig.GoogleBuilder().build(),
//                new AuthUI.IdpConfig.FacebookBuilder().build(),
//                new AuthUI.IdpConfig.TwitterBuilder().build()
//        );
//        startActivityForResult(AuthUI.getInstance()
//            .createSignInIntentBuilder()
//                .setAvailableProviders(providers)
//                .build(),
//                RC_SIGN_IN);
//    }
}
