package rstudios.textnotification;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.Buffer;

/**
 * Created by samra on 15/03/2018.
 */

public class NetworkConnection {
    private static Context context;

    private boolean correctNetwork() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        System.out.println("Test:   " + wifiInfo.getSSID());
        return wifiInfo.getSSID().equals("\"VM9211366\"");
    }
    public NetworkConnection(String title,String content,Context con){
        context=con;
        if(correctNetwork()){
            System.out.println("Starting");
            SocketAddress address = new InetSocketAddress("192.168.0.17", 18000);
            Socket socket = new Socket();
            //Connect to server with 5 second timeout
            try {
                System.out.println("Connecting to server");
                socket.connect(address, 5000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(title);
                out.println(content);
                out.close();
                socket.close();
                System.out.println("Finished\n");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to send notification to server");
                Handler handler= new Handler(Looper.getMainLooper());
                handler.sendEmptyMessage(0);
            }
        }
    }

   /* private static NetworkConnection INSTANCE = null;
    private static Context context;
    private PrintWriter out;
    private BufferedReader in;
    public static synchronized NetworkConnection getInstance(Context con) {
        if (INSTANCE == null) {
            System.out.println("Creating new instance...");
            context = con;
            INSTANCE = new NetworkConnection();
        }
        System.out.println("Returning instance");
        return INSTANCE;
    }
    private NetworkConnection() {
        if (correctNetwork()) {
            System.out.println("Correct Network");
            start();
        }else{
            System.out.println("Wrong Network");
        }
        INSTANCE = null;
    }

    private void start() {
        System.out.println("Starting");
        SocketAddress address = new InetSocketAddress("192.168.0.17", 18000);
        Socket socket = new Socket();
        //Connect to server with 5 second timeout
        try {
            System.out.println("Connecting to server");
            socket.connect(address, 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println("Sam's phone checking in...");
            System.out.println(in.readLine());
            System.out.println("Finished");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void sendMessage(String title, final String content){
        System.out.println("Sending: "+title+", "+content);
        try {
            out.println(title);
            out.println(content);
            if(in.readLine().equals("Received message")) {
                System.out.println("Done");
            }
        }catch (NullPointerException ex){
            INSTANCE=null;
            System.out.println("Failed to send notification to server");
            Handler handler= new Handler(Looper.getMainLooper());
            handler.sendEmptyMessage(0);
            *//*

            handler.post(new Runnable() {
                @Override
                public void run() {
                    System.out.print("Test");
                    Toast.makeText(context,"Unable to connect to server!",Toast.LENGTH_LONG).show();
                }
            });*//*

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
