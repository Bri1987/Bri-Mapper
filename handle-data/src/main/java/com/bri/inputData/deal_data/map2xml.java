package com.bri.inputData.deal_data;

import com.bri.inputData.entity.MetadataField;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class map2xml
{
    private Map<MetadataField,MetadataField> map;

    public map2xml(Map<MetadataField, MetadataField> map) {
        this.map = map;
    }

    public void writeXML(File file) throws ParserConfigurationException, TransformerException {
        /*
        <root>
            <map metadata="科技平台名称">
                <meta>
                     <name>...</name>
                </meta>

                <source>
                    <name>...</name>
                </source>
            </map>
        </root>
         */

        // 初始化一个XML解析工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // 创建一个DocumentBuilder实例
        DocumentBuilder builder = factory.newDocumentBuilder();

        // 构建一个Document实例
        Document doc = builder.newDocument();
        doc.setXmlStandalone(true);

        //创建根结点
        Element element= doc.createElement("root");

        map.forEach((meta,source)->{
            //创建map
            Element element_map_child = doc.createElement("map");
            element_map_child.setAttribute("metadata",meta.getNameZH());
            element.appendChild(element_map_child);

            //创建map的第一个子结点meta
            Element child_meta=doc.createElement("meta");
            element_map_child.appendChild(child_meta);
            //将meta对象的内容加入xml文件
            Element child_zh= doc.createElement("value");
            child_zh.setAttribute("name","中文名称");
            child_zh.setTextContent(meta.getNameZH());
            child_meta.appendChild(child_zh);

            Element child_data_type= doc.createElement("value");
            child_data_type.setAttribute("name","数据类型");
            child_data_type.setTextContent(meta.getDataType().name());
            child_meta.appendChild(child_data_type);

            Element child_type= doc.createElement("value");
            child_type.setAttribute("name","类词");
            child_type.setTextContent(meta.getFieldType());
            child_meta.appendChild(child_type);

            Element child_length= doc.createElement("value");
            child_length.setAttribute("name","长度");
            //最小长度与最大长度
            Element length_min=doc.createElement("min");
            length_min.setTextContent(Integer.toString(meta.getMinLength()));
            child_length.appendChild(length_min);
            Element length_max=doc.createElement("max");
            length_max.setTextContent(Integer.toString(meta.getMaxLength()));
            child_length.appendChild(length_max);

            child_meta.appendChild(child_length);

            // TODO 值域未做


            Element child_unit= doc.createElement("value");
            child_unit.setAttribute("name","计量单位");
            child_unit.setTextContent(meta.getUnit());
            child_meta.appendChild(child_unit);

            Element child_description= doc.createElement("value");
            child_description.setAttribute("name","定义");
            child_description.setTextContent(meta.getUnit());
            child_meta.appendChild(child_description);

            Element child_reference= doc.createElement("value");
            child_reference.setAttribute("name","参考来源");
            child_reference.setTextContent(meta.getReference());
            child_meta.appendChild(child_reference);

            Element child_reference_mj= doc.createElement("value");
            child_reference_mj.setAttribute("name","参考来源MJ");
            child_reference_mj.setTextContent(meta.getReferenceMJ());
            child_meta.appendChild(child_reference_mj);

            Element child_object= doc.createElement("value");
            child_object.setAttribute("name","描述对象");
            child_object.setTextContent(meta.getObject());
            child_meta.appendChild(child_object);

            Element child_category= doc.createElement("value");
            child_category.setAttribute("name","所属分类");
            child_category.setTextContent(meta.getCategory());
            child_meta.appendChild(child_category);


            //创建map的第二个子结点source
            Element child_source=doc.createElement("source");
            element_map_child.appendChild(child_source);

            //将source对象的内容加入xml文件
            Element child_zh2= doc.createElement("value");
            child_zh2.setAttribute("name","中文名称");
            child_zh2.setTextContent(source.getNameZH());
            child_source.appendChild(child_zh2);

            Element child_data_type2= doc.createElement("value");
            child_data_type2.setAttribute("name","数据类型");
            child_data_type2.setTextContent(source.getDataType().name());
            child_source.appendChild(child_data_type2);

            Element child_type2= doc.createElement("value");
            child_type2.setAttribute("name","类词");
            child_type2.setTextContent(source.getFieldType());
            child_source.appendChild(child_type2);

            Element child_length2= doc.createElement("value");
            child_length2.setAttribute("name","长度");
            //最小长度与最大长度
            Element length_min2=doc.createElement("min");
            length_min2.setTextContent(Integer.toString(source.getMinLength()));
            child_length2.appendChild(length_min2);
            Element length_max2=doc.createElement("max");
            length_max2.setTextContent(Integer.toString(source.getMaxLength()));
            child_length2.appendChild(length_max2);

            child_source.appendChild(child_length2);

            //值域未做


            Element child_unit2= doc.createElement("value");
            child_unit2.setAttribute("name","计量单位");
            child_unit2.setTextContent(source.getUnit());
            child_source.appendChild(child_unit2);

            Element child_description2= doc.createElement("value");
            child_description2.setAttribute("name","定义");
            child_description2.setTextContent(source.getUnit());
            child_source.appendChild(child_description2);

            Element child_reference2= doc.createElement("value");
            child_reference2.setAttribute("name","参考来源");
            child_reference2.setTextContent(source.getReference());
            child_source.appendChild(child_reference2);

            Element child_reference_mj2= doc.createElement("value");
            child_reference_mj2.setAttribute("name","参考来源MJ");
            child_reference_mj2.setTextContent(source.getReferenceMJ());
            child_source.appendChild(child_reference_mj2);

            Element child_object2= doc.createElement("value");
            child_object2.setAttribute("name","描述对象");
            child_object2.setTextContent(source.getObject());
            child_source.appendChild(child_object2);

            Element child_category2= doc.createElement("value");
            child_category2.setAttribute("name","所属分类");
            child_category2.setTextContent(source.getCategory());
            child_source.appendChild(child_category2);
        });

        //添加根结点
        doc.appendChild(element);

        //把构造的xml结构，写入到具体文件中
        TransformerFactory formerFactory = TransformerFactory.newInstance();
        Transformer transformer=formerFactory.newTransformer();
        //换行
        transformer.setOutputProperty(OutputKeys.INDENT,"YES");
        //字符编码
        transformer.setOutputProperty(OutputKeys.ENCODING,"utf-8");

        transformer.transform(new DOMSource(doc),new StreamResult(file));

        System.out.println("success");
    }

    /*
    public static MetadataField setValue(Node value, Attr attr,MetadataField field1)
    {
        if(field1==null)
            field1=new MetadataField();
        String content= value.getTextContent();

        if(attr.getValue().equals("中文名称")) {
            field1.setNameZH(content);
        }
        //TODO 数据类型
        //else if(attr.getValue().equals("数据类型"))
        //field1.setDataType(value.getNodeValue());
        else if(attr.getValue().equals("类词"))
            field1.setFieldType(content);
        else if(attr.getValue().equals("计量单位"))
            field1.setUnit(content);
        else if(attr.getValue().equals("定义"))
            field1.setDescription(content);
        else if(attr.getValue().equals("参考来源"))
            field1.setReference(content);
        else if(attr.getValue().equals("参考来源MJ"))
            field1.setReferenceMJ(content);
        else if(attr.getValue().equals("描述对象"))
            field1.setObject(content);
        else if(attr.getValue().equals("所属分类"))
            field1.setCategory(content);
        else if(attr.getValue().equals("长度")) {
            NodeList length_list=value.getChildNodes();
            for(int tmp=0;tmp<length_list.getLength();tmp++)
            {
                Node min_max=length_list.item(tmp);
                if(min_max.getNodeName().equals("min"))
                    field1.setMinLength(Integer.parseInt(min_max.getTextContent()));
                else if(min_max.getNodeName().equals("max"))
                    field1.setMaxLength(Integer.parseInt(min_max.getTextContent()));
            }
        }
        return field1;
    }
    //进来的是root节点
    public static Map<MetadataField, MetadataField> parseElement(Element element)
    {
        Map<MetadataField,MetadataField> map=new HashMap<>();

        //节点
        //是所有map节点
        NodeList nodeList=element.getChildNodes();
        Node childNode;
        MetadataField field1=null,field2=null;
        for(int i=0;i<nodeList.getLength();i++)
        {
            childNode=nodeList.item(i);              //childNode是每一个map节点
            NodeList map_list=childNode.getChildNodes();
            //再走一层，是meta或source
            for(int k=0;k<map_list.getLength();k++)
            {
                Node map_child=map_list.item(k);
                //meta
                if(map_child.getNodeName().equals("meta"))
                {
                    NodeList meta_list=map_child.getChildNodes();
                    //是各个属性了
                    for(int j=0;j<meta_list.getLength();j++)
                    {
                        Node value=meta_list.item(j);
                        NamedNodeMap attris = value.getAttributes();
                        if(attris!=null)
                        {
                            for (int o = 0; o < attris.getLength(); o++) {
                                //只会有一个attr
                                Attr attr = (Attr) attris.item(o);
                                if(attr!=null)
                                    field1=setValue(value,attr,field1);
                            }
                        }
                    }
                }
                //source
                else if(map_child.getNodeName().equals("source"))
                {
                    NodeList source_list=map_child.getChildNodes();
                    //是各个属性了
                    for(int j=0;j<source_list.getLength();j++)
                    {
                        Node value=source_list.item(j);
                        NamedNodeMap attris = value.getAttributes();
                        if(attris!=null)
                        {
                            for (int o = 0; o < attris.getLength(); o++) {
                                //只会有一个attr
                                Attr attr = (Attr) attris.item(o);
                                if(attr!=null)
                                    field2=setValue(value,attr,field2);
                            }
                        }
                    }
                }
            }
            if(field1!=null && field2!=null)
            {
                //加入map
                map.put(field1,field2);
                field1=null;
                field2=null;
            }
        }
        return map;
    }

    public Map<MetadataField,MetadataField> readXML(File file) throws IOException, SAXException, ParserConfigurationException {
        Map<MetadataField,MetadataField> map=new HashMap<>();

        // 初始化一个XML解析工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // 创建一个DocumentBuilder实例
        DocumentBuilder builder = factory.newDocumentBuilder();

        // 创建一个解析XML的Document实例
        Document doc = builder.parse(file);

        //递归解析element
        //第一个节点,是root节点
        Element element = doc.getDocumentElement();

        map=parseElement(element);

        return map;
    }*/
}