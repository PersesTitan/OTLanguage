package bin.apply;

import bin.apply.sys.make.Bracket;
import bin.calculator.NumberCalculator;
import bin.calculator.bool.BoolCalculator;
import bin.calculator.bool.CompareCalculator;
import bin.orign.loop.tool.LoopController;

public interface Controller {
    NumberCalculator numberCalculator = new NumberCalculator();
    BoolCalculator boolCalculator = new BoolCalculator();
    CompareCalculator compareCalculator = new CompareCalculator();
    LoopController loopController = new LoopController();
    Bracket bracket = new Bracket();
}
