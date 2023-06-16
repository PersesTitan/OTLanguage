package bin.apply.work.error;

import bin.apply.item.Item;
import bin.token.work.ThrowToken;
import bin.exception.Error;
import bin.token.SepToken;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ThrowItem implements Item {
    private final StringBuilder sub = new StringBuilder();
    private String big, mes;

    public ThrowItem(Error tool) {
        this(tool.getBig(), tool.getMes());
        this.sub.append(tool.getSub());
    }

    public ThrowItem(String big, String mes, String sub) {
        this(big, mes);
        this.sub.append(sub);
    }

    public void createError(String error) {
        throw new Error(big, mes, sub.toString(), error.isEmpty() ? null : error);
    }

    public void addSub(String sub) {
        this.sub.append(SepToken.LINE).append(sub);
    }

    public String getSub() {
        return sub.toString();
    }

    public void setSub(SepToken sub) {
        this.sub.setLength(0);
        this.sub.append(sub);
    }

    @Override
    public String toString() {
        return itemToString(ThrowToken.THROW, big, mes, sub);
    }
}
