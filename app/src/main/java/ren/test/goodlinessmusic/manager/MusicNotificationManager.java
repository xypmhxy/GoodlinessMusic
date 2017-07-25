package ren.test.goodlinessmusic.manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.ImageView;
import android.widget.RemoteViews;

import java.lang.ref.WeakReference;

import ren.test.goodlinessmusic.R;
import ren.test.goodlinessmusic.activity.MainActivity;
import ren.test.goodlinessmusic.beans.Music;
import ren.test.goodlinessmusic.broadcast.NotifiClickReceive;
import ren.test.goodlinessmusic.impl.IPlayMusicImp;
import ren.test.goodlinessmusic.presenter.PlayMusicPresenter;
import ren.test.goodlinessmusic.service.MusicService;
import ren.test.goodlinessmusic.utils.MusicUtils;
import ren.test.goodlinessmusic.view.IPlayMusicView;

/**
 * Created by Administrator on 2017/7/13
 */

public class MusicNotificationManager implements IPlayMusicView{
    public static  final int NOTIFICATION_ID=230;
    private static MusicNotificationManager manager;
    private Notification notification;
    private MusicService service;
    private PlayMusicPresenter presenter;
    private RemoteViews notifactionView ;

    private MusicNotificationManager(MusicService service){
        WeakReference<MusicService> serviceWeakReference=new WeakReference<MusicService>(service);
        this.service=serviceWeakReference.get();
        initNotifi();
        showNotifi();
        presenter=new PlayMusicPresenter(this,service.getCompat(),service);
        NotifiClickReceive receive=new NotifiClickReceive(presenter);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(NotifiClickReceive.ACTION_PLAY_OR_PAUSE);
        intentFilter.addAction(NotifiClickReceive.ACTION_LAST);
        intentFilter.addAction(NotifiClickReceive.ACTION_NEXT);
        intentFilter.addAction(NotifiClickReceive.ACTION_STOP);
        service.registerReceiver(receive,intentFilter);
    }

    public static MusicNotificationManager getInstance(MusicService context){
        return manager==null ? manager=new MusicNotificationManager(context) : manager;
    }

    private void initNotifi(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(service);
        notifactionView = new RemoteViews(service.getPackageName(),
                R.layout.notification_layout);
        Intent intent = new Intent(service, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(service, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Music recentMusic= MusicUtils.getRecentMusic();
        notifactionView.setTextViewText(R.id.text_tittle_notify , recentMusic.getTittle());
        notifactionView.setTextViewText(R.id.text_singer_notify , recentMusic.getArtist());
        notifactionView.setImageViewUri(R.id.img_head_notify, Uri.parse(recentMusic.getBigPic()));
        notifactionView.setImageViewResource(R.id.img_play_notify , R.drawable.play_white);

        Intent playIntent = new Intent(NotifiClickReceive.ACTION_PLAY_OR_PAUSE);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(service, 1, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notifactionView.setOnClickPendingIntent(R.id.img_play_notify, playPendingIntent);

        Intent lastIntent=new Intent(NotifiClickReceive.ACTION_LAST);
        PendingIntent lastPendingIntent = PendingIntent.getBroadcast(service, 1, lastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notifactionView.setOnClickPendingIntent(R.id.img_last_notify, lastPendingIntent);

        Intent nextIntent=new Intent(NotifiClickReceive.ACTION_NEXT);
        PendingIntent nexPendingIntent = PendingIntent.getBroadcast(service, 1, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notifactionView.setOnClickPendingIntent(R.id.img_next_notify, nexPendingIntent);

        // 设置自定义 RemoteViews
        builder.setCustomContentView(notifactionView)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.icon)
                .setStyle(new NotificationCompat.BigPictureStyle());
        notification = builder.build();
        // 如果系统版本 >= Android 4.1，设置大视图 RemoteViews
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification.bigContentView = notifactionView;
        }
    }

    public void showNotifi(){
        service.startForeground(NOTIFICATION_ID,notification);
    }

    public void stopNotifi(){
        service.stopForeground(false);
    }

    public void stopService(){
        service.stopForeground(true);
        service.stopSelf();
    }

    @Override
    public void onPlay(Music music) {
        notifactionView.setTextViewText(R.id.text_tittle_notify , music.getTittle());
        notifactionView.setTextViewText(R.id.text_singer_notify , music.getArtist());
        notifactionView.setImageViewUri(R.id.img_head_notify, Uri.parse(music.getBigPic()));
        notifactionView.setImageViewResource(R.id.img_play_notify , R.drawable.pause);
        showNotifi();
    }

    @Override
    public void onPause_() {
        notifactionView.setImageViewResource(R.id.img_play_notify , R.drawable.play_white);
        service.startForeground(NOTIFICATION_ID,notification);
        stopNotifi();
    }

    @Override
    public void onLast() {

    }

    @Override
    public void onNext() {

    }
}
