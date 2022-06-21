package service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import myUtils.DataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

//删除只是更新的一种特殊的情况
public class deleteOrderService_5 {
    static Connection con;
    static CSVReader reader;
    static String[] nextLine;

    public static void execute(String path) throws SQLException, IOException, CsvValidationException {
        con = DataSource.getInstance().getConnection();
        reader = new CSVReader(new InputStreamReader(Files.newInputStream(Paths.get(path)), StandardCharsets.UTF_8));
        reader.readNext();
        while ((nextLine = reader.readNext()) != null) {
            singleDeleteOrder cur = new singleDeleteOrder(nextLine);
            cur.checkIsValid();
            if (cur.valid) {
                updateOrderRecord(cur);
                updateInventory(cur);
            }
        }
        con.close();
    }

    //存在可以代码复用的部分 使用mybatis时再考虑修正
    static void updateOrderRecord(singleDeleteOrder cur) throws SQLException {
        PreparedStatement statement = con.prepareStatement("delete from order_record where contract_num = ? and product_model = ? and salesman_num = ? and estimated_delivery_date = ? ");
        statement.setString(1, cur.contract_num);
        statement.setString(2, cur.product_model);
        statement.setString(3, cur.salesman_num);
        statement.setDate(4, cur.estimated_delivery_date);
        statement.executeUpdate();

        //更新contract中的数目
        statement = con.prepareStatement("update contract set ordernum = ordernum - 1 where contract_number = ?");
        statement.setString(1, cur.contract_num);
        statement.executeUpdate();
    }

    static void updateInventory(singleDeleteOrder cur) throws SQLException {

        PreparedStatement statement = con.prepareStatement("select unit_price from model where model = ?");
        statement.setString(1, cur.product_model);
        statement.executeQuery();
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        long unit_price = resultSet.getLong(1);

        //如何唯一地确定这条order 对 进货的哪一个指标有影响？
        //通过contract找到enterprise

        //这里听过contract找到对应的center是可以优化的
        statement = con.prepareStatement("select enterprise from contract where contract_number = ?");
        statement.setString(1, cur.contract_num);
        statement.executeQuery();
        resultSet = statement.getResultSet();
        resultSet.next();
        String enterprise = resultSet.getString(1);

        //enterprise找到supply_center
        statement = con.prepareStatement("select supply_center from enterprise where name = ?");
        statement.setString(1, enterprise);
        statement.execute();
        resultSet = statement.getResultSet();
        resultSet.next();
        String supply_center = resultSet.getString(1);

        //通过center和product_model唯一确定

        //删除订单 出库减少 利润减少
        statement = con.prepareStatement("update inventory set exportnum = exportnum - ?," +
                " totalinterest = totalinterest - ? * ?,placeordernum = placeordernum -1  where product_model = ? and supply_center = ?");
        statement.setLong(1, cur.quantity);
        statement.setLong(2, cur.quantity);
        statement.setLong(3, unit_price);
        statement.setString(4, cur.product_model);
        statement.setString(5, supply_center);

        statement.executeUpdate();
    }

    static class singleDeleteOrder {
        boolean valid = false;
        long seq;

        String product_model;
        long quantity;
        Date estimated_delivery_date;
        String contract_num;
        String salesman_num;

        private singleDeleteOrder(String[] line) {
            contract_num = line[0];
            salesman_num = line[1];
            seq = Long.parseLong(line[2]);
        }

        //判断是不是有效的删除信息
        void checkIsValid() throws SQLException {
            String sql = "select * from order_record where contract_num = ? and salesman_num = ? " +
                    "order by estimated_delivery_date, product_model; ";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, contract_num);
            statement.setString(2, salesman_num);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            for (int i = 0; i < seq - 1; i++) {
                resultSet.next();
            }
            if (resultSet.next()) {
                valid = true;
                product_model = resultSet.getString(3);
                quantity = resultSet.getLong(4);
                estimated_delivery_date = resultSet.getDate(7);
            }
        }
    }
}

