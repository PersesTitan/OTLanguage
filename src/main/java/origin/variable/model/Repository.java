package origin.variable.model;

import http.model.HttpWork;
import math.model.CalculationWork;
import math.model.RandomWork;
import origin.consol.define.PrintWork;
import origin.consol.define.PriorityPrintWork;
import origin.loop.model.LoopWork;

import java.util.*;

public interface Repository {
    List<VariableWork> variableWorks = new ArrayList<>(); //실행할 메소드 저장
    List<PriorityPrintWork> priorityPrintWorks = new ArrayList<>(); //강제 출력 메소드 저장
    List<PrintWork> printWorks = new ArrayList<>(); //출력 메소드 저장
    List<CalculationWork> calculationWorks = new ArrayList<>(); //계산 메소드 저장
    List<LoopWork> loopWorks = new ArrayList<>(); //루프 메소드 저장
    List<HttpWork> httpWorks = new ArrayList<>(); //Http
    List<RandomWork> randomWorks = new ArrayList<>(); //램던 메소드 저장
//    List<HttpMoveWork> httpMoveWorks = new ArrayList<>(); //경로 이동 저장소 등록 메소드

    Map<String, Map<String, Object>> repository = new HashMap<>(); //변수 저장
    Set<String> set = new HashSet<>(); //변수명 저장
    Map<String, String> uuidMap = new HashMap<>();
}
