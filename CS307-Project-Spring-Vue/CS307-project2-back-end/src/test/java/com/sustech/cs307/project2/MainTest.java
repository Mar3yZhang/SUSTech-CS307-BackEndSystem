package com.sustech.cs307.project2;

import com.opencsv.exceptions.CsvValidationException;
import com.sustech.cs307.project2.service.impl.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(false)
public class MainTest {
    @Autowired
    private StaffServiceImpl staffService;
    @Autowired
    private ModelServiceImpl modelService;
    @Autowired
    private EnterpriseServiceImpl enterpriseService;
    @Autowired
    private SupplyCenterServiceImpl supplyCenterService;
    @Autowired
    private OrderRecordServiceImpl OrderRecordService;
    @Autowired
    private StockInRecordServiceImpl StockInRecordService;
    @Autowired
    private ContractServiceImpl contractService;
    @Autowired
    private InventoryServiceImpl inventoryService;

    @org.junit.Test
    public void test() throws SQLException, IOException, CsvValidationException {

//        staffService.initTable();
//        modelService.initTable();
//        enterpriseService.initTable();
//        supplyCenterService.initTable();

//        StockInRecordService.stock_in("src/main/resources/testcase/task1_in_stoke_test_data_publish.csv");        //2 from testcase
//////        StockInRecordService.stock_in("src/main/resources/testcase_521/in_stoke_test.csv");                    //2 from testcase_521
//        OrderRecordService.placeOrder("src/main/resources/testcase/task2_test_data_publish.csv");                 //3  from testcase
//////        OrderRecordService.placeOrder("src/main/resources/testcase_521/task2.csv");                           //3 from testcase_521
//        OrderRecordService.updateOrder("src/main/resources/testcase/task34_update_test_data_publish.tsv");        //4 from testcase
//////        OrderRecordService.updateOrder("src/main/resources/testcase_521/update_final_test.tsv");               //4 from testcase_521
//        OrderRecordService.deleteOrder("src/main/resources/testcase/task34_delete_test_data_publish.tsv");        //5 from testcase
//////        OrderRecordService.deleteOrder("src/main/resources/testcase_521/delete_final.tsv");                    //5 from testcase_521
//
        staffService.getCount();                   //6  API
        contractService.getCount();                //7  API
        OrderRecordService.getCount();             //8  API
        inventoryService.getNeverSoldModelCount(); //9  API
        modelService.getFavoriteProductModel();    //10 API
        inventoryService.getAvgStockByCenter();    //11 API

//        inventoryService.getProductByNumber("120"); //12 API
//        contractService.getContractInfo("CSE0000106");

    }
}
