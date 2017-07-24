package ren.test.goodlinessmusic.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ren.test.goodlinessmusic.beans.Music;
import ren.test.goodlinessmusic.manager.PlayManager;
import ren.test.goodlinessmusic.view.IPlayMusicView;

/**
 * Created by Administrator on 2017/7/13
 */

public class MusicStateBroadcast extends BroadcastReceiver {

    private IPlayMusicView playMusicView;
    public MusicStateBroadcast(IPlayMusicView playMusicView){
        this.playMusicView=playMusicView;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Music music = (Music) intent.getSerializableExtra(PlayManager.KEY_MUSIC_INFO);
            String action = intent.getAction();
            if (action.equals(PlayManager.ACTION_MUSIC_INFO)){
                int state=intent.getIntExtra(PlayManager.KEY_STATE , 0);
                switch (state){
                    case PlayManager.STATE_PLAING :
                        playMusicView.onPlay(music);
                        break ;
                    case PlayManager.STATE_PAUSE :
                        playMusicView.onPause_();
                        break ;
                }
            }
        }
    }
}
