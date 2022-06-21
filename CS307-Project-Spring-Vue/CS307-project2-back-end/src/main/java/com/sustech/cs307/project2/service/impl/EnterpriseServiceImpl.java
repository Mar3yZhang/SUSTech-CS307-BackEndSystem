package com.sustech.cs307.project2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.sustech.cs307.project2.entity.Enterprise;
import com.sustech.cs307.project2.mapper.EnterpriseMapper;
import com.sustech.cs307.project2.service.EnterpriseService;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Marcy ZHANG
 * @since 2022-05-15
 */
@Service
public class EnterpriseServiceImpl extends ServiceImpl<EnterpriseMapper, Enterprise> implements EnterpriseService {
    @Override
    public boolean initTable() {
        List<Enterprise> enterprises = new LinkedList<>();
        try (FileInputStream FileInputStream = new FileInputStream("src/main/resources/data/enterprise.csv");
             InputStreamReader InputStreamReader = new InputStreamReader(FileInputStream, StandardCharsets.UTF_8);
             CSVReader reader = new CSVReader(InputStreamReader)) {
            reader.readNext();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Enterprise enterprise = new Enterprise();
                enterprise.setName(nextLine[1]);
                enterprise.setCountry(nextLine[2]);
                enterprise.setCity(nextLine[3]);
                enterprise.setSupplyCenter(nextLine[4]);
                enterprise.setIndustry(nextLine[5]);
                enterprises.add(enterprise);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return this.saveBatch(enterprises);
    }
}
