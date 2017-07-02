package ren.test.goodlinessmusic.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import ren.test.goodlinessmusic.R;

/**
 * 开屏页
 * Created by Administrator on 2017/06/27
 */

public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission_group.STORAGE}, 1);
        }
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                MusicUtils.getAllMusics(Splash.this);
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
