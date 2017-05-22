package com.example.samsung.p1271_soundpool;

import android.media.AudioManager;
import android.media.SoundPool;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static android.media.SoundPool.*;

public class MainActivity extends AppCompatActivity implements OnLoadCompleteListener {

    private final int MAX_STREAMS = 5;
    private String msg;

    private SoundPool sp;
    private int soundIdShot, soundIdExplosion, streamIdShot, streamIdExplosion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(this);

        soundIdShot = sp.load(this, R.raw.shot, 1);
        msg = "soundIdShot = " + soundIdShot;
        Messager.sendToAllRecipients(getBaseContext(), msg);

        try {
            soundIdExplosion = sp.load(getAssets().openFd("explosion.ogg"), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        msg = "soundIdExplosion = " + soundIdExplosion;
        Messager.sendToAllRecipients(getBaseContext(), msg);
    }

    public void onClickBtn(View view) {
        streamIdShot = sp.play(soundIdShot, 1, 0, 0, 9, 1);
        msg = "streamIdShot = " + streamIdShot;
        Messager.sendToAllRecipients(getBaseContext(), msg);
        streamIdExplosion = sp.play(soundIdExplosion, 0, 1, 0, 4, 1);
        msg = "streamIdExplosion = " + streamIdExplosion;
        Messager.sendToAllRecipients(getBaseContext(), msg);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Step 5
//        sp.autoPause();
//        try {
//            TimeUnit.SECONDS.sleep(3);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        sp.autoResume();
        //Step 6
        sp.setVolume(streamIdShot, 0, 1);
        sp.setVolume(streamIdExplosion, 1, 0);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        msg = "onLoadComplete(), sampleId = " + sampleId + ", status = " + status;
        Messager.sendToAllRecipients(getBaseContext(), msg);
    }
}
