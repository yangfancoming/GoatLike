package com.solituder.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.primitives.Ints;
import com.solituder.model.Menu;
import com.solituder.model.resultmodel.RestResult;
import com.solituder.model.resultmodel.ResultGenerator;
import com.solituder.service.BaseService;
import com.solituder.utils.GoatInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController // 等价于  @Controller +  @ResponseBody
@RequestMapping(value="/api/sys_permission")     // 通过这里配置使下面的映射都在/users下
/**
     * @Description: 功能描述：(这里用一句话描述这个方法的作用)
     * @author: 杨帆
     * @Param:
     * @Return:
     * @Date:   2018/7/3
*/
public class PermissionController
{
    private final ResultGenerator generator = new ResultGenerator();
    @Autowired  private BaseService baseService;

    @RequestMapping(value="/queryTree", method= RequestMethod.POST)
    public Map<String, Object> queryTree() throws  Exception
    {
        Map<String, Object> map = new HashMap<>(16);
        List<Menu> fuck = baseService.findForTree("Sys_permissionMapper.findForTree",null);
        map.put("menus", fuck);
        return map;
//        return generator.getSuccessResult("查询角色成功",map); // fuck 这里返回类型 必须是 Map<String, Object> 不能是 RestResult 否则前台不识别。。。
    }


    @RequestMapping(value="/queryList", method= RequestMethod.POST)
    public RestResult queryList(HttpServletRequest request) throws  Exception
    {
        Map map = GoatInfo.getInfo(request);
        if(map == null){return generator.getFailResult("fuck");}
        int pageNum  = (int)map.get("pageNum");
        int pageSize  = (int)map.get("pageSize");
        PageHelper.startPage(pageNum,pageSize);
        Page<Map> fuck = baseService.findForList("Sys_permissionMapper.findForList",map);
        // 设置返回 总记录数
        long total = fuck.getTotal();
        return generator.getSuccessResult("查询权限成功",fuck,total);
    }


    @RequestMapping(value="/POST", method= RequestMethod.POST)
    public RestResult POST(HttpServletRequest request) throws  Exception {
        Map map = GoatInfo.getInfo(request);
        int fuck = baseService.save("Sys_permissionMapper.save",map);
        return generator.getSuccessResult("添加角色成功",fuck);
    }
    @RequestMapping(value="/PATCH", method= RequestMethod.POST)
    public RestResult PATCH(HttpServletRequest request) throws  Exception {
        Map map = GoatInfo.getInfo(request);
        int fuck = baseService.update("Sys_permissionMapper.update",map);
        return generator.getSuccessResult("修改角色成功",fuck);
    }
    @RequestMapping(value="/DELETE/{id}", method= RequestMethod.DELETE)
    public RestResult DELETE(@PathVariable int id) throws Exception { // sos 已经ok
        int result = baseService.delete("Sys_permissionMapper.delete",id);
        return generator.getSuccessResult("删除角色成功",result);
    }
    @RequestMapping(value="/batchDelete/{ids}", method= RequestMethod.DELETE)
    public RestResult batchDelete(@PathVariable int[] ids) throws Exception{
        // sos guava 可以的
        List<Integer> list = Ints.asList(ids);
        int result = baseService.batchDelete("Sys_permissionMapper.batchDelete",list);
        return generator.getSuccessResult("批量删除成功",result);
    }

}

