<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>An error was found</title>
<link href="style.css" media="screen" rel="Stylesheet" type="text/css">
</head>
<body>
	<h1>An error was found</h1>
	<%
		if (request.getParameter("err").equals("0")) {
	%>
	<h3>The form you sent does not have the expected contents</h3>
	<%
		}
	%>

	<%
		if (request.getParameter("err").equals("1")) {
	%>
	<h3>The package you are trying to upload is named serverClasses, this is
		not a valid package name</h3>
	<%
		}
	%>

	<%
		if (request.getParameter("err").equals("2")) {
			String algName = request.getParameter("filename");
	%>
	<h3>
		The file
		<%=algName%>
		that you are trying to upload is not a .zip file
	</h3>
	<%
		}
	%>
	
	<%
		if (request.getParameter("err").equals("3")) {
	%>
	<h3>
		Compiling Algorithms.java failed (after adding the new algorithm case corresponding to your package)
	</h3>
	<%
		}
	%>
	
	<%
		if (request.getParameter("err").equals("4")) {
	%>
	<h3>
		The webapp could not be reloaded
	</h3>
	<%
		}
	%>
</body>
</html>