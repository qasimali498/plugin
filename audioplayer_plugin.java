package com.example.audioplayer_plugin;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;


public class audioplayer_plugin extends GodotPlugin {
    MediaPlayer mediaPlayer;
    boolean is_playing=false;
    boolean is_initialized=false;

    public audioplayer_plugin(Godot godot) {
        super(godot);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()

        );

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                is_initialized = true;
            }
        });


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                is_playing = false;
            }
        });
    }


    @NonNull
    @Override
    public String getPluginName() {
        return "audioplayer_plugin";
    }

    @Override
    public List<String> getPluginMethods() {
        return Arrays.asList("innit","start","pause","stop","prepare","get_is_playing");
    }

    public void innit(String Url) {

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(Url);
            mediaPlayer.prepare();


        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void start() {

        if (is_playing == false) {
            mediaPlayer.start();
            is_playing = true;
        }

        else{

            Log.d("gd-android","Audio Already Playing");
        }
    }

    public void pause() {

        if (is_playing == true) {
            is_playing = false;
            mediaPlayer.pause();
        }
        else{

            Log.d("gd-android","Nothing is playing");
        }


    }

    public void stop() {

        if (is_initialized == true) {

            is_playing = false;
            is_initialized = false;
            mediaPlayer.stop();
        }
        else{

            Log.d("gd-android","Nothing is playing");
        }

    }

    public void prepare() {


        if (is_initialized == false) {

            try {
                mediaPlayer.prepare();
                is_initialized=true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            Log.d("gd-android","already initialized");
        }

    }
    public boolean get_is_playing(){
        return is_playing;
    }

    @Override
    public void onMainPause(){
        super.onMainPause();

        if(is_playing==true){
            mediaPlayer.pause();
            is_playing=false;
        }
    }
}






