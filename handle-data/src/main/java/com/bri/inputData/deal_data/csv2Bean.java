package com.bri.inputData.deal_data;

import com.bri.inputData.entity.MetadataField;
import com.bri.inputData.entity.MetadataFieldDataType;
import com.bri.inputData.translate.ParseResult;
import com.bri.inputData.translate.TransApi;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class csv2Bean
{
    private static final String APP_ID = "20230211001557557";
    private static final String SECURITY_KEY = "AH2maRs1ZpNspudnmXrG";
    public static ArrayList<MetadataField> readMetadataField(String file) throws IOException {
        ArrayList<MetadataField> fieldList = new ArrayList<>();
        CSVFormat format = CSVFormat.EXCEL.builder().setHeader().setSkipHeaderRecord(false).build();
        try (CSVParser csvParser = CSVParser.parse(new File(file), Charset.forName("gb2312"), format)) {
            for (CSVRecord record : csvParser) {
                MetadataField field = new MetadataField();
                //field.setFieldId(record.get("数据元字段ID"));
                field.setNameZH(record.get("中文名称"));
                field.setSynonym(record.get("同义名称"));
                field.setNameEN(record.get("英文名称"));
                field.setSynonym(record.get("表达符号"));
                field.setFieldType(record.get("类词"));
                field.setUnit(record.get("计量单位"));
                field.setDescription(record.get("定义"));
                field.setReference(record.get("参考来源"));
                field.setReferenceMJ(record.get("参考来源MJ"));
                field.setObject(record.get("描述对象"));
                field.setCategory(record.get("所属分类"));
                String dataType = record.get("数据类型");
                switch (dataType) {
                    case "布尔型":
                        field.setDataType(MetadataFieldDataType.BOOLEAN);
                        break;
                    case "二进制型":
                        field.setDataType(MetadataFieldDataType.BINARY);
                        break;
                    case "日期时间型":
                        field.setDataType(MetadataFieldDataType.DATETIME);
                        break;
                    case "数值型":
                        field.setDataType(MetadataFieldDataType.NUMBER);
                        break;
                    case "字符串型":
                        field.setDataType(MetadataFieldDataType.TEXT);
                        break;
                }
                String length = record.get("长度");
                if (length.contains("..")) {
                    field.setMinLength(Integer.parseInt(length.substring(0, length.indexOf(".."))));
                    field.setMaxLength(Integer.parseInt(length.substring(length.indexOf("..") + 2)));
                } else {
                    try {
                        field.setMinLength(Integer.parseInt(length));
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                }
                fieldList.add(field);
            }
        }
        return fieldList;
    }
}
