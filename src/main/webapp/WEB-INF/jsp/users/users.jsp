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
    <h1 align="center">List of Contributors</h1>
    <br/>

    <table align="center">
        <tr>
            <th>Username</th><th>roles</th>
        </tr>
        <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.username}</td>
            <td>
                <c:forEach var="role" items="${user.roles}">
                    ${role.name}
                </c:forEach>
            </td>
        </tr>
        </c:forEach>
    </table><br><br>


    <form action="/" style="text-align: center">
        <input type="submit" value="Home" class="button" />
    </form>
</body>
