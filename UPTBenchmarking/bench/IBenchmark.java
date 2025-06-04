package bench;

public interface IBenchmark {
    void initialize(Object... params);
    void run(Object... options);
    void clean();
    void cancel();
    void warmup();
    String getResult();
}
