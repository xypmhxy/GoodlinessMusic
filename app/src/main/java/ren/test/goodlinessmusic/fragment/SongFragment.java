package ren.test.goodlinessmusic.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ren.test.goodlinessmusic.R;
import ren.test.goodlinessmusic.adapter.SongAdapter;
import ren.test.goodlinessmusic.beans.Music;
import ren.test.goodlinessmusic.manager.PlayManager;
import ren.test.goodlinessmusic.presenter.PlayMusicPresenter;
import ren.test.goodlinessmusic.service.MusicService;
import ren.test.goodlinessmusic.utils.MusicUtils;
import ren.test.goodlinessmusic.view.IPlayMusicView;
import ren.test.goodlinessmusic.widget.SideBar;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * 歌曲列表fragment
 * Created by Administrator on 2017/06/27
 */

public class SongFragment extends Fragment implements AdapterView.OnItemClickListener,IPlayMusicView {
    @BindView(R.id.listview_song)
    public ListView listView;
    @BindView(R.id.sidebar)
    public SideBar sideBar;
    private List<Music> musics;
    private PlayMusicPresenter musicPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("rq"," "+System.currentTimeMillis());
        View view = inflater.inflate(R.layout.framgent_song, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        listView.setOnItemClickListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        musics = MusicUtils.getAllMusics();
        PlayManager.getInstance(getActivity()).setMusicList(musics);
        SongAdapter adapter = new SongAdapter(musics, getActivity());
        listView.setAdapter(adapter);
        sideBar.setListView(listView);
        startService();
        super.onActivityCreated(savedInstanceState);
    }

//    public void initPresenter(){
//        musicPresenter=new PlayMusicPresenter(this,getActivity().getSupportMediaController(),getActivity());
//    }

    private void startService() {
        final FragmentActivity activity=getActivity();
        Intent intent = new Intent(activity, MusicService.class);
        activity.startService(intent);
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
                musicPresenter = new PlayMusicPresenter(SongFragment.this,binder.getController(),getContext());
                Toast.makeText(activity, "绑定服务已经启动 " + binder, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Toast.makeText(activity, "绑定服务已经关闭 " + name, Toast.LENGTH_SHORT).show();
            }
        };
        activity.bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        musicPresenter.play(musics.get(position));
    }

    @Override
    public void onPlay(Music music) {

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

    @Override
    public void onDestroy() {
        musicPresenter.unregister();
        super.onDestroy();
    }
}
