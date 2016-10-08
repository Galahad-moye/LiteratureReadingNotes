package com.teamghz.connecter;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class MysqlConnecter  
{  
    private String dbDriver="com.mysql.jdbc.Driver";   
    private String dbUrl="jdbc:mysql://localhost:3306/??characterEncoding=utf8";//����ʵ������仯  
    private String dbUser="";  
    private String dbPass="";  
    private Pattern p = Pattern.compile("\\?");
    public Connection getConn(String url,String user,String pass)  
    {  
    	Matcher m1 = p.matcher(dbUrl);
		dbUrl = m1.replaceFirst('"'+url+'"');
		dbUser=user;
		dbPass=pass;
        Connection conn=null;  
        try  
        {  
            Class.forName(dbDriver);  
        }  
        catch (ClassNotFoundException e)  
        {  
            e.printStackTrace();  
        }  
        try  
        {  
            conn = DriverManager.getConnection(dbUrl,dbUser,dbPass);//ע������������  
        }  
        catch (SQLException e)  
        {  
            e.printStackTrace();  
        }  
        return conn;  
    }

public int insert(String sql,String url,String user,String pass)  
{  
    int i=0;  
    /*String sql="insert into author(authorid,name,age,country) values(?,?,?,?)"; 
    String sql0="insert into book(isbn,title,authorid,publisher,publishdate,price) values(?,?,?,?,?,?)";
    */
    Connection cnn=getConn(url,user,pass);  
  
    try{  
        PreparedStatement preStmt =cnn.prepareStatement(sql);  
        i=preStmt.executeUpdate();  
    }  
    catch (SQLException e)  
    {  
        e.printStackTrace();  
    }  
    return i;//����Ӱ���������1Ϊִ�гɹ�  
}  
public int update(String sql,String url,String user,String pass)
{  
    int i=0;  
    /*String sql="update author set  authorid=?,name=?,age=?,country=? where authorid=?";//ע��Ҫ��where����  
    */
    Connection cnn=getConn(url,user,pass);  
  
  
    try{  
        PreparedStatement preStmt =cnn.prepareStatement(sql);  
        i=preStmt.executeUpdate();  
    }  
    catch (SQLException e)  
    {  
        e.printStackTrace();  
    }  
    return i;//����Ӱ���������1Ϊִ�гɹ�  
}  
public List<String> select(String sql,String url,String user,String pass)
{  
	int i;
    Connection cnn = getConn(url,user,pass);//�˴�Ϊͨ���Լ�д�ķ���getConn()�������
    List<String> list = new LinkedList<String>();
    try  
    {  
        Statement stmt = cnn.createStatement();  
        ResultSet rs = stmt.executeQuery(sql);  
        while(rs.next())  
        {  
        	for(i = 1;i<=rs.getMetaData().getColumnCount();i++){
            list.add(rs.getString(i));}
        }  
        //���Խ����ҵ���ֵд���࣬Ȼ�󷵻���Ӧ�Ķ���  
    }  
    catch (SQLException e)  
    {  
        e.printStackTrace();  
    }  
    return list;  
}  
public int delete(String sql,String url,String user,String pass)  
{  
    /*String sql = "delete from author where authorid=?"; */ 
    int i=0;  
    Connection conn = getConn(url,user,pass);//�˴�Ϊͨ���Լ�д�ķ���getConn()�������  
    try  
    {  
        Statement stmt = conn.createStatement();  
        i = stmt.executeUpdate(sql);  
    }  
    catch (SQLException e)  
    {  
        e.printStackTrace();  
    }  
    return i;//������ص���1����ִ�гɹ�;  
}  
}