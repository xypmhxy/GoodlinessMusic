package ren.test.goodlinessmusic.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import ren.test.goodlinessmusic.holder.ViewHolder;

/**
 * 通用适配器基类
 * @author 漆可
 * @date 2016-6-28 下午4:23:51
 * @param <T>
 */
public abstract class BaseCommAdapter<T> extends BaseAdapter
{

    private List<T> mDatas;
    private LayoutInflater inflater;

    public BaseCommAdapter(List<T> datas,Context context)
    {
        mDatas = datas;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView=inflater.inflate(getLayoutId(),null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else
            holder= (ViewHolder) convertView.getTag();
        setUI(holder,position,parent.getContext());

        return convertView;
    }

    /**
     * 设置UI
     */
    protected abstract void setUI(ViewHolder holder, int position, Context context);

    /**
     * 获取适配器的布局id
     */
    protected abstract int getLayoutId();
}
