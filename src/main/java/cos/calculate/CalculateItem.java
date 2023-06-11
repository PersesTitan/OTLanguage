package cos.calculate;

import bin.apply.item.Item;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiFunction;

class CalculateItem implements Item, CalculateToken {
    private final static String SING_TOKEN = ADD + SUB + MUL + DIV + REM;
    public final static Map<String, BiFunction<BigDecimal, BigDecimal, BigDecimal>> SINGS_CALCULATE = Map.of(
            ADD, BigDecimal::add,
            SUB, BigDecimal::subtract,
            MUL, BigDecimal::multiply,
            DIV, BigDecimal::divide,
            REM, BigDecimal::remainder
    );

    private final BigDecimal[] NUMBERS;
    private final String[] SINGS;

    public CalculateItem(String val) {
        StringTokenizer tokenizer = new StringTokenizer(val, SING_TOKEN, true);
        List<String> NUMBERS = new ArrayList<>();
        List<String> SINGS = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            final String token = tokenizer.nextToken();
            if (CalculateItem.SINGS_CALCULATE.containsKey(token)) SINGS.add(token);
            else NUMBERS.add(token);
        }
        this.NUMBERS = NUMBERS.stream().map(BigDecimal::new).toArray(BigDecimal[]::new);
        this.SINGS = SINGS.toArray(String[]::new);
    }

    private static BigDecimal toDecimal(String number) {
        // make sort
        int dot, s, e;
        char[] in = number.toCharArray();
        s = in[0] == '-' || in[0] == '+' ? 1 : 0;
        if ((dot=number.indexOf('.'))>=0) {
            // ex) 10.000 -> 10.0
            dot++;
            for (e=in.length-1; e>dot; e--) if (in[e] != '0') break;
            for (; s<dot; s++) if (in[s] != '0') break;
            System.out.println(Arrays.copyOfRange(in, s, e+1));
        } else {
            // ex) 0100 -> 100
            for (; s<in.length-1; s++) if (in[s] != '0') break;
            System.out.println(Arrays.copyOfRange(in, s, in.length));
        }
        return switch (number) {
            case "0", "0.0" -> BigDecimal.ZERO;
            case "1", "1.0" -> BigDecimal.ONE;
            case "10", "10.0" -> BigDecimal.TEN;
            default -> new BigDecimal(in);
        };
    }

    @Override
    public String toString() {
        return toString(CalculateToken.CALCULATE);
    }
}
