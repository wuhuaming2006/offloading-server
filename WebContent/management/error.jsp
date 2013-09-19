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
					<li><a href="./">Management</a></li>
					<%
						if (request.getSession().getAttribute("loginDone") != null) {
					%>
					<li><a href="../test" rel="nofollow">Test1</a></li>
					<li><a href="../run?algName=doSomeLoops&param1=20000000" rel="nofollow">Test2</a></li>
					<%
						}
					%>
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
					} else if (request.getParameter("err") == null) {
				%>
				<p class="error">Invalid access attempt</p>
				<%
					} else if (request.getParameter("err").equals("1")) {
				%>
				<p class="error">The .jar file that you are trying to upload does not have a valid name.</p>
				<p class="error">The names asm-4.1.jar, asm-tree-4.1.jar, commons-fileupload-1.3.jar, commons-io-2.4.jar, servlet-api.jar and sqlite-jdbc-3.7.2.jar are not permitted.</p>
				<%
					} else if (request.getParameter("err").equals("2")) {
						String fileName = request.getParameter("filename");
				%>
				<p class="error">The file <%=fileName%> that you are trying to upload is not a .jar file</p>
				<%
					} else if (request.getParameter("err").equals("3")) {
				%>
				<p class="error">Compiling Algorithms.java failed (after adding the new algorithm(s) case(s))</p>
				<%
					} else if (request.getParameter("err").equals("4")) {
				%>
				<p class="error">The WebApp could not be reloaded</p>
				<%
					} else if (request.getParameter("err").equals("5")) {
				%>
				<p class="error">Database error</p>
				<%
					} else if (request.getParameter("err").equals("6")) {
				%>
				<p class="error">Could not load the SQLite-JDBC driver using the current class loader. The class org.sqlite.JDBC was not found.</p>
				<%
					} else if (request.getParameter("err").equals("7")) {
				%>
				<p class="error">Problems releasing database objects</p>
				<%
					} else if (request.getParameter("err").equals("8")) {
						String fileName = request.getParameter("filename");
				%>
				<p class="error">The file <%=fileName%> that you are trying to upload is not a .csv file</p>
				<%
					} else if (request.getParameter("err").equals("9")) {
				%>
				<p class="error">Cannot parse the MultiPart request</p>
				<%
					}
				%>
			</div>
			<div id="footer">Freie Universität Berlin, 2013</div>
		</div>
	</body>
</html>