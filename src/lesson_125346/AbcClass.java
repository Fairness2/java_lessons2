package lesson_125346;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AbcClass {
    private final Object monitor = new Object();
    private char currentLetter = 'A';

    public void doIt() {
        ExecutorService exec = Executors.newFixedThreadPool(3);
        exec.execute(() -> {
            letterHandler('A', 'B');
        });
        exec.execute(() -> {
            letterHandler('B', 'C');
        });
        exec.execute(() -> {
            letterHandler('C', 'A');
        });
        exec.shutdown();
    }

    private void letterHandler(char printLetter, char nextLetter) {
        synchronized (monitor) {
            for (int i = 0; i < 5; i++) {
                while (currentLetter != printLetter) {
                    try {
                        monitor.wait();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(printLetter);
                currentLetter = nextLetter;
                monitor.notifyAll();
            }
        }
    }
}
