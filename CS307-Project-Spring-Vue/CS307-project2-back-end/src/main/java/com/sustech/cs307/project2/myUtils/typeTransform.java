package com.sustech.cs307.project2.myUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class typeTransform {
    //将字符串转化为sql日期类型
    public static Date StringToDate(String sDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date d = null;
        try {
            d = format.parse(sDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert d != null;
        return new Date(d.getTime());
    }

    //将模板标准化为产品
    public static String ModelToProduct(String model) {
        String product = model.substring(0, model.length() - 2);
        String[] partName = product.replaceAll("[A-Z]", "_$0").split("_");
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 1; i < partName.length - 1; i++) {
            if (partName[i].charAt(partName[i].length() - 1) != '-') {
                stringBuffer.append(partName[i]).append(" ");
            } else {
                stringBuffer.append(partName[i]);
            }
        }
        stringBuffer.append(partName[partName.length - 1]);
        return stringBuffer.toString();
    }
}
