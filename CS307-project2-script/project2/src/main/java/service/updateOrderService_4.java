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

public class updateOrderService_4 {
    static Connection con;
    static CSVReader reader;
    static String[] nextLine;


    //插入目标数据 ,同时要更新库存
    public static void execute(String path) throws SQLException, IOException, CsvValidationException {
        con = DataSource.getInstance().getConnection();
        reader = new CSVReader(new InputStreamReader(Files.newInputStream(Paths.get(path)), StandardCharsets.UTF_8));
        reader.readNext();
        while ((nextLine = reader.readNext()) != null) {
            singleUpdateOrder cur = new singleUpdateOrder(nextLine);
            cur.checkIsOwnOrder();
            if (cur.isOwnOrder) {
                updateInventory(cur);
                updateOrderRecord(cur);
            }
        }
        con.close();
    }

    //更新order record 和 contact的内容
    //若订单订购的数量变为0 则从order中删除订单
    static void updateOrderRecord(singleUpdateOrder cur) throws SQLException {
        if (Long.parseLong(cur.quantity) == 0) {
            PreparedStatement statement = con.prepareStatement("delete from order_record where contract_num = ? and product_model = ? and salesman_num = ?");
            statement.setString(1, cur.contract);
            statement.setString(2, cur.product_model);
            statement.setString(3, cur.salesman);
            statement.executeUpdate();

            //更新contract中的数目
            statement = con.prepareStatement("update contract set ordernum = ordernum - 1 where contract_number = ?");
            statement.setString(1, cur.contract);
            statement.executeUpdate();
        } else {
            PreparedStatement statement = con.prepareStatement("update order_record set quantity = ? , estimated_delivery_date = ?, lodgement_date = ?  where contract_num = ? and product_model = ? and salesman_num = ?");
            statement.setLong(1, Long.parseLong(cur.quantity));
            statement.setDate(2, Date.valueOf(cur.estimate_delivery_date));
            statement.setDate(3, Date.valueOf(cur.lodgement_date));
            statement.setString(4, cur.contract);
            statement.setString(5, cur.product_model);
            statement.setString(6, cur.salesman);
            statement.executeUpdate();
        }
    }

    //根据当前的更新值更新库存 即 若为数量变为0 要更新卖出的产品数量和挣的钱
    static void updateInventory(singleUpdateOrder cur) throws SQLException {

        PreparedStatement statement = con.prepareStatement("select unit_price from model where model = ?");
        statement.setString(1, cur.product_model);
        statement.executeQuery();
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        long unit_price = resultSet.getLong(1);

        statement = con.prepareStatement("select quantity from order_record where contract_num = ? and product_model = ? and salesman_num = ?");
        statement.setString(1, cur.contract);
        statement.setString(2, cur.product_model);
        statement.setString(3, cur.salesman);
        statement.executeQuery();
        resultSet = statement.getResultSet();
        resultSet.next();
        long quantity = resultSet.getLong(1);


        //如何唯一地确定这条order对进货的哪一个指标有影响？
        //通过contract找到enterprise
        statement = con.prepareStatement("select enterprise from contract where contract_number = ?");
        statement.setString(1, cur.contract);
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
        statement = con.prepareStatement("update inventory set exportnum = exportnum - (? - ?)," +
                " totalinterest = totalinterest - (? - ?) * ?  where product_model = ? and supply_center = ?");
        statement.setLong(1, quantity);
        statement.setLong(2, Long.parseLong(cur.quantity));
        statement.setLong(3, quantity);
        statement.setLong(4, Long.parseLong(cur.quantity));
        statement.setLong(5, unit_price);
        statement.setString(6, cur.product_model);
        statement.setString(7, supply_center);
        statement.executeUpdate();

        if (Long.parseLong(cur.quantity) == 0) {
            statement = con.prepareStatement("update inventory set placeordernum = placeordernum -1  where product_model = ? and supply_center = ?");
            statement.setString(1, cur.product_model);
            statement.setString(2, supply_center);
            statement.executeUpdate();
        }
    }

    static class singleUpdateOrder {
        boolean isOwnOrder = false;

        String contract;
        String product_model;
        String salesman;
        String quantity;
        String estimate_delivery_date;
        String lodgement_date;

        private singleUpdateOrder(String[] line) {
            contract = line[0];
            product_model = line[1];
            salesman = line[2];
            quantity = line[3];
            estimate_delivery_date = line[4];
            lodgement_date = line[5];
        }

        //补货数据中员工和供货中心信息不匹配
        //需要三个信息都能匹配上才行 contract number, product model and salesman number
        void checkIsOwnOrder() throws SQLException {
            String sql = "select * from order_record where product_model = ? and salesman_num = ? and contract_num = ? ";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, product_model);
            statement.setString(2, salesman);
            statement.setString(3, contract);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                isOwnOrder = true;
            }
        }
    }
}

