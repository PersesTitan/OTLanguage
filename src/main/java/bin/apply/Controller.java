package bin.apply;

import bin.apply.sys.make.Bracket;
import bin.apply.sys.run.LoopBracket;
import bin.calculator.NumberCalculator;
import bin.calculator.bool.BoolCalculator;
import bin.calculator.bool.CompareCalculator;

public interface Controller {
    NumberCalculator numberCalculator = new NumberCalculator();
    BoolCalculator boolCalculator = new BoolCalculator();
    CompareCalculator compareCalculator = new CompareCalculator();
    Bracket bracket = new Bracket();
}
