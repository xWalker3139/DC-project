package logging;

public interface ILogger {
    void write(long l);
    void write(String s);
    void write(Object... values);
    void writeTime(String message, long time, TimeUnit unit);
    void close();
}
