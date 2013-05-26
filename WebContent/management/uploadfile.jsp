<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="style.css" media="screen" rel="Stylesheet" type="text/css">
<title>Upload File</title>
</head>
<body>
	<h1>WELCOME</h1>
	<h2>Please choose the algorithm files to upload</h2>
	<form action="uploadFile" enctype="multipart/form-data" method="post">
		
		<p>
			Please specify a file, or a set of files:<br> <input type="file"
				name="datafile" size="40">
		</p>
		<div>
			<input type="submit" value="Upload">
		</div>
	</form>
</body>
</html>