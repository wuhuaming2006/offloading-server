<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<% 
	if (request.getSession().getAttribute("loginDone") == null) {
		getServletContext().getRequestDispatcher("/management/error.jsp").forward(request, response);
		return;
	}
	ArrayList<String> newMethods = null;
	Object auxObject = request.getSession().getAttribute("newMethods");
	if (auxObject != null) newMethods = (ArrayList<String>) auxObject;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../css/style.css" media="screen" rel="Stylesheet" type="text/css">
		<title>Management area - Main menu</title>
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
					<li><a href="../logout">Logout</a></li>
				</ul>
			</div>
			<div id="content">
				<h2>Management area - Main menu</h2>
				<% 
					if (newMethods != null) {
				%>
				<p class="allRight">Successfully recompiled Algorithms.java and restarted the WebApp after adding the following detected methods:</p>
				<ul>
				<%
				 		String algName;
						for (int i = 0; i < newMethods.size(); i++) {
							algName = newMethods.get(i);
				%>
							<li class="allRight"><%=algName%></li>
				<%	
						}
				%>		
				</ul>
				<p>You may now request the execution of any of its algorithms; do it like in the following example:</p>
				<p><em>http://www.mi.fu-berlin.de/offload/run?algName=exampleAlg&amp;param1=1234&amp;param2=helloWorld</em></p>
				<p>You can also use an equivalent POST query.</p>
				<br>
				<%	
					}
				%>
				<p>Select what to do next:</p>
				<ul>
					<li><a href="uploadFile.jsp">Upload JAR file</a></li>
					<li><a href="selectAlgs.jsp">Automated cost estimation</a></li>
				</ul>
				<br>
			</div>
			<div id="footer">Freie Universität Berlin, 2013</div>
		</div>
	</body>
</html>