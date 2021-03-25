package lesson_125344.Client;

import java.io.*;
import java.util.LinkedList;

public class History implements Serializable {
    private static final String fileName = "history.txt";
    private static final int limit = 100;
    private final LinkedList<String> history;

    public History() {
        history = new LinkedList<>();
    }

    public synchronized void add(String message) {
        if (history.size() >= limit) {
            history.remove(0);
        }
        history.add(message);
    }

    public synchronized LinkedList<String> getAll() {
        return history;
    }

    public synchronized void clear() {
        history.clear();
    }

    public synchronized void save() {
        try (FileOutputStream out = new FileOutputStream(fileName);
             ObjectOutputStream objOut = new ObjectOutputStream(out)) {
            objOut.writeObject(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized History load() {
        try (FileInputStream in = new FileInputStream(fileName);
            ObjectInputStream objIn = new ObjectInputStream(in)) {
            return (History) objIn.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new History();
        }
    }
}
