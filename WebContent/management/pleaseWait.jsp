<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<title>Management area - Generating the database, please wait...</title>
		<script type="text/javascript">
			
			var XMLHttpRequestObject = false;
			if (window.XMLHttpRequest) XMLHttpRequestObject = new XMLHttpRequest();
			else if (window.ActiveXObject) XMLHttpRequestObject = new ActiveXObject("Microsoft.XMLHTTP");
			
			function checkAnswer() {
				if (XMLHttpRequestObject.readyState == 4) {
					if (XMLHttpRequestObject.status == 200) {
						if (XMLHttpRequestObject.responseText.indexOf("error") != -1) {
							window.location.href = XMLHttpRequestObject.responseText;
						}
						else if (XMLHttpRequestObject.responseText.indexOf("YES") != -1) {
							window.location.href = "menu.jsp?dbReady=1";
						}
					}
	        	}
			}
			
			function queryIsDbReady() {
				if (XMLHttpRequestObject) {
			        XMLHttpRequestObject.open("GET", "isDbReady?t=" + Math.random(), true);
			        XMLHttpRequestObject.onreadystatechange = checkAnswer;
			        XMLHttpRequestObject.send(null);
				}
			}
			
			setInterval("queryIsDbReady()", 500); //Query the server to see if the job is done every 500 ms
			
		</script>
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
					<li><a href="../test" rel="nofollow">Test1</a></li>
					<li><a href="../run?algName=doSomeLoops&param1=20000000" rel="nofollow">Test2</a></li>
					<li><a href="../contact.jsp">Contact us</a></li>
					<li><a href="../logout">Logout</a></li>
				</ul>
			</div>
			<div id="content">
				<br>
				<div id="waitClock">
					<p>Generating the database, please wait...</p>
					<br>
					<p><img src="../img/clock.gif" alt="clock" height="150" width="150"></p>
					<br>
				</div>
				<br>
			</div>
			<div id="footer">Freie Universität Berlin, 2013</div>
		</div>
	</body>
</html>