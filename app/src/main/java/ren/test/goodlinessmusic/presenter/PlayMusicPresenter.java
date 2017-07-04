package ren.test.goodlinessmusic.presenter;

import ren.test.goodlinessmusic.impl.IPlayMusicImp;
import ren.test.goodlinessmusic.view.IPlayMusicView;

/**
 * Created by Administrator on 2017/7/4
 */

public class PlayMusicPresenter {
    private IPlayMusicView playMusicView;
    private IPlayMusicImp playMusicImp;

    public PlayMusicPresenter(IPlayMusicView playMusicView) {
        this.playMusicView = playMusicView;
        playMusicImp = new IPlayMusicImp();
    }

    public void play() {
        playMusicImp.play();
        playMusicView.onPlay();
    }

    public void pause() {
        playMusicImp.pause();
        playMusicView.onPause_();
    }

    public void last() {
        playMusicImp.last();
        playMusicView.onLast();
    }

    public void next() {
        playMusicImp.next();
        playMusicView.onNext();
    }
}
