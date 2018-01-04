package com.huoshan.api.huoshan.frist.city;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.adapter.ContentFragmentAdapter;
import com.huoshan.api.huoshan.utils.VerticalViewPager;
import com.huoshan.api.huoshan.video.ContentFragment;

public class CityingActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private VerticalViewPager verticalViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citying);
        initViewPager();
    }
    private void initViewPager() {
        VerticalViewPager viewPager = (VerticalViewPager) findViewById(R.id.vertical_viewpager1);
        //viewPager.setPageTransformer(false, new ZoomOutTransformer());
        //viewPager.setPageTransformer(true, new StackTransformer());
        String title = "ContentFragment";
        viewPager.setAdapter(new ContentFragmentAdapter.Holder(getSupportFragmentManager())
                .add(ContentFragment.newInstance(title, 1))
                .add(ContentFragment.newInstance(title, 2))
                .add(ContentFragment.newInstance(title, 3))
                .add(ContentFragment.newInstance(title, 4))
                .add(ContentFragment.newInstance(title, 5))
                .set());
        //If you setting other scroll mode, the scrolled fade is shown from either side of display.
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }
}
