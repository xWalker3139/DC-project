package testbench;

import bench.IBenchmark;
import bench.ram.VirtualMemoryBenchmark;

public class TestVirtualMemory {
    public static void main(String[] args) {
        IBenchmark bench = new VirtualMemoryBenchmark();
        long fileSize = 512L * 1024 * 1024; // 512MB
        int bufferSize = 1024 * 1024; // 1MB

        bench.initialize(fileSize);
        bench.run(bufferSize);
    }
}