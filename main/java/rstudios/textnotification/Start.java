package rstudios.textnotification;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Start  extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        setContentView(R.layout.home);
        Handler mHandler=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message message){
                System.out.println("Heyaknfdla");
                if(message.what==0) {
                    System.out.println("Test");
                    Toast.makeText(getApplicationContext(), "Unable to connect to server!", Toast.LENGTH_LONG).show();
                }
            }
        };

/*        new Thread(new Runnable() {
            @Override
            public void run() {
                NetworkConnection.getInstance(getApplicationContext()).sendMessage("Text","Hello Sam");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //NetworkConnection.getInstance(getApplicationContext()).sendMessage("Call","From Dad");
            }
        }).start();*/





        super.onCreate(savedInstanceState);
    }
}
