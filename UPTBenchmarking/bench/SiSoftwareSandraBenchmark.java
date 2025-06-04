package bench;

import java.util.Random;

public class SiSoftwareSandraBenchmark implements IBenchmark {
    private int[] data;
    private boolean running = true;
    private String result;

    @Override
    public void initialize(Object... params) {
        int size = (int) params[0];
        data = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            data[i] = rand.nextInt(1000);
        }
    }

    @Override
    public void run(Object... options) {
        if (!running) return;
        long sum = 0;
        for (int value : data) {
            sum += Math.sqrt(value);
        }
    }

    @Override
    public void clean() {
        data = null;
    }

    @Override
    public void cancel() {
        running = false;
    }

    @Override
    public void warmup() {
        
    }

    @Override
    public String getResult() { return String.valueOf(result); }
}
