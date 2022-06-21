package service;

import myUtils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class getNeverSoldProductCount_9 {
    static Connection con;
    static long num;

    //这里的表需要整合一下 整合成一个复杂查询才可以
    public static void execute() throws SQLException {
        con = DataSource.getInstance().getConnection();
        PreparedStatement statement = con.prepareStatement
                ("select count(a) from (select product_model, sum(placeOrderNum) as toatlNum from inventory where (importnum - exportnum) > 0 group by product_model) a where toatlNum = 0;");

        statement.executeQuery();
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        num = resultSet.getLong(1);

        print();
        con.close();
    }

    private static void print() {
        System.out.println(num);
    }
}
