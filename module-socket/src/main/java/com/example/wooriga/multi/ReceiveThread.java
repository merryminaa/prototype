package com.example.wooriga.multi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReceiveThread extends Thread {

    static List<PrintWriter> list = Collections.synchronizedList(new ArrayList<PrintWriter>());

    Socket socket = null;
    BufferedReader in = null;
    PrintWriter out = null;


    public ReceiveThread(Socket socket) {
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            list.add(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println(">>>>>> ReceiveThread : " + Thread.currentThread().getName());

        String name = "";

        try {
            name = in.readLine();
            sendAll("< " + name + " >" + " in");

            while (true) {
                String inputMsg = in.readLine();
                if (inputMsg.contains("out")) break;
                sendAll(name + " : " + inputMsg);

            }
        } catch (IOException ioException) {
            System.out.println(name + " connection error");
        } finally {
            sendAll("< " + name + " >" + " out");
            list.remove(out);

            try {
                // 6. 접속종료
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                System.out.println("error");
            }

        }
    }

    private void sendAll (String s) {
        for (PrintWriter out: list) {
            out.println(s);
            out.flush();
        }
    }
}
