package cos.poison;

import bin.apply.Repository;
import bin.token.LoopToken;
import cos.http.controller.HttpMethod;
import cos.poison.controller.HttpServerManager;
import cos.poison.method.PoisonMethod;
import cos.poison.root.VariableHTML;
import cos.poison.run.replace.GetCookie;
import cos.poison.run.replace.GetUrlParam;
import cos.poison.run.start.DeleteCookie;
import cos.poison.run.start.Redirect;
import cos.poison.run.start.SetCookie;
import cos.poison.setting.PoisonCreate;
import cos.poison.setting.PoisonStart;
import work.StartWork;
import work.v3.StartWorkV3;

import javax.servlet.http.Cookie;
import java.util.LinkedList;
import java.util.Map;

import static bin.apply.Repository.startWorks;

public class Poison extends StartWorkV3 implements StartWork, LoopToken, PoisonRepository, Repository {
    private final String className;
    public static HttpServerManager httpServerManager = new HttpServerManager();
    public static VariableHTML variableHTML = new VariableHTML(MODEL);

    public Poison(String className) {
        this.className = className;
        first();
    }

    @Override
    public boolean check(String line) {
        return false;
    }

    @Override
    public void start(String line, String origen,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {}

    @Override
    public void first() {
        startWorks.add(new PoisonCreate(className));
        startWorks.add(new PoisonStart(className));
        startWorks.add(new PoisonMethod(className, LoopToken.POISON_GET, HttpMethod.GET));
        startWorks.add(new PoisonMethod(className, LoopToken.POISON_POST, HttpMethod.POST));
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        final SetCookie setCookie = new SetCookie(2, 3, 4);
        final DeleteCookie deleteCookie = new DeleteCookie(1, 2);
        final Redirect redirect = new Redirect(1);
        final GetCookie getCookie = new GetCookie(1);
        final GetUrlParam getUrlParam = new GetUrlParam(1);

        poisonStartList.add(setCookie);
        poisonStartList.add(deleteCookie);
        poisonStartList.add(redirect);
        poisonStartList.add(getCookie);
        poisonStartList.add(getUrlParam);

        createStartWorks(poisonStartWorks, POISON, SET_COOKIE, setCookie);
        createStartWorks(poisonStartWorks, POISON, DELETE_COOKIE, deleteCookie);
        createStartWorks(poisonStartWorks, POISON, REDIRECT, redirect);

        createReturnWorks(poisonReturnWorks, POISON, GET_COOKIE, getCookie);
        createReturnWorks(poisonReturnWorks, POISON, GET_URL, getUrlParam);
    }
}
