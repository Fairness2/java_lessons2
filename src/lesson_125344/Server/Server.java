package lesson_125344.Server;

import lesson_125344.Server.Auth.AuthenticationService;
import lesson_125344.Server.Auth.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket socket;
    private final int portNumber = 8000;
    private final AuthenticationService authService;
    private final Set<ClientHandler> clients;
    private final ExecutorService exService;

    public Server(){
        authService = new AuthenticationService();
        clients = new HashSet<>();
        exService = Executors.newCachedThreadPool();

        try {
            socket = new ServerSocket(portNumber);
            System.out.println("Сервер запущен");
            run();
        }
        catch (IOException e){
            System.out.printf("Can't initialize server! Exception: %s%n", e.getMessage());
            e.printStackTrace();
        }
    }

    private void run() {
        while (true){
            try {
                Socket client = socket.accept();
                new ClientHandler(this, client);
            }
            catch (IOException e){
                System.out.printf("Can't accept client! Exception: %s%n", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public ExecutorService getExService() {
        return exService;
    }

    public AuthenticationService getAuthService() {
        return authService;
    }

    public synchronized boolean isLoginAvailable(String login) {
        return findClientByLogin(login) == null;
    }

    private synchronized ClientHandler findClientByLogin(String login) {
        Optional<ClientHandler> oClient = clients.stream().filter(client -> client.getUser().getLogin().equals(login)).findFirst();
        return oClient.orElse(null);
    }

    private synchronized ClientHandler findClientByName(String name) {
        Optional<ClientHandler> oClient = clients.stream().filter(client -> client.getUser().getName().equals(name)).findFirst();
        return oClient.orElse(null);
    }

    public synchronized void broadcast(String message){
        for (ClientHandler client: clients) {
            sendMessage(client, message);
        }
    }

    public synchronized void subscribe(ClientHandler client) {
        clients.add(client);
    }

    public synchronized void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

    public void sendDirectMessage(String name, String message){
        ClientHandler client = findClientByName(name);
        if (client != null){
            sendMessage(client, message);
        }
    }

    private void sendMessage(ClientHandler client, String message) {
        try {
            client.sendMessage(message);
        }
        catch (IOException e){
            unsubscribe(client);
        }
    }
}
