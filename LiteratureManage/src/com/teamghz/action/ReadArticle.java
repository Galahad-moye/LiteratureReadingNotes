package com.teamghz.action;


import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;


//import this package for using the configure values or other such as mysql-username etc.
//if you want to use the value in this package, just like that: Configure.DRIVER  --(className.valueName)
import com.teamghz.configure.*;
//import this package for connecting the mysql
import com.teamghz.connecter.*;

public class ReadArticle {
	private String id;
	private String url;
	private String articlename;
	private String notename;
	private String content;
	private String flag;
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArticlename() {
		return articlename;
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setArticlename(String articlename) {
		this.articlename = articlename;
	}
	public String getNotename() {
		return notename;
	}

	public void setNotename(String notename) {
		this.notename = notename;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String readPDF() {
		System.out.println("HEHEH" + id + articlename);
		MysqlConnecter mc = new  MysqlConnecter();
		String sql = "select notename, note, time from Note where articleid=" + id;
		ArrayList<Map<String, String>> r = mc.select(sql);
		ServletRequest request = ServletActionContext.getRequest();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		session.setAttribute("note", r);
		System.out.println(url);
		return "SUCCESS";
	}
	public String saveAndLeave() {
		ServletRequest request = ServletActionContext.getRequest();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String usermail = (String) session.getAttribute("usermail");
		MysqlConnecter mc = new  MysqlConnecter();
		
		String sql_id = "select userid from User where mail=\"" + usermail +"\"";
		System.out.println(sql_id);
		ArrayList<Map<String, String>> r = mc.select(sql_id);
		System.out.println(r.size());
		String userid = r.get(0).get("1");
		System.out.println(content);
		content = content.replaceAll("\"", "\\\\\"");
		content = content.replaceAll("\'", "\\\\\'");
		String sql = "insert into Note(userid, articleid, notename, note) values(" + userid + ", " +id+ ",\"" + notename + "\",\"" + content + "\")" ;
		System.out.println(sql);
		if (mc.update(sql) > 0) {
			flag = "TRUE";
		    return "SUCCESS";
		}
		else {
			flag = "FALSE";
			return "FALSE";
		}
	}

	
}
