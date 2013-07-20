<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	if (request.getSession().getAttribute("loginDone") == null) {
		getServletContext().getRequestDispatcher("/management/error.jsp").forward(request, response);
		return;
	}
	String[] methodNames = request.getParameterValues("methodNamesSelect");
	if (methodNames == null) {
		getServletContext().getRequestDispatcher("/management/error.jsp").forward(request, response);
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../css/style.css" media="screen" rel="Stylesheet" type="text/css">
		<title>Management area - Automated cost estimation system</title>
		<script type="text/javascript">
			function fileOnChange() {
				var inputElems = document.getElementsByTagName("input");
				var allSelected = true;
				for (var i = 0; i < inputElems.length; i++) {
				    if (inputElems[i].getAttribute("type") == "file") {
				    	if (inputElems[i].value == null) allSelected = false;
				    	else if (inputElems[i].value == "") allSelected = false;
				    }
				}
				if (allSelected) document.getElementById("uploadCSVs").disabled = false; 
				else document.getElementById("uploadCSVs").disabled = true;
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
					<li><a href="../test" rel="nofollow">Test1</a></li>
					<li><a href="../run?algName=doSomeLoops&param1=20000000" rel="nofollow">Test2</a></li>
					<li><a href="../contact.jsp">Contact us</a></li>
					<li><a href="../logout">Logout</a></li>
				</ul>
			</div>
			<div id="content">
				<h2>Management area - Automated cost estimation system</h2>
				<p>The database information will be in the form of &lt;inputRepresentation, executionTime&gt; pairs for each method. In order to obtain the execution times, we will run now a battery of tests. Unfortunately, we cannot do this automatically and for each method we need you to define its expected input parameters, with a CSV file.</p>
				<p>Take into account the following considerations when preparing the CSV files:</p>
				<form action="generateDB" enctype="multipart/form-data" method="post">
				<ul>
					<li>The elements of each line will be separated by this/these "<input type="text" id="sepChar" name="sepChar" maxlength="4" size="2" value=",">" character(s).</li>
					<li>Every line must contain the String version of the input parameters of your method, in the order you expect them to receive (remember how did you implement the parsing from Strings to specific types in the uploaded JAR library).</li>
					<li>You must define a function to summarize any input case to a single (as small as possible while representative) numeric value, and for every line append the corresponding numeric representation as the last element.</li>
					<li>The number of lines depends on your method. With a simple method having only one input parameter and with an execution time growing linearly and proportionally to this parameter, between 10 and 100 lines might be enough. For methods with more parameters and more unpredictable execution times, around 500 and 1000 lines could be more appropriate. It is about the variety of possible inputs and the regularity of runtime costs in relation to them. We also expect you to provide as many "sparse" (significantly different) input cases as possible, but still give more emphasis to the input cases that you know that are more probable. If you upload a CSV file with too few cases, the predictions will not be accurate enough. On the other hand, a CSV file with too much cases could produce a too large database and this could increment notably the weight of your Android application; furthermore, depending on the complexity of your method, the battery of testing could take long.</li>
				</ul>
				<% 
					String methodName;
					for (int i = 0; i < methodNames.length; i++) {
						methodName = methodNames[i];
				%>
				<p>Please, select a CSV file for the method <em><%=methodName%></em>:</p>
				<p><input id="<%=methodName%>" name="<%=methodName%>" type="file" name="datafile" size="40" onchange="fileOnChange()"></p>
				<%	
					}
				%>
				<p><input id="uploadCSVs" name="uploadCSVs" type="submit" value="Upload CSV(s)" disabled></p>
				</form>
			</div>
			<div id="footer">Freie Universität Berlin, 2013</div>
		</div>
	</body>
</html>