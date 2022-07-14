package com.example.decisionmaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取控件id
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);

        // 监听点击事件
        btn1.setOnClickListener(new View.OnClickListener() {//骰子界面
            @Override
            public void onClick(View v) {
                // 跳转到另一个名为ButtonActivity的界面
                Intent intent=new Intent(MainActivity.this,DiceActivity.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {//转盘界面
            @Override
            public void onClick(View v) {
                // 跳转到界面
                Intent intent=new Intent(MainActivity.this,TurntableActivity.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {//抽签界面
            @Override
            public void onClick(View v) {
                // 跳转到另一个名为ButtonActivity的界面
                Intent intent=new Intent(MainActivity.this,MusicActivity.class);
                startActivity(intent);
            }
        });

    }

}

