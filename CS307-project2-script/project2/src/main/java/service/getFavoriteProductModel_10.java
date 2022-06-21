package service;

import myUtils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class getFavoriteProductModel_10 {
    static Connection con;
    static long num;
    static String product_model;

    public static void execute() throws SQLException {
        con = DataSource.getInstance().getConnection();
        PreparedStatement statement = con.prepareStatement("select product_model, max(sum) as max from (select product_model, sum(exportNum) as sum from inventory group by product_model) a group by product_model order by max desc limit 1");
        statement.executeQuery();
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        product_model = resultSet.getString(1);
        num = resultSet.getLong(2);
        print();
        con.close();
    }

    private static void print() {
        System.out.printf("%-20s %-20d", product_model, num);
        System.out.println();
    }
}
