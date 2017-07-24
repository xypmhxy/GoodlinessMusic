package ren.test.goodlinessmusic.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;

import ren.test.goodlinessmusic.R;
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
            Music music = (Music) intent.getSerializableExtra("music");
            String action = intent.getAction();
            if (action.equals(PlayManager.MUSIC_INFO))
                playMusicView.onPlay(music);
        }
    }
}
