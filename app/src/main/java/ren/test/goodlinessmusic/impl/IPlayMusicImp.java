package ren.test.goodlinessmusic.impl;

import android.os.Bundle;
import android.support.v4.media.session.MediaControllerCompat;

import ren.test.goodlinessmusic.beans.Music;
import ren.test.goodlinessmusic.manager.PlayManager;
import ren.test.goodlinessmusic.model.IPlayMusic;
import ren.test.goodlinessmusic.service.MusicService;

/**
 * Created by Administrator on 2017/7/4
 */

public class IPlayMusicImp implements IPlayMusic {
    private MediaControllerCompat mediaControllerCompat;
    private  PlayManager playManager;


    public IPlayMusicImp(MediaControllerCompat mediaControllerCompat) {
        this.mediaControllerCompat = mediaControllerCompat;
        playManager = PlayManager.getInstance(null);
    }

    @Override
    public void play(Music music) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(MusicService.KEY_MUSIC_BEAN, music);
        Music currentMuisc = playManager.getCurrentMusic();
        if (music.getId() != currentMuisc.getId())
            mediaControllerCompat.getTransportControls().playFromMediaId(music.getId() + "", bundle);
        else if (playManager.isPause())
            mediaControllerCompat.getTransportControls().play();
        else if (!playManager.isPause()){
            if (playManager.isPlaying())
                mediaControllerCompat.getTransportControls().pause();
            else
                mediaControllerCompat.getTransportControls().playFromMediaId(music.getId() + "", bundle);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void last() {
        playManager.last();
    }

    @Override
    public void next() {
        playManager.next();
    }
}
