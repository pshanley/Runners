<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration Success</title>
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
        <span>First Name:</span><span>${runner.firstName}</span><br/>
        <span>Last Name:</span><span>${runner.lastName}</span><br/>
        <span>Instagram Handle:</span><span>${runner.instagramHandle}</span><br/>
        <span>Number of Followers:</span><span>${runner.followersCount}</span><br/>
        <span>Image:</span><span>${runner.imageURL}</span><br/>
    </div><br><br>

    <form action="/" style="text-align: center">
        <input type="submit" value="View Runners" />
    </form>
</body>
</html>
