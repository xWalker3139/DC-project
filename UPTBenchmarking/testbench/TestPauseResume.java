package testbench;

import bench.DemoBenchmark;
import bench.IBenchmark;
import timing.ITimer;
import timing.Timer;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;

public class TestPauseResume {
    public static void main(String[] args) {
        IBenchmark bench = new DemoBenchmark();
        ITimer timer = new Timer();
        ILogger log = new ConsoleLogger();

        bench.initialize(5000);

        for (int i = 0; i < 5; i++) {
            timer.resume();
            bench.run();
            long segment = timer.pause();
            log.writeTime("Segment " + (i + 1) + " duration:", segment, TimeUnit.Milli);
        }

        long total = timer.stop();
        log.writeTime("Total time:", total, TimeUnit.Milli);
    }
}
