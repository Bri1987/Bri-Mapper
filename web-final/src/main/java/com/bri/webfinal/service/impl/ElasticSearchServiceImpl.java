package com.bri.webfinal.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bri.webfinal.model.科技平台DO;
import com.bri.webfinal.service.ElasticSearchService;
import com.bri.webfinal.service.FunctionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.queryparser.surround.query.SrndTermQuery;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

@Service
@Slf4j
public class ElasticSearchServiceImpl implements ElasticSearchService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private FunctionService functionService;

    private static final String ES_INDEX="tech_latest";

    @Override
    public boolean addTech(科技平台DO tech, String id) throws IOException {
        IndexRequest request=new IndexRequest("tech_latest").id(id).source(beanToMap(tech));
        IndexResponse response=restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(JSONObject.toJSON(response));
        return false;
    }

    @Override
    public Map<String,Object> getTech(String id) throws IOException {
        GetRequest request=new GetRequest(ES_INDEX,id);
        GetResponse response=restHighLevelClient.get(request,RequestOptions.DEFAULT);
        return response.getSource();
    }

    @Override
    public boolean deleteTech(String id) throws IOException {
        DeleteRequest request=new DeleteRequest(ES_INDEX,id);
        restHighLevelClient.delete(request,RequestOptions.DEFAULT);
        return true;
    }

    @Override
    public boolean deleteAllTech() throws IOException {
        DeleteByQueryRequest request=new DeleteByQueryRequest(ES_INDEX);
        request.setQuery(QueryBuilders.matchAllQuery());
        BulkByScrollResponse response=restHighLevelClient.deleteByQuery(request,RequestOptions.DEFAULT);
        return true;
    }

    @Override
    public boolean importAll(int id1, int id2, File file1,File file2) throws Exception {
        String select_sql="select * from 科技平台";
        List<科技平台DO> list=functionService.syncSelect(id1,id2,file1,file2,select_sql);
        Random random=new Random();
        for(科技平台DO tech:list)
        {
            addTech(tech,String.valueOf(random.nextInt()));
        }
        return true;
    }

    @Override
    public List<科技平台DO> searchAll() throws IOException {
        SearchRequest request=new SearchRequest(ES_INDEX);
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(100);
        request.source(searchSourceBuilder);
        SearchResponse response=restHighLevelClient.search(request,RequestOptions.DEFAULT);
        SearchHit[] hits=response.getHits().getHits();
        List<科技平台DO> list=new LinkedList<>();
        for(SearchHit hit:hits)
        {
            科技平台DO tech=JSONObject.parseObject(hit.getSourceAsString(),科技平台DO.class);
            list.add(tech);
        }
        return list;
    }

    public static <T> Map<String,Object> beanToMap(T bean)
    {
        Map<String,Object> map=new HashMap<>();
        if(bean!=null)
        {
            BeanMap beanMap=BeanMap.create(bean);
            for(Object key:beanMap.keySet())
                if(beanMap.get(key)!=null)
                    map.put(key+"",beanMap.get(key));
        }
        return map;
    }
}
