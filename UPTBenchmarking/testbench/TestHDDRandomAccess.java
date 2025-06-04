package testbench;

import bench.hdd.HDDRandomAccess;
import bench.IBenchmark;

public class TestHDDRandomAccess {
    public static void main(String[] args) {
        IBenchmark bench = new HDDRandomAccess();
        bench.initialize(50L * 1024 * 1024);

        bench.run(new Object[]{"r", "fs", 4 * 1024});
        System.out.println(bench.getResult());

        bench.run(new Object[]{"w", "ft", 4 * 1024});
        System.out.println(bench.getResult());

        bench.clean();
    }
}
