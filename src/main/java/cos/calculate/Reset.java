package cos.calculate;

import work.ResetWork;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Reset implements ResetWork, CalculateToken {
    @Override
    public void reset() {
        create(CALCULATE, CalculateItem.class, v -> new CalculateItem((String) v[0]), s);
    }
}
