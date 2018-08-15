package com.example.controlsystem.b1.banner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.controlsystem.c2_operation.MainActivity;
import com.example.controlsystem.R;
import com.example.controlsystem.c2_operation.diffcult_choose;
import com.example.controlsystem.util.DLog;
import com.example.controlsystem.util.OsUtil;
import com.example.controlsystem.util.fullscreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class b1_MainActivity extends AppCompatActivity implements GalleryRecyclerView.OnItemClickListener {
    public static final String TAG = "MainActivity_TAG";

    private GalleryRecyclerView mRecyclerView;
    private RelativeLayout mContainer;

    private Map<String, Drawable> mTSDraCacheMap = new HashMap<>();
    private static final String KEY_PRE_DRAW = "key_pre_draw";

    private TextView textView;

    /**
     * 获取虚化背景的位置
     */
    private int mLastDraPosition = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getScreenWidth();
        setContentView(R.layout.a1_activity_main);
        textView = findViewById(R.id.btntext);
        DLog.setDebug(false);

        DLog.d(TAG, "b1_MainActivity onCreate()");

        mRecyclerView = findViewById(R.id.rv_list);
        mContainer = findViewById(R.id.rl_container);


        final RecyclerAdapter adapter = new RecyclerAdapter(getApplicationContext(), getDatas());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.initFlingSpeed(90000)                                   // 设置滑动速度（像素/s）
                     .initPageParams(0, 150)     // 设置页边距和左右图片的可见宽度，单位dp
                     .setAnimFactor(0.15f)                                   // 设置切换动画的参数因子
                     .setAnimType(AnimManager.ANIM_BOTTOM_TO_TOP)            // 设置切换动画类型，目前有AnimManager.ANIM_BOTTOM_TO_TOP和目前有AnimManager.ANIM_TOP_TO_BOTTOM
                     .setOnItemClickListener(this);                          // 设置点击事件


       //设置按钮字
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    setBlurImage();
                }
            }
        });
        setBlurImage();
    }

    /**
     * 设置背景高斯模糊
     */
    public void setBlurImage() {

        final int mCurViewPosition = mRecyclerView.getScrolledPosition();

        switch (mCurViewPosition){
            case 0:
                textView.setText("动作库");
                break;
            case 1:
                textView.setText("操作界面");
                break;
            case 2:
                textView.setText("常用设置");
                break;
            case 3:
                textView.setText("敬请期待");
                break;
        }

        mLastDraPosition = mCurViewPosition;

    }


    /***
     * 测试数据
     * @return List<Integer>
     */
    public List<Integer> getDatas() {
        TypedArray ar = getResources().obtainTypedArray(R.array.test_arr);
        final int[] resIds = new int[ar.length()];
        for (int i = 0; i < ar.length(); i++) {
            resIds[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();
        List<Integer> tDatas = new ArrayList<>();
        for (int resId : resIds) {
            tDatas.add(resId);
        }
        return tDatas;
    }

    @Override
    protected void onResume() {
        super.onResume();

        DLog.d(TAG, "b1_MainActivity onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();

        DLog.d(TAG, "b1_MainActivity onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        DLog.d(TAG, "b1_MainActivity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        DLog.d(TAG, "b1_MainActivity onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        DLog.d(TAG, "b1_MainActivity onDestroy()");
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position){
            case 0:
                break;
            case 1:
                Intent intent = new Intent(b1_MainActivity.this,diffcult_choose.class );
                startActivity(intent);
                break;
        }
    }
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        fullscreen.NavigationBarStatusBar(this,hasFocus);

    }

    public void getScreenWidth() {
        WindowManager windowManager =
                (WindowManager) getApplication().getSystemService(Context.
                        WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        if (Build.VERSION.SDK_INT >= 19) {
            // 可能有虚拟按键的情况
            display.getRealSize(outPoint);
        } else {
            // 不可能有虚拟按键
            display.getSize(outPoint);
        }
        int mRealSizeWidth;//手机屏幕真实宽度
        int mRealSizeHeight;//手机屏幕真实高度
        mRealSizeHeight = outPoint.y;
        mRealSizeWidth = outPoint.x;

        OsUtil.setScreenWidth(mRealSizeWidth);
    }
}
