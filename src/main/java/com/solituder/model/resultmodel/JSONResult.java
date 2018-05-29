package com.solituder.model.resultmodel;

//import org.json.JSONObject;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by 64274 on 2018/5/19.
 *
 * // status - 返回状态码 0 代表正常返回，其他都是错误
 // message - 一般显示错误信息
 // result - 结果集
 */
public class JSONResult
{
    public static String fillResultString(Integer status, String message, Object result){
        JSONObject jsonObject = new JSONObject(){{ put("status", status);  put("message", message);put("result", result);}};
        return jsonObject.toString();
    }
}
