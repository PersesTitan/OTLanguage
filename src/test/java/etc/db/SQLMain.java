package etc.db;

import java.sql.*;
import java.util.*;

public class SQLMain implements SQLStart {
    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/jpashop", "sa", "");
            Statement stat = con.createStatement();

//            stat.executeUpdate("ALTER TABLE TEST ADD b TEXT");

            List<String[]> list = new SQLMain().select(stat, "TEST", "*");
            list.stream()
                    .map(Arrays::asList)
                    .forEach(System.out::println);

//            SQLStart.getType(stat, "TEST");
//            DatabaseMetaData metaData = con.getMetaData();
//            ResultSet result = stat.executeQuery("SELECT * FROM TEST");
//            ResultSet meta = metaData.getColumns(null, null, "TEST", null);
//            while (result.next()) {
//                int row = result.getRow();  // 현재 라인
//                System.out.print(row);
//                System.out.print(" | ");
//                meta.next();
//                System.out.println(meta.getString("COLUMN_NAME"));
//                System.out.println();
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
