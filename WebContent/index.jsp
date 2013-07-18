<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/style.css" media="screen" rel="Stylesheet" type="text/css">
		<title>Welcome!</title>
	</head>
	<body>
		<div id="container">
			<div id="header">
				<h1>Android mobile devices computation offloading project</h1>
			</div>
			<div id="navigation">
				<ul>
					<li><a href="#">Home</a></li>
					<li><a href="management">Management</a></li>
					<li><a href="test" rel="nofollow">Test1</a></li>
					<li><a href="run?algName=doSomeLoops&param1=20000000" rel="nofollow">Test2</a></li>
					<li><a href="contact.jsp">Contact us</a></li>
					<%
						if (request.getSession().getAttribute("loginDone") != null) {
					%>
					<li><a href="logout">Logout</a></li>
					<%
						}
					%>
				</ul>
			</div>
			<div id="content">
				<h2>Welcome!</h2>
				<%
					if (request.getAttribute("loggedOut") != null) {
				%>
				<p class="allRight">You were successfully logged out.</p>
				<%
					}
				%>
				<p>Please, enter the Management area if you want to upload Java classes or try the automated cost estimation system.</p>
				<p>Test1 will return some information about this server.</p>
				<p>Test2 is an example of calling a method in this server, it will run 20 millions of iterations of a simple looping algorithm.</p>
				<p>Keep in mind that the XML formatted data returned when clicking on Test1 or Test2 is to be interpreted by computers.</p>
			</div>
			<div id="footer">Freie Universität Berlin, 2013</div>
		</div>
	</body>
</html>
