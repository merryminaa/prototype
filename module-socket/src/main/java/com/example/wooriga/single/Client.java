package com.example.wooriga.single;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader input = null;
        PrintWriter output = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // 1. 클라이언트 소켓 생성 및 서버 접속
            socket = new Socket(InetAddress.getLocalHost(), 7777);

            // 2. 네트워크 입출력스트림 생성
            input = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 입력스트림 생성
            output =new PrintWriter(socket.getOutputStream()); // 출력스트림 생성

            System.out.println("===== connection started =====");
            System.out.println(socket.toString());

            while (true) {
                // 3. 데이터 전송 (클라이언트 -> 서버)
                System.out.print("to Server : ");
                String outMsg = scanner.nextLine();
                output.println(outMsg);
                output.flush();

                // 4. 데이터 수신 (서버 -> 클라이언트)
                String inMsg = input.readLine(); // 서버로부터 되돌아오는 데이터를 읽어들임
                System.out.println("From Server : " + inMsg);
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                // 6. 접속종료
                scanner.close();
                output.close();
                input.close();
                socket.close();
                System.out.println("===== connection ended =====");
            } catch (IOException e) {
                System.out.println("error");
            }
        }
    }

}
