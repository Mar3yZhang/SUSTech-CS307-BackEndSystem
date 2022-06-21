package myUtils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;


//使用openCSV
//将4个CSV文件导入
public class DataImporter {
    static Connection con;
    static CSVReader reader;
    static String[] nextLine;


    //可以考虑后期修改为多线程导入
    public static void ImportCsvFile() throws IOException, CsvValidationException, SQLException {
        con = DataSource.getInstance().getConnection();
        ImportSupplyCenter("data/center.csv");
        ImportEnterprise("data/enterprise.csv");
        ImportModel("data/model.csv");
        ImportStaff("data/staff.csv");
        con.close();
    }

    public static void ImportSupplyCenter(String path) throws IOException, CsvValidationException, SQLException {
        reader = new CSVReader(new InputStreamReader(Files.newInputStream(Paths.get(path)), StandardCharsets.UTF_8));
        PreparedStatement statement = con.prepareStatement("insert into supply_center(name)"
                + "values(?)");
        reader.readNext();
        while ((nextLine = reader.readNext()) != null) {
            statement.setString(1, nextLine[1]);
            statement.executeUpdate();
        }
    }

    public static void ImportEnterprise(String path) throws IOException, CsvValidationException, SQLException {
        reader = new CSVReader(new InputStreamReader(Files.newInputStream(Paths.get(path)), StandardCharsets.UTF_8));
        PreparedStatement statement = con.prepareStatement("insert into enterprise(name,country,city,supply_center,industry)"
                + "values(?,?,?,?,?)");
        reader.readNext();
        while ((nextLine = reader.readNext()) != null) {
            statement.setString(1, nextLine[1]);
            statement.setString(2, nextLine[2]);
            statement.setString(3, nextLine[3]);
            statement.setString(4, nextLine[4]);
            statement.setString(5, nextLine[5]);
            statement.executeUpdate();
        }
    }

    public static void ImportModel(String path) throws IOException, CsvValidationException, SQLException {
        reader = new CSVReader(new InputStreamReader(Files.newInputStream(Paths.get(path)), StandardCharsets.UTF_8));
        PreparedStatement statement = con.prepareStatement("insert into model(number,model,product,unit_price)"
                + "values(?,?,?,?)");
        reader.readNext();
        while ((nextLine = reader.readNext()) != null) {
            statement.setString(1, nextLine[1]);
            statement.setString(2, nextLine[2]);
            statement.setString(3, nextLine[3]);
            statement.setLong(4, Long.parseLong(nextLine[4]));
            statement.executeUpdate();
        }
    }

    public static void ImportStaff(String path) throws IOException, CsvValidationException, SQLException {
        reader = new CSVReader(new InputStreamReader(Files.newInputStream(Paths.get(path)), StandardCharsets.UTF_8));
        PreparedStatement statement = con.prepareStatement("insert into staff(name,age,gender,number,supply_center,mobile_number,type)"
                + "values(?,?,?,?,?,?,?)");
        reader.readNext();
        while ((nextLine = reader.readNext()) != null) {
            statement.setString(1, nextLine[1]);
            statement.setLong(2, Long.parseLong(nextLine[2]));
            statement.setString(3, nextLine[3]);
            statement.setString(4, nextLine[4]);
            statement.setString(5, nextLine[5]);
            statement.setString(6, nextLine[6]);
            statement.setString(7, nextLine[7]);
            statement.executeUpdate();
        }
    }

    //将字符串转化为sql日期类型
    public static Date StringToDate(String sDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date d = null;
        try {
            d = format.parse(sDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert d != null;
        return new Date(d.getTime());
    }

    //将模板标准化为产品
    public static String ModelToProduct(String model) {
        String product = model.substring(0, model.length() - 2);
        String[] partName = product.replaceAll("[A-Z]", "_$0").split("_");
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 1; i < partName.length - 1; i++) {
            if (partName[i].charAt(partName[i].length() - 1) != '-') {
                stringBuffer.append(partName[i]).append(" ");
            } else {
                stringBuffer.append(partName[i]);
            }
        }
        stringBuffer.append(partName[partName.length - 1]);
        return stringBuffer.toString();
    }
}