package logging;

public class ConsoleLogger implements ILogger {
    @Override
    public void write(long l) {
        System.out.println(l);
    }

    @Override
    public void write(String s) {
        System.out.println(s);
    }

    @Override
    public void write(Object... values) {
        for (Object value : values) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    @Override
    public void writeTime(String message, long time, TimeUnit unit) {
        long converted = unit.convert(time);
        System.out.println(message + " " + converted + " " + unit.getSuffix());
    }

    @Override
    public void close() {
        
    }
}
