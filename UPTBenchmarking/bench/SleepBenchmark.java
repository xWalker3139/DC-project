/**
 * Benchmark that sleeps the thread to simulate known execution time
*/

package bench;

public class SleepBenchmark implements IBenchmark {
    private int sleepTimeMs;
    private boolean running = true;
    private String result;

    @Override
    public void initialize(Object... params) {
        sleepTimeMs = (int) params[0];
        running = true;
    }

    @Override
    public void run(Object... options) {
        try {
            if (running) {
                Thread.sleep(sleepTimeMs);
            }
        } catch (InterruptedException e) {
            
        }
    }

    @Override
    public void clean() {}

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