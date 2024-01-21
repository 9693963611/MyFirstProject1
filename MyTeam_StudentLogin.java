package com.example.androidlearningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class studentlogin extends AppCompatActivity {
    TextView ScreatenewAccount;
    EditText Sinputemail;
    TextInputEditText Sinputpassword;
    Button SbtnLogin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlogin);
        ScreatenewAccount=findViewById(R.id.ScreatenewAccount);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Sinputemail=findViewById(R.id.Sinputemail);
        Sinputpassword=findViewById(R.id.Spassword);
        SbtnLogin=findViewById(R.id.SbtnLogin);
        mAuth= FirebaseAuth.getInstance();

        SbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =Sinputemail.getText().toString().trim();
                String password =Sinputpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Sinputemail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Sinputpassword.setError("Password must be required");
                    return;
                }
                if (password.length() < 6) {
                    Sinputpassword.setError("Password length must be >= 6");
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            boolean value=mAuth.getCurrentUser().isEmailVerified();
                            if(value){
                                Toast.makeText(studentlogin.this, "Login sucessfully" +
                                        "", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(studentlogin.this, teacherscreen.class));
                            }else{
                                Toast.makeText(studentlogin.this, "Verify your Email.." , Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(studentlogin.this, "Error." , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        ScreatenewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(studentlogin.this,studentRegistration.class);
                startActivity(i);
            }
        });
    }
}