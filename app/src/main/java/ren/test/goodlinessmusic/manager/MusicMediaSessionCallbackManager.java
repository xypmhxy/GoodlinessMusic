package ren.test.goodlinessmusic.manager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;

import ren.test.goodlinessmusic.beans.Music;
import ren.test.goodlinessmusic.service.MusicService;

/**
 * Created by Administrator on 2017/7/11
 */

public class MusicMediaSessionCallbackManager extends MediaSessionCompat.Callback {

    private PlayManager playManager;
    private MusicService service;
    private MusicNotificationManager notificationManager;

    public MusicMediaSessionCallbackManager(MusicService context,MusicNotificationManager notificationManager) {
        playManager = PlayManager.getInstance(context);
        service=context;
        this.notificationManager=notificationManager ;
    }

    @Override
    public void onPlay() {
        playManager.start();
        notificationManager.showNotifi();
    }

    @Override
    public void onPlayFromMediaId(String mediaId, Bundle extras) {
        if (extras != null)
            playManager.play((Music) extras.getSerializable(MusicService.KEY_MUSIC_BEAN));
        notificationManager.showNotifi();
    }

    @Override
    public void onPause() {
        playManager.pause();
        notificationManager.stopNotifi();
    }

    @Override
    public void onStop() {
        super.onStop();
        notificationManager.stopNotifi();
    }

    @Override
    public void onSeekTo(long pos) {
        super.onSeekTo(pos);
    }

    @Override
    public void onSkipToNext() {
        super.onSkipToNext();
    }

    @Override
    public void onSkipToPrevious() {
        super.onSkipToPrevious();
    }


    @Override
    public boolean onMediaButtonEvent(Intent mediaButtonEvent) {
        return super.onMediaButtonEvent(mediaButtonEvent);
    }
}
