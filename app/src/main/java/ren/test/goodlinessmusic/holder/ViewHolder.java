package ren.test.goodlinessmusic.holder;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import ren.test.goodlinessmusic.R;


/**
 * Created by Administrator on 2017/07/03
 */

public class ViewHolder {
    private View mConvertView;

    public ViewHolder(View view){
        mConvertView=view;
    }

    private  <T extends View> T getWidget( int id){
        return ButterKnife.findById(mConvertView,id);
    }

    public void setText(int id,String text){
        TextView textView= getWidget(id);
        textView.setText(text);
        textView.setSelected(true);
    }

    public void setImage(int id,int imgId){
        ImageView imageView= getWidget(id);
        imageView.setImageResource(imgId);
    }

    public void setImage(int id,String url){
        ImageView imageView= getWidget(id);
        Picasso.with(mConvertView.getContext()).load(url).placeholder(R.drawable.head).error(R.drawable.head).into(imageView);
    }
}
