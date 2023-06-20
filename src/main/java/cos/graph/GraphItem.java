package cos.graph;

import bin.apply.item.Item;

class GraphItem implements Item {


    @Override
    public String toString() {
        return itemToString(GraphToken.GRAPH);
    }
}