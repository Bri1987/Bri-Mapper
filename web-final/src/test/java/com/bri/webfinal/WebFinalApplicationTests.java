package com.bri.webfinal;

import com.bri.webfinal.model.科技平台DO;
import com.bri.webfinal.service.ElasticSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class WebFinalApplicationTests {

    @Autowired
    private ElasticSearchService service;

    @Test
    void contextLoads()
    {

    }

    @Test
    public void add() throws IOException {
        科技平台DO tech=new 科技平台DO();
        tech.set科技平台服务关键词("kkk");
        tech.set科技平台服务分类("gzs");
        tech.set科技平台服务内容描述("cool");
        service.addTech(tech,"888");
    }

    @Test
    public void delete() throws IOException
    {
        service.deleteTech("888");
    }

}
