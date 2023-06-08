package bin.apply.repository.variable;

import bin.apply.item.ParamItem;
import bin.exception.VariableException;
import bin.token.EditToken;
import bin.token.Token;

import java.util.LinkedList;
import java.util.Map;

public class AccessList extends LinkedList<TypeMap> {
    public AccessList() {
        this.add(new TypeMap());
    }

    public void remove(String type, String name) {
        super.getFirst().get(type).remove(name);
    }

    public void remove(ParamItem paramItem) {
        this.remove(paramItem.type(), paramItem.name());
    }

    public void create(ParamItem paramItem, Object value) {
        super.getFirst().create(paramItem, value);
    }

    public void create(ParamItem paramItem, Object value, int HP) {
        super.getFirst().create(paramItem, value, HP);
    }

    public void create(String type, String name, Object value) {
        super.getFirst().create(type, name, value);
    }

    public void create(String type, String name, Object value, int HP) {
        super.getFirst().create(type, name, value, HP);
    }

    public void createLoop(ParamItem paramItem) {
        super.getFirst().createLoop(paramItem);
    }

    public void updateLoop(ParamItem item, Object value) {
        if (value == null) throw VariableException.VALUE_ERROR.getThrow(null);
        super.getFirst().updateLoop(item, value);
    }

    public void update(int access, String type, String name, Object value) {
        if (this.isLevel(access)) {
            super.get(access).update(type, name, value);
        } else {
            String message = String.valueOf(Token.ACCESS).repeat(access) + name;
            throw VariableException.ACCESS_ERROR.getThrow(message);
        }
    }

    public void update(ParamItem paramItem, Object value) {
        this.update(paramItem.type(), paramItem.name(), value);
    }

    public void update(String name, Object value) {
        int access = EditToken.getAccess(name);
        if (this.isLevel(access)) {
            super.get(access).update(name.substring(access), value);
        } else throw VariableException.ACCESS_ERROR.getThrow(name);
    }

    public void update(String type, String name, Object value) {
        int access = EditToken.getAccess(name);
        if (this.isLevel(access)) {
            super.get(access).update(type, name.substring(access), value);
        } else throw VariableException.ACCESS_ERROR.getThrow(name);
    }

    public HpMap getMap(String name) {
        int access = EditToken.getAccess(name);
        if (this.isLevel(access)) return super.get(access).getMap(name);
        else throw VariableException.ACCESS_ERROR.getThrow(name);
    }

    public HpMap getMap(int access, String name) {
        if (this.isLevel(access)) return super.get(access).getMap(name);
        else throw VariableException.ACCESS_ERROR.getThrow(name);
    }

    public Object get(String type, String name) {
        int access = EditToken.getAccess(name);
        if (this.isLevel(access)) {
            return super.get(access).find(type, name);
        } else throw VariableException.ACCESS_ERROR.getThrow(name);
    }

    public Object get(String name) {
        int access = EditToken.getAccess(name);
        if (this.isLevel(access)) {
            return super.get(access).find(name.substring(access));
        } else throw VariableException.ACCESS_ERROR.getThrow(name);
    }

    public boolean find(String name) {
        int access = EditToken.getAccess(name);
        if (this.isLevel(access)) {
            return super.get(access).contains(name.substring(access));
        } else throw VariableException.ACCESS_ERROR.getThrow(name);
    }

    public boolean find(int access, String name) {
        return this.isLevel(access) && super.get(access).contains(name);
    }

    // return Type, Value
    public Map.Entry<String, Object> getEntry(String name) {
        int access = EditToken.getAccess(name);
        if (this.isLevel(access)) return super.get(access).getEntry(name.substring(access));
        else throw VariableException.ACCESS_ERROR.getThrow(name);
    }

    private boolean isLevel(int level) {
        return super.size() > level;
    }
}