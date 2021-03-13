package lesson_125344.Server.Auth;

import lesson_125344.Server.Server;
import lesson_125344.WebChat.IO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ClientHandler extends IO {
    private final Server server;
    private final Socket clientSocket;
    private User user;
    private final Thread runedThred;
    private Timer authTimer;

    public ClientHandler(Server server, Socket clientSocket) throws IOException {
        try {
            this.server = server;
            this.clientSocket = clientSocket;
            setInStream(new DataInputStream(clientSocket.getInputStream()));
            setOutStream(new DataOutputStream(clientSocket.getOutputStream()));
            runedThred = new Thread(() -> {
                try {
                    run();
                }
                catch (IOException e){
                    server.unsubscribe(this);
                    if (user != null) {
                        server.broadcast(String.format("Пользователь %s вышел в чат%n", user.getName()));
                    }
                    throw new RuntimeException(e.getMessage(), e);
                }
            });
            runedThred.start();

        }
        catch (IOException | RuntimeException e){
            throw new IOException(e.getMessage(), e);
        }
    }

    protected void run() throws IOException{
        auth();
        waitMessage();
    }

    protected void auth() throws IOException{
        if (user != null) {
            user = null;
        }
        sendMessage("Авторизуйтесь...");
        if (authTimer != null) {
            authTimer.cancel();
        }
        authTimer = new Timer();
        authTimer.schedule(new DestroyTask(), 120000);
        while (true) {
            String input = getInStream().readUTF();
            if (input.startsWith("-auth")){
                String[] credentials = input.split("\\s");
                if (credentials.length >= 3) {
                    User user = server.getAuthService().findByCredentials(credentials[1], credentials[2]);
                    if (user != null){
                        if (server.isLoginAvailable(user.getLogin())) {
                            authTimer.cancel();
                            this.user = user;
                            server.broadcast(String.format("Пользователь %s вошёл в чат%n", user.getName()));
                            server.subscribe(this);
                            sendMessage(String.format("-auth %s", user.getName()));
                            return;
                        }
                        else {
                            sendMessage("Вы уже авторизованы");
                        }
                    }
                    else {
                        sendMessage("Пользователь не найден");
                    }
                }
                else {
                    sendMessage("Авторизационное сообщение должно содержать логин и пароль");
                }
            }
            else {
                sendMessage("Авторизационное сообщение должно начинаться с ключегого слова \"-auth\" и содержать логин и пароль");
            }
        }
    }

    public User getUser() {
        return user;
    }

    @Override
    protected void processIncomingMessage(String message) throws IOException {
        if (message.startsWith("/w")){
            String[] messagePieces = message.split("\\s");
            if (messagePieces.length >= 2) {
                String[] newMessagePieces = new String[messagePieces.length - 2];
                System.arraycopy(messagePieces, 2, newMessagePieces, 0, messagePieces.length - 2);
                String newMessage = String.join(" ", newMessagePieces);
                sendMessage(String.format("%s: %s", user.getName(), message));
                server.sendDirectMessage(messagePieces[1], String.format("%s (только вам): %s", user.getName(), newMessage));
            }
            else {
                sendMessage("Сообщение должно содержать имя пользователя");
            }
        }
        else if (message.startsWith("-close")) {
            server.unsubscribe(this);
            server.broadcast(String.format("Пользователь %s вышел в чат%n", user.getName()));
            sendMessage("-close");
            auth();
        }
        else if (message.startsWith("-cn")) {
            String[] messagePieces = message.split("\\s");
            if (messagePieces.length >= 2) {
                if (server.getAuthService().changeUserName(user, messagePieces[1])) {
                    sendMessage("Ваш никнейм изменён");
                }
                else {
                    sendMessage("Изменить никнейм не удалось");
                }
            }
            else {
                sendMessage("Сообщение должно содержать новый никнейм");
            }
        }
        else {
            server.broadcast(String.format("%s: %s", user.getName(), message));
        }
    }

    private class DestroyTask extends TimerTask {
        @Override
        public void run() {
            if (user == null) {
                try {
                    sendMessage("Вы бездействовали слишком долго, сервер разрывает соединение");
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }

                try {
                    clientSocket.close(); //По идее должно завершить всё остальное, потмоу что потоки данных будут прекращщены
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

}
