package cos.database;

import bin.apply.Repository;
import bin.apply.item.CodesItem;
import bin.apply.item.ParamItem;
import bin.apply.OSConsumer;
import bin.parser.param.ParamToken;
import bin.token.KlassToken;
import bin.token.check.CheckToken;
import work.LoopWork;

import java.sql.ResultSet;
import java.sql.SQLException;

class Select extends LoopWork {
    public Select() {
        super(DatabaseToken.DATABASE, false, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected void loopItem(Object klass, Object[] params, OSConsumer consumer) {
        ResultSet resultSet = ((DatabaseItem) klass).select(params[0].toString());
        ParamItem[] items = (ParamItem[]) params[1];
        try {
            while (resultSet.next()) {
                Object[] values = new Object[items.length];
                for (int i = 0; i<items.length; i++) {
                    values[i] = switch (items[i].type()) {
                        case KlassToken.INT_VARIABLE -> resultSet.getInt(i+1);
                        case KlassToken.LONG_VARIABLE -> resultSet.getLong(i+1);
                        case KlassToken.BOOL_VARIABLE -> resultSet.getBoolean(i+1);
                        case KlassToken.STRING_VARIABLE -> resultSet.getString(i+1);
                        case KlassToken.FLOAT_VARIABLE -> resultSet.getFloat(i+1);
                        case KlassToken.DOUBLE_VARIABLE -> resultSet.getDouble(i+1);
                        default -> resultSet.getObject(i+1);
                    };
                }
                consumer.accept(values);
            }
        } catch (SQLException e) {
            throw DatabaseException.SQL_RUN_ERROR.getThrow(params[0]);
        }
    }

    @Override
    public void loop(Object klass, ParamToken[] params, CodesItem CODE, ParamItem[] ITEM) {
        Object value = getKlassItem(klass);
        Object[] param = new Object[] {casting(params)[0].toString(), ITEM};
        for (ParamItem item : ITEM) Repository.repositoryArray.createLoop(item);
        try {
            loopItem(value, param, v -> {
                CheckToken.checkParamLength(ITEM.length, v.length);
                for (int i = 0; i<ITEM.length; i++) Repository.repositoryArray.updateLoop(ITEM[i], v[i]);
                CODE.start();
            });
        } finally {
            for (ParamItem item : ITEM) Repository.repositoryArray.remove(item);
        }
    }
}
