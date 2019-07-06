package com.bytedance.videoplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends AppCompatActivity {

    Uri uri;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = findViewById(R.id.videoView);
        Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        final SeekBar seekBar = findViewById(R.id.seekBar);



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.pause();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select video"),1);
            }
        });

        videoView.setVideoPath(getVdieoPath(R.raw.make));
//        videoView.start();

        final Handler mhandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                if (msg.what==1) {
                    seekBar.setMax(videoView.getDuration());
                    seekBar.setProgress(videoView.getCurrentPosition());
                    sendEmptyMessageDelayed(1,500);
                }
            }
        };

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.start();
                mhandler.sendEmptyMessage(1);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                videoView.seekTo(seekBar.getProgress());
            }
        });



    }
    private String getVdieoPath(int resId)    {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCold, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCold,data);
        if (resultCold == RESULT_OK && null != data && requestCode == 1){
            uri = data.getData();
            videoView.setVideoURI(uri);
        }
    }


}

