package ren.test.goodlinessmusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ren.test.goodlinessmusic.R;
import ren.test.goodlinessmusic.beans.Music;
import ren.test.goodlinessmusic.holder.ViewHolder;

/**
 * Created by Administrator on 2017/7/11.
 */

public class TempAdapter extends BaseAdapter {

    private List<Music> mDatas;
    private LayoutInflater inflater;
    public TempAdapter(List<Music> datas,Context context)
    {
        mDatas = datas;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Music getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Music music=getItem(position);
        if (convertView==null){
            convertView=inflater.inflate(R.layout.song_listview_item,null);
            holder = new ViewHolder();
            holder.tittle= (TextView) convertView.findViewById(R.id.song_item_name);
            holder.singer= (TextView) convertView.findViewById(R.id.song_item_singer);
            holder.head= (ImageView) convertView.findViewById(R.id.song_item_head);
            convertView.setTag(holder);
        }else
            holder= (ViewHolder) convertView.getTag();
        holder.tittle.setText(music.getTittle());
        holder.singer.setText(music.getArtist());
        Picasso.with(convertView.getContext()).load(music.getBigPic()).placeholder(R.drawable.head).error(R.drawable.head).into(holder.head);
        return convertView;
    }
    class ViewHolder{
        TextView tittle,singer;
        ImageView head;
    }
}
