package cos.poison;

import bin.apply.Repository;
import bin.apply.item.CodesItem;
import bin.apply.item.ParamItem;
import bin.apply.repository.function.OSConsumer;
import bin.apply.repository.variable.TypeMap;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.parser.param.ParserParamItem;
import bin.token.EditToken;
import bin.token.KlassToken;
import bin.token.Token;
import bin.token.check.CheckToken;
import cos.file.FileItem;
import cos.file.FileToken;
import cos.poison.tool.RexMap;
import cos.poison.type.DataType;
import cos.poison.type.MethodType;
import cos.poison.type.ResponseType;
import cos.poison.type.URLType;
import work.LoopWork;

import java.util.*;
import java.util.regex.Matcher;

public class PoisonMethod extends LoopWork {
    private final MethodType METHOD;
    private ParamItem[] ITEM;

    public PoisonMethod(MethodType METHOD, int size) {
        super(PoisonToken.POISON, false, 1,
                new String[]{KlassToken.STRING_VARIABLE, FileToken.FILE},
                new String[size]
        );
        Arrays.fill(getPARAMS(), KlassToken.STRING_VARIABLE);
        this.METHOD = METHOD;
    }

    @Override
    public void checkParams(String[] params) {}

    @Override
    public void loop(Object klass, ParamToken[] params, CodesItem CODE, ParamItem[] ITEM) {
        this.ITEM = ITEM;
        this.checkType(ITEM);
        loopItem(getKlassItem(klass), casting(params), v -> CODE.start());
    }

    @Override
    protected void loopItem(Object klass, Object[] params, OSConsumer consumer) {
        PoisonItem item = (PoisonItem) klass;
        if (params.length == 0) throw PoisonException.PARAMS_SIZE_ERROR.getThrow(params.length);
        ResponseType response = ResponseType.TEXT;
        DataType data = DataType.URL;
        int i = 0;
        String path = params[i++].toString();
        for (; i<params.length; i++) {
            String token = params[i].toString();
            if (CheckToken.startWith(token, PoisonToken.RESPONSE_TYPE))
                response = ResponseType.getType(token.substring(1));
            else if (CheckToken.startWith(token, PoisonToken.DATA_TYPE)) {
                data = DataType.getType(token.substring(1));
            } else break;
        }
        // 변수명을 저장하는 임시 저장소
        Set<String> name = new HashSet<>();
        Map<String, String> rexMap = new HashMap<>();
        Map<ParamItem, ParamToken> dataMap = new HashMap<>();
        for (; i<params.length; i++) {
            // <타입> <변수명>(:<기본값>)
            StringTokenizer tokenizer = new StringTokenizer(params[i].toString());
            if (tokenizer.countTokens() >= 2) {
                String type = tokenizer.nextToken();
                String value = tokenizer.nextToken("").stripLeading();
                if (value.isEmpty()) throw VariableException.NO_DEFINE_NAME.getThrow(value);
                if (value.indexOf(Token.PUT)>=0) {
                    // type check
                    if (!CheckToken.isKlass(type)) throw VariableException.NO_DEFINE_TYPE.getThrow(type);
                    // name, default
                    String[] tokens = EditToken.split(value, Token.PUT);
                    // name duplication check
                    if (name.contains(tokens[0])) throw VariableException.DEFINE_NAME.getThrow(tokens[0]);
                    else name.add(tokens[0]);
                    ParamItem paramItem = new ParamItem(type, tokens[0]);
                    if (tokens[1].isEmpty()) dataMap.put(paramItem, null);
                    else dataMap.put(paramItem, new ParserParamItem(tokens[1]).start());
                } else {
                    // type check
                    if (!KlassToken.ORIGINS.contains(type)) throw PoisonException.URL_TYPE_ERROR.getThrow(type);
                    // name check
                    char[] cs = value.toCharArray();
                    if ('A' > cs[0] || cs[0] > 'z' || ('Z' < cs[0] && 'a' > cs[0]))
                        throw PoisonException.URL_NAME_ERROR.getThrow(value);
                    for (char c : cs) if ('0' > c || 'z' < c || ('9' < c && 'A' > c) || ('Z' < c && 'a' > c))
                        throw PoisonException.URL_NAME_ERROR.getThrow(value);
                    // name duplication check
                    if (name.contains(value)) throw VariableException.DEFINE_NAME.getThrow(value);
                    else name.add(value);
                    // save (key: name, value: type)
                    rexMap.put(value, type);
                }
            } else throw MatchException.GRAMMAR_ERROR.getThrow(params[i]);
        }

        // final data set
        final DataType finalData = data;
        final ResponseType finalResponse = response;

        // finally set define
        ParamItem[] PARAMS = new ParamItem[dataMap.size()];
        ParamToken[] TOKENS = new ParamToken[dataMap.size()];
        i = 0;
        for (Map.Entry<ParamItem, ParamToken> entry : dataMap.entrySet()) {
            PARAMS[i] = entry.getKey();
            TOKENS[i++] = entry.getValue();
        }

        if (rexMap.isEmpty()) {
            item.put(METHOD, path, (exchange, status) -> {
                Map<String, Object> map = METHOD.getParams(exchange, finalData);
                TypeMap repository = new TypeMap() {{
                    if (ITEM.length == 1) createLoop(ITEM[0]);
                    for (int i = 0; i<PARAMS.length; i++) {
                        String name = PARAMS[i].name();
                        if (TOKENS[i] != null) create(PARAMS[i], map.getOrDefault(name, TOKENS[i].replace()));
                        else if (map.containsKey(name)) create(PARAMS[i], map.get(name));
                    }
                }};
                Repository.repositoryArray.addFirst(repository);
                try {
                    consumer.accept();
                } finally {
                    Repository.repositoryArray.removeFirst();
                }
                // set content type
                exchange.getResponseHeaders().add("Content-Type", finalResponse.getMime());
                METHOD.print(path, map.isEmpty() ? "" : map.toString());
                // read put file
                String responseData = "";
                if (ITEM.length == 1) {
                    Object value = repository.find(ITEM[0].name());
                    if (ITEM[0].type().equals(FileToken.FILE)) responseData = ((FileItem) value).readAll();
                    else responseData = EditToken.toString(value);
                }
                return finalResponse.getBody(responseData);
            });
        } else {
            URLType[] TYPES = new URLType[rexMap.size()];
            String[] NAMES = new String[rexMap.size()];
            i = 0;
            for (Map.Entry<String, String> entry : rexMap.entrySet()) {
                NAMES[i++] = entry.getKey();
                TYPES[i] = URLType.getType(entry.getValue());
            }

            Matcher matcher = RexMap.makeMatcher(path, rexMap);
            // set rex function
            item.put(METHOD, matcher, (exchange, status) -> {
                Map<String, Object> map = METHOD.getParams(exchange, finalData);
                TypeMap repository = new TypeMap() {{
                    if (ITEM.length == 1) createLoop(ITEM[0]);
                    for (int i = 0; i<TYPES.length; i++)
                        create(TYPES[i].type, NAMES[i], TYPES[i].value(matcher.group(NAMES[i])));
                    for (int i = 0; i<PARAMS.length; i++) {
                        String name = PARAMS[i].name();
                        if (TOKENS[i] != null) create(PARAMS[i], map.getOrDefault(name, TOKENS[i].replace()));
                        else if (map.containsKey(name)) create(PARAMS[i], map.get(name));
                    }
                }};
                Repository.repositoryArray.addFirst(repository);
                try {
                    consumer.accept();
                } finally {
                    Repository.repositoryArray.removeFirst();
                }
                // set content type
                exchange.getResponseHeaders().add("Content-Type", finalResponse.getMime());
                METHOD.print(exchange.getRequestURI().getPath(), map.isEmpty() ? "" : map.toString());
                // read put file
                String responseData = "";
                if (ITEM.length == 1) {
                    Object value = repository.find(ITEM[0].name());
                    if (ITEM[0].type().equals(FileToken.FILE)) responseData = ((FileItem) value).readAll();
                    else responseData = EditToken.toString(value);
                }
                return finalResponse.getBody(responseData);
            });
        }
    }
}
