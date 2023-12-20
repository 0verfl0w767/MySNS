<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.disk.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<%@ page import="util.FileUtil"%>
<%@ page import="dao.FeedDAO"%>
<%
request.setCharacterEncoding("utf-8");

String uid = null, ucon = null, ufname = null;
byte[] ufile = null;

ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
List items = sfu.parseRequest(request);
Iterator iter = items.iterator();
while (iter.hasNext()) {
	FileItem item = (FileItem) iter.next();
	String name = item.getFieldName();
	if (item.isFormField()) {
		String value = item.getString("utf-8");
		if (name.equals("id"))
			uid = value;
		else if (name.equals("content"))
			ucon = value;
	} else {
		if (name.equals("image")) {
			ufname = item.getName();
			if (!ufname.isEmpty()) { // 오류 대응
				ufile = item.get();
				String OS = System.getProperty("os.name").toLowerCase(); // OS 환경 대응
				String root = "";
				if (OS.indexOf("win") >= 0) {
					root = application.getRealPath(java.io.File.separator);
				} else {
					root = "/root/tomcat-9.0/webapps/ROOT";
				}
				FileUtil.saveImage(root, ufname, ufile);
			}
		}
	}
}

FeedDAO dao = new FeedDAO();
if (dao.insert(uid, ucon, ufname)) {
	out.print("OK"); // response.sendRedirect("main.jsp");
} else {
	out.print("ER"); // out.print("작성 글의 ... 오류가 발생하였습니다.");
}
%>