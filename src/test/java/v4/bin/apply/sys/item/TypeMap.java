package v4.bin.apply.sys.item;

import bin.apply.sys.item.HpMap;
import bin.exception.VariableException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static bin.token.MergeToken.access;

public class TypeMap extends HashMap<String, HpMap> implements Map<String, HpMap> {
    @Override
    public HpMap get(Object key) {
        if (super.containsKey(key)) return super.get(key);
        else {
            if (Type.isType(key.toString())) {
                HpMap map = new HpMap(key.toString());
                super.put(key.toString(), map);
                return map;
            } else throw new VariableException().noDefine();
        }
    }

    // 변수를 생성하는 로직
    public void createVariable(String type, String name, String value) {
        if (checkVariable(name)) throw new VariableException().sameVariable();
        else {
            if (value != null) super.get(type).put(name, value);
        }
    }

    // 존재하는 변수명일때 true, 존재하지 않는 변수일때 false 반환
    public boolean checkVariable(String variableName) {
        return super.values()
                .stream()
                .anyMatch(v -> v.containsKey(variableName));
    }

    // 변수 값 가져오는 로직
    public Object getValue(String variableName) {
        for (HpMap map : super.values()) {
            if (map.containsKey(variableName)) return map.get(variableName);
        }
        throw new VariableException().localNoVariable();
    }

    // 변수 타입, 변수명, 변수값을 반환하는 로직
    public Map.Entry<String, HpMap> getMap(String variableName) {
        for (Map.Entry<String, HpMap> map : super.entrySet()) {
            if (map.getValue().containsKey(variableName)) return map;
        }
        throw new VariableException().localNoVariable();
    }

    // 변수 타입 확인하고 가져오는 로직
    public Object getCheckValue(String type, String name) {
        // 존재하는지 확인하는 확인하는 로직
        if (this.checkVariable(name)) {
            // 해당 타입에 변수가 존재하는지 확인하는 로직
            if (this.get(type).containsKey(name)) {
                return this.get(type).get(name);
            } else throw new VariableException().typeMatch();
        } else throw new VariableException().localNoVariable();
    }

    public static VariableTypeName getTypeName(String variableName, LinkedList<TypeMap> repositoryArray) {
        int count = access(variableName, repositoryArray.size());
        if (count == -1) throw new VariableException().localNoVariable();
        variableName = variableName.substring(count);
        TypeMap repository = repositoryArray.get(count);
        for (Entry<String, HpMap> rep : repository.entrySet()) {
            if (rep.getValue().containsKey(variableName))
                return new VariableTypeName(rep.getKey(), variableName, rep.getValue());
        }
        throw new VariableException().noDefine();
    }

    // 변수 타입과 값을 반환하는 로직
    public static Entry<String, HpMap> getTypeValue(String variableName, LinkedList<TypeMap> repositoryArray) {
        int count = access(variableName, repositoryArray.size());
        if (count == -1) throw new VariableException().localNoVariable();
        variableName = variableName.substring(count);
        TypeMap repository = repositoryArray.get(count);
        return repository.getMap(variableName);
    }

    // name: 변수명
    public static Object getValue(String name, LinkedList<TypeMap> repositoryArray) {
        int count = access(name, repositoryArray.size());
        if (count == -1) throw new VariableException().localNoVariable();
        name = name.substring(count);
        TypeMap repository = repositoryArray.get(count);
        return repository.getValue(name);
    }

    @Override
    public HpMap put(String key, HpMap value) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends HpMap> m) {
    }

    @Override
    public HpMap putIfAbsent(String key, HpMap value) {
        return null;
    }
}
