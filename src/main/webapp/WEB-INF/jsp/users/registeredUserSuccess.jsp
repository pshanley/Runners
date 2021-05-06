<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/styles.css">
<title>Runner Successfully Added</title>
<style type="text/css">
    span {
        display: inline-block;
        width: 200px;
        text-align: left;
    }
</style>
</head>
<body>
    <div align="center">
        <h2>Registration Succeeded!</h2>
        <span>First Name:</span><span>${user.username}</span><br/>

    </div><br><br>

    <form action="/" align="center" >
        <input type="submit" value="Home" class="button" />
    </form><br>


</body>
</html>
