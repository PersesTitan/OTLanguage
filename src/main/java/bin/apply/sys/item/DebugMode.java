package bin.apply.sys.item;

public enum DebugMode {
    // Level = H >>> L
    INFO, WARRING, ERROR, COMPILE;

    private final int level;
    DebugMode() {
        this.level = this.ordinal();
    }

    DebugMode(int level) {
        this.level = level;
    }
    /**
     * ex) INFO.check(DebugMode.WARRING) <br>
     *               => 1 < 2            <br>
     *               => true             <br>
     */
     public boolean check(DebugMode debugMode) {
        return this.level < debugMode.level;
    }

    public boolean isCompile() {
         return this.equals(COMPILE);
    }

    public boolean isNoCompile() {
         return !isCompile();
    }
}
