package benchmark.cpu;

import bench.IBenchmark;

public class CPURecursionLoopUnrolling implements IBenchmark {
    private long size;
    private String result;

    @Override
    public void initialize(Object... params) {
        size = (long) params[0];
    }

    @Override
    public void run(Object... options) {
        boolean useUnrolling = (boolean) options[0];

        if (useUnrolling) {
            int unrollLevel = (int) options[1];
            try {
                long primes = recursiveUnrolled(2, unrollLevel, size, 0);
                System.out.println("Found " + primes + " primes.");
            } catch (StackOverflowError e) {
                System.out.println("[Unrolled] StackOverflow after recursion.");
            }
        } else {
            try {
                long primes = recursive(2, size, 0);
                System.out.println("Found " + primes + " primes.");
            } catch (StackOverflowError e) {
                System.out.println("[Basic] StackOverflow after recursion.");
            }
        }
    }

    private long recursive(long current, long limit, int counter) {
        if (current > limit) return 0;

        if (counter % 10000 == 0)
            System.out.println("Reached number " + current + " after " + counter + " calls");

        long count = isPrime((int) current) ? 1 : 0;
        return count + recursive(current + 1, limit, counter + 1);
    }

    private long recursiveUnrolled(long current, int unrollLevel, long limit, int counter) {
        if (current > limit) return 0;

        long count = 0;
        for (int i = 0; i < unrollLevel && current <= limit; i++, current++) {
            if (isPrime((int) current)) count++;
        }

        if (counter % 10000 == 0)
            System.out.println("[Unrolled] Reached " + current + " after " + counter + " calls");

        return count + recursiveUnrolled(current, unrollLevel, limit, counter + 1);
    }

    private boolean isPrime(int x) {
        if (x <= 2) return true;
        for (int i = 2; i <= Math.sqrt(x); i++) {
            if (x % i == 0) return false;
        }
        return true;
    }

    @Override
    public void clean() {}

    @Override
    public void cancel() {}

    @Override
    public void warmup() {}

    @Override
    public String getResult() { return String.valueOf(result); }
}
