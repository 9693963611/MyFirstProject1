package com.example.androidlearningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class EducatorRegistration extends AppCompatActivity {

    EditText EinputName,EinputMobileNUmber,inputemail,EAddress,EState,EDistrict,ECity;
    Button EbtnReg;
    TextView EAlreadyhaveanaccount;
    TextInputEditText inputpassword;
    FirebaseAuth mAuth;
    FirebaseUser muser;
    DatabaseReference reference;

    FirebaseDatabase rootnode;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        EinputName=findViewById(R.id.Einputname);
        EinputMobileNUmber=findViewById(R.id.EinputMobileNUmber);
        inputemail=findViewById(R.id.Einputemail);
        inputpassword=findViewById(R.id.Einputpassword);
        EAddress=findViewById(R.id.Address);
        EState=findViewById(R.id.State);
        EDistrict=findViewById(R.id.district);
        ECity=findViewById(R.id.city);
        EbtnReg=findViewById(R.id.EbtnReg);
        EAlreadyhaveanaccount=findViewById(R.id.EAlreadyhaveanaccount);
        EAlreadyhaveanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(EducatorRegistration.this,EducatorLogin.class);
                startActivity(i);
                finish();
            }
        });
        mAuth=FirebaseAuth.getInstance();
        muser=mAuth.getCurrentUser();
//
//
//         mAuth = FirebaseAuth.getInstance();
//
//        if(mAuth.getCurrentUser()!=null){
//            startActivity(new Intent(.this,MainActivity.class));
//            finish();
//        }
        EbtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rootnode = FirebaseDatabase.getInstance();
                reference=FirebaseDatabase.getInstance().getReference().child("users");
                String email=inputemail.getText().toString().trim();
                String password=inputpassword.getText().toString().trim();
                String name=EinputName.getText().toString().trim();
                String number =EinputMobileNUmber.getText().toString().trim();
                String address =EAddress.getText().toString().trim();
                String state = EState.getText().toString().trim();
                String district=EDistrict.getText().toString().trim();
                String city=ECity.getText().toString().trim();

                EducatorProfileModel model=new EducatorProfileModel(email,password,name,number,address,city,district,state);
                if(TextUtils.isEmpty(email)){
                    inputemail.setError("Email is required");return;
                }
                if(TextUtils.isEmpty(password)){
                    inputpassword.setError("Password Required"); return;
                }
                if(TextUtils.isEmpty(name)){
                    EinputName.setError("Enter Your Name"); return;
                }
                if(TextUtils.isEmpty(number)){
                    EinputMobileNUmber.setError("Enter Number"); return;
                }
                if(number.length()!=10){
                    EinputMobileNUmber.setError("Incorrect Number"); return;
                }
                if(TextUtils.isEmpty(address)){
                    EAddress.setError("Enter Address");  return;
                }
                if(TextUtils.isEmpty(state)){
                    EState.setError("Enter State"); return;
                }
                if(TextUtils.isEmpty(district)){
                    EDistrict.setError("Enter District"); return;
                }
                if(TextUtils.isEmpty(city)){
                    ECity.setError("Enter City"); return;
                }

                HashMap<String,Object> map=new HashMap<>();
                map.put("name",name);
                map.put("email",email);
                map.put("password",password);
                map.put("number",number);
                map.put("address",address);
                map.put("state",state);
                map.put("district",district);
                map.put("city",city);





                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(EducatorRegistration.this, "Please verify your Email", Toast.LENGTH_SHORT).show();
                                        String key=reference.push().getKey();
                                        reference.child(key).setValue(model);


                                        startActivity(new Intent(EducatorRegistration.this,EducatorLogin.class));

                                    }else{
                                        Toast.makeText(EducatorRegistration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                        else {
                            Toast.makeText(EducatorRegistration.this, "Error...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}