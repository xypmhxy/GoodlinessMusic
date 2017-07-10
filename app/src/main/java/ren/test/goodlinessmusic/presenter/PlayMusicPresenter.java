package ren.test.goodlinessmusic.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import ren.test.goodlinessmusic.beans.Music;
import ren.test.goodlinessmusic.impl.IPlayMusicImp;
import ren.test.goodlinessmusic.manager.PlayManager;
import ren.test.goodlinessmusic.service.MusicService;
import ren.test.goodlinessmusic.view.IPlayMusicView;

/**
 * Created by Administrator on 2017/7/4
 */

public class PlayMusicPresenter {
    private IPlayMusicView playMusicView;
    private IPlayMusicImp playMusicImp;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                Music music = (Music) intent.getSerializableExtra("music");
                String action = intent.getAction();
                if (action.equals(PlayManager.MUSIC_INFO))
                    playMusicView.onPlay(music);
            }
        }
    };

    public PlayMusicPresenter(IPlayMusicView playMusicView, MusicService service) {
        this.playMusicView = playMusicView;
        playMusicImp = new IPlayMusicImp(service);
        IntentFilter filter = new IntentFilter();
        filter.addAction(PlayManager.MUSIC_INFO);
        service.getApplicationContext().registerReceiver(receiver, filter);
    }

    public void play(Music music) {
        playMusicImp.play(music);
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
