package com.example.decisionmaker;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Random;

public class DiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        //从布局文件中获取名叫roll_button的按钮对象的引用
        Button rollButton = findViewById(R.id.roll_button);
        //在代码中修改按钮rollButton文本属性
        rollButton.setText("Let's Roll");
        //给按钮rollButton设置点击监听器，一旦用户点击按钮，就触发监听器的onClick方法
        rollButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //产生随机数
                Random rand = new Random();
                int randNumber = rand.nextInt(6)+1;

                ImageView diceImage = findViewById(R.id.dice_image);
                int drawableResource;
                switch (randNumber){
                    case 1: drawableResource = R.drawable.dice_1; break;
                    case 2: drawableResource = R.drawable.dice_2; break;
                    case 3: drawableResource = R.drawable.dice_3; break;
                    case 4: drawableResource = R.drawable.dice_4; break;
                    case 5: drawableResource = R.drawable.dice_5; break;
                    case 6: drawableResource = R.drawable.dice_6; break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + randNumber);
                }

                //实例Drawable类
                Drawable drawable = getBaseContext().getResources().getDrawable(drawableResource);
                diceImage.setImageDrawable(drawable);
            }
        });
    }
}