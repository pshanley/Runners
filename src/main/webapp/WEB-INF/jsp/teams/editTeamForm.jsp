<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add a Runner to a Team</title>
<link rel="stylesheet" href="styles.css">
</head>
<body>
    <h1 align="center">Edit Team: ${team.teamName}</h1>
    <br/>
         <table align="center">
             <tr><th>Remove</th><th>Pic</th><th>Name</th><th>Instagram Handle</th><th>Number of Followers</th></tr>
             <c:forEach var="runner" items="${team.athletes}">
                 <tr>
                 <td style="text-align:center;width: 20%">
                   <form action="/teams/removeRunnerLocal" method="POST">
                     <input type="hidden" name="runner" value=${runner.username}>
                     <input type="hidden" name="teamName" value="${team.teamName}" />
                     <input type="submit" value="Remove Runner">
                   </form>
           </td>
                      <td style="text-align:center;width: 20%"><img alt="" height=40  src=${runner.imageURL}></td>
                      <td><a href="runners?username=${runner.username}">${runner.firstName} ${runner.lastName}</a></td>
                      <td><a href="https://www.instagram.com/${runner.instagramHandle}">${runner.instagramHandle}</a></td>
                      <td>${runner.followersCount}</td>
                 </tr>
             </c:forEach>
         </table><br><br>

    <div align="center">
        <h2>Add a Runner to ${team.teamName}</h2>

         <table align="center">
             <tr><th>Add</th><th>Pic</th><th>Name</th><th>Instagram Handle</th><th>Number of Followers</th></tr>
             <c:forEach var="runner" items="${athletesNotOnTeam}">
                 <tr>
                      <!--<td><a href="" >
                            <div class="plus radius" style="--l:35px;--t:3px;--c2:green">
                           </a></td> -->
                      <td style="text-align:center;width: 20%">
                              <form action="/teams/addRunnerLocal" method="POST">
                                <input type="hidden" name="runner" value=${runner.username}>
                                <input type="hidden" name="teamName" value="${teamName}" />
                                <input type="submit" value="Add Runner">
                              </form>
                      </td>
                      <td style="text-align:center;width: 20%"><img alt="" height=40  src=${runner.imageURL}></td>
                      <td><a href="runners?username=${runner.username}">${runner.firstName} ${runner.lastName}</a></td>
                      <td><a href="https://www.instagram.com/${runner.instagramHandle}">${runner.instagramHandle}</a></td>
                      <td>${runner.followersCount}</td>
                 </tr>
             </c:forEach>
         </table><br><br>


        <form action="/teams/addRunner" method="POST">
          <label for="runner">Runner name:</label>
          <input type="text" id="runner" name="runner"><br><br>
          <input type="hidden" name="teamName" value="${team.teamName}"/>
          <input type="submit" value="Submit">
        </form><br>

        <form action="/teams" style="text-align: center">
            <input type="submit" value="Back to Teams"  />
        </form><br>

        <form action="/" style="text-align: center">
            <input type="submit" value="Home"  />
        </form><br>
    </div>
</body>
</html>
