package com.sustech.cs307.project2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.sustech.cs307.project2.entity.Staff;
import com.sustech.cs307.project2.mapper.StaffMapper;
import com.sustech.cs307.project2.service.StaffService;
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
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements StaffService {
    private final StaffMapper staffMapper;

    public StaffServiceImpl(StaffMapper staffMapper) {
        this.staffMapper = staffMapper;
    }

    @Override
    public boolean initTable() {
        List<Staff> staffs = new LinkedList<>();
        try (FileInputStream FileInputStream = new FileInputStream("src/main/resources/data/staff.csv");
             InputStreamReader InputStreamReader = new InputStreamReader(FileInputStream, StandardCharsets.UTF_8);
             CSVReader reader = new CSVReader(InputStreamReader)) {
            reader.readNext();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Staff staff = new Staff();
                staff.setName(nextLine[1]);
                staff.setAge(Long.parseLong(nextLine[2]));
                staff.setGender(nextLine[3]);
                staff.setNumber(nextLine[4]);
                staff.setSupplyCenter(nextLine[5]);
                staff.setMobileNumber(nextLine[6]);
                staff.setType(nextLine[7]);
                staffs.add(staff);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return this.saveBatch(staffs);
    }

    public long[] getCount() {
        final String[] type = {"Director", "Contracts Manager", "Salesman", "Supply Staff"};
        long[] num = new long[4];


        for (int i = 0; i < 4; i++) {
            num[i] = staffMapper.getCountByType(type[i]);
        }

        for (int i = 0; i < 4; i++) {
            System.out.printf("%-20s %-20d", type[i], num[i]);
            System.out.println();
        }
        return num;
    }
}
