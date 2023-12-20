<%@ page contentType="text/html" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="dao.*"%>
<%
ArrayList<FeedObj> list = (new FeedDAO()).getAllList();

JSONArray ary = new JSONArray();

for (FeedObj feed : list) {
	HashMap<String, String> obj = new HashMap<String, String>();
	
	obj.put("no", feed.getNo());
	obj.put("id", feed.getId());
	obj.put("content", feed.getContent());
	obj.put("ts", feed.getTs());
	obj.put("images", feed.getImages());
	
	JSONObject objs = new JSONObject(obj);
	ary.add(objs);
}
out.print(ary);
%>