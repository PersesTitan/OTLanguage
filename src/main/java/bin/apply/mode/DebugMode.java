package bin.apply.mode;

import java.util.concurrent.atomic.AtomicReference;

public enum DebugMode {
    // Level = H >>> L
    DEVELOPMENT, INFO, WARRING, ERROR, COMPILE;

    private final static AtomicReference<DebugMode> mode = new AtomicReference<>(DebugMode.INFO);
    private final int level;
    DebugMode() {
        this.level = this.ordinal();
    }

    DebugMode(int level) {
        this.level = level;
    }

    public void set() {
        mode.set(this);
    }

    /**
     * ex) INFO.check(DebugMode.WARRING) <br>
     *               => 1 < 2            <br>
     *               => true             <br>
     */
    public boolean check() {
        return mode.get().level < this.level;
    }

    public static boolean isCompile() {
        return mode.get().equals(COMPILE);
    }

    public static boolean isNoCompile() {
        return !isCompile();
    }

    public static boolean isDevelopment() {
        return mode.get().equals(DEVELOPMENT);
    }
}
