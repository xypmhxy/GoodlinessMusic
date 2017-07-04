package ren.test.goodlinessmusic.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ren.test.goodlinessmusic.R;
import ren.test.goodlinessmusic.adapter.MainPagerAdapter;
import ren.test.goodlinessmusic.fragment.SingerFragment;
import ren.test.goodlinessmusic.fragment.SongFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    public ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 初始化viewpager中的fragment
     */
    private void initFragment() {
        List<Fragment> fragments = new ArrayList<>();
        SongFragment songFragment = new SongFragment();
        fragments.add(songFragment);
        SingerFragment songFragment1 = new SingerFragment();
        fragments.add(songFragment1);
        SongFragment songFragment2 = new SongFragment();
        fragments.add(songFragment2);
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    /**
     * 点击返回键退出到后台运行
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
