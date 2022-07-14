package com.example.decisionmaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class TurntableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turntable);
        //实例化
        LuckyView mlucky = (LuckyView) findViewById(R.id.lucky);
        Button mybtn=(Button) findViewById(R.id.bt);
        //点击按钮转动
        mybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                int randNumber = rand.nextInt(6)+1;
                try {
                    mlucky.start(randNumber);
                } catch (Exception e) {
                }
            }
        });
    }
}