package bin.apply.work.system;

import bin.exception.SystemException;
import bin.token.EditToken;
import bin.token.KlassToken;
import bin.token.SepToken;
import work.MethodWork;

import java.io.*;

public class Shell extends MethodWork {
    private final String[] command = SepToken.isWindow
            ? new String[]{"cmd.exe", "/c", null}
            : new String[]{"sh", "-c", null};

    public Shell() {
        super(KlassToken.STRING_VARIABLE, KlassToken.SYSTEM, true, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected Object methodItem(Object klassItem, Object[] params) {
        this.command[2] = EditToken.toString(params[0]);
        try {
            StringBuilder builder = new StringBuilder();
            InputStream stream = new ProcessBuilder(command)
                    .directory(new File("").getAbsoluteFile())
                    .start()
                    .getInputStream();
            new BufferedReader(new InputStreamReader(stream))
                    .lines()
                    .forEach(line -> builder.append(line).append(SepToken.LINE));
            return builder.toString();
        } catch (IOException e) {
            throw SystemException.RIGHT_ERROR.getThrow(e.getMessage());
        } finally {
            this.command[2] = null;
        }
    }
}
