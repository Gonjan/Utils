package web.utils;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
/***
 * 页面返回工具类，封装对象为json格式的字符串
 * @author Gonjan
 *
 */
public class HTMLUtils {
	
	/**
	 * 直接通过response返回json格式的字符串
	 * @param response
	 * @param jsonStr   已经是json格式的字符串
	 */
	public static void writeJson(HttpServletResponse response, String jsonStr) {
		writer(response, jsonStr);
	}
	
	public static void writeJson(HttpServletResponse response,Object object) {
		try {
			response.setContentType("application/json");
			writeJson(response, JSONUtil.toJSONString(object));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 通过response返回json格式字符串
	 * @param response
	 * @param str    josn格式的字符串
	 */
	private static void writer(HttpServletResponse response, String str) {
		
		//try块中自动关闭打开的资源
		try(PrintWriter print = response.getWriter();) {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			print.print(str);
			print.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
