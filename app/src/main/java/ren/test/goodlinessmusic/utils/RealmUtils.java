package ren.test.goodlinessmusic.utils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import ren.test.goodlinessmusic.beans.Music;

/**
 * Created by Administrator on 2017/7/4
 */

public class RealmUtils {
    private static final RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();

    public static Realm getDefaultRealm() {
        return Realm.getInstance(realmConfig);
    }

    public static List<Music> query(String colunm, String condition, String sortFiledName) {
        Realm realm = getDefaultRealm();
        RealmQuery<Music> query = realm.where(Music.class);
        RealmResults<Music> result = query.equalTo(colunm, condition).findAll();
        List<Music> musics = realm.copyFromRealm(result);
        realm.close();
        return musics;
    }
}
