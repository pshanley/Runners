<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Contact List - Spring Boot Web Application Example</title>
<link rel="stylesheet" href="styles.css">
</head>
<body>
    <h1 align="center">List of Runners</h1>
    <br/>
    <table align="center">
        <tr>
            <th>Pic</th><th>Name</th><th>Instagram Handle</th><th>Number of Followers</th>
        </tr>
        <c:forEach var="runner" items="${runners}">
        <tr>
            <td style="text-align:center;width: 20%"><img alt="" height=40  src=${runner.imageURL}></td>
            <td>${runner.firstName} ${runner.lastName}</td>
            <td><a href="https://www.instagram.com/${runner.instagramHandle}">${runner.instagramHandle}</a></td>
            <td>${runner.followersCount}</td>


        </tr>
        </c:forEach>
    </table><br><br>

    <form align="center" action="/addRunnerForm">
        <input type="submit" value="Add A Runner" />
    </form><br>
    <form align="center" action="/registerUserForm">
        <input type="submit" value="Become a Contributor" />
    </form><br>
    <form action="/teams" style="text-align: center">
        <input type="submit" value="View Teams"  />
    </form>
</body>
