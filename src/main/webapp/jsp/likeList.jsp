<%@ page contentType="text/html" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="java.sql.*" %>
<%@ page import="util.*" %>
<%
String uid = request.getParameter("id");
String fno = request.getParameter("no");

Connection conn = null;
PreparedStatement stmt = null;
ResultSet rs = null;
try {
	JSONArray ary = new JSONArray();
	
	String sql = "SELECT * FROM `like` WHERE id = ?";

	conn = ConnectionPool.get();
	stmt = conn.prepareStatement(sql);
	stmt.setString(1, uid);
	rs = stmt.executeQuery();

	ArrayList users = new ArrayList();
	while (rs.next()) {
		HashMap<String, String> obj = new HashMap<String, String>();
		
		obj.put("id", rs.getString("id"));
		obj.put("no", rs.getString("no"));
		
		JSONObject objs = new JSONObject(obj);
		ary.add(objs);
	}
	out.print(ary);
} finally {
	if (rs != null)
		rs.close();
	if (stmt != null)
		stmt.close();
	if (conn != null)
		conn.close();
}
%>