<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Professional Running Clubs</title>
<link rel="stylesheet" href="/styles.css">
</head>
<body>
    <h1 align="center">${team.teamName}</h1>
    <h2 align="center"><img  src="/uploads/${team.teamName}" height="100" width="100"></h1><br> <!-- THE ROOT / is IMPORTANT-->

    <table align="center">
        <tr>
            <th>Pic</th><th>Name</th><th>Instagram Handle</th><th>Number of Followers</th>
        </tr>
        <c:forEach var="runner" items="${team.athletes}">
        <tr>
          <td style="text-align:center;width: 20%"><img alt="" height=40  src="/uploads/${runner.username}"></td>
                      <td><a href="runners?username=${runner.username}">${runner.firstName} ${runner.lastName}</a></td>
                      <td><a href="https://www.instagram.com/${runner.instagramHandle}">${runner.instagramHandle}</a></td>
                      <td>${runner.followersCount}</td>
        </tr>
        </c:forEach>
    </table><br><br>




    <form align="center" action="/showEditTeamForm">
       <input type="hidden" name="teamName" value="${team.teamName}" />
       <input type="submit" value="Edit Team" />
    </form><br>

    <form align="center" action="/addTeamForm">
       <input type="submit" value="Add a Team" />
    </form><br>

    <form align="center" action="/">
        <input type="submit" value="Back to Home" />
    </form><br>

</body>
