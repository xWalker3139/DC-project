/**
 * A demo benchmark implementing bubble sort to test timing and logging
*/

package bench;

import java.util.Random;

public class DemoBenchmark implements IBenchmark {
    private int[] array;
    private boolean running = true;
    private String result;

    @Override
    public void initialize(Object... params) {
        int size = (int) params[0];
        array = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(1000);
        }
        running = true;
    }

    @Override
    public void run(Object... options) {
        if (array == null || array.length == 0) return;
        for (int i = 0; i < array.length - 1 && running; i++) {
            for (int j = 0; j < array.length - i - 1 && running; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    @Override
    public void clean() {
        array = null;
    }

    @Override
    public void cancel() {
        running = false;
    }

    @Override
    public void warmup() {
        
    }

    @Override
    public String getResult() { return String.valueOf(result); }
}
