package bin.apply.line.loop;

import bin.apply.Repository;
import bin.apply.item.CodesItem;
import bin.apply.item.ParamItem;
import bin.apply.line.LineTool;
import bin.exception.SystemException;
import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.token.CastingToken;
import work.ResetWork;

public class ForPutLine implements LineTool {
    private final ParamToken A, B, C;
    private final CodesItem CODE;
    private final ParamItem VARIABLE;

    public ForPutLine(ParamToken A, ParamToken B, ParamToken C, CodesItem CODE, String TYPE, String NAME) {
        switch (TYPE) {
            case ResetWork.i, ResetWork.l, ResetWork.f, ResetWork.d -> {
                this.VARIABLE = new ParamItem(TYPE, NAME);
                this.CODE = CODE;
                this.A = A;
                this.B = B;
                this.C = C;
            }
            default -> throw VariableException.TYPE_ERROR.getThrow(TYPE);
        }
    }

    @Override
    public void startItem() {
        switch (VARIABLE.type()) {
            case ResetWork.i -> {
                int a = CastingToken.getInt(A.replace());
                int b = CastingToken.getInt(B.replace());
                int c = CastingToken.getInt(C.replace());
                try {
                    Repository.repositoryArray.create(VARIABLE, a);
                    for (int i = a; i < b; i+=c) {
                        Repository.repositoryArray.update(VARIABLE, i);
                        CODE.start();
                    }
                } finally {
                    Repository.repositoryArray.remove(VARIABLE);
                }
            }
            case ResetWork.l -> {
                long a = CastingToken.getLong(A.replace());
                long b = CastingToken.getLong(B.replace());
                long c = CastingToken.getLong(C.replace());
                try {
                    Repository.repositoryArray.create(VARIABLE, a);
                    for (long i = a; i < b; i+=c) {
                        Repository.repositoryArray.update(VARIABLE, i);
                        CODE.start();
                    }
                } finally {
                    Repository.repositoryArray.remove(VARIABLE);
                }
            }
            case ResetWork.f -> {
                float a = CastingToken.getFloat(A.replace());
                float b = CastingToken.getFloat(B.replace());
                float c = CastingToken.getFloat(C.replace());
                try {
                    Repository.repositoryArray.create(VARIABLE, a);
                    for (float i = a; i < b; i+=c) {
                        Repository.repositoryArray.update(VARIABLE, i);
                        CODE.start();
                    }
                } finally {
                    Repository.repositoryArray.remove(VARIABLE);
                }
            }
            case ResetWork.d -> {
                double a = CastingToken.getDouble(A.replace());
                double b = CastingToken.getDouble(B.replace());
                double c = CastingToken.getDouble(C.replace());
                try {
                    Repository.repositoryArray.create(VARIABLE, a);
                    for (double i = a; i < b; i+=c) {
                        Repository.repositoryArray.update(VARIABLE, i);
                        CODE.start();
                    }
                } finally {
                    Repository.repositoryArray.remove(VARIABLE);
                }
            }
            default -> throw SystemException.SYSTEM_ERROR.getThrow(null);
        }
    }

    @Override
    public int start(int line) {
        startItem();
        return CODE.END() + 1;
    }
}
