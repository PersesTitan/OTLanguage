package bin.orign.variable.controller

class ListObj {
    static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("b")
        list.add("a")
        list.add("c")

        println join(list, " ")
        println max(list)
        println max("[0, 2, 3, 6]")
    }

    // 삭제
    static def remove(Object list, Object value) {
        Eval.xy(list, value, "x.remove(y)")
    }

    // 최댓값
    static def max(Object list) {
        return Eval.me("list", list, "list.max()")
    }

    static def max(String list) {
        return Eval.me(list + ".max()")
    }

    // 최솟값
    static def min(Object list) {
        return Eval.me("list", list, "list.min()")
    }

    static def min(String list) {
        return Eval.me(list + ".min()")
    }

    // JOIN
    static def join(Object list, Object value) {
        return Eval.xy(list, value, "x.join(y)")
    }

    static def join(String list, Object value) {
        return Eval.x(value, list + ".join(x)")
    }

    // COUNT
    static def count(Object list, Object value) {
        return Eval.xy(list, value, "x.count(y)")
    }

    static def count(String list, Object value) {
        return Eval.x(value, list + ".count(x)")
    }


}
