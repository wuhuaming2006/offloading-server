<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
			if (request.getSession().getAttribute("loginDone") == null) {
		%>
		<h3 class="error">You must log in order to access the management area</h3>
		<%
			} else if (request.getAttribute("firstUpload") != null) {
		%>
		<h3 class="error">You must first upload before accessing this page</h3>
		<%
			} else if (request.getParameter("err").equals("1")) {
		%>
		<h3 class="error">The form you sent does not have the expected contents</h3>
		<%
			} else if (request.getParameter("err").equals("2")) {
		%>
		<h3 class="error">The package you are trying to upload is named serverClasses, this is not a valid package name</h3>
		<%
			} else if (request.getParameter("err").equals("3")) {
				String algName = request.getParameter("filename");
		%>
		<h3 class="error">The file <%=algName%> that you are trying to upload is not a .zip file</h3>
		<%
			} else if (request.getParameter("err").equals("4")) {
		%>
		<h3 class="error">Compiling Algorithms.java failed (after adding the new algorithm case corresponding to your package)</h3>
		<%
			} else if (request.getParameter("err").equals("5")) {
		%>
		<h3 class="error">The webapp could not be reloaded</h3>
		<%
			}
		%>
	</body>
</html>