package com.teamghz.connecter;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import com.teamghz.configure.*;

public class MysqlConnecter {
	private String dbDriver = Configure.DRIVER;
	private String dbUrl = Configure.URL;// ����ʵ������仯
	private String dbUser = Configure.USERNAME;
	private String dbPass = Configure.PASSWORD;

	public Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);// ע������������
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public int update(String sql) {
		int i = 0;
		/*
		 * String
		 * sql="update author set  authorid=?,name=?,age=?,country=? where authorid=?"
		 * ;//ע��Ҫ��where����
		 */
		Connection cnn = getConn();

		try {
			PreparedStatement preStmt = cnn.prepareStatement(sql);
			i = preStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;// ����Ӱ���������1Ϊִ�гɹ�
	}

	public ArrayList<Map<String, String>> select(String sql) {
		int i;
		ArrayList<Map<String, String>> result = new ArrayList<>();
		Connection cnn = getConn();// �˴�Ϊͨ���Լ�д�ķ���getConn()�������
		try {
			Statement stmt = cnn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Map<String, String> tmp = new HashMap<>();
				for (i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					tmp.put("" + i, rs.getString(i));
				}
				result.add(tmp);
			}
			// ���Խ����ҵ���ֵд���࣬Ȼ�󷵻���Ӧ�Ķ���
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int delete(String sql) {
		/* String sql = "delete from author where authorid=?"; */
		int i = 0;
		Connection conn = getConn();// �˴�Ϊͨ���Լ�д�ķ���getConn()�������
		try {
			Statement stmt = conn.createStatement();
			i = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;// ������ص���1����ִ�гɹ�;
	}
}