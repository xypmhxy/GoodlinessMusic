package ren.test.goodlinessmusic.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import ren.test.goodlinessmusic.R;

/**
 * Created by Administrator on 2017/07/03
 */

public class SideBar extends View {
    private Paint paint;
    private TextView header;
    private float height;
    private ListView mListView;
    private Context context;

    private SectionIndexer sectionIndex = null;

    public void setListView(ListView listView) {
        mListView = listView;
    }


    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private String[] sections;

    private void init() {
        String st = "搜";
        sections = new String[]{st, "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#838383"));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(sp2px(context, 10));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float center = getWidth() / 2;
        height = getHeight() / sections.length;
        for (int i = sections.length - 1; i > -1; i--) {
            canvas.drawText(sections[i], center, height * (i + 1), paint);
        }
    }

    private int sectionForPoint(float y) {
        int index = (int) (y / height);
        if (index < 0) {
            index = 0;
        }
        if (index > sections.length - 1) {
            index = sections.length - 1;
        }
        return index;
    }

    private void setHeaderTextAndscroll(MotionEvent event) {
        if (mListView == null) {
            //check the mListView to avoid NPE. but the mListView shouldn't be null
            //need to check the call stack later
            return;
        }
        String headerString = sections[sectionForPoint(event.getY())];
        header.setText(headerString);
        ListAdapter adapter = mListView.getAdapter();
        if (sectionIndex == null) {
            if (adapter instanceof HeaderViewListAdapter) {
                sectionIndex = (SectionIndexer) ((HeaderViewListAdapter) adapter).getWrappedAdapter();
            } else if (adapter instanceof SectionIndexer) {
                sectionIndex = (SectionIndexer) adapter;
            } else {
                throw new RuntimeException("listview sets adapter does not implement SectionIndexer interface");
            }
        }
        String[] adapterSections = (String[]) sectionIndex.getSections();
        try {
            for (int i = adapterSections.length - 1; i > -1; i--) {
                if (adapterSections[i].toUpperCase().equals(headerString)) {
                    mListView.setSelection(sectionIndex.getPositionForSection(i));
                    break;
                }
            }
        } catch (Exception e) {
            Log.e("setHeaderTextAndScroll", e.getMessage());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (header == null) {
                    header = (TextView) ((View) getParent()).findViewById(R.id.floating_header);
                }
                setHeaderTextAndscroll(event);
                header.setVisibility(View.VISIBLE);
//                setBackgroundResource(R.color.);
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                setHeaderTextAndscroll(event);
                return true;
            }
            case MotionEvent.ACTION_UP:
                header.setVisibility(View.INVISIBLE);
                setBackgroundColor(Color.TRANSPARENT);
                return true;
            case MotionEvent.ACTION_CANCEL:
                header.setVisibility(View.INVISIBLE);
                setBackgroundColor(Color.TRANSPARENT);
                return true;
        }
        return super.onTouchEvent(event);
    }
    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    private   int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
