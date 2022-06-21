package service;

import myUtils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class getAllStaffCount_6 {
    static Connection con;
    static final String[] type = {"Director", "Contracts Manager", "Salesman", "Supply Staff"};
    static long[] num;

    public static void execute() throws SQLException {
        con = DataSource.getInstance().getConnection();
        num = new long[4];
        PreparedStatement statement = con.prepareStatement("select count(*) from staff where type = ?");
        for (int i = 0; i < 4; i++) {
            statement.setString(1, type[i]);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            num[i] = resultSet.getLong(1);
        }
        print();
        con.close();
    }

    private static void print() {
        for (int i = 0; i < 4; i++) {
            System.out.printf("%-20s %-20d", type[i], num[i]);
            System.out.println();
        }
    }
}
