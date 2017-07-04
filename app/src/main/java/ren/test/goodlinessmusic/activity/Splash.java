package ren.test.goodlinessmusic.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ren.test.goodlinessmusic.R;
import ren.test.goodlinessmusic.utils.MusicUtils;

/**
 * 开屏页
 * Created by Administrator on 2017/06/27
 */

public class Splash extends Activity {
    private static final int STORAGE_PERMISSION_CODE = 230;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {    //判断是否有权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);//没有权限则请求权限
        } else {
            //拥有权限延迟1秒后跳转
            final Timer timer = new Timer("intent");
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    getAllMusic();
                }
            }, 1000);
        }
    }

    /**
     * 权限请求回调
     *
     * @param requestCode  请求码
     * @param permissions  请求的权限
     * @param grantResults 是否同意
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            getAllMusic();
        else
            Toast.makeText(this, "您已拒绝获取本地音乐，请在手机中重新打开获取", Toast.LENGTH_SHORT).show();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 获取本地所有音乐并跳转
     */
    private void getAllMusic() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                MusicUtils.loadAllMusic(Splash.this);
                MusicUtils.loadMusicsByArtist(Splash.this);
                intentMainActivity();
            }
        }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }

    /**
     * 跳转到主页面
     */
    private void intentMainActivity() {
        Intent intent = new Intent(Splash.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
