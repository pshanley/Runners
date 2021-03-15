<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Team Successfully Added</title>
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
        <h2>Team Added!</h2>
        <span>Team Name:</span><span>${team.teamName}</span><br/>
    </div><br><br>

    <form action="/teams" align="center" >
        <input type="submit" value="View Teams" />
    </form><br>
    <form align="center" action="/addTeamForm" >
        <input type="submit" value="Add Another Team" />
    </form><br>
    <form align="center" action="/" >
        <input type="submit" value="Home" />
    </form><br>


</body>
</html>
