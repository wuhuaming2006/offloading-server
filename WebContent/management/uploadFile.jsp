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
		<title>Management area - Upload Java classes</title>
		<script type="text/javascript">
			function fileOnChange() {
				if (document.getElementById("buttonChooseFile").value.length == 0) document.getElementById("buttonUpload").disabled = true; 
				else document.getElementById("buttonUpload").disabled = false;
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
					<li><a href="../logout">Logout</a></li>
				</ul>
			</div>
			<div id="content">
				<h2>Management area - Upload Java classes</h2>
				<p>Please, select a JAR file from your file system containing the functionalities you would like to upload. The JAR file may contain any number of packages, sub-packages, resources and classes (the .java source files are not needed, only the already compiled .class files). Take into account the following restrictions:</p>
				<ul>
					<li>The server works with Java 1.6, so you are expected to have compiled your classes with this or earlier versions of Java.</li>
					<li>You must have a special public class. All of its functions must have exactly this header <em>public static String anyMethodName (String... parameters)</em>. Each of this functions will be susceptible to be invoked remotely from the Android device through our engine. In each of them, you must parse all the String input parameters that you expect to receive and then call any other method (having as input the parsed parameters) implementing any functionalities. As the return type is also String, remember that you must convert to String the result of your algorithms.</li>
				</ul>
				<form action="uploadFile" enctype="multipart/form-data" method="post">
					<p><input id="buttonChooseFile" type="file" name="datafile" size="40" onchange="fileOnChange()"></p>
					<p><input id="buttonUpload" type="submit" value="Upload" disabled></p>
				</form>
			</div>
			<div id="footer">Freie Universität Berlin, 2013</div>
		</div>
	</body>
</html>