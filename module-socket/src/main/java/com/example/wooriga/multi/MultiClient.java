package com.example.wooriga.multi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiClient {

    public static void main(String[] args) {
        MultiClient multiClient = new MultiClient();
        multiClient.start();
    }

    private void start() {
        Socket socket = null;
        BufferedReader in = null;

        try {
            // 1. 소켓 연결
            socket = connect();

            // 2. 새로운 스레드 생성
            String username = "user" + (int)(Math.random() * 10);
            Thread sendThread = new SendThread(socket, username);
            sendThread.start();

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String inputMsg = in.readLine();
                System.out.println("from : " + inputMsg);
                if (inputMsg.contains("out")) break;
            }

        } catch (IOException ioException) {
            System.out.println("error");
        } finally {
            try {
                // 4. 접속종료
                socket.close();
                System.out.println("===== connection ended =====");
            } catch (IOException e) {
                System.out.println("error");
            }
        }
    }

    private Socket connect() {
        Socket socket = null;
        try {
            socket = new Socket(InetAddress.getLocalHost(), 8888);
        } catch (IOException e) {
            System.out.println("cannot connect server");
        }
        return socket;
    }

}
