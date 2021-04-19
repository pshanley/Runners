<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
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
        <span>First Name:</span><span>${runner.firstName}</span><br/>
        <span>Last Name:</span><span>${runner.lastName}</span><br/>
        <span>Instagram Handle:</span><span>${runner.instagramHandle}</span><br/>
        <span>Number of Followers:</span><span>${runner.followersCount}</span><br/>
        <span>Image:</span><span><img alt="" height=40  src="/uploads/${runner.username}"></span><br/>
        <span style="color:red">${error}</span><br>

    </div><br><br>

    <form action="/editRunnerForm" align="center">
        <input type="hidden" name="runnerName" value=${runner.username}>
        <input type="submit" value="Add ${runner.firstName} to a Team" />
    </form><br>
    <form action="/" align="center" >
        <input type="submit" value="View Runners" />
    </form><br>
    <form align="center" action="/addRunnerForm" >
        <input type="submit" value="Add Another Runner" />
    </form>

</body>
</html>
