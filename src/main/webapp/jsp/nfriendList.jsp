<%@ page contentType="text/html" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="dao.*"%>
<%
String uid = request.getParameter("id");
ArrayList<UserObj> list = (new FriendDAO()).getNList(uid);

JSONArray ary = new JSONArray();

for (UserObj user : list) {
	HashMap<String, String> obj = new HashMap<String, String>();
	
	obj.put("id", user.getId());
	obj.put("name", user.getName());
	obj.put("ts", user.getTs());
	obj.put("desc", user.getDesc());
	obj.put("image", user.getImage());
	
	JSONObject objs = new JSONObject(obj);
	ary.add(objs);
}
out.print(ary);
%>