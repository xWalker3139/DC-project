package testbench;

import benchmark.cpu.CPUFixedPoint;
import bench.IBenchmark;
import timing.ITimer;
import timing.Timer;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;

public class TestCPUFixedPoint {
    public static void main(String[] args) {
        IBenchmark bench = new CPUFixedPoint();
        ITimer timer = new Timer();
        ILogger log = new ConsoleLogger();

        int size = 100_000;
        bench.initialize(size);
        bench.warmup();

        timer.start();
        bench.run();
        long time = timer.stop();

        double mops = ((CPUFixedPoint) bench).getMOPS(time);

        log.writeTime("Fixed Point benchmark completed in:", time, TimeUnit.Milli);
        log.write("Estimated MOPS:", mops);

        bench.clean();
        log.close();
    }
}