package bench.hdd;

import bench.IBenchmark;

public class HDDWriteSpeed implements IBenchmark {

    private final FileWriter fileWriter;
    private String result;

    public HDDWriteSpeed() {
        fileWriter = new FileWriter();
    }

    @Override
    public void run(Object... options) {
        System.out.println("-- Fixed Size Mode --");
        long fileSize = 1024 * 1024; // 1MB
        int[] bufferSizes = {1024, 2048, 4096, 8192, 16384, 32768};

        for (int bufferSize : bufferSizes) {
            try {
                fileWriter.streamWriteFixedSize(fileSize, bufferSize);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n-- Fixed Buffer Mode --");
        long[] fileSizes = {1024 * 1024, 2 * 1024 * 1024, 5 * 1024 * 1024, 10 * 1024 * 1024};
        int bufferSize = 1024 * 1024;

        for (long size : fileSizes) {
            try {
                fileWriter.streamWriteFixedBuffer(bufferSize, size);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(Object... params) {}

    @Override
    public void clean() {}

    @Override
    public void cancel() {}

    @Override
    public void warmup() {}

    @Override
    public String getResult() { return String.valueOf(result); }
}
