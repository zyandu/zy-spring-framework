package me.zy.spring.framework.demo.action;

import me.zy.spring.framework.annotation.ZYAutowired;
import me.zy.spring.framework.annotation.ZYController;
import me.zy.spring.framework.annotation.ZYRequestMapping;
import me.zy.spring.framework.annotation.ZYRequestParam;
import me.zy.spring.framework.demo.service.IModifyService;
import me.zy.spring.framework.demo.service.IQueryService;
import me.zy.spring.framework.webmvc.ZYModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ZYController
@ZYRequestMapping("/web")
public class MyAction {

	@ZYAutowired
	IQueryService queryService;

	@ZYAutowired
	IModifyService modifyService;

	@ZYRequestMapping("/query.json")
	public ZYModelAndView query(HttpServletRequest request, HttpServletResponse response,
								@ZYRequestParam("name") String name){
		String result = queryService.query(name);
		return out(response,result);
	}
	
	@ZYRequestMapping("/add*.json")
	public ZYModelAndView add(HttpServletRequest request,HttpServletResponse response,
			   @ZYRequestParam("name") String name,@ZYRequestParam("addr") String addr){
		String result = null;
		try {
			result = modifyService.add(name,addr);
			return out(response,result);
		} catch (Exception e) {
//			e.printStackTrace();
			Map<String,Object> model = new HashMap<String,Object>();
			model.put("detail",e.getMessage());
//			System.out.println(Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]",""));
			model.put("stackTrace", Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]",""));
			return new ZYModelAndView("500",model);
		}

	}
	
	@ZYRequestMapping("/remove.json")
	public ZYModelAndView remove(HttpServletRequest request,HttpServletResponse response,
		   @ZYRequestParam("id") Integer id){
		String result = modifyService.remove(id);
		return out(response,result);
	}
	
	@ZYRequestMapping("/edit.json")
	public ZYModelAndView edit(HttpServletRequest request,HttpServletResponse response,
			@ZYRequestParam("id") Integer id,
			@ZYRequestParam("name") String name){
		String result = modifyService.edit(id,name);
		return out(response,result);
	}
	
	
	
	private ZYModelAndView out(HttpServletResponse resp,String str){
		try {
			resp.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
