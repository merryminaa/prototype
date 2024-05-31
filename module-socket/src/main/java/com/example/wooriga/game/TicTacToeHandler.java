package com.example.wooriga.game;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class TicTacToeHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> sessions = new ArrayList<>();
    private final AtomicBoolean isXTurn = new AtomicBoolean(true);
    private final char[][] board = new char[3][3];
    private int gameCount = 0; // 게임판 수 추적 변수

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (sessions.size() < 2) {
            sessions.add(session);
            String player = sessions.size() == 1 ? "X" : "O";
            session.sendMessage(new TextMessage("{\"type\":\"playerAssignment\", \"player\":\"" + player + "\"}"));
            System.out.println("Player " + player + " connected: " + session.getId());
        } else {
            session.sendMessage(new TextMessage("{\"type\":\"error\", \"message\":\"Game is full.\"}"));
            session.close();
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        if (payload.contains("\"type\":\"reset\"")) {
            resetGame();
        } else {
            String[] parts = payload.replace("{", "").replace("}", "").replace("\"", "").split(",");
            int row = Integer.parseInt(parts[1].split(":")[1].trim());
            int col = Integer.parseInt(parts[2].split(":")[1].trim());
            String player = parts[3].split(":")[1].trim();

            char currentPlayer = isXTurn.get() ? 'X' : 'O';

            if (!player.equals(String.valueOf(currentPlayer))) {
                session.sendMessage(new TextMessage("{\"type\":\"error\", \"message\":\"Not your turn.\"}"));
                return;
            }

            board[row][col] = currentPlayer;

            String messageWithPlayer = payload.replace("}", ",\"player\":\"" + currentPlayer + "\"}");
            sendToAll(messageWithPlayer);

            if (checkWinner(currentPlayer)) {
                gameCount++; // 승리자가 나올 때마다 게임판 수 증가
                for (WebSocketSession s : sessions) {
                    if (s.isOpen()) {
                        String resultMessage = s.getId().equals(session.getId())
                            ? String.format("{\"type\":\"win\", \"result\":\"YOU WIN\", \"winner\":\"%c\", \"gameCount\":%d}", currentPlayer, gameCount)
                            : String.format("{\"type\":\"win\", \"result\":\"YOU LOSE\", \"winner\":\"%c\", \"gameCount\":%d}", currentPlayer, gameCount);
                        s.sendMessage(new TextMessage(resultMessage));
                    }
                }
            } else {
                isXTurn.set(!isXTurn.get());
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Connection closed with session: " + session.getId());
    }

    private void resetGame() throws Exception {
        isXTurn.set(true); // Reset turn to X
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '\0';
            }
        }
        sendToAll("{\"type\":\"reset\", \"gameCount\":" + gameCount + "}");
    }

    private void sendToAll(String message) throws Exception {
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(message));
            }
        }
    }

    private boolean checkWinner(char player) {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }
        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
            (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
            return true;
        }
        return false;
    }
}