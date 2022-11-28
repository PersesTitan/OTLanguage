package cos.poison.setting;

import bin.exception.VariableException;
import cos.poison.controller.HttpServerManager;
import cos.poison.root.VariableHTML;
import cos.poison.run.replace.GetCookie;
import cos.poison.run.replace.GetUrlParam;
import cos.poison.run.replace.IsEmptyCookie;
import cos.poison.run.start.DeleteCookie;
import cos.poison.run.start.Redirect;
import cos.poison.run.start.SetCookie;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.apply.sys.item.SystemSetting.createReturnWorks;
import static bin.apply.sys.item.SystemSetting.createStartWorks;
import static bin.check.VariableCheck.isInteger;
import static bin.token.LoopToken.*;
import static cos.poison.PoisonRepository.*;

public class PoisonCreate extends StartWorkV3 {
    public static HttpServerManager httpServerManager = new HttpServerManager();
    public static VariableHTML variableHTML = new VariableHTML(1);

    public PoisonCreate(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        create();
        int count = params.length;
        if (count == 1 && params[0].isEmpty()) httpServerManager.createServer();
        else if (count == 1) {
            if (isInteger(params[0])) httpServerManager.createServer(Integer.parseInt(params[0]));
            else httpServerManager.createServer(params[0]);
        } else {
            if (isInteger(params[1])) httpServerManager.createServer(params[0], Integer.parseInt(params[1]));
            else throw new VariableException().typeMatch();
        }
    }

    private void create() {
        final SetCookie setCookie = new SetCookie(2, 3, 4);
        final DeleteCookie deleteCookie = new DeleteCookie(1, 2);
        final Redirect redirect = new Redirect(1);
        final GetCookie getCookie = new GetCookie(1);
        final GetUrlParam getUrlParam = new GetUrlParam(1);
        final IsEmptyCookie isEmptyCookie = new IsEmptyCookie(1);

        poisonStartList.add(setCookie);
        poisonStartList.add(deleteCookie);
        poisonStartList.add(redirect);
        poisonStartList.add(getCookie);
        poisonStartList.add(getUrlParam);
        poisonStartList.add(isEmptyCookie);

        createStartWorks(poisonStartWorks, POISON, SET_COOKIE, setCookie);
        createStartWorks(poisonStartWorks, POISON, DELETE_COOKIE, deleteCookie);
        createStartWorks(poisonStartWorks, POISON, REDIRECT, redirect);

        createReturnWorks(poisonReturnWorks, POISON, GET_COOKIE, getCookie);
        createReturnWorks(poisonReturnWorks, POISON, GET_URL, getUrlParam);
        createReturnWorks(poisonReturnWorks, POISON, ISEMPTY_COOKIE, isEmptyCookie);
    }
}
