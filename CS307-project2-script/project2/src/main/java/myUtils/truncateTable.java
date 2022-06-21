package myUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class truncateTable {
    public static void execute() throws SQLException {
        Connection con = DataSource.getInstance().getConnection();
        Statement statement = con.createStatement();
        statement.execute("truncate table enterprise");
        statement.execute("truncate table model");
        statement.execute("truncate table order_record");
        statement.execute("truncate table staff");
        statement.execute("truncate table stock_in_record");
        statement.execute("truncate table supply_center");
        con.close();
    }
}
