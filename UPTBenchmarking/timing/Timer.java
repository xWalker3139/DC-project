package timing;

public class Timer implements ITimer {
    private long startTime;
    private long elapsed;

    @Override
    public void start() {
        elapsed = 0;
        startTime = System.nanoTime();
    }

    @Override
    public long stop() {
        return elapsed + (System.nanoTime() - startTime);
    }

    @Override
    public void resume() {
        startTime = System.nanoTime();
    }

    @Override
    public long pause() {
        elapsed += (System.nanoTime() - startTime);
        return elapsed;
    }
}
