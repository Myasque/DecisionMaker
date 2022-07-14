package com.example.decisionmaker;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;

public class MusicActivity extends AppCompatActivity {

    Boolean isDestory;
    TextView tv_1;
    TextView tv_2;//seekbar当前时间
    TextView tv_3;//seekar 总时长
    SeekBar seekBar;
    MyConnection myConnection;
    MyMusicService.Finder controller;

    private final Handler handler = new Handler(msg -> {
        Update();
        return true;
    });

    public void Update(){//进度条更新函数
        int currentTime = controller.getCurrentPosition();
        seekBar.setProgress(currentTime);
        tv_2.setText(formatTime(currentTime));
        handler.sendEmptyMessageDelayed(0,1000);
    }

    private String formatTime(int length)//时间转化工具函数
    {
        Date date=new Date(length);
        SimpleDateFormat sdf=new SimpleDateFormat("mm:ss");
        String totalTime=sdf.format(date);
        return totalTime;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        isDestory=Boolean.FALSE;
        //添加seekbar
        seekBar=(SeekBar)findViewById(R.id.seekBar);
        tv_1 = (TextView)findViewById(R.id.tv_1);
        tv_1.setText("播放状态1：停止播放");
        tv_2=(TextView)findViewById(R.id.tv_progress);
        tv_3=(TextView)findViewById((R.id.tv_total));
        seekBar.setOnSeekBarChangeListener(new sbarClick());

    }

    class sbarClick implements  SeekBar.OnSeekBarChangeListener{
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {//拖动进度条实时监控
            int progress=seekBar.getProgress();//获取当前进度条位置
            seekBar.setProgress(progress);//更新进度条
            controller.setProgress(progress);//更新音乐进度
        }
    }

    public void play_onclick(View view)
    {
        Intent intent = new Intent(this,MyMusicService.class);

        intent.putExtra("action","play");

        startService(intent);

        myConnection = new MyConnection();

        bindService(intent,myConnection,BIND_AUTO_CREATE);

        tv_1.setText("播放状态2：正在播放");

        //Update();
    }

    public void stop_onclick(View view)
    {
        Intent intent = new Intent(this,MyMusicService.class);

        intent.putExtra("action","stop");

        startService(intent);

        tv_1.setText("播放状态3：停止播放");
    }
    public void pause_onclick(View view)
    {
        Intent intent = new Intent(this,MyMusicService.class);

        intent.putExtra("action","pause");

        startService(intent);

        tv_1.setText("播放状态4：暂停播放");
    }
    public void exit_onclick(View view)
    {
        stop_onclick(view);
        finish();
    }

    class MyConnection implements ServiceConnection {//控制连接实现mediaPlay的调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            controller= (MyMusicService.Finder) service;//获取控制连接对象
            int duration = controller.getDuration();//获取音乐总时长
            tv_3.setText(formatTime(duration));//设置总时长
            seekBar.setMax(duration);//设置进度条的最大值
            Update();//提醒进度条更新
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
    @Override
    public void onDestroy() {
        isDestory=Boolean.TRUE;
        super.onDestroy();
        unbindService(myConnection);
    }
}