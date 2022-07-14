package com.example.decisionmaker;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class MyMusicService extends Service {
    public MyMusicService() {
    }
    private MediaPlayer mediaPlayer;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return new Finder();
    }
    //新添加Binder
    public class Finder extends Binder {
        public int getDuration() {
            //获得总长
            return mediaPlayer.getDuration();
        }
        public int getCurrentPosition(){
            //获得当前进度
            return mediaPlayer.getCurrentPosition();
        }
        public void setProgress(int s){
            //更改当前进度
            mediaPlayer.seekTo((s));
        }
    }

    public void onCreate(){
        super.onCreate();
        Random rand = new Random();
        int randNumber = rand.nextInt(6)+1;
        switch(randNumber){
            case 1:mediaPlayer = mediaPlayer.create(this,R.raw.newyear);break;
            case 2:mediaPlayer = mediaPlayer.create(this,R.raw.sea);break;
            case 3:mediaPlayer = mediaPlayer.create(this,R.raw.seeyouagain);break;
            case 4:mediaPlayer = mediaPlayer.create(this,R.raw.guitar);break;
            case 5:mediaPlayer = mediaPlayer.create(this,R.raw.you);break;
            case 6:mediaPlayer = mediaPlayer.create(this,R.raw.beautiful);break;
            default:mediaPlayer = mediaPlayer.create(this,R.raw.you);break;
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //获取意图传递的信息
        String action = intent.getStringExtra("action");

        switch (action)
        {
            case "play":
                if (mediaPlayer == null)
                {
                    mediaPlayer = MediaPlayer.create(this,R.raw.newyear);
                }
                mediaPlayer.start();

                break;
            case "stop":
                if (mediaPlayer !=null)
                {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                break;
            case "pause":
                if (mediaPlayer !=null && mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                }
                break;
        }
        return super.onStartCommand(intent, flags, startId);

    }


}
