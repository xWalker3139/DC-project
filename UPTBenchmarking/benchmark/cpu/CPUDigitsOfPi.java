package benchmark.cpu;
import bench.IBenchmark;
import java.math.BigDecimal;
import java.math.MathContext;

public class CPUDigitsOfPi implements IBenchmark {
    private int digits;
    private boolean running = true;
    private String result;

    @Override
    public void initialize(Object... params) {
        this.digits = (int) params[0];
        this.running = true;
    }

    @Override
    public void run(Object... options) {
        computePiGregoryLeibniz(digits);
    }

    @Override
    public void clean() {}

    @Override
    public void cancel() {
        running = false;
    }

    @Override
    public void warmup() {
        computePiGregoryLeibniz(1000);
    }

    private BigDecimal computePiGregoryLeibniz(int iterations) {
        BigDecimal pi = BigDecimal.ZERO;
        BigDecimal one = BigDecimal.ONE;
        MathContext mc = new MathContext(20);

        for (int k = 0; k < iterations && running; k++) {
            BigDecimal numerator = (k % 2 == 0) ? one : one.negate();
            BigDecimal term = numerator.divide(new BigDecimal(2 * k + 1), mc);
            pi = pi.add(term);
        }

        pi = pi.multiply(new BigDecimal(4));
        return pi;
    }

    @Override
    public String getResult() { return String.valueOf(result); }
}