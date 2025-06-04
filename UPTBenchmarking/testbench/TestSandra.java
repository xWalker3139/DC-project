package testbench;

import bench.SiSoftwareSandraBenchmark;
import bench.IBenchmark;
import timing.ITimer;
import timing.Timer;
import logging.ConsoleLogger;
import logging.ILogger;

public class TestSandra {
    public static void main(String[] args) {
        IBenchmark bench = new SiSoftwareSandraBenchmark();
        ITimer timer = new Timer();
        ILogger log = new ConsoleLogger();

        bench.initialize(100000);

        timer.start();
        bench.run();
        long time = timer.stop();

        log.write("SiSoftwareSandra Benchmark completed in:", time, "ns");

        bench.clean();
        log.close();
    }
}
