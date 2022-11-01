package cos.poison;

import bin.apply.Repository;
import bin.token.LoopToken;
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

public class Poison extends StartWorkV3 implements LoopToken, PoisonRepository, Repository {
    public static HttpServerManager httpServerManager = new HttpServerManager();
    public static VariableHTML variableHTML = new VariableHTML(1);

    public Poison(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
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
        createReturnWorks(poisonReturnWorks, POISON, ISEMPTY_COOKIE.replace("\\", ""), isEmptyCookie);
    }
}
