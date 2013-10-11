<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="serverClasses.ManagementMethodsDB" %>
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
		<title>Management area - Automated cost estimation system</title>
		<script type="text/javascript">
			function selectOnChange() {
				var methodsNamesSelect = document.getElementById("methodNamesSelect");
				var somethingSelected = false;
				for (var i = 0; i < methodsNamesSelect.length; i++) {
					if (methodsNamesSelect[i].selected) somethingSelected = true;
				}
				if (somethingSelected) document.getElementById("buttonSelectMethods").disabled = false; 
				else document.getElementById("buttonSelectMethods").disabled = true;
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
				<h2>Management area - Automated cost estimation system</h2>
				<p>The Android application that will be run on the mobile devices and will communicate with the classes that you uploaded to this server (<a href="uploadFile.jsp">I didn't!</a>), needs an execution time estimation function (or <em>cost</em> function) for each of its potentially offloadable blocks of code (or <em>methods</em>). You can either implement it yourself or use the statistical non-parametric regression estimation system described next.</p>
				<p>The objective of the automated cost estimation system is to compute accurate execution time estimations given a method and its input parameters. The system will have to be able to produce reliable estimations at runtime (so it needs to be computationally efficient), whenever your Android application meets a potentially offloadable block of code, in order to decide whether it is worth it to start an offloading process or rather just keep processing in the Android device.</p>
				<p>The estimations will be computed from previously stored past observations, so we need to generate a relatively small sample database in order to have a starting reference point. Later, the database will be continuously enlarged, as the system will keep updating it with the real execution times obtained from every new execution. Generating the initial database during the runtime of your Android application would block the device or keep it busy for a too long time, so the first step will be to produce it beforehand.</p>
				<p>You can follow the steps below to obtain an initial database gathering information of each of the methods that you expect to invoke from the Android application.</p>
				<br>
				<p>The following box lists all the available methods in this server. First, select which one(s) you want to include in the database:</p>
				<form method="get" action="formAlgsInput.jsp">
				<% 
					String[] algNamesEnum = ManagementMethodsDB.getMethodNames();
					if (algNamesEnum.length < 15) {
						String algNamesSize = Integer.toString(algNamesEnum.length);
				%>
					<select id="methodNamesSelect" name="methodNamesSelect" size="<%=algNamesSize%>" onchange="selectOnChange()" multiple>
				<%
					} else {
				%>
					<select id="methodNamesSelect" name="methodNamesSelect" size="15" onchange="selectOnChange()" multiple>
				<%
					}
					String algName;
					for (int i = 0; i < algNamesEnum.length; i++) {
						algName = algNamesEnum[i];
				%>
						<option value="<%=algName%>"><%=algName%></option>
				<%	
					}
				%>
					</select>
					<p><input id="buttonSelectMethods" type="submit" name="submitSelectMethods" value="Select method(s)" disabled></p>
				</form>
			</div>
			<div id="footer">Freie Universität Berlin, 2013</div>
		</div>
	</body>
</html>