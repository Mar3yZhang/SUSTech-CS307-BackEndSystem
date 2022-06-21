package com.sustech.cs307.project2.ServiceTest;

import com.sustech.cs307.project2.service.impl.StockInRecordServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(false)
public class StokeInTest {
    @Autowired
    private StockInRecordServiceImpl service;

    @org.junit.Test
    public void test() {
        //service.stock_in("src/main/resources/testcase/task1_in_stoke_test_data_publish.csv");        //2 from testcase
        service.stock_in("src/main/resources/testcase_521/in_stoke_test.csv");                    //2 from testcase_521
    }
}
