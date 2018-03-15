package com.company;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws AWTException, InterruptedException, IOException {
        ServerSocket server = new ServerSocket(18000);
        while (true) {
            Socket socket = null;
            // PrintWriter out = null;
            BufferedReader in = null;
            try {
                socket = server.accept();
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("Started");

                String type = in.readLine();
                String content = in.readLine();

                System.out.println("Received: " + type);

                if (type.equals("TEXT")) {
                    Notifications notifications = new Notifications();
                    notifications.sendNotification("New Text!", content);
                    System.out.println("Text Alert Ran\n");

                } else {
                    Runtime rt = Runtime.getRuntime();
                    rt.exec("\"D:\\Programs2\\VLC\\vlc.exe\" -LZ E:\\Text_Alert\\complex.mp3");

                    Robot robot = new Robot();
                    robot.keyPress(KeyEvent.VK_WINDOWS);
                    robot.keyPress(KeyEvent.VK_D);
                    robot.keyRelease(KeyEvent.VK_WINDOWS);
                    robot.keyRelease(KeyEvent.VK_D);

                    Notifications notifications = new Notifications();
                    notifications.sendNotification("Incoming Call!", content);
                    System.out.println("Call Alert Ran!\n");
                }
                System.out.println("Closing Down!\n");
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(1000);
        }
    }
}

class Notifications {
    void sendNotification(String title, String content) throws AWTException {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
            TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("System tray icon demo");
            tray.add(trayIcon);

            trayIcon.displayMessage(title, content, TrayIcon.MessageType.INFO);

        }
    }
}

