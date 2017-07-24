package ren.test.goodlinessmusic.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import java.util.List;

import ren.test.goodlinessmusic.activity.MainActivity;
import ren.test.goodlinessmusic.beans.Music;
import ren.test.goodlinessmusic.broadcast.HeadsetButtonReceiver;
import ren.test.goodlinessmusic.manager.MusicMediaSessionCallbackManager;
import ren.test.goodlinessmusic.manager.MusicNotificationManager;
import ren.test.goodlinessmusic.manager.PlayManager;
import ren.test.goodlinessmusic.utils.MusicUtils;


/**
 * Created by Administrator on 2017/07/10
 */

public class MusicService extends Service {
    public static final String KEY_MUSIC_BEAN="musicBean";
    private MusicBinder binder;
    private PlayManager playManager;
    private MusicNotificationManager notificationManager;
    private MediaControllerCompat compat;
    private MediaSessionCompat sessionCompat;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder == null ? binder = new MusicBinder() : binder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate() {
        notificationManager=MusicNotificationManager.getInstance(this);
        ComponentName cn = new ComponentName(this.getApplicationContext().getPackageName(),
                HeadsetButtonReceiver.class.getName());
        sessionCompat=new MediaSessionCompat(this,"com.ren.music");
        sessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        sessionCompat.setCallback(new MusicMediaSessionCallbackManager(this,notificationManager));
        sessionCompat.setActive(true);
        try {
            compat=sessionCompat.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        startForeground(230, MusicNotificationManager.getInstance(this).notification);
        return super.onStartCommand(intent, flags, startId);
    }

    public class MusicBinder extends Binder {
        public MediaControllerCompat getController() {
            return compat;
        }
    }

    public void play(Music music) {
        playManager.play(music);
    }
}
