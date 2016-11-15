package com.teamghz.action;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.*;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;

import org.apache.struts2.ServletActionContext;

import com.teamghz.connecter.MysqlConnecter;

import net.sf.json.JSONArray;
public class Treemanage {
	private String username;
	public void searchtree() throws IOException{
		ServletRequest request = ServletActionContext.getRequest();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String usermail = (String) session.getAttribute("usermail");
		String sql = "select * from User where mail=\"" + usermail + "\"" ;
		MysqlConnecter mc = new MysqlConnecter();
		ArrayList<Map<String, String>> result =  mc.select(sql);
		String userid=result.get(0).get("1");
		username = result.get(0).get("2");
		session.setAttribute("username", username);
		userid = "2";
		sql = "select * from Tree where userid=\"" + userid + "\"" ;
		result =  mc.select(sql);
		List<String> tree = new ArrayList<String>();
		String tem ="";
		int i;
		for(i = 0;i < result.size();i++){
			tem ="{id:"+result.get(i).get("1")+", pid:"+result.get(i).get("3")+", name:\""+result.get(i).get("2")+"\" , open:true}";
			tree.add(tem);
		}
		ServletResponse response = null;
		response.getWriter().print(JSONArray.fromObject(tree).toString());
	}
}