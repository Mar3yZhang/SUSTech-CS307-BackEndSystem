package service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import myUtils.DataSource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Objects;

public class placeOrderService_3 {
    static Connection con;
    static CSVReader reader;
    static String[] nextLine;

    public static void execute(String path) throws SQLException, IOException, CsvValidationException {
        con = DataSource.getInstance().getConnection();
        reader = new CSVReader(new InputStreamReader(Files.newInputStream(Paths.get(path)), StandardCharsets.UTF_8));
        reader.readNext();
        while ((nextLine = reader.readNext()) != null) {
            singleOrderRecord cur = new singleOrderRecord(nextLine);
            cur.checkHasEnoughProduct_model();
            cur.checkIsSalesman();

            //是合法的数据 应该插入 同时更新相关联的表格
            if (cur.HasEnoughProduct_model && cur.IsSalesman) {
                //更新stock in record这个表格的先关信息
                updateOrderRecord(cur);
                updateInventory(cur);
                updateContactInfo(cur);
            }
        }
        con.close();
    }

    static void updateOrderRecord(singleOrderRecord cur) throws SQLException {
        PreparedStatement statement = con.prepareStatement("insert into " + "order_record(contract_num,enterprise,product_model," +
                "quantity,contract_manager,contract_date,estimated_delivery_date,lodgement_date,salesman_num,contract_type) values(?,?,?,?,?,?,?,?,?,?)");
        statement.setString(1, cur.contract_num);
        statement.setString(2, cur.enterprise);
        statement.setString(3, cur.product_model);
        statement.setLong(4, Long.parseLong(cur.quantity));
        statement.setString(5, cur.contract_manager);
        statement.setDate(6, Date.valueOf(cur.contract_date));
        statement.setDate(7, Date.valueOf(cur.estimated_delivery_date));
        statement.setDate(8, Date.valueOf(cur.lodgement_date));
        statement.setString(9, cur.salesman_num);
        statement.setString(10, cur.contract_type);
        statement.executeUpdate();
    }

    static void updateInventory(singleOrderRecord cur) throws SQLException {
        //先查找单价
        PreparedStatement statement = con.prepareStatement("select unit_price from model where model = ?");
        statement.setString(1, cur.product_model);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        long unit_price = resultSet.getLong(1);

        //查找对应的供货中心
        statement = con.prepareStatement("select supply_center from enterprise where name = ?");
        statement.setString(1, cur.enterprise);
        statement.execute();
        resultSet = statement.getResultSet();
        resultSet.next();
        String center = resultSet.getString(1);
        //更新相关的信息

        statement = con.prepareStatement("update inventory set exportNum = exportNum + ? , totalinterest = totalinterest + ?,placeordernum = placeordernum +1  where supply_center = ? and product_model = ?");
        statement.setLong(1, Long.parseLong(cur.quantity));
        statement.setLong(2, Long.parseLong(cur.quantity) * unit_price);
        statement.setString(3, center);
        statement.setString(4, cur.product_model);
        statement.executeUpdate();
    }

    static void updateContactInfo(singleOrderRecord cur) throws SQLException {

        PreparedStatement statement = con.prepareStatement("select contract_number from contract where contract_number = ?");
        statement.setString(1, cur.contract_num);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();

        //当前contract已经存在 只需要添加contact中order的数目
        if (resultSet.next()) {
            statement = con.prepareStatement("update contract set ordernum = ordernum + 1  where contract_number = ?");
            statement.setString(1, cur.contract_num);
            statement.executeUpdate();
        } else {
            statement = con.prepareStatement("select name from staff where number = ?");
            statement.setString(1, cur.contract_manager);
            statement.execute();
            resultSet = statement.getResultSet();
            resultSet.next();
            String contract_manager_name = resultSet.getString(1);

            statement = con.prepareStatement("select supply_center from enterprise where name = ?");
            statement.setString(1, cur.enterprise);
            statement.execute();
            resultSet = statement.getResultSet();
            resultSet.next();
            String center = resultSet.getString(1);

            statement = con.prepareStatement("insert into contract (contract_number,contract_manager_name,enterprise,supply_center,ordernum) values (?,?,?,?,?)");
            statement.setString(1, cur.contract_num);
            statement.setString(2, contract_manager_name);
            statement.setString(3, cur.enterprise);
            statement.setString(4, center);
            statement.setLong(5, 1);
            statement.execute();
        }
    }

    static class singleOrderRecord {
        boolean HasEnoughProduct_model = false;
        boolean IsSalesman = false;
        String contract_num;
        String enterprise;
        String product_model;
        String quantity;
        String contract_manager;
        String contract_date;
        String estimated_delivery_date;
        String lodgement_date;
        String salesman_num;
        String contract_type;

        private singleOrderRecord(String[] line) {
            contract_num = line[0];
            enterprise = line[1];
            product_model = line[2];
            quantity = line[3];
            contract_manager = line[4];
            contract_date = line[5];
            estimated_delivery_date = line[6];
            lodgement_date = line[7];
            salesman_num = line[8];
            contract_type = line[9];
        }


        //是否还有足够的库存
        void checkHasEnoughProduct_model() throws SQLException {
            //查找公司所对应的供货中心 getSupply_center
            String sql = "select supply_center from enterprise where name = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, enterprise);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            String center = resultSet.getString(1);

            //查找目标供货中心的目标模板的剩余数量
            statement = con.prepareStatement("select (importnum - exportnum) as difference from inventory where supply_center = ? and product_model = ?");
            statement.setString(1, center);
            statement.setString(2, product_model);
            statement.execute();
            resultSet = statement.getResultSet();
            //库存大于实际数量
            if (resultSet.next()) {
                HasEnoughProduct_model = (resultSet.getLong(1) > Long.parseLong(quantity));
            }
        }


        //补货数据中员工和供货中心信息不匹配
        void checkIsSalesman() throws SQLException {
            String sql = "select type from staff where staff.number = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, salesman_num);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();

            IsSalesman = Objects.equals("Salesman", resultSet.getString(1));
        }
    }
}
