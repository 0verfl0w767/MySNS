<%@ page contentType="text/html" pageEncoding="utf-8"%>
<%@ page import="dao.*"%>
<%
String fno = request.getParameter("no");
out.print((new FeedDAO()).remove(fno));
%>