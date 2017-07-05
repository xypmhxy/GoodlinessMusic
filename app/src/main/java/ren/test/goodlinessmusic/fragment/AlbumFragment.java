package ren.test.goodlinessmusic.fragment;

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
import ren.test.goodlinessmusic.adapter.AlbumAdapter;
import ren.test.goodlinessmusic.adapter.SingerAdapter;
import ren.test.goodlinessmusic.beans.Album;
import ren.test.goodlinessmusic.beans.Artist;
import ren.test.goodlinessmusic.utils.MusicUtils;
import ren.test.goodlinessmusic.widget.SideBar;

/**
 * Created by Administrator on 2017/07/05
 */

public class AlbumFragment extends Fragment {
    @BindView(R.id.listview_song)
    public ListView listView;
    @BindView(R.id.sidebar)
    public SideBar sideBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framgent_song, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        List<Album> albumList = MusicUtils.getAlbumList();
        AlbumAdapter adapter = new AlbumAdapter(albumList, getActivity());
        listView.setAdapter(adapter);
        sideBar.setListView(listView);
        super.onActivityCreated(savedInstanceState);
    }
}
