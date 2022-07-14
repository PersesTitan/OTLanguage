package event;

import http.controller.HttpGetPost;
import http.controller.PortVariable;
import origin.consol.controller.ConsoleScanner;
import origin.loop.define.Bracket;
import origin.variable.controller.GetVariable;
import origin.variable.controller.SetVariable;

public interface Controller {
    //변수
    GetVariable getVariable = new GetVariable();
    SetVariable setVariable = new SetVariable();
    //출력, 입력
    ConsoleScanner consoleScanner = new ConsoleScanner();
    //괄호를 토큰으로 변환
    Bracket bracket = new Bracket();
    //html 전용 변수
    PortVariable portVariable = new PortVariable();
    //post, get 값 가져오기
    HttpGetPost httpGetPost = new HttpGetPost();
}
