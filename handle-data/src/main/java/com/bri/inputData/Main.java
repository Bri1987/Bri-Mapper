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

//    public static Map<Set<MetadataField>,Set<MetadataField>> mapping2map(Mapping lexi_map,Map<String,Set<MetadataField>> map_meta,Map<String,Set<MetadataField>> map_source){
//
//
//        Set<MappingCell> map_cell = lexi_map.getM_mapping();
//        Map<Set<MetadataField>,Set<MetadataField>> exchange_map=new HashMap<Set<MetadataField>,Set<MetadataField>>();
//        Map<Set<MetadataField>,Set<MetadataField>> final_map=new HashMap<Set<MetadataField>,Set<MetadataField>>();
//        Map<Set<MetadataField>,Object> confi=new HashMap<>();
//        for (MappingCell cell : map_cell) {
//            String entity1 = cell.getEntity1().split("#")[1];            //第一个是元数据
//            String entity2 = cell.getEntity2().split("#")[1];              //第二个是有误数据
//            Set<MetadataField> meta = map_meta.get(entity1);
//            Set<MetadataField> source = map_source.get(entity2);
//
//            if(meta==null)
//                System.out.println(entity1);
//
//            if(source!=null && exchange_map.get(source)==null)
//            {
//                exchange_map.put(source,meta);
//                confi.put(source,cell.getSelf_confidence());
//            }
//            else if(exchange_map.get(source)!=null){
//                BigDecimal bigDecimal = new BigDecimal(confi.get(source).toString());
//                Double result = bigDecimal.doubleValue();
//                //如果原先的可信度比现在的小，就替换映射内容
//                if(result<cell.getSelf_confidence())
//                {
//                    exchange_map.put(source,meta);
//                    //并更新可信度
//                    confi.put(source,cell.getSelf_confidence());
//                }
//            }
//        }
//
//        //test
//        Set<String> test_set1=new HashSet<>();
//        exchange_map.forEach((source,meta)->{
//            source.forEach((s)->{
//                test_set1.add(s.getNameZH());
//            });
////            if(Double.parseDouble(String.valueOf((Double) confi.get(source)))<0.8)
////            {
////                source.forEach((s)->{
////                    //test_set1.add(s.getNameZH());
////                    System.out.print(s.getNameZH()+" ");
////                });
////                meta.forEach((m)->{
////                    System.out.print(m.getNameZH()+" ");
////                });
////                System.out.println(confi.get(source));
////            }
//        });
//        //System.out.println("\n");
//        Set<String> test_set2=new HashSet<>();
//        map_source.forEach((key,value)->{
//            test_set2.add(key.split("！")[0]);
//        });
////        for(String s:test_set2)
////        {
////            if(!test_set1.contains(s))
////                System.out.println(s);
////        }
//
//        //将exchange_map的key,value内容互换
//        exchange_map.forEach((source,meta)->{
//            final_map.put(meta,source);
//        });
//        return final_map;
//    }

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
                //遍历source
                for(MetadataField s:source)
                {
                    s.setConfi(cell.getSelf_confidence());
                }
                exchange_map.put(source,meta);
                confi.put(source,cell.getSelf_confidence());
            }
            else if(exchange_map.get(source)!=null){
                BigDecimal bigDecimal = new BigDecimal(confi.get(source).toString());
                Double result = bigDecimal.doubleValue();
                //如果原先的可信度比现在的小，就替换映射内容
                if(result<cell.getSelf_confidence())
                {
                    exchange_map.remove(source);
                    confi.remove(source);
                    //遍历source
                    for(MetadataField s:source)
                    {
                        s.setConfi(cell.getSelf_confidence());
                    }
                    exchange_map.put(source,meta);
                    //并更新可信度
                    confi.put(source,cell.getSelf_confidence());

                }
            }
        }

        //test
//        Set<String> test_set1=new HashSet<>();
//        exchange_map.forEach((source,meta)->{
//            source.forEach((s)->{
//                test_set1.add(s.getNameZH());
//            });
////            if(Double.parseDouble(String.valueOf((Double) confi.get(source)))<0.8)
////            {
////                source.forEach((s)->{
////                    //test_set1.add(s.getNameZH());
////                    System.out.print(s.getNameZH()+" ");
////                });
////                meta.forEach((m)->{
////                    System.out.print(m.getNameZH()+" ");
////                });
////                System.out.println(confi.get(source));
////            }
//        });
        //System.out.println("\n");
//        Set<String> test_set2=new HashSet<>();
//        map_source.forEach((key,value)->{
//            test_set2.add(key.split("！")[0]);
//        });
//        for(String s:test_set2)
//        {
//            if(!test_set1.contains(s))
//                System.out.println(s);
//        }

        //将exchange_map的key,value内容互换
        exchange_map.forEach((source,meta)->{
            final_map.put(meta,source);
        });

        // 创建随机数生成器
//        Random rand = new Random();
//
//        //遍历这个map，调整一下相似度
//        for(Set<MetadataField> m_key:final_map.keySet())
//        {
//            if(m_key!=null) {
//                //一个属性不同，就减0.05
//                Set<MetadataField> m_value=final_map.get(m_key);
//                //取一个元数据
//                MetadataField m_k=m_key.iterator().next();
//                for(MetadataField mm:m_value)
//                {
//                    //与元数据一项项比较
//                    if(!mm.getDataType().equals(m_k.getDataType()))
//                    {
//                        // 生成随机数
//                        double rand_num = rand.nextDouble() * 0.037 + 0.015;
//                        mm.setConfi(mm.getConfi()-rand_num);
//                    }
//                    if(!mm.getFieldType().equals(m_k.getFieldType()))
//                    {
//                        // 生成随机数
//                        double rand_num = rand.nextDouble() * 0.037 + 0.015;
//                        mm.setConfi(mm.getConfi()-rand_num);
//                    }
//
//                    if(!mm.getUnit().equals(m_k.getUnit()))
//                    {
//                        // 生成随机数
//                        double rand_num = rand.nextDouble() * 0.037 + 0.015;
//                        mm.setConfi(mm.getConfi()-rand_num);
//                    }
//                    if(!mm.getValue().equals(m_k.getValue()))
//                    {
//                        // 生成随机数
//                        double rand_num = rand.nextDouble() * 0.037 + 0.015;
//                        mm.setConfi(mm.getConfi()-rand_num);
//                    }
//                    if(mm.getFixLength()!=m_k.getFixLength() || mm.getMinLength()!=m_k.getMinLength() || mm.getMaxLength()!=m_k.getMaxLength())
//                    {
//                        // 生成随机数
//                        double rand_num = rand.nextDouble() * 0.047 + 0.015;
//                        mm.setConfi(mm.getConfi()-rand_num);
//                    }
//                }
//            }
//        }
        final_map.remove(null);
        return final_map;
    }

    public static void main(String[] args) throws IOException, TransformerException, ParserConfigurationException, SAXException {
        File file1=new File("./handle-data/src/main/resources/m88.csv");

        long startTime =  System.currentTimeMillis();

        ArrayList<MetadataField> metadataFields = readMetadataField(file1);
        File file2=new File("./handle-data/src/main/resources/w88.csv");
        ArrayList<MetadataField> sourceDataFields = readMetadataField(file2);

        Map<String,Set<MetadataField>> map_meta=metadata2map(metadataFields);
        Map<String,Set<MetadataField>> map_source=metadata2map(sourceDataFields);

        Mapping lexi_map =map_lexi("w88");
        Map<Set<MetadataField>,Set<MetadataField>> final_map= mapping2map(lexi_map,map_meta,map_source);

        //将结果写入xml文件
        File file=new File("./result_test.xml");
        map2xml final_xml=new map2xml(final_map);
        final_xml.writeXML(file);


        long endTime =  System.currentTimeMillis();
        long usedTime = (endTime-startTime)/1000;
        System.out.println(usedTime);
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
