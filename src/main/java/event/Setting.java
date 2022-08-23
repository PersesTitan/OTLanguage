package event;

import custom.use.controller.UseCustomClass;
import custom.use.controller.UseCustomString;
import custom.use.controller.UseCustomVoid;
import etc.reader.controller.FileRead;
import etc.reader.define.FileWork;
import event.db.DBSetting;
import http.controller.HTMLVariable;
import http.controller.StartServer;
import http.items.Color;
import http.items.HttpRepository;
import http.model.HttpWork;
import input.conroller.mouse.controller.MousePositionX;
import input.conroller.mouse.controller.MousePositionXY;
import input.conroller.mouse.controller.MousePositionY;
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
import origin.loop.controller.NewForEach;
import origin.loop.model.LoopWork;
import origin.variable.controller.MakeListVariable;
import origin.variable.controller.MakeVariable;
import origin.variable.controller.map.DeleteAllMapVariable;
import origin.variable.controller.map.DeleteMapVariable;
import origin.variable.controller.map.MakeMapVariable;
import origin.variable.controller.set.DeleteAllSetVariable;
import origin.variable.controller.set.DeleteSetVariable;
import origin.variable.controller.set.MakeSetVariable;
import origin.variable.define.VariableType;
import origin.variable.json.controller.json.controller.MakeJsonArray;
import origin.variable.json.controller.json.controller.MakeJsonObject;
import origin.variable.json.controller.json.define.JsonRepository;
import origin.variable.json.controller.json.set.SetJsonArray;
import origin.variable.json.controller.json.set.SetJsonObject;
import origin.variable.model.Repository;
import origin.variable.model.VariableWork;
import system.start.exception.Sleep;
import system.start.exception.TryCatch;
import system.start.keyword.KeyWordRepository;
import system.work.SystemWork;
import system.work.ThreadWork;

public class Setting implements Controller, Repository {
    public final static StringBuffer total = new StringBuffer();
    public final static String variableStyle = "[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z]+[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]*";
    public static String path = "";
    //상속
    private final static DBSetting dBSetting = new DBSetting();

    /**
     * 상속 <br>
     * @see event.db.DBSetting
     */
    public static void start(String line) {
        if (line.isBlank()) return;

        if (finishWork.check(line)) finishWork.start(); //강제 종료
        for (PriorityPrintWork work : priorityPrintWorks) {if (work.check(line)) {work.start(line); return;}} //강제 출력
        line = startString(line);

        if (defineCustomClass.check(line)) {defineCustomClass.start(line); return;} //클래스 생성
        if (defineMainMethod.check(line)) {defineMainMethod.start(line);return;} //메인 메소드 생성

        if (dBSetting.check(line)) {String l = DBSetting.start(line);if (l == null) return;else line = l;} //DB 클래스
        if (httpGetPost.check(line)) {httpGetPost.start(line);return;} //post, get 으로 받은 값 대치
        for (LoopWork work : loopWorks) {if (work.check(line)) {work.start(line); return;}} //for, if
        for (PrintWork work : printWorks) {if (work.check(line)) {work.start(line);return;}} //출력하는 동작
        for (VariableWork work : variableWorks) {if (work.check(line)) {work.start(line, repository, set); return;}} //변수 정의하는 동작
        if (setVariable.check(line)) {setVariable.start(line, repository, set);return;} //변수 값 넣기
        if (deleteListVariable.check(line)) {if (deleteListVariable.start(line) == null) return;} //리스트에 값 삭제
        for (var works : genericDeletes) {if (works.check(line)) {works.start(line, repository); return;}} // 맵, 셋 값 삭제
        for (FileWork works : fileWorks) {if (works.check(line)) {works.start(line); return;}} //파일 관련 동작

        for (UseCustomClass works : customClass) {if (works.check(line)) {works.start(line); return;}} //커스텀 클래스 동작
        for (UseCustomClass works : customClass) { //커스텀 메소드 동작
            for (UseCustomVoid work : works.useCustomVoids) {if (work.check(line)) {work.start(line);return;}}}
        for (UseCustomVoid works : useCustomVoids) {if (works.check(line)) {works.start(line); return;}} //메인 커스텀 메소드 동작

        //http
        if (portVariable.check(line)) {portVariable.start(line);return;} //html 변수 정의하기
        for (HttpWork work : httpWorks) {if (work.check(line)) {work.start(line); return;}}

        //시스템 동작
        for (ThreadWork works : threadWorks) {if (works.check(line)) {works.start(line);return;}} //쓰레드 관련 동작
        for (SystemWork works : systemWorks) {if (works.check(line)) {works.start(line); return;}} //특별 키워드 동작
        System.out.printf("%s경고! %s는 실행되지 않은 라인 입니다.%s\n", Color.YELLOW, line, Color.RESET);
    }

    public static String startString(String line) {
        if (getVariable.check(line)) line = getVariable.start(line, Repository.repository); //변수 불러오기
        if (getListVariable.check(line)) line = getListVariable.start(line); //리스트 변수 가져오기
        if (getMapVariable.check(line)) line = getMapVariable.start(line); // 맵 값 가져오기 >>>
        if (getSetVariable.check(line)) line = getSetVariable.start(line); // 셋 값 가져오기 >
        if (consoleScanner.check(line)) line = consoleScanner.start(line); //값 읽기
        for (RandomWork work : randomWorks) {if (work.check(line)) line = work.start(line);} //램던 값 불러오기
        for (CalculationWork work : calculationWorks) {if (work.check(line)) line = work.start(line);} //게산 (숫자, 블린)
        for (var works : mousePositionWorks) {if (works.check(line)) line = works.start(line);} //마우스 위치 반환

        for (var works : customClass) { // 커스텀 메소드
            for (var work : works.useCustomStrings) {if (work.check(line)) line = work.start(line);}}
        for (UseCustomString works : useCustomStrings) {if (works.check(line)) line = works.start(line);} //커스텀 메인 메소드

        return line;
    }



    //시작할때 변수 이름 및 매소드 새팅 생성
    /**
     * 저장소 <br>
     * @see Repository
     * @see Controller
     */
    public static void firstStart() {
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

        variableWorks.add(new MakeMapVariable("ㅈㅈㅈ", VariableType.Integer));
        variableWorks.add(new MakeMapVariable("ㅈㅉㅈ", VariableType.Long));
        variableWorks.add(new MakeMapVariable("ㅈㅂㅈ", VariableType.Boolean));
        variableWorks.add(new MakeMapVariable("ㅈㅁㅈ", VariableType.String));
        variableWorks.add(new MakeMapVariable("ㅈㄱㅈ", VariableType.Character));
        variableWorks.add(new MakeMapVariable("ㅈㅅㅈ", VariableType.Float));
        variableWorks.add(new MakeMapVariable("ㅈㅆㅈ", VariableType.Double));

        variableWorks.add(new MakeSetVariable("ㄴㅈㄴ", VariableType.Integer));
        variableWorks.add(new MakeSetVariable("ㄴㅉㄴ", VariableType.Long));
        variableWorks.add(new MakeSetVariable("ㄴㅂㄴ", VariableType.Boolean));
        variableWorks.add(new MakeSetVariable("ㄴㅁㄴ", VariableType.String));
        variableWorks.add(new MakeSetVariable("ㄴㄱㄴ", VariableType.Character));
        variableWorks.add(new MakeSetVariable("ㄴㅅㄴ", VariableType.Float));
        variableWorks.add(new MakeSetVariable("ㄴㅆㄴ", VariableType.Double));

        priorityPrintWorks.add(new PriorityPrint("!ㅅㅁㅅ!"));
        priorityPrintWorks.add(new PriorityPrintln("!ㅆㅁㅆ!"));

        printWorks.add(new Print("ㅅㅁㅅ"));
        printWorks.add(new Println("ㅆㅁㅆ"));

        calculationWorks.add(new Calculation());
        calculationWorks.add(new NumberCalculation());
        calculationWorks.add(new StringCalculation());
        calculationWorks.add(new BoolCalculation());

        loopWorks.add(new For());
        loopWorks.add(new If());
        loopWorks.add(new ForEach());
        loopWorks.add(new NewForEach());

        httpWorks.add(new HTMLVariable("<ㅇㅅㅇ<"));
        httpWorks.add(new StartServer("<<ㅇㅅㅇ>>"));

        randomWorks.add(new RandomBoolean("@ㅂ@"));
        randomWorks.add(new RandomDouble("@ㅆ@"));
        randomWorks.add(new RandomFloat("@ㅅ@"));
        randomWorks.add(new RandomInteger("@ㅈ@"));
        randomWorks.add(new RandomLong("@ㅉ@"));

        threadWorks.add(new TryCatch("ㅜㅅㅜ"));
        threadWorks.add(new Sleep("ㅡ_ㅡ"));

        mousePositionWorks.add(new MousePositionX("ㅁㅇㅁ", "ㄱㄹㄱ"));
        mousePositionWorks.add(new MousePositionY("ㅁㅇㅁ", "ㅅㄹㅅ"));
        mousePositionWorks.add(new MousePositionXY("ㅁㅇㅁ", "ㄱㄹㅅ"));

        fileWorks.add(new FileRead("ㅍㅅㅍ"));

        genericDeletes.add(new DeleteAllMapVariable("---!"));
        genericDeletes.add(new DeleteAllSetVariable("-!"));
        genericDeletes.add(new DeleteMapVariable("---"));
        genericDeletes.add(new DeleteSetVariable("-"));

        makeJsonWorks.add(new MakeJsonArray("ㅈㄹㅈ"));
        makeJsonWorks.add(new MakeJsonObject("ㅈㅇㅈ"));
        makeJsonWorks.add(new SetJsonObject("<-"));
        makeJsonWorks.add(new SetJsonArray("<--"));

        classNames.add("ㄷㅇㄷ");
        classNames.add("ㅁㅇㅁ");
        set.add("ㅅㅇㅅ"); //예약어
        KeyWordRepository.add(); //특수 단어 설정
    }

    private static void reset() {
        Setting.total.setLength(0);
        HttpRepository.pathMap.clear();
        HttpRepository.POST.clear();
        HttpRepository.GET.clear();
        HttpRepository.partMap.clear();
        JsonRepository.jsonObjectRepository.clear();
        JsonRepository.jsonArrayRepository.clear();
        variableWorks.clear();
        priorityPrintWorks.clear();
        printWorks.clear();
        calculationWorks.clear();
        loopWorks.clear();
        httpWorks.clear();
        randomWorks.clear();
        classList.clear();
        systemWorks.clear();
        threadWorks.clear();
        classNames.clear();
        customClass.clear();
        useCustomStrings.clear();
        useCustomVoids.clear();
        mousePositionWorks.clear();
        fileWorks.clear();
        genericDeletes.clear();
        makeJsonWorks.clear();
    }
}
