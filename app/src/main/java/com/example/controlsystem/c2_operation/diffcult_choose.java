package com.example.controlsystem.c2_operation;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.controlsystem.R;
import com.example.controlsystem.b1.banner.b1_MainActivity;
import com.example.controlsystem.util.fullscreen;

/**
 * Created by 12969 on 2018/8/11.
 */

public class diffcult_choose extends Activity implements View.OnClickListener {

    private ImageView easy;
    private ImageView ordinary;
    private ImageView difficult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //强制全屏
        if (Build.VERSION.SDK_INT >= 16)  {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        setContentView(R.layout.c2_diffcult_choose);

        easy = findViewById(R.id.easy);
        ordinary = findViewById(R.id.ordinary);
        difficult = findViewById(R.id.difficult);

        easy.setOnClickListener(this);
        ordinary.setOnClickListener(this);
        difficult.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.easy:
                Intent intent = new Intent(diffcult_choose.this,MainActivity.class);
                intent.putExtra("degree",0);
                startActivity(intent);
                break;
            case R.id.ordinary:
                Intent intent2 = new Intent(diffcult_choose.this,MainActivity.class);
                intent2.putExtra("degree",1);
                startActivity(intent2);
                break;
            case R.id.difficult:
                Intent intent3 = new Intent(diffcult_choose.this,MainActivity.class);
                intent3.putExtra("degree",2);
                startActivity(intent3);
                break;
        }
    }
    //返回
    public void back(View view){
        Intent intent = new Intent(diffcult_choose.this,b1_MainActivity.class);
        startActivity(intent);
        finish();
    }

    //全屏
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        fullscreen.NavigationBarStatusBar(this,hasFocus);

    }
}
