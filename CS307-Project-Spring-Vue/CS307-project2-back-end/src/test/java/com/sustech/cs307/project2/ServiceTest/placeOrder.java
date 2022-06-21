package com.sustech.cs307.project2.ServiceTest;

import com.opencsv.exceptions.CsvValidationException;
import com.sustech.cs307.project2.service.OrderRecordService;
import com.sustech.cs307.project2.service.impl.OrderRecordServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(false)
public class placeOrder {
    @Autowired
    private OrderRecordServiceImpl service;

    @org.junit.Test
    public void test() throws CsvValidationException, SQLException, IOException {
//        service.placeOrder("src/main/resources/testcase/task2_test_data_publish.csv");             //3  from testcase
        service.placeOrder("src/main/resources/testcase_521/task2_test_data_final_public.tsv");     //3 from testcase_521
    }
}