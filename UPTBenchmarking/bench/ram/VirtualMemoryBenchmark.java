package bench.ram;

import bench.IBenchmark;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;
import timing.Timer;

public class VirtualMemoryBenchmark implements IBenchmark {
    private String filename = "C:\\Users\\MSI\\core";
    private MemoryMapper memory;
    private DecimalFormat format = new DecimalFormat("0.00");

    @Override
    public void initialize(Object... params) {
        long fileSize = (Long) params[0];
        try {
            memory = new MemoryMapper(filename, fileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override public void warmup() {}

    @Override
    public void run(Object... options) {
        int bufferSize = (Integer) options[0];
        byte[] buffer = new byte[bufferSize];
        new Random().nextBytes(buffer);

        try {
            Timer timer = new Timer();

            // Write
            timer.start();
            for (long offset = 0; offset < memory.getSize(); offset += bufferSize) {
                memory.put(offset, buffer);
            }
            double writeTime = timer.stop() / 1_000_000.0;
            double writeSpeed = (memory.getSize() / 1024.0 / 1024.0) / (writeTime / 1000.0);

            // Read
            byte[] temp;
            timer.start();
            for (long offset = 0; offset < memory.getSize(); offset += bufferSize) {
                temp = memory.get(offset, bufferSize);
            }
            double readTime = timer.stop() / 1_000_000.0;
            double readSpeed = (memory.getSize() / 1024.0 / 1024.0) / (readTime / 1000.0);

            System.out.println("Write speed: " + format.format(writeSpeed) + " MB/s");
            System.out.println("Read speed: " + format.format(readSpeed) + " MB/s");

            memory.clean();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override public void clean() {}
    @Override public void cancel() {}
    @Override public String getResult() { return null; }
}