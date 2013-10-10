<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<% 
	if (request.getSession().getAttribute("loginDone") == null) {
		getServletContext().getRequestDispatcher("/management/error.jsp").forward(request, response);
		return;
	}
	ArrayList<String> classNames = null;
	Object auxObject = request.getSession().getAttribute("classNames");
	if (auxObject != null) classNames = (ArrayList<String>) auxObject;
	else {
		getServletContext().getRequestDispatcher("/management/error.jsp").forward(request, response);
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../css/style.css" media="screen" rel="Stylesheet" type="text/css">
		<title>Management area - Select the algorithms Java class</title>
		<script type="text/javascript">
			function selectOnChange() {
				var classNamesSelect = document.getElementById("classNamesSelect");
				var somethingSelected = false;
				for (var i = 0; i < classNamesSelect.length; i++) {
					if (classNamesSelect[i].selected) somethingSelected = true;
				}
				if (somethingSelected) document.getElementById("buttonSelectClass").disabled = false; 
				else document.getElementById("buttonSelectClass").disabled = true;
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
					<li><a href="../index.jsp">Home</a></li>
					<li><a href="./">Management</a></li>
					<li><a href="../downloads">Downloads</a></li>
					<li><a href="../test" rel="nofollow">Test1</a></li>
					<li><a href="../run?algName=doSomeLoops&param1=20000000" rel="nofollow">Test2</a></li>
					<li><a href="../contact.jsp">Contact us</a></li>
					<li><a href="../logout">Logout</a></li>
				</ul>
			</div>
			<div id="content">
				<h2>Management area - Select the algorithms Java class</h2>
				<%
					if (request.getParameter("newFile") != null) {
						if (request.getParameter("newFile").equals("1")) {
				%>
				<p class="allRight">The new JAR file has been successfully uploaded.</p>
				<%
						} else {
				%>
				<p class="allRight">A JAR file with the same name has been found. It has been successfully updated.</p>
				<%
						}
					}
				%>
				<p>In the following box you can find a list with all the Java classes contained within the JAR file that you just uploaded. Please select the special class with the properties described in the previous page.</p>
				<form method="post" action="updAndCompAlgs">
				<% 
					if (classNames.size() < 15) {
						String classNamesSize = Integer.toString(classNames.size());
				%>
					<select id="classNamesSelect" name="classNamesSelect" size="<%=classNamesSize%>" onchange="selectOnChange()">
				<% 
					} else {
				%>
					<select id="classNamesSelect" name="classNamesSelect" size="15" onchange="selectOnChange()">
				<%
					}
					String className;
					for (int i = 0; i < classNames.size(); i++) {
						className = classNames.get(i);
				%>
						<option value="<%=className%>"><%=className%></option>
				<%	
					}
				%>
					</select>
					<p><input id="buttonSelectClass" type="submit" name="submitSelectClass" value="Select class" disabled></p>
				</form>
			</div>
			<div id="footer">Freie Universität Berlin, 2013</div>
		</div>
	</body>
</html>