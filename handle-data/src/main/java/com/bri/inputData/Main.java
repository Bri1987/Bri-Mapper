package com.bri.inputData;

import cn.ac.amss.semanticweb.alignment.Mapping;
import cn.ac.amss.semanticweb.alignment.MappingCell;
import com.bri.inputData.deal_data.map2xml;
import com.bri.inputData.entity.MetadataField;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static cn.ac.amss.Demo.map_lexi;
import static com.bri.inputData.deal_data.bean2Map.metadata2map;
import static com.bri.inputData.deal_data.csv2Bean.readMetadataField;

public class Main {
    public static Map<Set<MetadataField>,Set<MetadataField>> mapping2map(Mapping lexi_map,Map<String,Set<MetadataField>> map_meta,Map<String,Set<MetadataField>> map_source){


        Set<MappingCell> map_cell = lexi_map.getM_mapping();
        Map<Set<MetadataField>,Set<MetadataField>> exchange_map=new HashMap<Set<MetadataField>,Set<MetadataField>>();
        Map<Set<MetadataField>,Set<MetadataField>> final_map=new HashMap<Set<MetadataField>,Set<MetadataField>>();
        Map<Set<MetadataField>,Object> confi=new HashMap<>();
        for (MappingCell cell : map_cell) {
            String entity1 = cell.getEntity1().split("#")[1];            //第一个是元数据
            String entity2 = cell.getEntity2().split("#")[1];              //第二个是有误数据
            Set<MetadataField> meta = map_meta.get(entity1);
            Set<MetadataField> source = map_source.get(entity2);

            if(source!=null && exchange_map.get(source)==null)
            {
                exchange_map.put(source,meta);
                confi.put(source,cell.getSelf_confidence());
            }
            else if(exchange_map.get(source)!=null){
                BigDecimal bigDecimal = new BigDecimal(confi.get(source).toString());
                Double result = bigDecimal.doubleValue();
                //如果原先的可信度比现在的小，就替换映射内容
                if(result<cell.getSelf_confidence())
                {
                    exchange_map.put(source,meta);
                    //并更新可信度
                    confi.put(source,cell.getSelf_confidence());
                }
            }
        }

        //test
        Set<String> test_set1=new HashSet<>();
        exchange_map.forEach((source,meta)->{
            source.forEach((s)->{
                test_set1.add(s.getNameZH());
            });
//            if(Double.parseDouble(String.valueOf((Double) confi.get(source)))<0.8)
//            {
//                source.forEach((s)->{
//                    //test_set1.add(s.getNameZH());
//                    System.out.print(s.getNameZH()+" ");
//                });
//                meta.forEach((m)->{
//                    System.out.print(m.getNameZH()+" ");
//                });
//                System.out.println(confi.get(source));
//            }
        });
        //System.out.println("\n");
        Set<String> test_set2=new HashSet<>();
        map_source.forEach((key,value)->{
            test_set2.add(key.split("！")[0]);
        });
//        for(String s:test_set2)
//        {
//            if(!test_set1.contains(s))
//                System.out.println(s);
//        }

        //将exchange_map的key,value内容互换
        exchange_map.forEach((source,meta)->{
            final_map.put(meta,source);
        });
        return final_map;
    }

    public static void main(String[] args) throws IOException, TransformerException, ParserConfigurationException, SAXException {
        File file1=new File("./handle-data/src/main/resources/m88.csv");
        ArrayList<MetadataField> metadataFields = readMetadataField(file1);
        File file2=new File("./handle-data/src/main/resources/w88.csv");
        ArrayList<MetadataField> sourceDataFields = readMetadataField(file2);

        Map<String,Set<MetadataField>> map_meta=metadata2map(metadataFields);
        Map<String,Set<MetadataField>> map_source=metadata2map(sourceDataFields);

        Mapping lexi_map =map_lexi("w88");
        Map<Set<MetadataField>,Set<MetadataField>> final_map= mapping2map(lexi_map,map_meta,map_source);

        //将结果写入xml文件
        File file=new File("./result.xml");
        map2xml final_xml=new map2xml(final_map);
        final_xml.writeXML(file);
        //Map<Set<MetadataField>,Set<MetadataField>> map2=final_xml.readXML(file);
//        int flag=0;
//        int count=0;
//        for(Set<MetadataField> set:final_map.keySet())
//        {
//            for(MetadataField m:set)
//            {
//                for(Set<MetadataField> set1:map2.keySet())
//                {
//                    for (MetadataField m2:set1)
//                    {
//                        if(m.getNameZH().equals(m2.getNameZH()))
//                            flag=1;
//                        break;
//                    }
//                    if(flag==1)
//                        break;
//                }
//                if(flag==0)
//                    System.out.println(m.getNameZH());
//                else
//                {
//                    flag=0;
//                    System.out.println(m.getNameZH()+":right:"+count++);
//                }
//                break;
//            }
//        }
    }
}
