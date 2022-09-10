package origin.variable.model;

import custom.use.controller.UseCustomClass;
import custom.use.controller.UseCustomString;
import custom.use.controller.UseCustomVoid;
import etc.reader.define.FileWork;
import http.model.HttpWork;
import input.conroller.mouse.define.MousePositionWork;
import math.model.CalculationWork;
import math.model.RandomWork;
import origin.consol.define.PrintWork;
import origin.consol.define.PriorityPrintWork;
import origin.loop.model.LoopWork;
import origin.variable.define.generic.GenericDelete;
import origin.variable.json.controller.json.define.MakeJsonWork;
import system.work.SystemWork;
import system.work.ThreadWork;

import java.util.*;

public interface Repository {
    List<VariableWork> variableWorks = new ArrayList<>(); //실행할 메소드 저장
    List<PriorityPrintWork> priorityPrintWorks = new ArrayList<>(); //강제 출력 메소드 저장
    List<PrintWork> printWorks = new ArrayList<>(); //출력 메소드 저장
    List<CalculationWork> calculationWorks = new ArrayList<>(); //계산 메소드 저장
    List<LoopWork> loopWorks = new ArrayList<>(); //루프 메소드 저장
    List<HttpWork> httpWorks = new ArrayList<>(); //Http
    List<RandomWork> randomWorks = new ArrayList<>(); //램던 메소드 저장
    List<SystemWork> systemWorks = new ArrayList<>(); //시스템 메소드 저장
    List<ThreadWork> threadWorks = new ArrayList<>(); //쓰레드 관련 메소드 저장
    List<MousePositionWork> mousePositionWorks = new ArrayList<>(); //마우스 위치 반환
    List<FileWork> fileWorks = new ArrayList<>(); //파일 관련 메소드 저장
    List<GenericDelete> genericDeletes = new ArrayList<>(); // 삭제 메소드
    List<MakeJsonWork> makeJsonWorks = new ArrayList<>(); // json 메소드

    Map<String, Map<String, Object>> repository = new HashMap<>(); //변수 저장
    Map<String, String> uuidMap = new HashMap<>(); //괄호 값 저장하는 곳
    Set<String> set = new HashSet<>(); //변수명 저장
    Set<String> classList = new HashSet<>(); //클래스 저장하는 곳

    Set<String> classNames = new HashSet<>(); //클래스 이름
    Set<String> methods = new HashSet<>(); //메소드 이름
    Set<UseCustomClass> customClass = new HashSet<>(); //클래스 메소드
    //메소드 저장소
    Set<UseCustomString> useCustomStrings = new HashSet<>();
    Set<UseCustomVoid> useCustomVoids = new HashSet<>();
}