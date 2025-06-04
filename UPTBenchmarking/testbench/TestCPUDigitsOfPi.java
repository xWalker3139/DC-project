package testbench;

import bench.IBenchmark;
import benchmark.cpu.CPUDigitsOfPi;
import timing.ITimer;
import timing.Timer;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;

public class TestCPUDigitsOfPi {
    public static void main(String[] args) {
        IBenchmark bench = new CPUDigitsOfPi();
        ITimer timer = new Timer();
        ILogger log = new ConsoleLogger();

        bench.initialize(100_000);
        bench.warmup();

        timer.start();
        bench.run();
        long time = timer.stop();

        log.writeTime("Pi computation completed in:", time, TimeUnit.Milli);

        bench.clean();
        log.close();
    }
}