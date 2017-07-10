package ren.test.goodlinessmusic.view;

import ren.test.goodlinessmusic.beans.Music;

/**
 * Created by Administrator on 2017/7/4
 */

public interface IPlayMusicView {
    void onPlay(Music music);

    void onPause_();

    void onLast();

    void onNext();
}
