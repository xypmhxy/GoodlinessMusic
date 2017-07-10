package ren.test.goodlinessmusic.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import java.util.List;

import ren.test.goodlinessmusic.activity.MainActivity;
import ren.test.goodlinessmusic.beans.Music;
import ren.test.goodlinessmusic.manager.PlayManager;
import ren.test.goodlinessmusic.utils.MusicUtils;


/**
 * Created by Administrator on 2017/07/10
 */

public class MusicService extends Service {
    private MusicBinder binder;
    private PlayManager playManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder == null ? binder = new MusicBinder() : binder;
    }

    @Override
    public void onCreate() {
        playManager = PlayManager.getInstance(this);
        playManager.setMusicList(MusicUtils.getAllMusics());
        super.onCreate();
    }


    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public void play(Music music) {
        playManager.play(music);
    }
}
