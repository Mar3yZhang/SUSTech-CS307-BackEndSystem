package service;

import myUtils.DataSource;

import java.sql.*;
import java.util.ArrayList;

public class getContractInfo_13 {
    static Connection con;

    static String contract_number;
    static String enterprise;
    static String manager;
    static String supply_center;

    static ArrayList<String> product_model;
    static ArrayList<String> salesman;
    static ArrayList<Long> quantity;
    static ArrayList<Long> unit_price;
    static ArrayList<Date> estimate_delivery_date;
    static ArrayList<Date> lodgement_date;


    public static void execute(String contract_num) throws SQLException {
        product_model = new ArrayList<>();
        salesman = new ArrayList<>();
        quantity = new ArrayList<>();
        unit_price = new ArrayList<>();
        estimate_delivery_date = new ArrayList<>();
        lodgement_date = new ArrayList<>();

        con = DataSource.getInstance().getConnection();
        PreparedStatement statement = con.prepareStatement("select * from contract where  contract_number = ?");
        statement.setString(1, contract_num);
        statement.executeQuery();
        ResultSet resultSet = statement.getResultSet();
        //判断是否存在这样的合同
        if (resultSet.next()) {
            contract_number = resultSet.getString(1);
            manager = resultSet.getString(2);
            enterprise = resultSet.getString(3);
            supply_center = resultSet.getString(4);

            statement = con.prepareStatement("select distinct order_record.product_model, name, quantity, unit_price, estimated_delivery_date, lodgement_date from order_record" +
                    "         join staff on order_record.salesman_num = staff.number" +
                    "         join inventory i on order_record.product_model = i.product_model" +
                    "         join model m on m.model = order_record.product_model where contract_num = ?");
            statement.setString(1, contract_number);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            while (resultSet.next()) {
                product_model.add(resultSet.getString(1));
                salesman.add(resultSet.getString(2));
                quantity.add(resultSet.getLong(3));
                unit_price.add(resultSet.getLong(4));
                estimate_delivery_date.add(resultSet.getDate(5));
                lodgement_date.add(resultSet.getDate(6));
            }
            print();
        } else {
            System.out.println("There is no such contract! Failed to get contract info!");
        }
        con.close();
    }


    private static void print() {
        System.out.println("contract_number: " + contract_number);
        System.out.println("enterprise: " + enterprise);
        System.out.println("manager: " + manager);
        System.out.println("supply_center: " + supply_center);

        for (int i = 0; i < product_model.size(); i++) {
            if (i == 0) {
                System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s", "product_model", "salesman", "quantity", "unit_price", "estimate_delivery_date", "lodgement_date");
                System.out.println();
            }
            System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s", product_model.get(i), salesman.get(i), quantity.get(i), unit_price.get(i), estimate_delivery_date.get(i), lodgement_date.get(i));
            System.out.println();
        }
    }
}
