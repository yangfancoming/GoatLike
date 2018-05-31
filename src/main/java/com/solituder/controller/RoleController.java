package com.solituder.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.primitives.Ints;
import com.solituder.model.resultmodel.RestResult;
import com.solituder.model.resultmodel.ResultGenerator;
import com.solituder.service.BaseService;
import com.solituder.utils.GoatInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController // 等价于  @Controller +  @ResponseBody
@RequestMapping(value="/api/sys_role")     // 通过这里配置使下面的映射都在/users下
public class RoleController
{
    private final ResultGenerator generator = new ResultGenerator();
    @Autowired
    private BaseService baseService;

    @RequestMapping(value="/queryList", method= RequestMethod.POST)
    public RestResult queryList(HttpServletRequest request) throws  Exception
    {
        Map map = GoatInfo.getInfo(request);
        if(map == null)return generator.getFailResult("fuck");
        int pageNum  = (int)map.get("pageNum");
        int pageSize  = (int)map.get("pageSize");
        PageHelper.startPage(pageNum,pageSize);//
        Page<Map> fuck = baseService.findForList("Sys_roleMapper.findForList",map);
        long total = fuck.getTotal();// 设置返回 总记录数
        return generator.getSuccessResult("查询角色成功",fuck,total);
    }


    @RequestMapping(value="/POST", method= RequestMethod.POST)
    public RestResult POST(HttpServletRequest request) throws  Exception {
        Map map = GoatInfo.getInfo(request);
        int fuck = baseService.save("Sys_roleMapper.save",map);
        return generator.getSuccessResult("添加角色成功",fuck);
    }
    @RequestMapping(value="/PATCH", method= RequestMethod.POST)
    public RestResult PATCH(HttpServletRequest request) throws  Exception {
        Map map = GoatInfo.getInfo(request);
        int fuck = baseService.update("Sys_userMapper.update",map);
        return generator.getSuccessResult("修改角色成功",fuck);
    }

    @RequestMapping(value="/DELETE/{id}", method= RequestMethod.DELETE)
    public RestResult DELETE(@PathVariable int id) throws Exception { // sos 已经ok
        int result = baseService.delete("Sys_userMapper.delete",id);
        return generator.getSuccessResult("删除角色成功",result);
    }
    @RequestMapping(value="/batchDelete/{ids}", method= RequestMethod.DELETE)
    public RestResult batchDelete(@PathVariable int[] ids) throws Exception{
        List<Integer> list = Ints.asList(ids); // sos guava 可以的
        int result = baseService.batchDelete("Sys_userMapper.batchDelete",list);
        return generator.getSuccessResult("批量删除成功",result);
    }

}



//    @RequestMapping(value = "/test1", method = RequestMethod.POST)
//    public Sys_user postUser1(@RequestBody Sys_user sys_user) // sos 这样可以
//    {
//        return sys_user;
//    }


//    @RequestMapping(value="/{id}", method= RequestMethod.PATCH)
//    public String patchUser(@PathVariable Long id, @ModelAttribute Sys_user user) {
//        return "success";
//    }