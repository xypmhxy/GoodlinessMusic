package ren.test.goodlinessmusic.model;

import ren.test.goodlinessmusic.beans.Music;

/**
 * Created by Administrator on 2017/7/4
 */

public interface IPlayMusic {
    void play(Music music);

    void pause();

    void last();

    void next();
}
