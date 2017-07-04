package ren.test.goodlinessmusic.adapter;

import android.content.Context;
import android.util.SparseIntArray;
import android.widget.SectionIndexer;

import java.util.ArrayList;
import java.util.List;

import ren.test.goodlinessmusic.R;
import ren.test.goodlinessmusic.beans.Music;
import ren.test.goodlinessmusic.holder.ViewHolder;

/**
 * Created by Administrator on 2017/07/03
 */

public class SingerAdapter extends BaseCommAdapter<List<Music>> implements SectionIndexer {

    private SparseIntArray positionOfSection;
    private SparseIntArray sectionOfPosition;
    private List<String> stringList;
    private String[] strs;

    public SingerAdapter(List<List<Music>> datas, Context context) {
        super(datas, context);
        positionOfSection = new SparseIntArray();
        sectionOfPosition = new SparseIntArray();
        positionOfSection.put(0, 0);
        sectionOfPosition.put(0, 0);
        stringList = new ArrayList<String>();
        stringList.add("搜");
    }

    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        holder.setText(R.id.song_item_name, getItem(position).get(0).getArtist());
        holder.setText(R.id.song_item_singer, getItem(position).size() + " 首");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.song_listview_item;
    }

    @Override
    public String[] getSections() {
        if (strs == null)
            strs = new String[stringList.size()];
        else
            return stringList.toArray(strs);
        int count = getCount();
        for (int i = 1; i < count; i++) {
            String letter = getItem(i).get(0).getInitialLetter();
            int section = stringList.size() - 1;
            if (stringList.get(section) != null && !stringList.get(section).equals(letter)) {
                stringList.add(letter);
                section++;
                positionOfSection.put(section, i);
            }
            sectionOfPosition.put(i, section);
        }
        return stringList.toArray(strs);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return positionOfSection.get(sectionIndex);
    }

    @Override
    public int getSectionForPosition(int position) {
        return sectionOfPosition.get(position);
    }
}
