package ren.test.goodlinessmusic.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Administrator on 2017/7/4
 */

public class MusicApplication extends Application {
    @Override
    public void onCreate() {
        Realm.init(this);
        super.onCreate();
    }
}
