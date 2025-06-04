package testbench;

import bench.DemoBenchmark;
import bench.IBenchmark;
import timing.ITimer;
import timing.Timer;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;

public class TestDemoBench {
    public static void main(String[] args) {
        IBenchmark bench = new DemoBenchmark();
        ITimer timer = new Timer();
        ILogger log = new ConsoleLogger();

        bench.initialize(10000);

        timer.start();
        bench.run();
        long time = timer.stop();

        log.writeTime("DemoBenchmark completed in:", time, TimeUnit.Milli);

        bench.clean();
        log.close();
    }
}
