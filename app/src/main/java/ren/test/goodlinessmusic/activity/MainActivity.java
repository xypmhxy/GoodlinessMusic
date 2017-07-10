package ren.test.goodlinessmusic.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ren.test.goodlinessmusic.R;
import ren.test.goodlinessmusic.adapter.MainPagerAdapter;
import ren.test.goodlinessmusic.beans.Music;
import ren.test.goodlinessmusic.fragment.AlbumFragment;
import ren.test.goodlinessmusic.fragment.SingerFragment;
import ren.test.goodlinessmusic.fragment.SongFragment;
import ren.test.goodlinessmusic.presenter.PlayMusicPresenter;
import ren.test.goodlinessmusic.service.MusicService;
import ren.test.goodlinessmusic.utils.MusicUtils;
import ren.test.goodlinessmusic.view.IPlayMusicView;

public class MainActivity extends AppCompatActivity implements IPlayMusicView {

    @BindView(R.id.viewpager)
    public ViewPager viewPager;
    @BindView(R.id.tab_layout)
    public TabLayout tabLayout;
    @BindView(R.id.text_song_name)
    public TextView songName;
    @BindView(R.id.text_singer)
    public TextView singer;

    private PlayMusicPresenter presenter;
    private MusicService musicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
        tabLayout.setupWithViewPager(viewPager);
        startService();
    }

    /**
     * 初始化viewpager中的fragment
     */
    private void initFragment() {
        List<Fragment> fragments = new ArrayList<>();
        SongFragment songFragment = new SongFragment();
        SingerFragment singerFragment = new SingerFragment();
        AlbumFragment albumFragment = new AlbumFragment();
        fragments.add(songFragment);
        fragments.add(singerFragment);
        fragments.add(albumFragment);
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    private void startService() {
        Intent intent = new Intent(this, MusicService.class);
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
                musicService = binder.getService();
                presenter = new PlayMusicPresenter(MainActivity.this, musicService);
                Toast.makeText(MainActivity.this, "绑定服务已经启动 " + musicService, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Toast.makeText(MainActivity.this, "绑定服务已经关闭 " + musicService, Toast.LENGTH_SHORT).show();
            }
        };
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    /**
     * 点击返回键退出到后台运行
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.image_play)
    public void onClick(View view) {
        presenter.play(MusicUtils.getAllMusics().get(0));
    }

    @Override
    public void onPlay(Music music) {
        songName.setText(music.getTittle());
        singer.setText(music.getArtist());
        Toast.makeText(this, music.getTittle() + " 开始播放", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause_() {

    }

    @Override
    public void onLast() {

    }

    @Override
    public void onNext() {

    }
}
