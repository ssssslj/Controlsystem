package com.example.controlsystem.c2_operation;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.controlsystem.R;
import com.example.controlsystem.util.SendMessage;
import com.example.controlsystem.util.fullscreen;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends Activity {

    private final static int REQUEST_CONNECT_DEVICE = 1;    //宏定义查询设备句柄
    private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";   //SPP服务UUID号
    private WheelView wheelView_01;
    private SeekBar seekBar_01;
    private SeekBar seekBar_02;
    private SeekBar seekBar_03;
    private SeekBar seekBar_04;
    private ImageView action1;
    private ImageView action2;
    private ImageView action3;

    private TextView speed;
    private int degree;

    BluetoothDevice _device = null;	//蓝牙设备
    BluetoothSocket _socket = null;	//蓝牙通信socket
    boolean bRun = false;

    private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();	//获取本地蓝牙适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //强制全屏
        if (Build.VERSION.SDK_INT >= 16)  {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
        degree = getIntent().getIntExtra("degree",0);
        Log.i("000000000000000000000",degree+"");
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        setContentView(R.layout.c2_activity_main_difficult);

        initView();
        initSeekBarView();
        BlueTooth_Connection();
    }

    private void initSeekBarView() {
        seekBar_01 = findViewById(R.id.seekbar_01);
        seekBar_02 = findViewById(R.id.seekbar_02);
        seekBar_03 = findViewById(R.id.seekbar_03);
        seekBar_04 = findViewById(R.id.seekbar_04);
        if (degree != 0){
            seekBar_01.setVisibility(View.VISIBLE);
            seekBar_02.setVisibility(View.VISIBLE);
            seekBar_03.setVisibility(View.INVISIBLE);
            seekBar_04.setVisibility(View.INVISIBLE);


            seekBar_01.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    SendMessage.send("process1"+i);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            seekBar_02.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    SendMessage.send("process2"+i);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            if(degree == 2){

                seekBar_01.setVisibility(View.VISIBLE);
                seekBar_02.setVisibility(View.VISIBLE);
                seekBar_03.setVisibility(View.VISIBLE);
                seekBar_04.setVisibility(View.VISIBLE);

                seekBar_03.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        SendMessage.send("process3"+i);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                seekBar_04.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        SendMessage.send("process4"+i);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
            }

        }else {
            seekBar_01.setVisibility(View.INVISIBLE);
            seekBar_02.setVisibility(View.INVISIBLE);
            seekBar_03.setVisibility(View.INVISIBLE);
            seekBar_04.setVisibility(View.INVISIBLE);
        }

    }

    private void BlueTooth_Connection() {
        //如果打开本地懒啊设备不成功，提示信息，结束程序
        if(_bluetooth == null) {
            Toast.makeText(this,"打开本地蓝牙失败，确定手机是否有蓝牙功能",Toast.LENGTH_LONG).show();

            return ;
        }

        //设置设备可以被搜索
        new Thread() {
            @Override
            public void run() {
                if(_bluetooth.isEnabled() == false)
                    _bluetooth.enable();
            }
        }.start();
    }

    /**
     * 初始化视图组件
     */
    private void initView() {

        action1 = findViewById(R.id.action1);
        action2 = findViewById(R.id.action2);
        action3 = findViewById(R.id.action3);

        action1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               SendMessage.send(""+1);
            }
        });
        action2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessage.send(""+2);
            }
        });
        action3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessage.send(""+3);
            }
        });

        wheelView_01 = findViewById(R.id.wheelView_01);

        speed = findViewById(R.id.speed);

                wheelView_01.setOnWheelViewMoveListener(
                        new WheelView.OnWheelViewMoveListener() {
                            @Override
                            public void onValueChanged(int angle, int distance) {
                                SendMessage.send(angle+ "++"+distance+"   ");
                                speed.setText("速度"+distance);
                            }
                        }, 100L);


    }

    public void onConnectButtonClicked(View v){
        if(_bluetooth.isEnabled()==false){  //如果蓝牙服务不可用则提示
            Toast.makeText(this, " 打开蓝牙中...", Toast.LENGTH_LONG).show();
            return;
        }


        //如未连接设备则打开DeviceListActivity进行设备搜索
//        Button btn = (Button) findViewById(R.id.connect);
        if(_socket==null){
            Intent serverIntent = new Intent(this, DeviceListActivity.class); //跳转程序设置
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);  //设置返回宏定义
        }
        else{
            //关闭连接socket
            try{

                _socket.close();
                _socket = null;
                bRun = false;
//                btn.setText("连接");
            }catch(IOException e){}
        }
        return;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case REQUEST_CONNECT_DEVICE:     //连接结果，由DeviceListActivity设置返回
                // 响应返回结果
                if (resultCode == Activity.RESULT_OK) {   //连接成功，由DeviceListActivity设置返回
                    // MAC地址，由DeviceListActivity设置返回
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // 得到蓝牙设备句柄
                    _device = _bluetooth.getRemoteDevice(address);

                    // 用服务号得到socket
                    try{
                        _socket = _device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
                    }catch(IOException e){
                        Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                    }
                    //连接socket
//                    Button btn = (Button) findViewById(R.id.connect);
                    try{
                        _socket.connect();
                        Toast.makeText(this, "连接"+_device.getName()+"成功！", Toast.LENGTH_SHORT).show();
//                        btn.setText("断开");
                    }catch(IOException e){
                        try{
                            Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                            _socket.close();
                            _socket = null;
                        }catch(IOException ee){
                            Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                        }

                        return;
                    }finally{
                        SendMessage.set_socket(_socket);
                    }
                }
                break;
            default:break;
        }
    }


    //关闭程序掉用处理部分
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(_socket!=null)  //关闭连接socket
            try{
                _socket.close();
            }catch(IOException e){}
        //	_bluetooth.disable();  //关闭蓝牙服务
    }

    //重力感应
    public void gravity(View view){
        Intent intent = new Intent(MainActivity.this,Gravity.class);
        startActivity(intent);
    }

    //返回
    public void back(View view){
        Intent intent = new Intent(MainActivity.this,diffcult_choose.class);
        startActivity(intent);
        finish();
    }

    //全屏
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        fullscreen.NavigationBarStatusBar(this,hasFocus);

    }
}
