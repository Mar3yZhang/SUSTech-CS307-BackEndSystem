import com.opencsv.exceptions.CsvValidationException;

import myUtils.*;
import service.*;


import java.io.IOException;
import java.sql.*;


public class client {
    public static void main(String[] args) throws SQLException, CsvValidationException, IOException {
        LogRecord.initLog();
        DataSource.getInstance().DataSourceConfig();


//        要把建表的语句整合进来才行捏

//        truncateTable.execute();
        DataImporter.ImportCsvFile();

        //testcase original
//        stockInService_2.execute("testcase/task1_in_stoke_test_data_publish.csv");
//        placeOrderService_3.execute("testcase/task2_test_data_publish.csv");
//        updateOrderService_4.execute("testcase/task34_update_test_data_publish.csv");
//        deleteOrderService_5.execute("testcase/task34_delete_test_data_publish.csv");

        //testcase 5_21
        stockInService_2.execute("testcase_521/in_stoke_test.csv");
        placeOrderService_3.execute("testcase_521/task2_test_data_final_public.csv");
        updateOrderService_4.execute("testcase_521/update_final_test.csv");
        deleteOrderService_5.execute("testcase_521/delete_final.csv");


        getAllStaffCount_6.execute();
        System.out.println();

        getContractCount_7.execute();
        System.out.println();

        getOrderCount_8.execute();
        System.out.println();

        getNeverSoldProductCount_9.execute();
        System.out.println();

        getFavoriteProductModel_10.execute();
        System.out.println();

        getAvgStockByCenter_11.execute();
        System.out.println();

        getProductByNumber_12.execute("A50L172");
        System.out.println();

        getContractInfo_13.execute("CSE0000106");
        System.out.println();

        getContractInfo_13.execute("CSE0000209");
        System.out.println();

        getContractInfo_13.execute("CSE0000306");
        System.out.println();


        DataSource.getInstance().close();
    }
}
