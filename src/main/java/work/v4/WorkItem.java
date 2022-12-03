package work.v4;

import bin.check.VariableType;

public record WorkItem(int count, VariableType...types) implements Casting {
    public Object[] getParams(String[] params) {
        Object[] objects = new Object[types.length];
        for (int i = 0; i < this.types.length; i++) {
            objects[i] = casting(this.types[i], params[i]);
        }
        return objects;
    }
}
