package com.example.wooriga.single;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        Socket socket = null;
        ServerSocket serverSocket = null;
        BufferedReader input = null;
        PrintWriter output = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // 1. 서버 소켓 생성
            try {
                serverSocket = new ServerSocket(7777);
            } catch (IOException ioException) {
                System.out.println("port already opened");
            }

            // 2. 서버 연결
            assert serverSocket != null;
            socket = serverSocket.accept();
            System.out.println("===== server connected =====");

            // 3. 네트워크 입출력스트림 생성
            input = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 입력스트림 생성
            output =new PrintWriter(socket.getOutputStream()); // 출력스트림 생성

            while (true) {
                // 4. 데이터 수신 (서버 <- 클라이언트)
                String inputMsg = input.readLine(); // 한줄씩 읽기
                System.out.println("from Client : " + inputMsg);

                // 5. 데이터 전송 (서버 -> 클라이언트)
                System.out.print("to Server : ");
                String outMsg = scanner.nextLine();
                output.println(outMsg);
                output.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                // 6. 접속종료
                scanner.close();
                output.close();
                input.close();
                socket.close();
                serverSocket.close();
                System.out.println("===== connection ended =====");
            } catch (IOException e) {
                System.out.println("error");
            }
        }
    }

}
