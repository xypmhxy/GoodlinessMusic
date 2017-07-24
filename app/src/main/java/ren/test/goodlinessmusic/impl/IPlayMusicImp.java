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

    public IPlayMusicImp(MediaControllerCompat mediaControllerCompat) {
        this.mediaControllerCompat = mediaControllerCompat;
    }

    @Override
    public void play(Music music) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(MusicService.KEY_MUSIC_BEAN, music);
        PlayManager playManager = PlayManager.getInstance(null);
        Music currentMuisc = playManager.getCurrentMusic();
        if (music.getId() != currentMuisc.getId())
            mediaControllerCompat.getTransportControls().playFromMediaId(music.getId() + "", bundle);
        else if (playManager.isPause())
            mediaControllerCompat.getTransportControls().play();
        else if (!playManager.isPause())
            mediaControllerCompat.getTransportControls().pause();
    }

    @Override
    public void pause() {

    }

    @Override
    public void last() {

    }

    @Override
    public void next() {

    }
}
