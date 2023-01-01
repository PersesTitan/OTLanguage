package etc.db.item;

public record CreateItem(String name, String type, String...options) {
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(name);
        builder.append(" ").append(type);
        for (String option : options)
            builder.append(" ").append(option);
        return builder.toString();
    }
}
