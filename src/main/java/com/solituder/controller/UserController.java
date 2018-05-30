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
@RequestMapping(value="/api/sys_user")     // 通过这里配置使下面的映射都在/users下
public class UserController
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
        Page<Map> fuck = baseService.findForList("Sys_userMapper.findForList",map);
        long total = fuck.getTotal();// 设置返回 总记录数
        return generator.getSuccessResult("查询用户成功",fuck,total);
    }

    @RequestMapping(value="/", method= RequestMethod.GET)
    public List<Map> getUserList()  throws Exception //
    {
//        pd.put("tableName","sys_user");// SOS: 这里put的内容必须与 xml中的${} 一一对应啊！ SELECT * FROM ${tableName} WHERE ${columnName} = #{ID}
        List<Map> fuck =  baseService.findForList("Sys_userMapper.findForList",null);
        return fuck;
    }
    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public Map getUser(@PathVariable int id) {
        Map pd = new HashMap();
        pd.put("123",id);
        return pd;
    }
    /***
     *  fuck @RequestParam 表示 请求中 必须带有 名称相同的变量 才能进入该方法 否则直接404 ！！！
     *  fuck RequestMapping 后的字符串  大小写敏感！！！
     *  http://localhost:8080/api/sys_user/GET?name=123
     */
    @RequestMapping(value="/GET", method= RequestMethod.GET)
    public String getUser1(@RequestParam String name) {
        return name;
    }
    @RequestMapping(value="user/{id}/{name}",method= RequestMethod.GET)
    public String myController(@PathVariable String id, @PathVariable String name, ModelMap model)
    {
        return "ok";
    }



    @RequestMapping(value="/POST", method= RequestMethod.POST)
    public RestResult POST(HttpServletRequest request) throws  Exception { // sos 这样可以  但是需要注意 这种方式 只能是POST方式 才可以 GET方式 不行！
        Map map = GoatInfo.getInfo(request);
        int fuck = baseService.save("Sys_userMapper.save",map);
        return generator.getSuccessResult("添加用户成功",fuck);
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
    @RequestMapping(value="/DELETE/{id}", method= RequestMethod.DELETE)
    public RestResult DELETE(@PathVariable int id) throws Exception { // sos 已经ok
        int result = baseService.delete("Sys_userMapper.delete",id);
        return generator.getSuccessResult("删除成功",result);
    }
    @RequestMapping(value="/batchDelete/{ids}", method= RequestMethod.DELETE)
    public RestResult batchDelete(@PathVariable int[] ids) throws Exception{
        List<Integer> list = Ints.asList(ids); // sos guava 可以的
        int result = baseService.batchDelete("Sys_userMapper.batchDelete",list);
        return generator.getSuccessResult("批量删除成功",result);
    }

}
