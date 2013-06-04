<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="style.css" media="screen" rel="Stylesheet" type="text/css">
<title>Upload File</title>
<script type="text/javascript">

function arxiuOnChange() {
	if (document.getElementById("botoChooseFile").value.length == 0) document.getElementById("botoUpload").disabled = true; 
	else document.getElementById("botoUpload").disabled = false;
}

</script>
</head>
<body>
	<h1>WELCOME</h1>
	<h2>Please choose the zip file with you Algorithms to upload</h2>
	<p> The zip file must have the name of your main package and you must have a main file called ParseAndCall.java</p>
	<form action="uploadFile" enctype="multipart/form-data" method="post">
		
		<p>
			Please specify a file, or a set of files:<br> <input id="botoChooseFile" type="file"
				name="datafile" size="40" onchange="arxiuOnChange()">
		</p>
		<div>
			<input id="botoUpload" type="submit" value="Upload" disabled>
		</div>
	</form>
</body>
</html>