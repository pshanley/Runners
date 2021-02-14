<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" language="java">
<link rel="stylesheet" href="styles.css">
<title>Runners</title>
</head>
<body style="margin-left: 50px;">

<h1>Running Application</h1>
<h3>Come see the most popular runners on instagram</h3>

<body>
    <jsp:include page="/getRunners" />

    <table>
        <tr>
            <th>Pic</th>
            <th>Name</th>
            <th>Instagram</th>
            <th>Followers</th>
        </tr>
        <c:forEach items="${runners}" var="runner" varStatus="status">
            <tr>
                <td style="text-align:center;width: 20%"><img alt="" height=40  src=${runner.imageURL}></td>
                <td>${runner.firstName} ${runner.lastName}</td>
                <td><a href="https://www.instagram.com/${runner.instagramHandle}">${runner.instagramHandle}</a></td>
                <td style="text-align:right;padding-right:5px">${runner.followersCount}</td>

            </tr>
        </c:forEach>
    </table><br>

    <form action="addRunner.jsp">
        <input type="submit" value="Add Another Runner" />
    </form>

</body>
</html>
