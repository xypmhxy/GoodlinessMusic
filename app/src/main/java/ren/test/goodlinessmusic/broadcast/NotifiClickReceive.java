package ren.test.goodlinessmusic.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ren.test.goodlinessmusic.manager.MusicNotificationManager;
import ren.test.goodlinessmusic.manager.PlayManager;
import ren.test.goodlinessmusic.presenter.PlayMusicPresenter;

/**
 * Created by Administrator on 2017/7/25
 */

public class NotifiClickReceive extends BroadcastReceiver {
    public static final String ACTION_PLAY_OR_PAUSE="playOrPause";
    public static final String ACTION_LAST="last";
    public static final String ACTION_NEXT="next";
    public static final String ACTION_STOP="stop";
    private PlayMusicPresenter presenter;

    public NotifiClickReceive(PlayMusicPresenter playMusicPresenter){
        presenter = playMusicPresenter;
    }

    public NotifiClickReceive(){}
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent==null)
            return ;
        String action=intent.getAction();
        if (ACTION_PLAY_OR_PAUSE.equals(action))
            presenter.play(PlayManager.getInstance(context).getCurrentMusic());
        else if (ACTION_LAST.equals(action))
            presenter.last();
        else if (ACTION_NEXT.equals(action))
            presenter.next();
//        else  if (ACTION_STOP.equals(action))

    }
}
