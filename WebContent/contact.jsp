<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/style.css" media="screen" rel="Stylesheet" type="text/css">
		<title>Contact</title>
		<script type="text/javascript">
			function writeEmailAddress(part1, part2, part3) {
				var emailAddress = (part1 + part2 + part3);
				document.write('<a href="mailto:' + emailAddress + '">' + emailAddress + '</a>');
			}
		</script>
	</head>
	<body>
		<div id="container">
			<div id="header">
				<h1>Android mobile devices computation offloading project</h1>
			</div>
			<div id="navigation">
				<ul>
					<li><a href="index.jsp">Home</a></li>
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
					<li><a href="#">Contact us</a></li>
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
				<h2>Contact</h2>
				<p>Martí­ Griera Jorba: <script type="text/javascript">writeEmailAddress("martigri", "era@ze", "dat.fu-berlin.de");</script>, <script type="text/javascript">writeEmailAddress("wart", "iw@gm", "ail.com");</script></p>
				<p>Joan Martínez Ripoll: <script type="text/javascript">writeEmailAddress("yuangm", "r@gma", "il.com");</script></p>
			</div>
			<div id="footer">Freie Universität Berlin, 2013</div>
		</div>
	</body>
</html>
