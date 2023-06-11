package bin.token.check;

import bin.token.Token;

interface CheckName {
    // check ex) :~~variable
    static boolean isUseName(String line) {
        if (line.isEmpty()) return false;
        int i = 0;
        char[] cs = line.toCharArray();
        if (cs[i] == Token.PUT) i++;
        if (i >= cs.length) return false;
        while (cs[i] == Token.ACCESS) if (++i >= cs.length) return false;
        if (isName1(cs[i])) i++;
        else return false;
        if (i >= cs.length) return true;
        while (isName2(cs[i])) if (++i >= cs.length) return true;
        return false;
    }

    static boolean isName(String line) {
        return !line.isEmpty()
                && isName1(line.charAt(0))
                && line.chars().allMatch(c -> isName2((char) c));
    }

    private static boolean isName1(char c) {
        return ('ㄱ' <= c && c <= 'ㅣ') || ('가' <= c && c <= '힣') || ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
    }

    private static boolean isName2(char c) {
        return isName1(c) || ('0' <= c && c <= '9') || '-' == c || '_' == c;
    }
}
