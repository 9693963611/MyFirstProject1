package com.example.androidlearningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button educatorB,studentB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        educatorB=findViewById(R.id.educatorbutton);
        studentB=findViewById(R.id.studentbutton);

        educatorB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,EducatorLogin.class);
                startActivity(i);
            }
        });
        studentB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,studentlogin.class);
                startActivity(i);
            }
        });
    }
}