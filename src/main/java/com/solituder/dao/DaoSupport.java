package com.solituder.dao;


//import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

//import javax.annotation.Resource;


@Repository
public class DaoSupport implements DAO
{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate ; // sos : 这里 只需要加入  @Autowired 后 直接使用   因为 springboot 框架已经为我们配置好了！

	public Object save(String str, Object obj) throws Exception {
		return sqlSessionTemplate.insert(str, obj);
	}
	
	public Object batchSave(String str, List objs )throws Exception{
		return sqlSessionTemplate.insert(str, objs);
	}
	
	public Object update(String str, Object obj) throws Exception {
		return sqlSessionTemplate.update(str, obj);
	}

	public Object batchDelete(String str, List objs )throws Exception{
		return sqlSessionTemplate.delete(str, objs);
	}
	

	public Object delete(String str, Object obj) throws Exception {
		return sqlSessionTemplate.delete(str, obj);
	}

	public Object findForObject(String str, Object obj) throws Exception {
		return sqlSessionTemplate.selectOne(str, obj);
	}


	public Object findForList(String str, Object obj) throws Exception {
		return sqlSessionTemplate.selectList(str, obj);
	}
	
	public Object findForMap(String str, Object obj, String key, String value) throws Exception {
		return sqlSessionTemplate.selectMap(str, obj, key);
	}
	
	public Object selectList(String str,Object parameter) throws Exception{
		return sqlSessionTemplate.selectList(str, parameter);
	}
	
	public Object selectList(String str) throws Exception{
		return sqlSessionTemplate.selectList(str);
	}

}


