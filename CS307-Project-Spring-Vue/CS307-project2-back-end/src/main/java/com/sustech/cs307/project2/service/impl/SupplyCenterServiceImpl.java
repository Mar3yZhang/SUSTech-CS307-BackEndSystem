package com.sustech.cs307.project2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.sustech.cs307.project2.entity.SupplyCenter;
import com.sustech.cs307.project2.mapper.SupplyCenterMapper;
import com.sustech.cs307.project2.service.SupplyCenterService;
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


///reference: https://github.com/ZephyrusZhang/CS307-Project-2-SpringBoot
@Service
public class SupplyCenterServiceImpl extends ServiceImpl<SupplyCenterMapper, SupplyCenter> implements SupplyCenterService {
    @Override
    public boolean initTable() {
        List<SupplyCenter> centers = new LinkedList<>();
        try (FileInputStream FileInputStream = new FileInputStream("src/main/resources/data/center.csv");
             InputStreamReader InputStreamReader = new InputStreamReader(FileInputStream, StandardCharsets.UTF_8);
             CSVReader reader = new CSVReader(InputStreamReader)) {
            reader.readNext();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                SupplyCenter center = new SupplyCenter();
                center.setName(nextLine[1]);
                centers.add(center);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return this.saveBatch(centers);
    }
}
