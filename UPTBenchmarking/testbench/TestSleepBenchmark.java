package testbench;

import bench.SleepBenchmark;
import bench.IBenchmark;
import timing.ITimer;
import timing.Timer;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;

public class TestSleepBenchmark {
    public static void main(String[] args) {
        int expectedMs = 1000;

        IBenchmark bench = new SleepBenchmark();
        ITimer timer = new Timer();
        ILogger log = new ConsoleLogger();

        bench.initialize(expectedMs);

        timer.start();
        bench.run();
        long actualTimeNs = timer.stop();

        long expectedNs = expectedMs * 1_000_000;
        double offsetPercent = 100.0 * (actualTimeNs - expectedNs) / expectedNs;

        log.writeTime("Expected time:", expectedNs, TimeUnit.Milli);
        log.writeTime("Measured time:", actualTimeNs, TimeUnit.Milli);
        log.write("Offset:", offsetPercent, "%");
    }
}