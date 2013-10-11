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
					<li><a href="downloads">Downloads</a></li>
					<%
						if (request.getSession().getAttribute("loginDone") != null) {
					%>
					<li><a href="test" rel="nofollow">Test1</a></li>
					<li><a href="run?algName=doSomeLoops&param1=20000000" rel="nofollow">Test2</a></li>
					<%
						}
					%>
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
				<p> This is the web interface of the offloading engine project.<p>
				<p> Please enter the Management area if you wish to:</p>
				<ul>
				<li>Upload your own Java classes containing computation expensive methods in order to try the offloading engine for an application of your own.</li>
				<li>Want to try the automated cost estimation system for a previously uploaded method.</li>
				</ul>
				<p> Please enter the Downloads area if you wish to download:</p>
				<ul>
				<li>The core files of the offloading engine.</li>
				<li>The source code and .apk Android files of the applications that were used for the testing of the engine.</li>
				<li>The source code of this web, which contains the handling of the offloaded tasks by the server as well as the implementation of the functionalities from the Management area.</li>
				</ul>
				<p> Due to security reasons, both areas require an identification. Please <a href="contact.jsp">contact us</a> if you do not know this authentication and you wish to use the offloading engine.</p>
			</div>
			<div id="footer">Freie Universität Berlin, 2013</div>
		</div>
	</body>
</html>
