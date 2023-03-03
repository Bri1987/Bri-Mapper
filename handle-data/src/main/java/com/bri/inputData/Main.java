package com.bri.inputData;

import cn.ac.amss.semanticweb.alignment.Mapping;
import cn.ac.amss.semanticweb.alignment.MappingCell;
import com.bri.inputData.deal_data.map2xml;
import com.bri.inputData.entity.MetadataField;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static cn.ac.amss.Demo.map_lexi;
import static com.bri.inputData.deal_data.bean2Map.metadata2map;
import static com.bri.inputData.deal_data.csv2Bean.readMetadataField;

public class Main {
    public static Map<MetadataField,MetadataField> mapping2map(Mapping lexi_map,Map<String,MetadataField> map_meta,Map<String,MetadataField> map_source){
        Set<MappingCell> map_cell = lexi_map.getM_mapping();
        Map<MetadataField,MetadataField> exchange_map=new HashMap<MetadataField,MetadataField>();
        Map<MetadataField,MetadataField> final_map=new HashMap<MetadataField,MetadataField>();
        Map<MetadataField,Object> confi=new HashMap<>();
        for (MappingCell cell : map_cell) {
            String entity1 = cell.getEntity1().split("#")[1];            //第一个是元数据
            String entity2 = cell.getEntity2().split("#")[1];              //第二个是有误数据
            MetadataField meta = map_meta.get(entity1);
            MetadataField source = map_source.get(entity2);

            if(source!=null && final_map.get(source)==null)
            {
                exchange_map.put(source,meta);
                confi.put(source,cell.getSelf_confidence());
            }
            else if(source!=null){
                BigDecimal bigDecimal = new BigDecimal(confi.get(source).toString());
                long result = bigDecimal.longValue();
                //如果原先的可信度比现在的小，就替换映射内容
                if(result<cell.getSelf_confidence())
                {
                    exchange_map.put(source,meta);
                }
            }
        }

        //将exchange_map的key,value内容互换
        exchange_map.forEach((source,meta)->{
            final_map.put(meta,source);
        });
        return final_map;
    }

    public static void main(String[] args) throws IOException, TransformerException, ParserConfigurationException, SAXException {
        ArrayList<MetadataField> metadataFields = readMetadataField("./handle-data/src/main/resources/test/metadata-tech.csv");
        ArrayList<MetadataField> sourceDataFields = readMetadataField("./handle-data/src/main/resources/test/wrong-tech.csv");

        Map<String,MetadataField> map_meta=metadata2map(metadataFields);
        Map<String,MetadataField> map_source=metadata2map(sourceDataFields);

        Mapping lexi_map =map_lexi();
       Map<MetadataField,MetadataField> final_map= mapping2map(lexi_map,map_meta,map_source);

        //将结果写入xml文件
        File file=new File("./result.xml");
        map2xml final_xml=new map2xml(final_map);
        final_xml.writeXML(file);
    }
}
