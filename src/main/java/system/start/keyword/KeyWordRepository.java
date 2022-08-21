package system.start.keyword;

import event.Setting;
import http.items.Color;
import origin.variable.model.Repository;

import java.util.Map;

public interface KeyWordRepository extends Repository {
    String seeDefineVariable = "정의된 변수 보기";
    String seeDefineClass = "정의된 클래스 보기";
    String codeReset = "코드 초기화";
    /**
     * @see event.Setting
     */
    static void add() {
        // 정의된 변수 보기
        systemWorks.add(new SpecialKeyword(seeDefineVariable) {
            @Override
            public void start(String line) {
                String[] varKindKor = {
                        "ㅇㅈㅇ", "ㅇㅉㅇ", "ㅇㅂㅇ", "ㅇㅁㅇ", "ㅇㄱㅇ", "ㅇㅅㅇ", "ㅇㅆㅇ",
                        "ㄹㅈㄹ", "ㄹㅉㄹ", "ㄹㅂㄹ", "ㄹㅁㄹ", "ㄹㄱㄹ", "ㄹㅅㄹ", "ㄹㅆㄹ"};
                String[] varKindEng = {
                        "oio", "oIo", "obo", "oCo", "oco", "ofo", "oFo",
                        "lil", "lIl", "lbl", "lCl", "lcl", "lfl", "lFl"};

                Map<String, Map<String, Object>> repository = Repository.repository;
                StringBuilder builder = new StringBuilder(Color.CYAN_BOLD);
                for (int i = 0; i< varKindKor.length; i++) {
                    builder.append(varKindKor[i]).append(", ").append(varKindEng[i]);
                    builder.append(" = ").append("[");
                    for (String key : repository.get(varKindKor[i]).keySet()) {
                        builder.append(key).append(" = ").append(repository.get(varKindKor[i]).get(key));
                        builder.append(", ");
                    }
                    for (String key : repository.get(varKindEng[i]).keySet()) {
                        builder.append(key).append(" = ").append(repository.get(varKindEng[i]).get(key));
                        builder.append(", ");
                    }
                    int count = repository.get(varKindKor[i]).size() + repository.get(varKindEng[i]).size();
                    if (count != 0) builder.delete(builder.length()-2, builder.length());
                    builder.append("]").append("\n");
                }
                builder.deleteCharAt(builder.length() - 1);
                builder.append(Color.RESET);
                System.out.println(builder);
            }
        });

        systemWorks.add(new SpecialKeyword(codeReset) {
            @Override
            public void start(String line) {
                Setting.firstStart();
            }
        });

        systemWorks.add(new SpecialKeyword(seeDefineClass) {
            @Override
            public void start(String line) {
                StringBuilder builder = new StringBuilder(Color.CYAN_BOLD + "[");
                Repository.classNames.forEach(v -> builder.append(v).append(", "));
                builder.delete(builder.length()-2, builder.length());
                builder.append("]"+Color.RESET);
                System.out.println(builder);
            }
        });
    }
}
