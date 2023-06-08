package bin.exception;

import bin.apply.Setting;
import bin.apply.mode.RunMode;
import bin.token.SepToken;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.StringTokenizer;

@Getter
public class Error extends RuntimeException {
    @Getter(value = AccessLevel.NONE)
    private final StringBuilder message = new StringBuilder("OTLanguage.");
    private final String big, mes, sub;

    public Error(ErrorTool tool, String error) {
        this(tool.getClass().getSimpleName(), tool.getMessage(), tool.getSubMessage().strip(), error);
    }

    public Error(ErrorTool tool, String big, String error) {
        this(big, tool.getMessage(), tool.getSubMessage(), error);
    }

    public Error(String big, String mes, String sub, String error) {
        super(mes);
        this.big = big;
        this.mes = mes;
        this.sub = (sub = sub.strip());
        this.message.append(big).append(": ").append(mes);
        int i = 0;
        StringTokenizer tokenizer = new StringTokenizer(sub, SepToken.LINE);
        while (tokenizer.hasMoreTokens()) {
            this.message.append(SepToken.LINE).append("\totl ").append(tokenizer.nextToken());
            if (i++ == 0 && error != null) this.message.append('(').append(error).append(')');
        }
    }

    public void print() {
        if (RunMode.isNormal()) System.err.println(message);
        else Setting.errorMessage(message.toString());
    }

    public void print(String path) {
        setMessage(path);
        print();
    }

    public void print(String path, String line, int pos) {
        setMessage(path, line, pos);
        print();
    }

    public Error setMessage(String path) {
        message.append(SepToken.LINE).append("\totl Path (").append(path).append(')');
        return this;
    }

    public Error setMessage(String path, String line, int pos) {
        message.append(SepToken.LINE).append("\totl file location where it occurred(").append(path).append(':').append(pos).append(')');
        message.append(SepToken.LINE).append("\totl line that occurred(").append(line).append(")");
        return this;
    }
}
