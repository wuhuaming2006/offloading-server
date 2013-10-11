<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<% 
	if (request.getSession().getAttribute("loginDone") == null) {
		getServletContext().getRequestDispatcher("/management/error.jsp").forward(request, response);
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../css/style.css" media="screen" rel="Stylesheet" type="text/css">
		<title>Downloads - Main menu</title>
	</head>
	<body>
		<div id="container">
			<div id="header">
				<h1>Android mobile devices computation offloading project</h1>
			</div>
			<div id="navigation">
				<ul>
					<li><a href="../index.jsp">Home</a></li>
					<li><a href="../management/">Management</a></li>
					<li><a href="#">Downloads</a></li>
					<li><a href="../test" rel="nofollow">Test1</a></li>
					<li><a href="../run?algName=doSomeLoops&param1=20000000" rel="nofollow">Test2</a></li>
					<li><a href="../contact.jsp">Contact us</a></li>
					<li><a href="../logout">Logout</a></li>
				</ul>
			</div>
			<div id="content">
				<h2>Downloads area - Main menu</h2>
				<p>The following files are available to download:</p>
				<ul>
					<li>The <a href="../downloadsServlet?fileName=offloading-engine-core.zip">core files</a> of the offloading engine.</li>
					<li>The <a href="../downloadsServlet?fileName=chess-game.zip">source code</a> and <a href="../downloadsServlet?fileName=ChessGame.apk">.apk Android file</a> of the Chess Game application.</li>
					<li>The <a href="../downloadsServlet?fileName=engine-testing.zip">source code</a> and <a href="../downloadsServlet?fileName=EngineTesting.apk">.apk Android file</a> of the Engine Testing application.</li>
					<li>The <a href="../downloadsServlet?fileName=server.zip">source code of this web</a> and the corresponding <a href="../downloadsServlet?fileName=offload.war">WAR file</a>, which contain the handling of the offloaded tasks by the server as well as the implementation of the functionalities from the Management area.</li>
				</ul>
				<br>
			</div>
			<div id="footer">Freie Universität Berlin, 2013</div>
		</div>
	</body>
</html>