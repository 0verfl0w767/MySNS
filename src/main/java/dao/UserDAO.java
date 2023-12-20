package dao;

import java.sql.*;
import java.util.ArrayList;

import javax.naming.NamingException;
import util.*;

public class UserDAO {
	public boolean insert(String uid, String upass, String uname) throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			String sql = "INSERT INTO user(id, password, name) VALUES(?, ?, ?)";

			conn = ConnectionPool.get();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
			stmt.setString(2, upass);
			stmt.setString(3, uname);

			int count = stmt.executeUpdate();
			return (count == 1) ? true : false;
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	public boolean exists(String uid) throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT id FROM user WHERE id = ?";

			conn = ConnectionPool.get();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);

			rs = stmt.executeQuery();
			return rs.next();
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	public boolean delete(String uid) throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			String sql = "DELETE FROM user WHERE id = ?";

			conn = ConnectionPool.get();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);

			int count = stmt.executeUpdate();
			return (count == 1) ? true : false;
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	public int login(String uid, String upass) throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT id, password FROM user WHERE id = ?";

			conn = ConnectionPool.get();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);

			rs = stmt.executeQuery();
			if (!rs.next())
				return 1;
			if (!upass.equals(rs.getString("password")))
				return 2;
			return 0;
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	public ArrayList<UserObj> getList() throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM user ORDER BY ts DESC";

			conn = ConnectionPool.get();
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

	public ArrayList<UserObj> get(String uid) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM user WHERE id = ?";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uid);
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

	public boolean update(String uid, String name, String desc, String image) throws NamingException, SQLException {
		Connection conn = ConnectionPool.get();
		PreparedStatement stmt = null;
		try {
			if (image == null) {
				String sql = "UPDATE user SET name = ?, `desc` = ? WHERE id = ?"; // desc keyword escape
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, name);
				stmt.setString(2, desc);
				stmt.setString(3, uid);
				int count = stmt.executeUpdate();
				return (count == 1) ? true : false;
			} else {
				String sql = "UPDATE user SET name = ?, `desc` = ?, image = ? WHERE id = ?"; // desc keyword escape
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, name);
				stmt.setString(2, desc);
				stmt.setString(3, image);
				stmt.setString(4, uid);
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
}