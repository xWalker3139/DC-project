package testbench;

import bench.IBenchmark;
import bench.hdd.HDDWriteSpeed;

public class TestHDDWriteSpeed {
    public static void main(String[] args) {
        IBenchmark bench = new HDDWriteSpeed();

        System.out.println("-- Fixed Size Mode --");
        bench.run("fs", true);

        System.out.println("\n-- Fixed Buffer Mode --");
        bench.run("fb", true);
    }
}
