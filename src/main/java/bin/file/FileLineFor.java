package bin.file;

import bin.exception.FileException;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.MergeToken;
import org.apache.parquet.Files;
import work.v3.StartWorkV3;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Map;

import static bin.token.VariableToken.STRING_VARIABLE;

public class FileLineFor extends StartWorkV3 implements MergeToken {
    // 1: // ㅍㅅㅍ~ㄹㅍㄹ 파일경로 (test,10,20) <= ㅇㅁㅇ 안녕
    public FileLineFor() {
        super(1);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String[] loops = cutLoop(params[0]);
        String loop = loops[1], filePath = loops[0];
        // ㅍㅅㅍ~ㄹㅍㄹ 파일경로 (test,10,20) <= ㅇㅁㅇ 안녕
        // 파일 이름, 시작, 마지막, 변수 타입, 변수 이름
        String[] loopToken = getLoop(loop);
        if (loopToken.length != 5) throw new MatchException().grammarError();
        else if (!loopToken[3].equals(STRING_VARIABLE)) throw new VariableException().typeMatch();
        variableDefineError(loopToken[4], repositoryArray.get(0));

        File file = new File(filePath);
        checkFile(file); // 파일 상태 확인

        final String variableName = loopToken[4];
        try {
            for (String v : Files.readAllLines(file, StandardCharsets.UTF_8)) {
                repositoryArray.get(0).get(STRING_VARIABLE).put(variableName, v);
                if (loopStart(loopToken[0], loopToken[1], loopToken[2], repositoryArray)) break;
            }
        } catch (IOException e) {
            throw new FileException().noReadError();
        } finally {
            repositoryArray.get(0).get(STRING_VARIABLE).remove(variableName);
        }
    }
}
