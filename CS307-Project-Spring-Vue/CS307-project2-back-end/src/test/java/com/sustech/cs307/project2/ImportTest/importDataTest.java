package com.sustech.cs307.project2.ImportTest;

import com.sustech.cs307.project2.service.impl.EnterpriseServiceImpl;
import com.sustech.cs307.project2.service.impl.ModelServiceImpl;
import com.sustech.cs307.project2.service.impl.StaffServiceImpl;
import com.sustech.cs307.project2.service.impl.SupplyCenterServiceImpl;
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
public class importDataTest {
    @Autowired
    private StaffServiceImpl staffService;
    @Autowired
    private ModelServiceImpl modelService;
    @Autowired
    private EnterpriseServiceImpl enterpriseService;
    @Autowired
    private SupplyCenterServiceImpl supplyCenterService;

    @org.junit.Test
    public void test() {
        staffService.initTable();
        modelService.initTable();
        enterpriseService.initTable();
        supplyCenterService.initTable();
    }
}
