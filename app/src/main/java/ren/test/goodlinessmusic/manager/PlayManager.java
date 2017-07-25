package ren.test.goodlinessmusic.manager;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Message;

import java.io.IOException;
import java.util.List;

import ren.test.goodlinessmusic.beans.Music;
import ren.test.goodlinessmusic.utils.MusicUtils;

/**
 * Created by Administrator on 2017/07/10
 */

public class PlayManager implements MediaPlayer.OnCompletionListener {
    public static final String ACTION_MUSIC_INFO = "musicInfo";
    public static final String KEY_STATE = "state";
    public static final String KEY_MUSIC_INFO = "music";
    public static final int STATE_PLAING = 1;
    public static final int STATE_PAUSE = 2;
    public static final int STATE_STOP = 3;
    private static PlayManager playManager;
    private Context context;
    private MediaPlayer mediaPlayer;
    private Music currentMusic;
    private int currentPosition = -1;
    private List<Music> musics;
    private boolean isPause;
    private Intent intent;
    private HandleManager handleManager;

    private PlayManager(Context context) {
        this.context = context;
        mediaPlayer = new MediaPlayer();
        currentMusic = MusicUtils.getRecentMusic();
        handleManager=HandleManager.getInstance();
    }

    public Music getCurrentMusic() {
        return currentMusic;
    }

    public static PlayManager getInstance(Context context) {
        return playManager == null ? playManager = new PlayManager(context) : playManager;
    }

    public void setMusicList(List<Music> musics) {
        this.musics = musics;
//        if (currentMusic == null)
//            currentMusic = musics.get(0);
    }

    public List<Music> getMusicList() {
        return musics;
    }

    public boolean isPause() {
        return isPause;
    }

    public boolean isPlaying(){
       return mediaPlayer.isPlaying();
    }

    public void play(Music music) {
        try {
            handleManager.removeCallbacks(r);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(music.getUrl());
            mediaPlayer.prepare();
            mediaPlayer.start();
            handleManager.post(r);
            currentMusic = music;
            currentMusic.setIsrecentPlay(true);
            currentMusic.setPlayTime(System.currentTimeMillis() + "");
            MusicUtils.insert(currentMusic);
            sendBroadCast(STATE_PLAING);
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
            sendBroadCast(STATE_PAUSE);
            handleManager.removeCallbacks(r);
        }
    }

    public void start() {
        if (!mediaPlayer.isPlaying() && isPause)
            mediaPlayer.start();
        isPause = false;
        sendBroadCast(STATE_PLAING);
        handleManager.post(r);
    }

    public void last(){
        if (currentPosition>0)
            currentPosition-=1;
        else
            currentPosition=musics.size()-1;
        Music music=musics.get(currentPosition);
        play(music);
    }

    public void next(){
        if (currentPosition == musics.size() - 1)
            currentPosition = 0;
        else
            currentPosition++;
        Music music = musics.get(currentPosition);
        play(music);
    }

    public void stop(){
        handleManager.removeCallbacks(r);
        mediaPlayer.release();
        musics=null ;
        currentMusic=null ;
        context=null ;
        playManager=null;
    }

    public void seekTo(int position){
        mediaPlayer.seekTo(position);
    }

    public int getCurrentPosition(){
        return  mediaPlayer.getCurrentPosition();
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            Message msg = new Message();
            msg.what = HandleManager.CURRENT_POSITION;
            msg.arg1 = mediaPlayer.getCurrentPosition();//当前播放位置
            msg.arg2 = mediaPlayer.getDuration();
            handleManager.sendMessage(msg);
            handleManager.postDelayed(r, 500);
        }
    };

    private void sendBroadCast(int state) {
        if (intent == null) {
            intent = new Intent();
            intent.setAction(ACTION_MUSIC_INFO);
        }
        intent.putExtra(KEY_MUSIC_INFO, currentMusic);
        intent.putExtra(KEY_STATE, state);
        context.sendBroadcast(intent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (!mp.isLooping())
            next();
    }
}
