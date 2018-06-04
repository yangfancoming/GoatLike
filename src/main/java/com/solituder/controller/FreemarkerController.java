package com.solituder.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.solituder.freemarker.FreemarkerUtils;
import com.solituder.model.resultmodel.RestResult;
import com.solituder.utils.GoatInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
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
        mv1.put("add_component","Role_add");
        mv1.put("edit_component","Role_edit");
        mv1.put("model_name","listQuery");
        mv1.put("form_prop","username");
        mv1.put("form_label","账号");
        mv1.put("form_placeholder","请输入账号");
        freemarkerUtils.common(mv1,"List.ftl","result.vue");

    }
    @RequestMapping(value="/test2", method= RequestMethod.POST)
    public void queryList(HttpServletRequest request) throws  Exception
    {
        Map mv1 = GoatInfo.getInfo(request);
        mv1.put("add_component","Role_add");
        mv1.put("edit_component","Role_edit");
        mv1.put("model_name","listQuery");
        System.out.println(mv1);
        freemarkerUtils.common(mv1,"List.ftl","result.vue");


    }
}
