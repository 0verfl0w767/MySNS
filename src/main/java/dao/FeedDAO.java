package dao;

import java.sql.*;
import java.util.ArrayList;

import javax.naming.NamingException;
import util.*;

public class FeedDAO {
	public boolean insert(String uid, String upass, String uimages) throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			String sql = "INSERT INTO feed(id, content, images) VALUES(?, ?, ?)";

			conn = ConnectionPool.get();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			stmt.setString(2, upass);
			stmt.setString(3, uimages);

			int count = stmt.executeUpdate();
			return (count == 1) ? true : false;
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}
	
	public boolean update(String no, String uid, String upass, String uimages) throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			if (uimages == null) {
				String sql = "UPDATE feed SET content = ? WHERE no = ?";
	
				conn = ConnectionPool.get();
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, upass);
				stmt.setString(2, no);
	
				int count = stmt.executeUpdate();
				return (count == 1) ? true : false;
			} else {
				String sql = "UPDATE feed SET content = ?, images = ? WHERE no = ?";
				
				conn = ConnectionPool.get();
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, upass);
				stmt.setString(2, uimages);
				stmt.setString(3, no);
	
				int count = stmt.executeUpdate();
				return (count == 1) ? true : false;
			}
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}
	
	public boolean remove(String no) throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			String sql = "DELETE FROM feed WHERE no = ?";
			
			conn = ConnectionPool.get();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, no);

			int count = stmt.executeUpdate();
			return (count == 1) ? true : false;
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}


	public ArrayList<FeedObj> getAllList() throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM feed ORDER BY no DESC";

			conn = ConnectionPool.get();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			ArrayList<FeedObj> feeds = new ArrayList<FeedObj>();
			while (rs.next()) {
				feeds.add(new FeedObj(rs.getString("no"), rs.getString("id"), rs.getString("content"),
						rs.getString("ts"), rs.getString("images")));
			}
			return feeds;
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	public ArrayList<FeedObj> getGroup(String frids, String maxNo) throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM feed WHERE id IN (" + frids + ")";

			if (maxNo != null) {
				sql += " AND no < " + maxNo;
			}
			sql += " ORDER BY no DESC LIMIT 3";

			conn = ConnectionPool.get();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			ArrayList<FeedObj> feeds = new ArrayList<FeedObj>();
			while (rs.next()) {
				feeds.add(new FeedObj(rs.getString("no"), rs.getString("id"), rs.getString("content"),
						rs.getString("ts"), rs.getString("images")));
			}
			return feeds;
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}
	
	public ArrayList<FeedObj> getList(String uid) throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM feed WHERE id = ? ORDER BY no DESC";

			conn = ConnectionPool.get();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			rs = stmt.executeQuery();

			ArrayList<FeedObj> feeds = new ArrayList<FeedObj>();
			while (rs.next()) {
				feeds.add(new FeedObj(rs.getString("no"), rs.getString("id"), rs.getString("content"),
						rs.getString("ts"), rs.getString("images")));
			}
			return feeds;
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