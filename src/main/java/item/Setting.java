package item;

import print.ScannerP;

import java.util.HashMap;
import java.util.Map;

public class Setting {
    protected ScannerP scannerP = new ScannerP();

    public Map<String, Integer> IM = new HashMap<>();
    public Map<String, Long> LM = new HashMap<>();
    public Map<String, Boolean> BM = new HashMap<>();
    public Map<String, String> SM = new HashMap<>();
    public Map<String, Character> CM = new HashMap<>();
    public Map<String, Float> FM = new HashMap<>();
    public Map<String, Double> DM = new HashMap<>();
}
