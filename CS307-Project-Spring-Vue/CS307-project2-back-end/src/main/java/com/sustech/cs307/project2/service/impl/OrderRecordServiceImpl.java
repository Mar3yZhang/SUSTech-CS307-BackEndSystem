package com.sustech.cs307.project2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.sustech.cs307.project2.entity.Contract;
import com.sustech.cs307.project2.entity.OrderRecord;
import com.sustech.cs307.project2.mapper.*;
import com.sustech.cs307.project2.service.OrderRecordService;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Marcy ZHANG
 * @since 2022-05-15
 */
@Service
public class OrderRecordServiceImpl extends ServiceImpl<OrderRecordMapper, OrderRecord> implements OrderRecordService {
    private final StaffMapper staffMapper;
    private final ModelMapper modelMapper;
    private final InventoryMapper inventoryMapper;
    private final EnterpriseMapper enterpriseMapper;
    private final OrderRecordMapper orderRecordMapper;
    private final ContractMapper contractMapper;

    public OrderRecordServiceImpl(StaffMapper staffMapper, ModelMapper modelMapper,
                                  InventoryMapper inventoryMapper, EnterpriseMapper enterpriseMapper,
                                  OrderRecordMapper orderRecordMapper, ContractMapper contractMapper) {
        this.staffMapper = staffMapper;
        this.modelMapper = modelMapper;
        this.inventoryMapper = inventoryMapper;
        this.enterpriseMapper = enterpriseMapper;
        this.orderRecordMapper = orderRecordMapper;
        this.contractMapper = contractMapper;
    }

    @Override
    public void placeOrder(String path) throws CsvValidationException, IOException, SQLException {
        boolean HasEnoughProduct_model;
        boolean IsSalesman;
        String contract_num;
        String enterprise;
        String product_model;
        String quantity;
        String contract_manager;
        String contract_date;
        String estimated_delivery_date;
        String lodgement_date;
        String salesman_num;
        String contract_type;


        CSVReader reader = new CSVReader(new InputStreamReader(Files.newInputStream(Paths.get(path)), StandardCharsets.UTF_8));
        reader.readNext();
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            contract_num = nextLine[0];
            enterprise = nextLine[1];
            product_model = nextLine[2];
            quantity = nextLine[3];
            contract_manager = nextLine[4];
            contract_date = nextLine[5];
            estimated_delivery_date = nextLine[6];
            lodgement_date = nextLine[7];
            salesman_num = nextLine[8];
            contract_type = nextLine[9];


            String supply_center = enterpriseMapper.getCenterByEnterprise(enterprise);
            long validNum = inventoryMapper.getValidNumByCenterAndModel(supply_center, product_model);
            if (validNum != 0) {
                HasEnoughProduct_model = (inventoryMapper.getInventoryByCenterAndModel(supply_center, product_model) > Long.parseLong(quantity));


                IsSalesman = Objects.equals(staffMapper.getStaffByNumber(salesman_num).getType(), "Salesman");

                if (HasEnoughProduct_model && IsSalesman) {
                    //更新订单信息
                    orderRecordMapper.insertOrderRecord(contract_num, enterprise, product_model,
                            Long.parseLong(quantity), contract_manager,
                            Date.valueOf(contract_date), Date.valueOf(estimated_delivery_date),
                            Date.valueOf(lodgement_date), salesman_num, contract_type);


                    //更新库存信息
                    long unit_price = modelMapper.getPriceByModel(product_model);
                    inventoryMapper.updateWhenPlaceOrder(Long.parseLong(quantity), Long.parseLong(quantity) * unit_price, supply_center, product_model);

                    //更新合同信息
                    boolean hasSuchContract = (contractMapper.getContractByNumber(contract_num) != null);
                    if (hasSuchContract) {
                        contractMapper.UpdateContractWhenPlaceOrder(contract_num);
                    } else {
                        String contract_manager_name = staffMapper.getStaffByNumber(contract_manager).getName();
                        Contract contract = new Contract();
                        contract.setContractNumber(contract_num);
                        contract.setContractManagerName(contract_manager_name);
                        contract.setEnterprise(enterprise);
                        contract.setSupplyCenter(supply_center);
                        contract.setOrdernum(1L);
                        contractMapper.insert(contract);
                    }
                }
            }
        }
    }


    @Override
    public void updateOrder(String path) throws IOException, SQLException, CsvValidationException {
        boolean isOwnOrder;

        String contract;
        String product_model;
        String salesman;
        String quantity;
        String estimate_delivery_date;
        String lodgement_date;

        String[] line;

        CSVReader reader = new CSVReader(new InputStreamReader(Files.newInputStream(Paths.get(path)), StandardCharsets.UTF_8));
        reader.readNext();
        while ((line = reader.readNext()) != null) {
            contract = line[0];
            product_model = line[1];
            salesman = line[2];
            quantity = line[3];
            estimate_delivery_date = line[4];
            lodgement_date = line[5];

            //满足这种条件的orderRecord是否会超过1？
            isOwnOrder = orderRecordMapper.getOrderRecordBy3Attribute(product_model, salesman, contract) != null;
            if (isOwnOrder) {
                //更新库存
                long unit_price = modelMapper.getPriceByModel(product_model);
                long quantity_in_order = contractMapper.getQuantityBy3Attribute(product_model, salesman, contract);
                String enterprise = contractMapper.getContractByNumber(contract).getEnterprise();
                String supply_center = enterpriseMapper.getCenterByEnterprise(enterprise);
                long difference = Long.parseLong(quantity) - quantity_in_order;
                inventoryMapper.updateWhenUpdateOrder(difference, difference, unit_price, supply_center, product_model);

                //更新订单
                if (Long.parseLong(quantity) == 0) {
                    inventoryMapper.updatePlaceOrderNum(supply_center, product_model);
                    orderRecordMapper.deleteOrderBy3Attribute(product_model, salesman, contract);
                } else {
                    orderRecordMapper.updateOrderBy3Attribute(Long.parseLong(quantity), Date.valueOf(estimate_delivery_date), Date.valueOf(lodgement_date),
                            contract, product_model, salesman);
                }
            }
        }
    }

    @Override
    public void deleteOrder(String path) throws IOException, SQLException, CsvValidationException {
        long seq;

        String product_model;
        long quantity;
        Date estimated_delivery_date;
        String contract_num;
        String salesman_num;

        String[] line;
        CSVReader reader = new CSVReader(new InputStreamReader(Files.newInputStream(Paths.get(path)), StandardCharsets.UTF_8));
        reader.readNext();
        while ((line = reader.readNext()) != null) {
            contract_num = line[0];
            salesman_num = line[1];
            seq = Long.parseLong(line[2]);

            ArrayList<OrderRecord> records = orderRecordMapper.getSortedOrderRecord(contract_num, salesman_num);
            if (seq <= records.size()) {
                OrderRecord record = records.get((int) (seq - 1));
                product_model = record.getProductModel();
                quantity = record.getQuantity();
                estimated_delivery_date = Date.valueOf(record.getEstimatedDeliveryDate());

                orderRecordMapper.deleteOrderBy3Attribute(product_model, salesman_num, contract_num);
                contractMapper.orderNumSubOne(contract_num);

                long unit_price = modelMapper.getPriceByModel(product_model);
                String enterprise = contractMapper.getContractByNumber(contract_num).getEnterprise();
                String supply_center = enterpriseMapper.getCenterByEnterprise(enterprise);
                inventoryMapper.updateWhenDeleteOrder(quantity, quantity, unit_price, supply_center, product_model);

                //还没有更新order record
            }
        }

    }

    @Override
    public long getCount() {
        long count = orderRecordMapper.getCount();
        System.out.println(count);
        return count;
    }
}
