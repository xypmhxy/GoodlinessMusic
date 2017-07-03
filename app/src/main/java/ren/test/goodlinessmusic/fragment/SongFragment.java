package ren.test.goodlinessmusic.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ren.test.goodlinessmusic.R;
import ren.test.goodlinessmusic.adapter.SongAdapter;
import ren.test.goodlinessmusic.beans.Music;
import ren.test.goodlinessmusic.holder.ViewHolder;
import ren.test.goodlinessmusic.utils.MusicUtils;
import ren.test.goodlinessmusic.view.SideBar;

/**
 * 歌曲列表fragment
 * Created by Administrator on 2017/06/27
 */

public class SongFragment extends Fragment {
    @BindView(R.id.listview_song)
    public ListView listView;
    @BindView(R.id.sidebar)
    public SideBar sideBar;
    private List<Music> musics;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.framgent_song, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        musics = MusicUtils.getAllMusics(getActivity());
        SongAdapter adapter = new SongAdapter(musics, getActivity()) {
            @Override
            protected void setUI(ViewHolder holder, int position, Context context) {
                holder.setText(R.id.song_item_name, getItem(position).getTittle());
                holder.setText(R.id.song_item_singer, getItem(position).getArtist());
            }

            @Override
            protected int getLayoutId() {
                return R.layout.song_listview_item;
            }
        };
        listView.setAdapter(adapter);
        sideBar.setListView(listView);
        super.onActivityCreated(savedInstanceState);
    }
}
