package ren.test.goodlinessmusic.manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.lang.ref.WeakReference;

import ren.test.goodlinessmusic.R;
import ren.test.goodlinessmusic.service.MusicService;

/**
 * Created by Administrator on 2017/7/13
 */

public class MusicNotificationManager {
    public static  final int NOTIFICATION_ID=230;
    private static MusicNotificationManager manager;
    private Notification notification;
    private MusicService service;

    private MusicNotificationManager(MusicService service){
        WeakReference<MusicService> serviceWeakReference=new WeakReference<MusicService>(service);
        this.service=serviceWeakReference.get();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(service);
        RemoteViews notifactionView = new RemoteViews(service.getPackageName(),
                R.layout.notification_layout);
        // 设置自定义 RemoteViews
        builder.setCustomContentView(notifactionView).setSmallIcon(R.mipmap.icon).setStyle(new NotificationCompat.BigTextStyle());
        notification = builder.build();

        // 如果系统版本 >= Android 4.1，设置大视图 RemoteViews
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification.bigContentView = notifactionView;
        }
            showNotifi();
//        NotificationManager notificationManager = (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(NOTIFICATION_ID, notification);

    }

    public static MusicNotificationManager getInstance(MusicService context){
        return manager==null ? manager=new MusicNotificationManager(context) : manager;
    }

    public void showNotifi(){
        service.startForeground(NOTIFICATION_ID,notification);
    }
    public void stopNotifi(){
        service.stopForeground(false);
    }
}
