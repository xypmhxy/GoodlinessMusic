package ren.test.goodlinessmusic.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ren.test.goodlinessmusic.R;
import ren.test.goodlinessmusic.adapter.SongAdapter;
import ren.test.goodlinessmusic.beans.Music;
import ren.test.goodlinessmusic.holder.ViewHolder;
import ren.test.goodlinessmusic.presenter.PlayMusicPresenter;
import ren.test.goodlinessmusic.utils.MusicUtils;
import ren.test.goodlinessmusic.view.IPlayMusicView;
import ren.test.goodlinessmusic.widget.SideBar;

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
        SongAdapter adapter = new SongAdapter(musics, getActivity());
        listView.setAdapter(adapter);
        sideBar.setListView(listView);
//        musicPresenter = new PlayMusicPresenter(this,null);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        musicPresenter.play();
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
}
