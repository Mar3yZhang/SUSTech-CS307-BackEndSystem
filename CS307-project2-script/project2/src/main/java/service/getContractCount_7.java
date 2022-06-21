package service;

import myUtils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class getContractCount_7 {
    static Connection con;
    static long num;

    public static void execute() throws SQLException {
        con = DataSource.getInstance().getConnection();
        PreparedStatement statement = con.prepareStatement("select count(*) from contract ");
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
