package dao;

import java.sql.*;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.json.simple.parser.ParseException;

import util.*;

public class FriendDAO {
	public String insert(String uid, String frid) throws NamingException, SQLException, ParseException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT id FROM friend WHERE id = ? AND frid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			stmt.setString(2, frid);
			rs = stmt.executeQuery();
			if (rs.next())
				return "EX";
			stmt.close();
			sql = "INSERT INTO friend VALUES(?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			stmt.setString(2, frid);
			int count = stmt.executeUpdate();
			return (count == 1) ? "OK" : "ER";
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	public String remove(String uid, String frid) throws NamingException, SQLException, ParseException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		try {
			String sql = "DELETE FROM friend WHERE id = ? AND frid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			stmt.setString(2, frid);
			int count = stmt.executeUpdate();
			if (count != 1)
				return "ER";
			stmt.close();
			sql = "DELETE FROM `like` WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			count = stmt.executeUpdate();
			return "OK";
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	public ArrayList<UserObj> getList(String uid) throws NamingException, SQLException, ParseException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT frid FROM friend WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			rs = stmt.executeQuery();
			String str = "";
			int cnt = 0;
			while (rs.next()) {
				if (cnt++ > 0)
					str += ",";
				str += "\"" + rs.getString("frid") + "\"";
			}
			if (cnt == 0)
				return new ArrayList<UserObj>();
			rs.close();
			stmt.close();
			sql = "SELECT * FROM user WHERE id IN (" + str + ")";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			ArrayList<UserObj> users = new ArrayList<UserObj>();
			while (rs.next()) {
				users.add(new UserObj(rs.getString("id"), rs.getString("name"), rs.getString("ts"),
						rs.getString("desc"), rs.getString("image")));
			}
			return users;
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	public ArrayList<UserObj> getNList(String uid) throws NamingException, SQLException, ParseException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT frid FROM friend WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			rs = stmt.executeQuery();
			String str = "";
			int cnt = 0;
			while (rs.next()) {
				if (cnt++ > 0)
					str += ",";
				str += "\"" + rs.getString("frid") + "\"";
			}
			/*
			 * if (cnt == 0) return new ArrayList<UserObj>();
			 */
			rs.close();
			stmt.close();
			sql = "SELECT * FROM user WHERE id NOT IN (" + str + ")";
			if (cnt == 0)
				sql = "SELECT * FROM user ORDER BY ts DESC";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			ArrayList<UserObj> users = new ArrayList<UserObj>();
			while (rs.next()) {
				users.add(new UserObj(rs.getString("id"), rs.getString("name"), rs.getString("ts"),
						rs.getString("desc"), rs.getString("image")));
			}
			return users;
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}
}