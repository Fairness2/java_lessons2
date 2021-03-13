package lesson_125344.Client;

import lesson_125344.WebChat.IO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends IO {
    private Socket socket;
    private String nickname;

    public Client(String serverAddress, int portNumber) {
        try {
            socket = new Socket(serverAddress, portNumber);
            System.out.println("Клиент запущен");
            setInStream(new DataInputStream(socket.getInputStream()));
            setOutStream(new DataOutputStream(socket.getOutputStream()));
        }
        catch (IOException e){
            System.out.printf("Can't initialize client! Exception: %s%n", e.getMessage());
            e.printStackTrace();
        }
    }

    public String getMessage(){
        try {
            return getInStream().readUTF().trim();
        }
        catch (IOException e){
            nickname = null;
            return "-error";
        }
    }

    @Override
    protected void processIncomingMessage(String message) {
        System.out.println(message);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String name) {
        nickname = name;
    }

    public String sendMessageHandler(String message) {
        try{
            sendMessage(message);
        }
        catch (IOException e) {
            nickname = null;
            return "Разорвано соединение с сервером";
        }
        return null;
    }
}
