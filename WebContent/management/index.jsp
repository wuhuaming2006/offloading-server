<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="style.css" media="screen" rel="Stylesheet" type="text/css">
<title>Log in please!</title>
</head>
<body>
	<h1>Log in please!</h1>
	<form method="post" action="loginCheck">
		<table border="0" cellspacing="5">
			<tr>
				<th align="right">Username:</th>
				<td align="left"><input type="text" name="username"></td>
			</tr>
			<tr>
				<th align="right">Password:</th>
				<td align="left"><input type="password" name="password"></td>
			</tr>
			<tr>
				<td align="right"><input type="submit" name="submitLogin"
					value="Log In"></td>
			</tr>
		</table>
	</form>
	<%
		Boolean wrongPassword = (Boolean) request.getAttribute("wrongPass");
		if (wrongPassword != null) {
	%>

	<h3>Wrong password, please try again</h3>
	<%
		}
	%>
</body>
</html>