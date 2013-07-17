<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../css/style.css" media="screen" rel="Stylesheet" type="text/css">
		<title>Management area - An error was found</title>
	</head>
	<body>
		<div id="container">
			<div id="header">
				<h1>Android mobile devices computation offloading project</h1>
			</div>
			<div id="navigation">
				<ul>
					<li><a href="../index.jsp">Home</a></li>
					<li><a href="#">Management</a></li>
					<li><a href="../test" rel="nofollow">Test1</a></li>
					<li><a href="../run?algName=doSomeLoops&param1=20000000" rel="nofollow">Test2</a></li>
					<li><a href="../contact.jsp">Contact us</a></li>
					<%
						if (request.getSession().getAttribute("loginDone") != null) {
					%>
					<li><a href="../logout">Logout</a></li>
					<%
						}
					%>
				</ul>
			</div>
			<div id="content">
				<h2>Management area - An error was found</h2>
				<%
					if (request.getSession().getAttribute("loginDone") == null) {
				%>
				<p class="error">You must log in order to access the management area</p>
				<%
					} else if (request.getAttribute("firstUpload") != null) {
				%>
				<p class="error">You must first upload before accessing this page</p>
				<%
					} else if (request.getAttribute("noJar") != null) {
				%>
				<p class="error">No .jar file was previously uploaded</p>
				<%
					} else if (request.getParameter("err").equals("1")) {
				%>
				<p class="error">The form you sent does not have the expected contents</p>
				<%
					} else if (request.getParameter("err").equals("2")) {
				%>
				<p class="error">The .jar file you are trying to upload is has a not a valid name</p>
				<%
					} else if (request.getParameter("err").equals("3")) {
						String algName = request.getParameter("filename");
				%>
				<p class="error">The file <%=algName%> that you are trying to upload is not a .jar file</p>
				<%
					} else if (request.getParameter("err").equals("4")) {
				%>
				<p class="error">Compiling Algorithms.java failed (after adding the new algorithm case corresponding to your package)</p>
				<%
					} else if (request.getParameter("err").equals("5")) {
				%>
				<p class="error">The WebApp could not be reloaded</p>
				<%
					}
				%>
			</div>
			<div id="footer">Freie Universität Berlin, 2013</div>
		</div>
	</body>
</html>