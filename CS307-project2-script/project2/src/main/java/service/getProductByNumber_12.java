package service;

import myUtils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class getProductByNumber_12 {
    static Connection con;
    static ArrayList<String> supply_center;
    static ArrayList<String> product_model;
    static ArrayList<Long> quantity;

    public static void execute(String product_number) throws SQLException {
        supply_center = new ArrayList<>();
        product_model = new ArrayList<>();
        quantity = new ArrayList<>();
        con = DataSource.getInstance().getConnection();
        PreparedStatement statement = con.prepareStatement("select supply_center,product_model,importNum-exportNum as quantity from model join inventory on model.model = inventory.product_model where number = ? order by supply_center");
        statement.setString(1, product_number);
        statement.executeQuery();
        ResultSet resultSet = statement.getResultSet();
        Boolean hasElement = false;
        while (resultSet.next()) {
            hasElement = true;
            supply_center.add(resultSet.getString(1));
            product_model.add(resultSet.getString(2));
            quantity.add(resultSet.getLong(3));
        }
        if (!hasElement) {
            System.out.println("There is no such product! Failed to get product info!");
        }
        print();
        con.close();
    }

    private static void print() {

        for (int i = 0; i < supply_center.size(); i++) {
            if (i == 0) {
                System.out.printf("%-30s %-30s %-30s", "supply_center", "product_model", "quantity");
                System.out.println();
            }
            System.out.printf("%-30s %-30s %-30s", supply_center.get(i), product_model.get(i), quantity.get(i));
            System.out.println();
        }
    }
}
