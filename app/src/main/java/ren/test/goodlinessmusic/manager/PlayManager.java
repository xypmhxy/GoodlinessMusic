package ren.test.goodlinessmusic.manager;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import ren.test.goodlinessmusic.application.MusicApplication;
import ren.test.goodlinessmusic.beans.Music;
import ren.test.goodlinessmusic.utils.MusicUtils;

/**
 * Created by Administrator on 2017/07/10
 */

public class PlayManager implements MediaPlayer.OnCompletionListener {
    public static final String MUSIC_INFO = "musicInfo";
    public static final String MUSIC_TITTLE = "musicInfo";
    public static final String MUSIC_ = "musicInfo";
    private static PlayManager playManager;
    private WeakReference<Context> context;
    private MediaPlayer mediaPlayer;
    private Music currentMusic;
    private int currentPosition = -1;
    private List<Music> musics;
    private boolean isPause;
    private Intent intent;

    private PlayManager(Context context) {
        this.context = new WeakReference<Context>(context);
        mediaPlayer = new MediaPlayer();
        currentMusic = MusicUtils.getRecentMusic();
    }

    public Music getCurrentMusic() {
        return currentMusic;
    }

    public static PlayManager getInstance(Context context) {
        return playManager == null ? playManager = new PlayManager(context) : playManager;
    }

    public void setMusicList(List<Music> musics) {
        this.musics = musics;
        if (currentMusic == null)
            currentMusic = musics.get(0);
    }

    public List<Music> getMusicList() {
        return musics;
    }

    public boolean isPause() {
        return isPause;
    }

    public void play(Music music) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(music.getUrl());
            mediaPlayer.prepare();
            mediaPlayer.start();
            currentMusic = music;
            currentMusic.setPlayTime(System.currentTimeMillis() + "");
            MusicUtils.insert(currentMusic);
            sendBroadCast();
            isPause = false;
            if (currentPosition != -1) {
                return;
            }
            for (int i = 0; i < musics.size(); i++) {
                if (musics.get(i).getId() == music.getId()) {
                    currentPosition = i;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPause = true;
        }
    }

    public void start() {
        if (!mediaPlayer.isPlaying() && isPause)
            mediaPlayer.start();
        isPause = false;
    }

    private void sendBroadCast() {
        if (intent == null) {
            intent = new Intent();
            intent.setAction(MUSIC_INFO);
        }
        intent.putExtra("music", currentMusic);
        context.get().sendBroadcast(intent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (currentPosition == musics.size() - 1)
            currentPosition = 0;
        else
            currentPosition++;
        Music music = musics.get(currentPosition);
        play(music);
    }
}
