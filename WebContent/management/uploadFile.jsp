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
		<title>Management area - Upload application</title>
		<script type="text/javascript">
			function arxiuOnChange() {
				if (document.getElementById("botoChooseFile").value.length == 0) document.getElementById("botoUpload").disabled = true; 
				else document.getElementById("botoUpload").disabled = false;
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
				<h2>Management area - Upload application</h2>
				<p>Please, select a .zip file from your file system containing the application you would like to upload. Take into account the following restrictions:</p>
				<ul>
					<li>You should upload only already compiled .class files. The server works with Java 1.6, so you are expected to have compiled your classes with this or earlier versions of Java.</li>
					<li>You must compress the folder that corresponds to the main package of your application, so that the .zip file contains only this folder in the first level. The name of your main package, the name of its corresponding folder and the name of the .zip file should be then the same.</li>
					<li>Your package must contain a public class called ParseAndCall (its file would be ParseAndCall.class).</li>
					<li>This class must implement a function with exactly this header <em>public static String parseAndCall (String... parameters)</em>. In this function you must parse all the String parameters that you expect to receive and then call whatever algorithm you need from your application. As the return type is also String, remember that you must convert to String the result of your algorithms.</li>
					<li>Your package can contain any number of sub-packages or resources.</li>
				</ul>
				<form action="uploadFile" enctype="multipart/form-data" method="post">
					<p><input id="botoChooseFile" type="file" name="datafile" size="40" onchange="arxiuOnChange()"></p>
					<p><input id="botoUpload" type="submit" value="Upload" disabled></p>
				</form>
			</div>
			<div id="footer">Freie Universität Berlin, 2013</div>
		</div>
	</body>
</html>