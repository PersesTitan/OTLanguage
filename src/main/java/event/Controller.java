package event;

import custom.registration.controller.DefineCustomClass;
import custom.registration.controller.DefineMainMethod;
import custom.tool.CustomGetVariable;
import custom.tool.CustomSetVariable;
import event.list.ListSetting;
import http.controller.HttpGetPost;
import http.controller.PortVariable;
import origin.consol.controller.ConsoleScanner;
import origin.loop.define.Bracket;
import origin.variable.controller.GetVariable;
import origin.variable.controller.SetVariable;
import system.start.Finish;
import system.work.FinishWork;

public interface Controller extends ListSetting {
    //변수
    GetVariable getVariable = new GetVariable();
    SetVariable setVariable = new SetVariable();
    ConsoleScanner consoleScanner = new ConsoleScanner("ㅅㅇㅅ"); //출력, 입력
    Bracket bracket = new Bracket(); //괄호를 토큰으로 변환
    PortVariable portVariable = new PortVariable(); //html 전용 변수
    HttpGetPost httpGetPost = new HttpGetPost(); //post, get 값 가져오기
    //시스템
    FinishWork finishWork = new Finish("ㄲㅌㄲ"); //강제 종료
    // 커스텀 클래스
    DefineCustomClass defineCustomClass = new DefineCustomClass("ㅋㅅㅋ");
    DefineMainMethod defineMainMethod = new DefineMainMethod("ㅁㅅㅁ");
    CustomGetVariable customGetVariableWork = new CustomGetVariable();
    CustomSetVariable customSetVariableWork = new CustomSetVariable();
}
