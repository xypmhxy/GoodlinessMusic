package ren.test.goodlinessmusic.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.media.session.MediaControllerCompat;

import ren.test.goodlinessmusic.beans.Music;
import ren.test.goodlinessmusic.broadcast.MusicStateBroadcast;
import ren.test.goodlinessmusic.impl.IPlayMusicImp;
import ren.test.goodlinessmusic.manager.PlayManager;
import ren.test.goodlinessmusic.view.IPlayMusicView;

/**
 * Created by Administrator on 2017/7/4
 */

public class PlayMusicPresenter {
    private IPlayMusicImp playMusicImp;
    private Context context;
    private MusicStateBroadcast receiver;

    public PlayMusicPresenter(IPlayMusicView playMusicView, MediaControllerCompat mediaControllerCompat, Context context) {
        playMusicImp = new IPlayMusicImp(mediaControllerCompat);
        IntentFilter filter = new IntentFilter();
        filter.addAction(PlayManager.MUSIC_INFO);
         receiver=new MusicStateBroadcast(playMusicView);
        context.registerReceiver(receiver, filter);
        this.context=context;
    }

    public void play(Music music) {
        playMusicImp.play(music);
    }

    public void pause() {
        playMusicImp.pause();
//        playMusicView.onPause_();
    }

    public void last() {
        playMusicImp.last();
//        playMusicView.onLast();
    }

    public void next() {
        playMusicImp.next();
//        playMusicView.onNext();
    }
    public void unregister(){
        context.unregisterReceiver(receiver);
    }
}
