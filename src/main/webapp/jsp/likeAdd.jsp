<%@ page contentType="text/html" pageEncoding="utf-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="util.*" %>
<%
String uid = request.getParameter("id");
String fno = request.getParameter("no");

Connection conn = ConnectionPool.get();
PreparedStatement stmt = null;
ResultSet rs = null;
try {
	String sql = "SELECT id FROM `like` WHERE id = ? AND no = ?";
	stmt = conn.prepareStatement(sql);
	stmt.setString(1, uid);
	stmt.setString(2, fno);
	rs = stmt.executeQuery();
	if (rs.next()){
		out.print("ER");
		return;
	}
	stmt.close();
	sql = "INSERT INTO `like` VALUES(?, ?)";
	stmt = conn.prepareStatement(sql);
	stmt.setString(1, uid);
	stmt.setString(2, fno);
	int count = stmt.executeUpdate();
	if (count != 1){
		out.print("ER");
		return;
	}
	out.print("OK");
} finally {
	if (rs != null)
		rs.close();
	if (stmt != null)
		stmt.close();
	if (conn != null)
		conn.close();
}
%>