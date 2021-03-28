package lesson_125347;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private Semaphore sp;
    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        this.sp = new Semaphore((int)Math.floor(MainClass.CARS_COUNT / 2.0));
    }
    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                sp.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                sp.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
