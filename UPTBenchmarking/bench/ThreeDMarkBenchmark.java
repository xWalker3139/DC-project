package bench;

public class ThreeDMarkBenchmark implements IBenchmark {
    private boolean running = true;
    private String result;

    @Override
    public void initialize(Object... params) {
        
    }

    @Override
    public void run(Object... options) {
        if (!running) return;
        for (int i = 0; i < 3; i++) {
            int frames = 60;
            for (int f = 0; f < frames; f++) {
                double frameCalc = Math.pow(Math.random(), 5);
            }
        }
    }

    @Override
    public void clean() {

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
