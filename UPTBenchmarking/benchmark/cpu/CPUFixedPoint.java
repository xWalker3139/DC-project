package benchmark.cpu;

import bench.IBenchmark;

public class CPUFixedPoint implements IBenchmark {
    private int size;
    private boolean running = true;
    private static final int OPS_PER_ITERATION = 29;
    private String result;

    @Override
    public void initialize(Object... params) {
        size = (int) params[0];
        running = true;
    }

    @Override
    public void run(Object... options) {
        fixedArithmetic();
        fixedBranching();
        fixedArrayAccess();
    }

    private void fixedArithmetic() {
    int[] num = { 1, 2, 3, 4 };
    int j = 1, k = 2, l = 3;
    int[] res = new int[size];

    for (int i = 2; i < size && running; i++) {
        j = num[1] * (k - j) * (l - k);
        k = num[3] * k - (l - j) * k;
        l = (l - k) * (num[2] + j);
        res[i - 2] = j + k + l;

        int index = Math.abs(k % (size - 2));
        res[index] = j * k * l;
    }
}


    private void fixedBranching() {
        int[] num = { 0, 1, 2, 3 };
        int j = 0;

        for (int i = 0; i < size && running; i++) {
            if (j == 1) {
                j = num[2];
            } else {
                j = num[3];
            }

            if (j > 2) {
                j = num[0];
            } else {
                j = num[1];
            }

            if (j < 1) {
                j = num[1];
            } else {
                j = num[0];
            }
        }
    }

    private void fixedArrayAccess() {
        int[] a = new int[size];
        int[] b = new int[size];
        int[] c = new int[size];

        for (int i = 0; i < size && running; i++) {
            a[i] = i;
            b[i] = size - 1 - i;
        }

        for (int i = 0; i < size && running; i++) {
            int index = b[i];
            if (index >= 0 && index < size) {
                c[i] = a[index];
            }
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
        int[] dummy = new int[1000];
        for (int i = 0; i < 1000; i++) {
            dummy[i] = i;
        }
    }

    public double getMOPS(long timeNs) {
        return (OPS_PER_ITERATION * (double) size) / (timeNs * 1e-6);
    }

    @Override
    public String getResult() { return String.valueOf(result); }
}
