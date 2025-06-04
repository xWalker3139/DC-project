package testbench;

import bench.ThreeDMarkBenchmark;
import bench.IBenchmark;
import timing.ITimer;
import timing.Timer;
import logging.ConsoleLogger;
import logging.ILogger;

public class Test3DMark {
    public static void main(String[] args) {
        IBenchmark bench = new ThreeDMarkBenchmark();
        ITimer timer = new Timer();
        ILogger log = new ConsoleLogger();

        bench.initialize();

        timer.start();
        bench.run();
        long time = timer.stop();

        log.write("3DMark Benchmark completed in:", time, "ns");

        bench.clean();
        log.close();
    }
}
