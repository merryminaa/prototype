package com.example.wooriga.game;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class TicTacToeHandler extends TextWebSocketHandler {
    private final Map<WebSocketSession, String> sessionUserMap = new LinkedHashMap<>();
    private final AtomicBoolean isXTurn = new AtomicBoolean(true);
    private final char[][] board = new char[3][3];
    private int gameCount = 0;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 닉네임을 설정하기 전까지는 사용자 목록에 추가하지 않습니다.
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        JsonNode jsonNode = objectMapper.readTree(payload);
        JsonNode typeNode = jsonNode.get("type");

        if (typeNode == null) {
            session.sendMessage(new TextMessage("{\"type\":\"error\", \"message\":\"Invalid message format.\"}"));
            return;
        }

        String type = typeNode.asText();

        if ("nickname".equals(type)) {
            JsonNode nicknameNode = jsonNode.get("nickname");
            if (nicknameNode == null) {
                session.sendMessage(new TextMessage("{\"type\":\"error\", \"message\":\"Nickname is required.\"}"));
                return;
            }
            String nickname = nicknameNode.asText();
            sessionUserMap.put(session, nickname);

            String player = sessionUserMap.size() % 2 == 0 ? "O" : "X";
            ObjectNode playerAssignmentMessage = objectMapper.createObjectNode();
            playerAssignmentMessage.put("type", "playerAssignment");
            playerAssignmentMessage.put("player", player);
            session.sendMessage(new TextMessage(playerAssignmentMessage.toString()));

            sendUserListToAll();
        } else if ("reset".equals(type)) {
            resetGame();
        } else if ("move".equals(type)) {
            JsonNode rowNode = jsonNode.get("row");
            JsonNode colNode = jsonNode.get("col");
            JsonNode playerNode = jsonNode.get("player");

            if (rowNode == null || colNode == null || playerNode == null) {
                session.sendMessage(new TextMessage("{\"type\":\"error\", \"message\":\"Invalid move format.\"}"));
                return;
            }

            int row = rowNode.asInt();
            int col = colNode.asInt();
            String player = playerNode.asText();

            char currentPlayer = isXTurn.get() ? 'X' : 'O';

            if (!player.equals(String.valueOf(currentPlayer))) {
                session.sendMessage(new TextMessage("{\"type\":\"error\", \"message\":\"Not your turn.\"}"));
                return;
            }

            board[row][col] = currentPlayer;

            ObjectNode moveMessage = objectMapper.createObjectNode();
            moveMessage.put("type", "move");
            moveMessage.put("row", row);
            moveMessage.put("col", col);
            moveMessage.put("player", String.valueOf(currentPlayer)); // 여기서 currentPlayer를 문자열로 변환
            sendToAll(moveMessage.toString());

            if (checkWinner(currentPlayer)) {
                gameCount++;
                for (WebSocketSession s : sessionUserMap.keySet()) {
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
        sessionUserMap.remove(session);
        sendUserListToAll();
        System.out.println("Connection closed with session: " + session.getId());
    }

    private void resetGame() {
        isXTurn.set(true);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '\0';
            }
        }
        try {
            sendToAll("{\"type\":\"reset\", \"gameCount\":" + gameCount + "}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendToAll(String message) throws Exception {
        for (WebSocketSession s : sessionUserMap.keySet()) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(message));
            }
        }
    }

    private void sendUserListToAll() throws Exception {
        ObjectNode userListMessage = objectMapper.createObjectNode();
        userListMessage.put("type", "userList");
        userListMessage.set("users", objectMapper.valueToTree(sessionUserMap.values()));
        sendToAll(userListMessage.toString());
    }

    private boolean checkWinner(char player) {
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
