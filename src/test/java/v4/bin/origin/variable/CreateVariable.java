package v4.bin.origin.variable;

import v4.bin.apply.sys.item.TypeMap;
import v4.item.StartItem;
import v4.work.StartWork;

import java.util.LinkedList;

public class CreateVariable extends StartWork {
    public CreateVariable(StartItem... workItems) {
        super(workItems);
    }

    @Override
    public void start(String line, String loop, Object[] params, LinkedList<TypeMap> repositoryArray) {
        // 생성하는 로직에서는 메소드[값] 형태는 사용 불가
        // value = 이름:값
        String value = params[0].toString();
    }
}
