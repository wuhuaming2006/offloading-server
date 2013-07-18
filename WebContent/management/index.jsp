<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	if (request.getSession().getAttribute("loginDone") != null) {
		getServletContext().getRequestDispatcher("/management/menu.jsp").forward(request, response);
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../css/style.css" media="screen" rel="Stylesheet" type="text/css">
		<title>Management area - Login</title>
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
				<h2>Management area - Login</h2>
				<%
					String userName = "";
					String password = "";
					if (request.getParameter("invUserPass") != null) {
						userName = (String) request.getSession().getAttribute("userName");
						password = (String) request.getSession().getAttribute("password");
				%>
				<p class="error">Please, check that the username and password are correct.</p>
				<%
					} else {
				%>
				<p>Please, enter your username and password in order to access the management area.</p>
				<%
					}
				%>
				<form method="post" action="loginCheck">
					<table border="0" cellspacing="5">
						<tr>
							<th align="right">Username:</th>
							<td align="left"><input type="text" name="username" value="<%=userName%>"></td>
						</tr>
						<tr>
							<th align="right">Password:</th>
							<td align="left"><input type="password" name="password" value="<%=password%>"></td>
						</tr>
					</table>
					<p><input type="submit" name="submitLogin" value="Log In"></p>
				</form>
			</div>
			<div id="footer">Freie Universität Berlin, 2013</div>
		</div>
	</body>
</html>