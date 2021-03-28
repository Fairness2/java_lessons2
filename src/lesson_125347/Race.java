package lesson_125347;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Race {
    private ArrayList<Stage> stages;

    private CyclicBarrier cdlReady;
    private CountDownLatch cdlFinish;

    public ArrayList<Stage> getStages() { return stages; }
    public Race(CyclicBarrier cdlReady, CountDownLatch cdlFinish, Stage... stages) {
        this.cdlReady = cdlReady;
        this.cdlFinish = cdlFinish;
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }


    public CountDownLatch getCdlFinish() {
        return cdlFinish;
    }

    public CyclicBarrier getCdlReady() {
        return cdlReady;
    }
}
