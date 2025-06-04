package bench.hdd;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriter {
    private static final String BASE_PATH = "C:\\Users\\MSI\\000-bench";
    private static final String CSV_PATH = BASE_PATH + "\\hdd_write_results_sample.csv";

    private final PrintWriter csvWriter;

    public FileWriter() {
        try {
            createDirectoryIfMissing(BASE_PATH);
            csvWriter = new PrintWriter(new java.io.FileWriter(CSV_PATH, false));
            csvWriter.println("Mode,FileSize,BufferSize,ElapsedTime(ms)");
        } catch (IOException e) {
            throw new RuntimeException("Cannot open CSV for writing", e);
        }
    }

    public void streamWriteFixedSize(long fileSize, int bufferSize) {
        String path = BASE_PATH + "\\fs\\test-" + fileSize + "B-" + bufferSize + "B.dat";
        createDirectoryIfMissing(BASE_PATH + "\\fs");
        long elapsed = writeFile(path, fileSize, bufferSize);
        csvWriter.printf("FixedSize,%d,%d,%d%n", fileSize, bufferSize, elapsed);
        System.out.printf("[FixedSize] File: %dB, Buffer: %dB, Time: %d ms%n", fileSize, bufferSize, elapsed);
    }

    public void streamWriteFixedBuffer(int bufferSize, long fileSize) {
        String path = BASE_PATH + "\\fb\\test-" + fileSize + "B-" + bufferSize + "B.dat";
        createDirectoryIfMissing(BASE_PATH + "\\fb");
        long elapsed = writeFile(path, fileSize, bufferSize);
        csvWriter.printf("FixedBuffer,%d,%d,%d%n", fileSize, bufferSize, elapsed);
        System.out.printf("[FixedBuffer] File: %dB, Buffer: %dB, Time: %d ms%n", fileSize, bufferSize, elapsed);
    }

    private long writeFile(String path, long fileSize, int bufferSize) {
        byte[] buffer = new byte[bufferSize];
        long bytesWritten = 0;
        long start = System.currentTimeMillis();
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path))) {
            while (bytesWritten < fileSize) {
                int toWrite = (int) Math.min(bufferSize, fileSize - bytesWritten);
                bos.write(buffer, 0, toWrite);
                bytesWritten += toWrite;
            }
        } catch (IOException e) {
            System.err.println("Failed to write file: " + path);
            e.printStackTrace();
        }
        return System.currentTimeMillis() - start;
    }

    private void createDirectoryIfMissing(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                System.err.println("Could not create directory: " + path);
            }
        }
    }

    public void close() {
        csvWriter.close();
    }
}
