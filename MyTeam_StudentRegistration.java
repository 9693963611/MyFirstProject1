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

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;
public class studentRegistration extends AppCompatActivity {

    EditText SinputName,SinputMobileNUmber,inputemail;
    Button SbtnReg;
    TextView SAlreadyhaveanaccount;
    TextInputEditText inputpassword;
    FirebaseAuth mAuth;
    FirebaseUser muser;
    DatabaseReference reference;

    FirebaseDatabase rootnode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        SinputName=findViewById(R.id.SinputName);
        SinputMobileNUmber=findViewById(R.id.SinputMobileNUmber);
        inputemail=findViewById(R.id.inputemail);
        inputpassword=findViewById(R.id.password);
        SbtnReg=findViewById(R.id.SbtnReg);
        SAlreadyhaveanaccount=findViewById(R.id.SAlreadyhaveanaccount);
        SAlreadyhaveanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(studentRegistration.this,studentlogin.class);
                startActivity(i);
                finish();
            }
        });
        mAuth=FirebaseAuth.getInstance();
        muser=mAuth.getCurrentUser();


//         mAuth = FirebaseAuth.getInstance();
//
//        if(mAuth.getCurrentUser()!=null){
//            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
//            finish();
//        }
        SbtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rootnode = FirebaseDatabase.getInstance();
                reference=FirebaseDatabase.getInstance().getReference().child("users");
                String email=inputemail.getText().toString().trim();
                String password=inputpassword.getText().toString().trim();
                String name=SinputName.getText().toString().trim();
                String number =SinputMobileNUmber.getText().toString().trim();

                StudentProfileModel model=new StudentProfileModel(email,password,name,number);
                if(TextUtils.isEmpty(email)){
                    inputemail.setError("Email is required");return;
                }
                if(TextUtils.isEmpty(password)){
                    inputpassword.setError("Password Required"); return;
                }
                if(TextUtils.isEmpty(name)){
                    SinputName.setError("Enter Your Name"); return;
                }
                if(TextUtils.isEmpty(number)){
                    SinputMobileNUmber.setError("Enter Number"); return;
                }
                if(number.length()!=10){
                    SinputMobileNUmber.setError("Incorrect Number"); return;
                }

                HashMap<String,Object> map=new HashMap<>();
                map.put("name",name);
                map.put("email",email);
                map.put("password",password);
                map.put("number",number);




                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(studentRegistration.this, "Please verify your Email", Toast.LENGTH_SHORT).show();
                                        String key=reference.push().getKey();
                                        reference.child(key).setValue(model);


                                        startActivity(new Intent(studentRegistration.this,studentlogin.class));

                                    }else{
                                        Toast.makeText(studentRegistration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                        else {
                            Toast.makeText(studentRegistration.this, "Error...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}