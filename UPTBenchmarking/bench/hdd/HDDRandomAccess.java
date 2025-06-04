package bench.hdd;

import bench.IBenchmark;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import timing.Timer;

public class HDDRandomAccess implements IBenchmark {

    private final static String PATH = "D:\\IT\\UPTBenchmarking\\test.raf";
    private String result;

    @Override
    public void initialize(Object... params) {
        File tempFile = new File(PATH);
        RandomAccessFile rafFile;
        long fileSizeInBytes = (Long) params[0];

        try {
            rafFile = new RandomAccessFile(tempFile, "rw");
            Random rand = new Random();
            int bufferSize = 4 * 1024;
            long toWrite = fileSizeInBytes / bufferSize;
            byte[] buffer = new byte[bufferSize];
            long counter = 0;

            while (counter++ < toWrite) {
                rand.nextBytes(buffer);
                rafFile.write(buffer);
            }
            rafFile.close();    
            tempFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override public void warmup() {}

    @Override
    public void run(Object ...options) {
        Object[] param = (Object[]) options;
        final int steps = 25000;
        final int runtime = 5000;

        try {
            if (String.valueOf(param[0]).toLowerCase().equals("r")) {
                int bufferSize = Integer.parseInt(String.valueOf(param[2]));

                if (String.valueOf(param[1]).toLowerCase().equals("fs")) {
                    long timeMs = new RandomAccess().randomReadFixedSize(PATH, bufferSize, steps);
                    result = steps + " random reads in " + timeMs + " ms [" 
                            + (steps * bufferSize / 1024 / 1024) + " MB, "
                            + 1.0 * (steps * bufferSize / 1024 / 1024) / timeMs * 1000 + "MB/s]";
                } else if (String.valueOf(param[1]).toLowerCase().equals("ft")) {
                    int ios = new RandomAccess().randomReadFixedTime(PATH, bufferSize, runtime);
                    result = ios + " I/Os per second [" 
                            + (ios * bufferSize / 1024 / 1024) + " MB, "
                            + 1.0 * (ios * bufferSize / 1024 / 1024) / runtime * 1000 + "MB/s]";
                } else throw new UnsupportedOperationException("Read option \"" + String.valueOf(param[1]) + "\" is not implemented");

            } else if (String.valueOf(param[0]).toLowerCase().equals("w")) {
                int bufferSize = Integer.parseInt(String.valueOf(param[2]));

                if (String.valueOf(param[1]).toLowerCase().equals("fs")) {
                    long timeMs = new RandomAccess().randomWriteFixedSize(PATH, bufferSize, steps);
                    result = steps + " random writes in " + timeMs + " ms [" 
                            + (steps * bufferSize / 1024 / 1024) + " MB, "
                            + 1.0 * (steps * bufferSize / 1024 / 1024) / timeMs * 1000 + "MB/s]";
                } else if (String.valueOf(param[1]).toLowerCase().equals("ft")) {
                    int ios = new RandomAccess().randomWriteFixedTime(PATH, bufferSize, runtime);
                    result = ios + " I/Os per second [" 
                            + (ios * bufferSize / 1024 / 1024) + " MB, "
                            + 1.0 * (ios * bufferSize / 1024 / 1024) / runtime * 1000 + "MB/s]";
                } else throw new UnsupportedOperationException("Write option \"" + String.valueOf(param[1]) + "\" is not implemented");

            } else throw new UnsupportedOperationException("Benchmark option \"" + String.valueOf(param[0]) + "\" is not implemented");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override public void clean() {}
    @Override public void cancel() {}
    public String getResult() { return String.valueOf(result); }

    class RandomAccess {
        private Random random = new Random();

        public long randomReadFixedSize(String filePath, int bufferSize, int toRead) throws IOException {
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            long fileSize = file.getChannel().size();
            int counter = 0;
            byte[] bytes = new byte[bufferSize];
            Timer timer = new Timer();

            timer.start();
            while (counter++ < toRead) {
                file.seek(random.nextInt((int)(fileSize - bufferSize)));
                file.read(bytes);
            }
            file.close();
            return timer.stop() / 1000000;
        }

        public int randomReadFixedTime(String filePath, int bufferSize, int millis) throws IOException {
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            long fileSize = file.getChannel().size();
            int counter = 0;
            byte[] bytes = new byte[bufferSize];
            long start = System.nanoTime();

            while ((System.nanoTime() - start) / 1_000_000 < millis) {
                file.seek(random.nextInt((int)(fileSize - bufferSize)));
                file.read(bytes);
                counter++;
            }
            file.close();
            return counter;
        }

        public long randomWriteFixedSize(String filePath, int bufferSize, int toWrite) throws IOException {
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            long fileSize = file.getChannel().size();
            int counter = 0;
            byte[] bytes = new byte[bufferSize];
            random.nextBytes(bytes);
            Timer timer = new Timer();

            timer.start();
            while (counter++ < toWrite) {
                file.seek(random.nextInt((int)(fileSize - bufferSize)));
                file.write(bytes);
            }
            file.close();
            return timer.stop() / 1_000_000;
        }

        public int randomWriteFixedTime(String filePath, int bufferSize, int millis) throws IOException {
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            long fileSize = file.getChannel().size();
            int counter = 0;
            byte[] bytes = new byte[bufferSize];
            random.nextBytes(bytes);
            long start = System.nanoTime();

            while ((System.nanoTime() - start) / 1_000_000 < millis) {
                file.seek(random.nextInt((int)(fileSize - bufferSize)));
                file.write(bytes);
                counter++;
            }
            file.close();
            return counter;
        }
    }
}
