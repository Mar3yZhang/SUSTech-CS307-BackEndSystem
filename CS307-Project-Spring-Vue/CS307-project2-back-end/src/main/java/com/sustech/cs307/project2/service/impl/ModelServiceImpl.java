package com.sustech.cs307.project2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.sustech.cs307.project2.entity.Model;
import com.sustech.cs307.project2.mapper.ModelMapper;
import com.sustech.cs307.project2.service.ModelService;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Marcy ZHANG
 * @since 2022-05-15
 */
@Service
public class ModelServiceImpl extends ServiceImpl<ModelMapper, Model> implements ModelService {
    private final ModelMapper modelMapper;

    public ModelServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean initTable() {
        List<Model> models = new LinkedList<>();
        try (FileInputStream FileInputStream = new FileInputStream("src/main/resources/data/model.csv");
             InputStreamReader InputStreamReader = new InputStreamReader(FileInputStream, StandardCharsets.UTF_8);
             CSVReader reader = new CSVReader(InputStreamReader)) {
            reader.readNext();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Model model = new Model();
                model.setNumber(nextLine[1]);
                model.setModel(nextLine[2]);
                model.setProduct(nextLine[3]);
                model.setUnitPrice(Long.parseLong(nextLine[4]));
                models.add(model);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return this.saveBatch(models);
    }

    @Override
    public void getFavoriteProductModel() {
        List<Map<String, Object>> list = modelMapper.getFavoriteProductModel();
        for (Map<String, Object> row : list) {
            String product_model = (String) row.get("product_model");
            long max = (Long) row.get("max");
            System.out.printf("%-20s %-20d", product_model, max);
            System.out.println();
        }
    }
}
