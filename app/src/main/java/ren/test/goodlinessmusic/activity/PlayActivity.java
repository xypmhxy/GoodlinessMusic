package ren.test.goodlinessmusic.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ren.test.goodlinessmusic.R;
import ren.test.goodlinessmusic.beans.Music;
import ren.test.goodlinessmusic.manager.HandleManager;
import ren.test.goodlinessmusic.manager.PlayManager;
import ren.test.goodlinessmusic.presenter.PlayMusicPresenter;
import ren.test.goodlinessmusic.service.MusicService;
import ren.test.goodlinessmusic.view.IPlayMusicView;

/**
 * Created by Administrator on 2017/07/25 0025
 */

public class PlayActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,IPlayMusicView,HandleManager.onHandleMessageListener {

    @BindView(R.id.img_head_play)
    public ImageView headImage;
    @BindView(R.id.text_song_play)
    public TextView song;
    @BindView(R.id.text_singer_play)
    public TextView singer;
    @BindView(R.id.seekbar_play)
    public SeekBar seekBar;
    @BindView(R.id.text_start_time_play)
    TextView startTimeView;
    @BindView(R.id.text_end_time_play)
    TextView endTimeView;
    @BindView(R.id.img_pause_play)
    ImageView playOrPause;

    private Music music;
    private PlayMusicPresenter presenter;
    private HandleManager handleManager;
    private SimpleDateFormat format;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        music= PlayManager.getInstance(this).getCurrentMusic();
        if (music==null)
            finish();

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.BLACK);
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        bindService();
        initData();
        super.onCreate(savedInstanceState);
    }

    private void bindService(){
        Intent intent = new Intent(this, MusicService.class);
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
                setSupportMediaController(binder.getController());
                presenter = new PlayMusicPresenter(PlayActivity.this, getSupportMediaController(), PlayActivity.this);
//                Toast.makeText(PlayActivity.this, "PlayActivity绑定服务已经启动 " + binder, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Toast.makeText(PlayActivity.this, "PlayActivity绑定服务已经关闭 " + name, Toast.LENGTH_SHORT).show();
            }
        };
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    private void initData(){
        format=new SimpleDateFormat("mm:ss", Locale.CHINA);
        Picasso.with(this).load(music.getBigPic()).placeholder(R.drawable.big_head).error(R.drawable.big_head).into(headImage);
        song.setText(music.getTittle());
        singer.setText(music.getArtist());
        if (PlayManager.getInstance(this).isPlaying())
            playOrPause.setImageResource(R.drawable.pause_white);
        else
            playOrPause.setImageResource(R.drawable.play_white);
        seekBar.setOnSeekBarChangeListener(this);
        handleManager=HandleManager.getInstance();
        handleManager.setOnHandleMessageListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser)
            getSupportMediaController().getTransportControls().seekTo(progress);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @OnClick({R.id.img_last_play,R.id.img_pause_play,R.id.img_nex_play})
    public void onClick(View view){
        if (view.getId()==R.id.img_last_play)
            presenter.last();
        else if (view.getId()==R.id.img_pause_play)
            presenter.play(PlayManager.getInstance(this).getCurrentMusic());
        else if (view.getId()==R.id.img_nex_play)
            presenter.next();

    }

    @Override
    public void onPlay(Music music) {
        Picasso.with(this).load(music.getBigPic()).placeholder(R.drawable.big_head).error(R.drawable.big_head).into(headImage);
        song.setText(music.getTittle());
        singer.setText(music.getArtist());
        playOrPause.setImageResource(R.drawable.pause_white);
    }

    @Override
    public void onPause_() {
        playOrPause.setImageResource(R.drawable.play_white);
    }

    @Override
    public void onLast() {

    }

    @Override
    public void onNext() {

    }

    @Override
    public void onHandleMessage(Message msg) {
        String startTime=format.format(msg.arg1);
        String endTime=format.format(msg.arg2);
        startTimeView.setText(startTime);
        endTimeView.setText(endTime);
        seekBar.setMax(msg.arg2);
        seekBar.setProgress(msg.arg1);
    }
}
