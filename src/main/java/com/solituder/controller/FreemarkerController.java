package com.solituder.controller;

import com.solituder.freemarker.FreemarkerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 64274 on 2018/6/2.
 */
@Controller
@RequestMapping(value="/freemarker")
public class FreemarkerController{

    @Autowired private FreemarkerUtils freemarkerUtils; // sos 注意这里  必须通过注入使用

    @RequestMapping(value="/test1", method= RequestMethod.POST)
    public void test1() throws Exception    {

        Map<String, Object> mv1 = new HashMap<>();
        mv1.put("intVar",100);
        mv1.put("LongVar",10000000000000000L);
        mv1.put("doubleVar",3.141592675d);
        mv1.put("StringVar","我是freemarker,是字符串！");
        mv1.put("booleanVar", true);
        mv1.put("dateVar1",new Date());
        mv1.put("nullVar1",null);
        mv1.put("nullVar","我是空");
        freemarkerUtils.common(mv1,"free1.html","result.vue");

    }

}
