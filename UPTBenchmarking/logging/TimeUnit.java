package logging;

public enum TimeUnit {
    Nano(1),
    Micro(1_000),
    Milli(1_000_000),
    Sec(1_000_000_000);

    private final long factor;

    TimeUnit(long factor) {
        this.factor = factor;
    }

    public long convert(long nanoTime) {
        return nanoTime / factor;
    }

    public String getSuffix() {
        switch (this) {
            case Nano: return "ns";
            case Micro: return "μs";
            case Milli: return "ms";
            case Sec: return "s";
            default: return "";
        }
    }
}
