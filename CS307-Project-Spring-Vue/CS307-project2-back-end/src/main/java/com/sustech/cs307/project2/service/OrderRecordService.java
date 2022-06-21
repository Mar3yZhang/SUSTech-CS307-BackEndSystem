package com.sustech.cs307.project2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.opencsv.exceptions.CsvValidationException;
import com.sustech.cs307.project2.entity.OrderRecord;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Marcy ZHANG
 * @since 2022-05-15
 */
public interface OrderRecordService extends IService<OrderRecord> {

    void placeOrder(String path) throws CsvValidationException, IOException, SQLException;

    void updateOrder(String path) throws IOException, SQLException, CsvValidationException;

    void deleteOrder(String path) throws IOException, SQLException, CsvValidationException;

    long getCount();
}
