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

    <form align="center" action="/showEditTeamForm">
       <input type="hidden" name="teamName" value="${team.teamName}" class="button"/>
       <input type="submit" value="Edit Team" />
    </form><br>
    <form action="/teams" align="center" >
        <input type="submit" value="View Teams" class="button"/>
    </form><br>
    <form align="center" action="/addTeamForm" >
        <input type="submit" value="Add Another Team" class="button"/>
    </form><br>
    <form align="center" action="/" >
        <input type="submit" value="Home" class="button"/>
    </form><br>


</body>
</html>
