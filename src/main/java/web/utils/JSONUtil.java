package web.utils;


import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.security.acl.Permission;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import POJO.Person;
import base.utils.DateUtil;

/***
 * JSON工具类，通过反射的方式转换java对象为json对象
 * @author Gonjan
 *
 */
public class JSONUtil {
	
	/**
	 * 代理类时做的检查.返回应该检查的对象.
	 * @param bean
	 * @return
	 */
	protected static Object proxyCheck(Object bean) {
		return bean;
	}
	
	/**
	 * 将对象转成json字符串，对象可以是任意java对象
	 * @param obj
	 * @return 对象转换后的json字符串
	 * @throws JSONException
	 */
	public static String toJSONString(Object obj) throws JSONException {
		return toJSONString(obj,false);
	}

	private static String toJSONString(Object obj, boolean useClassConvert) {
		 return getJSONObject(obj, useClassConvert).toString();
	}

	/**
	 * 
	 * 将集合类（Collection）或者数组类转换成json格式的字符串
	 * @param arrayObj   传入的集合或者array
	 * @param useClassConvert 
	 * @return
	 */
	private static String getJSONArray(Object arrayObj, boolean useClassConvert) throws JSONException {
		if(arrayObj == null) {
			return "null";
		}
		
		arrayObj = proxyCheck(arrayObj);

		JSONArray jsonArray = new JSONArray();
		if(arrayObj instanceof Collection) {
			Iterator iterator = ((Collection)arrayObj).iterator();
			while(iterator.hasNext()) {
				Object rowObject = iterator.next();
				if(rowObject == null) {
					jsonArray.put(new JSONStringObject(null));
				} 
				else if(rowObject.getClass().isArray() || rowObject instanceof Collection) {
					jsonArray.put(getJSONArray(rowObject, useClassConvert));
				} 
				else {
					jsonArray.put(getJSONObject(rowObject,useClassConvert));
				}
			}
		}
		 if(arrayObj.getClass().isArray()){
	            int arrayLength = Array.getLength(arrayObj);
	            for(int i = 0; i < arrayLength; i ++){
	                Object rowObj = Array.get(arrayObj, i);
	                if(rowObj == null)
	                	jsonArray.put(new JSONStringObject(null));
	                else if(rowObj.getClass().isArray() || rowObj instanceof Collection)
	                	jsonArray.put(getJSONArray(rowObj, useClassConvert));
	                else
	                	jsonArray.put(getJSONObject(rowObj, useClassConvert));
	            }
	        }
		return jsonArray.toString();
	}
	
	/**
	 * 根据对象类型转换成对应的string串并存入JSONStringObject中
	 * 
	 * @param value
	 * @param useClassConvert
	 * @return
	 */
	private static JSONStringObject getJSONObject(Object value, boolean useClassConvert) {
		 //处理原始类型
        if (value == null){
            return new JSONStringObject("null");
        }
        value = proxyCheck(value);
        
        if (value instanceof JSONString){
            Object o;
            try{
                o = ((JSONString)value).toJSONString();
            } catch (Exception e){
                throw new JSONException(e);
            }
            throw new JSONException("Bad value from toJSONString: " + o);
        }
        if (value instanceof Number){
            return new JSONStringObject(JSONObject.numberToString((Number) value));
        }
        if (value instanceof Boolean || value instanceof JSONObject ||
                value instanceof JSONArray){
            return new JSONStringObject(value.toString());
        }
        if (value instanceof Timestamp){
        	String str = DateUtil.getFormattedDateUtil((Timestamp)value, "yyyy-MM-dd HH:mm:ss");
            return new JSONStringObject(JSONObject.quote(str));
        }
        if (value instanceof Date){
        	String str = DateUtil.getFormattedDateUtil((Date)value, "yyyy-MM-dd HH:mm:ss");
            return new JSONStringObject(JSONObject.quote(str));
        }
        if (value instanceof String)
            return new JSONStringObject(JSONObject.quote(value.toString()));
        if (value instanceof Map){
            
            JSONObject jSONObject = new JSONObject();

            Iterator iterator = ((Map)value).keySet().iterator();
            while(iterator.hasNext()){
                String key = iterator.next().toString();
                Object valueObj = ((Map)value).get(key);
                jSONObject.put(key, getJSONObject(valueObj, useClassConvert));
            }
            return new JSONStringObject(jSONObject.toString());
        }

        //class

        if(value instanceof Class)
            return new JSONStringObject(JSONObject.quote(((Class)value).getSimpleName()));
        
        //数组

        if (value instanceof Collection || value.getClass().isArray()){
        	String jsonString = getJSONArray(proxyCheck(value), useClassConvert);
            return new JSONStringObject(jsonString);
        }

        return reflectObject(value, useClassConvert);
	}

	
	/**
	 * 将普通java对象转换成json字符串放入JSONStringObject
	 * @param value
	 * @param useClassConvert
	 * @return
	 */
	private static JSONStringObject reflectObject(Object value, Boolean useClassConvert) {
		 JSONObject jSONObject = new JSONObject();

	        Class klass = value.getClass();
	        Method[] methods = klass.getMethods();
	        for (int i = 0; i < methods.length; i += 1){
	            try{
	                Method method = methods[i];
	                String name = method.getName();
	                String key = "";
	                if (name.startsWith("get")){
	                    key = name.substring(3);
	                } else if (name.startsWith("is")){
	                    key = name.substring(2);
	                }
	                if (key.length() > 0 &&
	                        Character.isUpperCase(key.charAt(0)) &&
	                        method.getParameterTypes().length == 0){
	                    if (key.length() == 1){
	                        key = key.toLowerCase();
	                    } else if (!Character.isUpperCase(key.charAt(1))){
	                        key = key.substring(0, 1).toLowerCase() +
	                            key.substring(1);
	                    }
	                    Object elementObj = method.invoke(value, null);
	                    if(!useClassConvert && elementObj instanceof Class)
	                        continue;

	                    jSONObject.put(key, getJSONObject(elementObj, useClassConvert));
	                }
	            } catch (Exception e){
	                /**先不管 */
	            }
	        }
	        return new JSONStringObject(jSONObject.toString());
	}
}



