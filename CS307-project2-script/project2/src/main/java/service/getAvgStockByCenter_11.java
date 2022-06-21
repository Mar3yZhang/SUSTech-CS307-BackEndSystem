package service;

import myUtils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class getAvgStockByCenter_11 {
    static Connection con;
    static ArrayList<String> supply_center;
    static ArrayList<Double> average;

    public static void execute() throws SQLException {
        con = DataSource.getInstance().getConnection();
        supply_center = new ArrayList<>();
        average = new ArrayList<>();
        PreparedStatement statement = con.prepareStatement("select supply_center, round(avg(stock), 1) as avg from (select supply_center, importNum - exportNum as stock from inventory) a group by supply_center order by supply_center;");
        statement.executeQuery();
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            supply_center.add(resultSet.getString(1));
            average.add(resultSet.getDouble(2));
        }
        print();
        con.close();
    }

    private static void print() {
        for (int i = 0; i < supply_center.size(); i++) {
            System.out.printf("%-50s %-20s %.1f", supply_center.get(i), "", average.get(i));
            System.out.println();
        }
    }
}
