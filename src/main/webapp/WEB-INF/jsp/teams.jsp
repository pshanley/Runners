<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Professional Running Clubs</title>
<link rel="stylesheet" href="styles.css">
</head>
<body>
    <h1 align="center">List of Professional Running Teams</h1>
    <br/>
    <table align="center">
        <tr>
         <th>TeamName</th>
        </tr>
        <c:forEach var="team" items="${teams}">
        <tr>
          <td>${team.teamName}</td>
        </tr>
        </c:forEach>
    </table><br><br>

    <form align="center" action="/addTeamForm">
       <input type="submit" value="Add a Team" />
    </form><br>

    <form align="center" action="/">
        <input type="submit" value="Back to Home" />
    </form><br>

</body>
