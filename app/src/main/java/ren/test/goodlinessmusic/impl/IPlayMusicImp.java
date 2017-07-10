package ren.test.goodlinessmusic.impl;

import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;

import ren.test.goodlinessmusic.beans.Music;
import ren.test.goodlinessmusic.model.IPlayMusic;
import ren.test.goodlinessmusic.service.MusicService;
import ren.test.goodlinessmusic.utils.MusicUtils;

/**
 * Created by Administrator on 2017/7/4
 */

public class IPlayMusicImp implements IPlayMusic {
    private MusicService service;

    public IPlayMusicImp(MusicService service) {
        this.service = service;

    }

    @Override
    public void play(Music music) {
        service.play(music);
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
