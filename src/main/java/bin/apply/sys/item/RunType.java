package bin.apply.sys.item;

public enum RunType {
    Normal, Shell;

    public boolean isNormal() {
        return this.equals(Normal);
    }

    public boolean isShell() {
        return this.equals(Shell);
    }
}
