package testbench;

import bench.IBenchmark;
import benchmark.cpu.CPURecursionLoopUnrolling;
import timing.ITimer;
import timing.Timer;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;

public class TestCPURecursionLoopUnrolling {
    public static void main(String[] args) {
        IBenchmark bench = new CPURecursionLoopUnrolling();
        ITimer timer = new Timer();
        ILogger log = new ConsoleLogger();

        long size = 2_000_000;
        bench.initialize(size);

        System.out.println("-- Simple Recursion --");
        timer.start();
        bench.run(false);
        long t1 = timer.stop();
        log.writeTime("Time: ", t1, TimeUnit.Milli);

        int[] unrollLevels = {1, 5, 10, 15};
        for (int unroll : unrollLevels) {
            System.out.println("-- Loop Unrolling Level: " + unroll + " --");
            bench.initialize(size);
            timer.start();
            bench.run(true, unroll);
            long t = timer.stop();
            log.writeTime("Time: ", t, TimeUnit.Milli);
        }

        log.close();
    }
}
