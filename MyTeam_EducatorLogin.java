package com.example.androidlearningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EducatorLogin extends AppCompatActivity {
    TextView EcreatenewAccount;
    EditText Einputemail;
    TextInputEditText inputpassword;
    Button EbtnLogin;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlogin);
        EcreatenewAccount=findViewById(R.id.EcreatenewAccount);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Einputemail=findViewById(R.id.Einputemail);
        inputpassword=findViewById(R.id.inputpassword);
        EbtnLogin=findViewById(R.id.EbtnLogin);
        mAuth= FirebaseAuth.getInstance();

        EbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =Einputemail.getText().toString().trim();
                String password =inputpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Einputemail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    inputpassword.setError("Password must be required");
                    return;
                }
                if (password.length() < 6) {
                    inputpassword.setError("Password length must be >= 6");
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            boolean value=mAuth.getCurrentUser().isEmailVerified();
                            if(value){
                                Toast.makeText(EducatorLogin.this, "Login sucessfully" +
                                        "", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EducatorLogin.this, Dashboard.class));
                            }else{
                                Toast.makeText(EducatorLogin.this, "Verify your Email.." , Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(EducatorLogin.this, "Error." , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        EcreatenewAccount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(EducatorLogin.this,EducatorRegistration.class);
                startActivity(i);
            }
        });
    }
}