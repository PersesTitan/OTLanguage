package event;

import event.db.DBSetting;
import http.controller.HTMLVariable;
import http.controller.StartServer;
import http.items.Color;
import http.items.HttpRepository;
import http.model.HttpWork;
import math.controller.BoolCalculation;
import math.controller.Calculation;
import math.controller.NumberCalculation;
import math.controller.StringCalculation;
import math.controller.random.*;
import math.model.CalculationWork;
import math.model.RandomWork;
import origin.consol.controller.Print;
import origin.consol.controller.Println;
import origin.consol.controller.PriorityPrint;
import origin.consol.controller.PriorityPrintln;
import origin.consol.define.PrintWork;
import origin.consol.define.PriorityPrintWork;
import origin.loop.controller.For;
import origin.loop.controller.ForEach;
import origin.loop.controller.If;
import origin.loop.model.LoopWork;
import origin.variable.controller.MakeListVariable;
import origin.variable.controller.MakeVariable;
import origin.variable.define.VariableType;
import origin.variable.model.Repository;
import origin.variable.model.VariableWork;

public class Setting implements Controller, Repository {
    public final static StringBuffer total = new StringBuffer();
    public static String path = "";
    //상속
    private final static DBSetting dBSetting = new DBSetting();

    /**
     * 상속 <br>
     * @see event.db.DBSetting
     */
    public void start(String line) {
        if (line.isBlank()) return;
        for (PriorityPrintWork work : priorityPrintWorks) {if (work.check(line)) {work.start(line); return;}} //강제 출력
        if (getVariable.check(line)) line = getVariable.start(line); //변수 불러오기
        if (dBSetting.check(line)) {String l = DBSetting.start(line);if (l == null) return;else line = l;} //DB 클래스
        if (getListVariable.check(line)) line = getListVariable.start(line); //리스트 변수 가져오기
        if (httpGetPost.check(line)) {
            httpGetPost.start(line); //post, get 으로 받은 값 대치
            return;
        }
        if (consoleScanner.check(line)) line = consoleScanner.start(line); //값 읽기
        for (RandomWork work : randomWorks) {if (work.check(line)) line = work.start(line);} //램던 값 불러오기
        for (CalculationWork work : calculationWorks) {if (work.check(line)) line = work.start(line);} //게산 (숫자, 블린)

        for (LoopWork work : loopWorks) {if (work.check(line)) {work.start(line); return;}} //for, if
        for (PrintWork work : printWorks) {if (work.check(line)) {work.start(line);return;}} //출력하는 동작
        for (VariableWork work : variableWorks) {if (work.check(line)) {work.start(line); return;}} //변수 정의하는 동작
        if (setListVariable.check(line)) {setListVariable.start(line);return;} //리스트에 값 넣기
        if (setVariable.check(line)) {setVariable.start(line);return;} //변수 값 넣기
        if (deleteListVariable.check(line)) {if (deleteListVariable.start(line) == null) return;} //리스트에 값 삭제

        //http
        if (portVariable.check(line)) {portVariable.start(line);return;}
        for (HttpWork work : httpWorks) {if (work.check(line)) {work.start(line); return;}}
        System.out.printf("%s경고! %s는 실행되지 않은 라인 입니다.%s\n", Color.YELLOW, line, Color.RESET);
    }

    //시작할때 변수 이름 및 매소드 새팅 생성

    /**
     * 저장소 <br>
     * @see Repository
     * @see Controller
     */
    public void firstStart() {
        reset();
        dBSetting.firstStart();

        variableWorks.add(new MakeVariable("ㅇㅈㅇ", VariableType.Integer));
        variableWorks.add(new MakeVariable("ㅇㅉㅇ", VariableType.Long));
        variableWorks.add(new MakeVariable("ㅇㅂㅇ", VariableType.Boolean));
        variableWorks.add(new MakeVariable("ㅇㅁㅇ", VariableType.String));
        variableWorks.add(new MakeVariable("ㅇㄱㅇ", VariableType.Character));
        variableWorks.add(new MakeVariable("ㅇㅅㅇ", VariableType.Float));
        variableWorks.add(new MakeVariable("ㅇㅆㅇ", VariableType.Double));

        variableWorks.add(new MakeVariable("oio", VariableType.Integer));
        variableWorks.add(new MakeVariable("oIo", VariableType.Long));
        variableWorks.add(new MakeVariable("obo", VariableType.Boolean));
        variableWorks.add(new MakeVariable("oCo", VariableType.String));
        variableWorks.add(new MakeVariable("oco", VariableType.Character));
        variableWorks.add(new MakeVariable("ofo", VariableType.Float));
        variableWorks.add(new MakeVariable("oFo", VariableType.Double));

        variableWorks.add(new MakeListVariable("ㄹㅈㄹ", VariableType.Integer));
        variableWorks.add(new MakeListVariable("ㄹㅉㄹ", VariableType.Long));
        variableWorks.add(new MakeListVariable("ㄹㅂㄹ", VariableType.Boolean));
        variableWorks.add(new MakeListVariable("ㄹㅁㄹ", VariableType.String));
        variableWorks.add(new MakeListVariable("ㄹㄱㄹ", VariableType.Character));
        variableWorks.add(new MakeListVariable("ㄹㅅㄹ", VariableType.Float));
        variableWorks.add(new MakeListVariable("ㄹㅆㄹ", VariableType.Double));

        variableWorks.add(new MakeListVariable("lil", VariableType.Integer));
        variableWorks.add(new MakeListVariable("lIl", VariableType.Long));
        variableWorks.add(new MakeListVariable("lbl", VariableType.Boolean));
        variableWorks.add(new MakeListVariable("lCl", VariableType.String));
        variableWorks.add(new MakeListVariable("lcl", VariableType.Character));
        variableWorks.add(new MakeListVariable("lfl", VariableType.Float));
        variableWorks.add(new MakeListVariable("lFl", VariableType.Double));

        priorityPrintWorks.add(new PriorityPrint());
        priorityPrintWorks.add(new PriorityPrintln());

        printWorks.add(new Print());
        printWorks.add(new Println());

        calculationWorks.add(new Calculation());
        calculationWorks.add(new NumberCalculation());
        calculationWorks.add(new StringCalculation());
        calculationWorks.add(new BoolCalculation());

        loopWorks.add(new For());
        loopWorks.add(new If());
        loopWorks.add(new ForEach());

        httpWorks.add(new HTMLVariable());
        httpWorks.add(new StartServer());

        randomWorks.add(new RandomBoolean());
        randomWorks.add(new RandomDouble());
        randomWorks.add(new RandomFloat());
        randomWorks.add(new RandomInteger());
        randomWorks.add(new RandomLong());

    }

    private void reset() {
        Setting.total.setLength(0);
        HttpRepository.pathMap.clear();
        HttpRepository.POST.clear();
        HttpRepository.GET.clear();
        HttpRepository.partMap.clear();
        variableWorks.clear();
        priorityPrintWorks.clear();
        printWorks.clear();
        calculationWorks.clear();
        loopWorks.clear();
        httpWorks.clear();
        randomWorks.clear();
    }
}
