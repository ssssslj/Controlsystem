package com.example.controlsystem.util;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by 12969 on 2018/8/12.
 */

public class SendMessage {

    private static BluetoothSocket _socket;

    public static void set_socket(BluetoothSocket socket){
        _socket = socket;
    }
    //发送数据
    public static void send(String com) {
        int i=0;
        int n=0;
        try{
            OutputStream os = null;
            if (_socket != null) {
                os = _socket.getOutputStream();   //蓝牙连接输出流
            }else {
                return;
            }
            byte[] bos = com.getBytes();
            for(i=0;i<bos.length;i++){
                if(bos[i]==0x0a)n++;
            }
            byte[] bos_new = new byte[bos.length+n];
            n=0;
            for(i=0;i<bos.length;i++){ //手机中换行为0a,将其改为0d 0a后再发送
                if(bos[i]==0x0a){
                    bos_new[n]=0x0d;
                    n++;
                    bos_new[n]=0x0a;
                }else{
                    bos_new[n]=bos[i];
                }
                n++;
            }

            os.write(bos_new);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
