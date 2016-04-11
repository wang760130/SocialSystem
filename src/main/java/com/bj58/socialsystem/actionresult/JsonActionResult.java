package com.bj58.socialsystem.actionresult;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.bj58.wf.mvc.ActionResult;
import com.bj58.wf.mvc.BeatContext;
/**
 * 给前端页面返回json统一数据封装
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2015年4月10日
 */
public class JsonActionResult extends ActionResult {
	
	private String content;
	
	public JsonActionResult(JSONObject jsonObject) {
		content = jsonObject.toString();
	}
	
	public JsonActionResult(Boolean isSuccess,String returnMessage,Object entity) {
		this.content = this.setContent(isSuccess, returnMessage, entity);
	}
	
	public JsonActionResult(Boolean isSuccess,String returnMessage,Object entity,String callBack) {
		String content = this.setContent(isSuccess, returnMessage, entity);
		if(callBack != null && !"null".equals(callBack) && !"".equals(callBack))
			this.content = callBack + "(" + content + ")";
		else 
			this.content = content;
	}
	
	private String setContent(Boolean isSuccess,String returnMessage,Object entity) {
		if(entity == null)
			entity = new HashMap<String,Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", isSuccess);
		map.put("message", returnMessage);
		map.put("entity", entity);
		JSONObject jsonObject = JSONObject.fromObject(map);  
		return jsonObject.toString();
	}
	
	@Override
	public void render(BeatContext beat) throws Exception {
		HttpServletResponse response = beat.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().print(content);
		response.getWriter().flush();
		response.getWriter().close();
	}

}