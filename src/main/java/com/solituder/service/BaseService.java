package com.solituder.service;


//import com.example.demo.ExcelVerifyEntity.Sys_user_Verify;
//import com.example.demo.dao.DaoSupport;
//import com.example.demo.model.Sys_user;
//import com.github.pagehelper.Page;
import com.github.pagehelper.Page;
import com.solituder.dao.DaoSupport;
import com.solituder.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor=Exception.class) // sos 类中的方法抛出异常，就会回滚，数据库里面的数据也会回滚。 也可以用在单个方法上
public class BaseService
{

    @Autowired
    private DaoSupport dao;
//    public List<PageData> findForList(String mapperId,Page page)throws Exception
//    {
//        return (List<PageData>) dao.findForList(mapperId, page);
//    }
//    public Sys_user findByUserName(String username)throws Exception
//    {
//        return (Sys_user) dao.findForList("Sys_userMapper.findForList", username);
//    }
	public void  deleteById(String mapperId,Map  pd)throws Exception
    {
        dao.delete(mapperId, pd);
    }
	public Map  findForObject(String mapperId,Map  pd)throws Exception
    {
        return (Map)dao.findForObject(mapperId,pd);
    }
    public Map  findForObject(String mapperId,String  pd)throws Exception
    {
        return (Map)dao.findForObject(mapperId,pd);
    }
	public int  findForCount(String mapperId,Map  pd)throws Exception
    {
        return (int)dao.findForObject(mapperId,pd);
    }
	public Page<Map> findForList(String mapperId, Map  pd)throws Exception{return (Page<Map>)dao.findForList(mapperId, pd);}
	public List<Menu>  findForTree(String mapperId, Map  pd)throws Exception{return (List<Menu>)dao.findForList(mapperId, pd);}
    public List<Map> Export(String mapperId, Map  pd)throws Exception{return (List<Map>)dao.findForList(mapperId, pd);}
    public Integer  update(String mapperId,Map  pd)throws Exception
    {
        return (Integer)dao.update(mapperId, pd);
    }
    public int save(String mapperId,Map pd)throws Exception
    {
        int temp = (int)dao.save(mapperId, pd);
        return temp;
    }
//    public Integer batchSave(String mapperId,List<Sys_user_Verify> list)throws Exception
//    {
//        int temp =(int)dao.batchSave(mapperId, list);
//        return  temp;
//    }

    public Integer delete(String mapperId,int id)throws Exception
    {
        return (Integer)dao.delete(mapperId, id);
    }
    public Integer batchDelete(String mapperId,List<Integer> list)throws Exception
    {
        int temp =(int)dao.delete(mapperId, list);
        return  temp;
    }

    public Map getRoleId(String mapperId, Map  pd)throws Exception
    {
        return (Map)dao.findForObject(mapperId, pd);
    }

    public  List<Map> getAccountList(String roleid, String appcode) throws Exception
    {
        Map pd =  new HashMap();
        pd.put("roleid", roleid);
        pd.put("appcode", appcode);
        List<Map> userPd = (List<Map>)dao.findForList("BaseMapper.getAccountList", pd);
        return userPd;
    }

}

 
