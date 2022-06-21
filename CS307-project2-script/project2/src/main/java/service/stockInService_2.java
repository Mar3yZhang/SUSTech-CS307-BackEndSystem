package service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import myUtils.DataImporter;
import myUtils.DataSource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


//LONG 还没有修正过来！

public class stockInService_2 {
    static Connection con;
    static CSVReader reader;
    static String[] nextLine;

    //插入目标数据 ,同时要更新库存
    public static void execute(String path) throws SQLException, IOException, CsvValidationException {
        con = DataSource.getInstance().getConnection();
        reader = new CSVReader(new InputStreamReader(Files.newInputStream(Paths.get(path)), StandardCharsets.UTF_8));
        reader.readNext();
        while ((nextLine = reader.readNext()) != null) {
            singleStockInRecord cur = new singleStockInRecord(nextLine);
            cur.checkSuchCenter();
            cur.checkSuchProduct();
            cur.checkSuchStaff();
            cur.checkIsValidType();
            cur.checkMismatchOfStaffAndCenter();

            //是合法的数据 应该插入 同时更新相关联的表格
            if (cur.hasSuchCenter && cur.hasSuchProduct && cur.hasSuchStaff && cur.isValidType && cur.MatchOfStaffAndCenter) {
                //更新stock in record这个表格的先关信息
                updateStockInRecord(cur);
                updateInventory(cur);
            }
        }
        con.close();
    }

    static void updateStockInRecord(singleStockInRecord cur) throws SQLException {
        PreparedStatement statement = con.prepareStatement("insert into " + "stock_in_record(supply_center,product_model,supply_staff," + "date,purchase_price,quantity) values(?,?,?,?,?,?)");
        statement.setString(1, cur.supply_center);
        statement.setString(2, cur.product_model);
        statement.setString(3, cur.supply_staff);
        statement.setDate(4, DataImporter.StringToDate(cur.date));
        statement.setInt(5, Integer.parseInt(cur.purchase_price));
        statement.setInt(6, Integer.parseInt(cur.quantity));
        statement.executeUpdate();
    }

    static void updateInventory(singleStockInRecord cur) throws SQLException {
        boolean hasSuchModel; //在目标供货中心中存在该产品 直接更新即可
        //判断改模板在库存中是否有记录
        String sql = "select product_model from inventory where supply_center = ? and product_model = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, cur.supply_center);
        statement.setString(2, cur.product_model);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        hasSuchModel = resultSet.next();


        if (hasSuchModel) { //存在记录 直接更新库存 和 总花费
            int extraCost = Integer.parseInt(cur.purchase_price) * Integer.parseInt(cur.quantity);
            statement = con.prepareStatement("update inventory set importnum=importnum+?,totalcost=totalcost+?,stockInNum=stockInNum + 1 where supply_center = ? and product_model = ?");
            statement.setInt(1, Integer.parseInt(cur.quantity));
            statement.setInt(2, extraCost);
            statement.setString(3, cur.supply_center);
            statement.setString(4, cur.product_model);
            statement.executeUpdate();
        } else {
            statement = con.prepareStatement("insert into inventory(supply_center,product_model,importnum,exportnum,totalcost,totalinterest,stockInNum,placeOrderNum) values(?,?,?,?,?,?,?,?)");
            statement.setString(1, cur.supply_center);
            statement.setString(2, cur.product_model);
            statement.setInt(3, Integer.parseInt(cur.quantity));
            statement.setInt(4, 0);
            statement.setInt(5, Integer.parseInt(cur.purchase_price) * Integer.parseInt(cur.quantity));
            statement.setInt(6, 0);
            statement.setInt(7, 1);
            statement.setInt(8, 0);
            statement.executeUpdate();
        }
    }


    static class singleStockInRecord {
        boolean MatchOfStaffAndCenter = false;
        boolean isValidType = false;
        boolean hasSuchCenter = false;
        boolean hasSuchProduct = false;
        boolean hasSuchStaff = false;
        int id;
        String supply_center;
        String product_model;
        String supply_staff;
        String date;
        String purchase_price;
        String quantity;

        private singleStockInRecord(String[] line) {
            id = Integer.parseInt(line[0]);
            supply_center = line[1];
            product_model = line[2];
            supply_staff = line[3];
            date = line[4];
            purchase_price = line[5];
            quantity = line[6];
        }


        //检验是否是Supply Staff类型
        void checkIsValidType() throws SQLException {

            if (!hasSuchStaff) {
                return;
            }

            String sql = "select type from staff where staff.number = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, supply_staff);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();

            isValidType = Objects.equals(resultSet.getString(1), "Supply Staff");
        }

        //是否存在当前供货中心
        void checkSuchCenter() throws SQLException {
            String sql = "select supply_center from supply_center where name = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, supply_center);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            hasSuchCenter = resultSet.next();
        }


        //只需要查找不包含最后两个字符的产品是否存在即可
        void checkSuchProduct() throws SQLException {
            String product = DataImporter.ModelToProduct(product_model);
            String sql = "select product from model where product = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, product);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            hasSuchProduct = resultSet.next();
        }

        //是否存在当前员工
        void checkSuchStaff() throws SQLException {
            String sql = "select number from staff where number = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, supply_staff);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            hasSuchStaff = resultSet.next();
        }


        //补货数据中员工和供货中心信息不匹配
        void checkMismatchOfStaffAndCenter() throws SQLException {

            if (!(hasSuchStaff && hasSuchCenter)) {
                return;
            }

            String sql = "select supply_center from staff where staff.number = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, supply_staff);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();

            MatchOfStaffAndCenter = Objects.equals(resultSet.getString(1), supply_center);
        }
    }
}
