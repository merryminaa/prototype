package com.example.wooriga.multi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {

    public static void main(String[] args) {
        MultiServer multiServer = new MultiServer();
        multiServer.start();
    }

    private void start() {
        Socket socket = null;
        ServerSocket serverSocket = null;

        try {
            // 1. 서버 소켓 생성
            serverSocket = connect();

            while (true) {
                // 2. 서버 연결
                assert false;
                socket = serverSocket.accept();
                System.out.println("===== connection successful =====");

                // 3. client가 접속할때마다 새로운 스레드 생성
                // @TODO: 스레드 갯수 특정하는 방식
                ReceiveThread receiveThread = new ReceiveThread(socket);
                receiveThread.start();
            }

        } catch (IOException ioException) {
            System.out.println("error");
        } finally {
            try {
                // 4. 접속종료
                serverSocket.close();
                System.out.println("===== connection ended =====");
            } catch (IOException e) {
                System.out.println("error");
            }
        }
    }

    private static ServerSocket connect() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8888);
        } catch (IOException ioException) {
            System.out.println("port already opened");
        }
        return serverSocket;
    }

}
