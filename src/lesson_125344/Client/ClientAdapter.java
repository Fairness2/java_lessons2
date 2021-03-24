package lesson_125344.Client;

import lesson_125344.chat.Chat;

import java.util.function.Consumer;

public class ClientAdapter {
    private final Client networkClient;
    private final Chat chatClient;

    public ClientAdapter(String host, int port) {
        networkClient = new Client(host, port);
        chatClient = new Chat(sender());

        new Thread(() -> {
            while (true) {
                String message = networkClient.getMessage();

                if (message.startsWith("-auth")) {
                    chatClient.showMessage("Вы авторизованы", false);
                    String[] authPieces = message.split("\\s");
                    networkClient.setNickname(authPieces[1]);
                }
                else if(message.startsWith("-close")) {
                    chatClient.showMessage("Вы отключены от чата", false);
                    networkClient.setNickname(null);
                }
                else if(message.startsWith("-error")) {
                    chatClient.showMessage("Разорвано соединение с сервером", false);
                    return;
                }
                else {
                    chatClient.showMessage(message, networkClient.getNickname() != null && message.startsWith(networkClient.getNickname()));
                }
            }
        }).start();
    }

    private Consumer<String> sender() {
        return new Consumer<String>() {
            @Override
            public void accept(String s) {
                String res = networkClient.sendMessageHandler(s);
                if (res != null){
                    chatClient.showMessage(res, false);
                }
            }
        };
    }
}
