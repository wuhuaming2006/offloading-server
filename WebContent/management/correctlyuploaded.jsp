<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	if (request.getSession().getAttribute("loginDone") == null) {
		getServletContext().getRequestDispatcher("/management/error.jsp").forward(request, response);
		return;
	} else if (request.getSession().getAttribute("uploadDone") == null) {
		request.setAttribute("firstUpload", true);
		getServletContext().getRequestDispatcher("/management/error.jsp").forward(request, response);
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Successfully uploaded the files</title>
		<link href="style.css" media="screen" rel="Stylesheet" type="text/css">
	</head>
	<body>
		<%
			if (request.getParameter("newFile").equals("1")) {
		%>
		<p>The new files were correctly uploaded, you may now use your new algorithm</p>
		<%
			}
			else {
		%>
		<p>The files were correctly updated, you may now use your updated algorithm</p>
		<%
			}
		%>
	</body>
</html>