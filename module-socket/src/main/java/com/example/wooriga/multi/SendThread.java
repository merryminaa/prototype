package com.example.wooriga.multi;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SendThread extends Thread {
    Socket socket = null;
    String username;

    Scanner scanner = new Scanner(System.in);

    public SendThread(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
    }

    @Override
    public void run() {
        try {
            System.out.println(">>>>>> SendThread : " + Thread.currentThread().getName());

            PrintStream out = new PrintStream(socket.getOutputStream());
            out.println(username);
            out.flush();

            while (true) {
                String outputMsg = scanner.nextLine();
                out.println(outputMsg);
                out.flush();
                if (outputMsg.contains("out")) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
