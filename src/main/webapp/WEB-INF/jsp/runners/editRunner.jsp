<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Runner Page</title>
<link rel="stylesheet" href="/styles.css">
</head>
<body>
    <div align="center">
        <h2>Runner information</h2>
        <span>Name:</span><span>${runner.firstName} ${runner.lastName}</span><br/><br/>
        <span>Instagram Handle:</span><span><a href="https://www.instagram.com/${runner.instagramHandle}">${runner.instagramHandle}</a></span><br/><br/>
        <span>Number of Followers:</span><span>${runner.followersCount}</span><br/><br/>
        <span>Image:</span><span><img alt="" height=40  src=${runner.imageURL}></span><br/><br/>
        <span>Team:</span><span><a href="teams?teamName=${runner.team.teamName}">${runner.team.teamName}</a></span><br/><br/>
    </div><br><br>

    <h3 align="center">Update ${runner.firstName} ${runner.lastName}&#39;s Picture </h3>
   <form action="/runners/updateRunnerPicture" method="POST" align="center">
     <label for="newImageUrl">New Image URL:</label><br>
     <input type="text" id="newImageUrl" name="newImageUrl"><br>
     <input type="hidden" name="runnerName" value="${runner.username}"><br>
     <input type="submit">
   </form><br><br>

    <h3 align="center">Change ${runner.firstName} ${runner.lastName}&#39;s team </h3>
        <br/>
        <table align="center">
            <tr>
             <th>Select</th><th>Picture</th><th>TeamName</th>
            </tr>
            <c:forEach var="team" items="${teams}">
            <tr>
           <td style="text-align:center;width: 20%">
               <form action="/runners/addToTeam" method="POST">
                 <input type="hidden" name="runnerName" value="${runner.username}">
                 <input type="hidden" name="teamName" value="${team.teamName}" />
                 <input type="submit" value="Add to Team">
               </form>
           </td>
              <td><img  src="/uploads/${team.teamName}" alt="" height="50" width="50"></td>
              <td><a href="teams?teamName=${team.teamName}">${team.teamName}</a></td>
           </tr>
           </c:forEach>
           <tr>
            <td style="text-align:center;width: 20%">
                           <form action="/runners/addToTeam" method="POST">
                             <input type="hidden" name="runnerName" value="${runner.username}">
                             <input type="hidden" name="teamName" value=null />
                             <input type="submit" value="No Team">
                           </form>
            </td>
                          <td></td>
                          <td></td>

           </tr>

        </table><br><br>

    <form action="/" align="center" >
        <input type="submit" value="Back to Runners" />
    </form><br>
    <form align="center" action="/addRunnerForm" >
        <input type="submit" value="Add Another Runner" />
    </form>
    <form align="center" action="/runners/delete" method="POST">
         <input type="hidden" name="runnerName" value="${runner.username}"><br>
        <input type="submit" value="Delete Runner" class="button-red"/>
    </form>

</body>
</html>
