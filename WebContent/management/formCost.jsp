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
		<link href="../css/style.css" media="screen" rel="Stylesheet" type="text/css">
		<title>Management area - Automatic cost estimation</title>
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
				<h2>Management area - Automatic cost estimation</h2>
				<%
					if (request.getParameter("newFile") != null) {
						if (request.getParameter("newFile").equals("1")) {
				%>
				<p class="allRight">The new application has been successfully uploaded.</p>
				<%
						} else {
				%>
				<p class="allRight">The application has been successfully updated.</p>
				<%
						}
				%>
				<p>You may now request the execution of any of its algorithms; do it like in the following example:</p>
				<p><em>http://www.mi.fu-berlin.de/offload/run?algName=exampleAlg&amp;param1=1234&amp;param2=helloWorld</em></p>
				<%
					}
				%>
				<br>
				<p>The Android application that will be run on the mobile devices and will communicate with the classes that you just uploaded to this server, needs an execution time estimation function (or <em>cost</em> function) for each of its potentially offloadable parts of code. You can either implement it yourself or use the statistical estimation system described next.</p>
				<p class="error">Work in progress...!</p>
			</div>
			<div id="footer">Freie Universität Berlin, 2013</div>
		</div>
	</body>
</html>