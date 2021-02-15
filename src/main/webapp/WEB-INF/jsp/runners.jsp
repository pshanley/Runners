<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Contact List - Spring Boot Web Application Example</title>
</head>
<body>
    <h1 align="center">List of Runners</h1>
    <br/>
    <table border="1" cellpadding="10">
        <tr>
            <th>Name</th><th>Email</th><th>Country</th>
        </tr>
        <c:forEach var="runner" items="${runners}">
        <tr>
            <td>${runner.firstName}</td>
            <td>${runner.lastName}</td>
            <td>${runner.instagramHandle}</td>
        </tr>
        </c:forEach>
    </table>

    <form action="addRunner.jsp">
        <input type="submit" value="Add Another Runner" />
    </form>
</body>
