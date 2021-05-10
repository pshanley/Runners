<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List of Professional Runners</title>
<link rel="stylesheet" href="/styles.css">
</head>
<body>
    <div align="center">
    <div class="runners-outline" align="center">
    <h1 align="center">Professional Distance Runners</h1>
    <div class="table-and-paging">
    <table align="center">
        <tr>
            <th>Pic</th><th>Name</th><th>Instagram Handle</th><th>Number of Followers</th><th>Team</th>
        </tr>
        <c:forEach var="runner" items="${runners}">
        <tr>
            <td style="text-align:center;width: 20%"><img class="runner-image" alt="" height=40  src="/uploads/${runner.username}"></td>
            <td><a href="runners?username=${runner.username}">${runner.firstName} ${runner.lastName}</a></td>
            <td><a href="https://www.instagram.com/${runner.instagramHandle}" target="_blank" rel="noreferrer noopener">${runner.instagramHandle}</a></td>
            <td>${runner.followersCount}</td>
            <td style="text-align:center;width: 20%"><a href="teams?teamName=${runner.team.teamName}"><img alt="" height=40  src="/uploads/${runner.team.teamName}"></a></td>
        </tr>
        </c:forEach>
    </table>
    <script type="text/javascript">
    window.onload = function() {
      var currentPage = "${currentPage}";
      var numberOfPages = "${numberOfPages}"
      var previousPage = +currentPage - 1;
      var nextPage = +currentPage + 1;
      var lastPage = +numberOfPages - 1;
      var previousPageLink = "?pageNo=" + previousPage;
      var nextPageLink = "?pageNo=" + nextPage;
      var lastPageLink = "?pageNo=" + lastPage;
      document.getElementById("nextPage").setAttribute("href",nextPageLink);
      document.getElementById("lastPage").setAttribute("href",lastPageLink);
      document.getElementById("previousPage").setAttribute("href",previousPageLink);
      if(+currentPage === +numberOfPages-1){
      	       document.getElementById('nextPage').style.display = "none";
      	       document.getElementById('lastPage').style.display = "none";
      }
      if(+currentPage === 0){
       	        document.getElementById('previousPage').style.display = "none";
                document.getElementById('firstPage').style.display = "none";
      }
      if(+numberOfPages === 2){
            document.getElementById('firstPage').style.display = "none";
            document.getElementById('lastPage').style.display = "none";
      }
    }
    </script>

    <div class="pagination">
      <div class="arrow"><a id="firstPage" href="?pageNo=0">&laquo;</a>
      <a id="previousPage">&lsaquo;</a></div>
      <a><b>${currentViewedRunners}</b> of ${numberOfRunners}</a>
      <div class="arrow"><a id="nextPage">&rsaquo;</a>
      <a id="lastPage">&raquo;</a></div>
    </div>

    </div>

    </div><br>
    </div><br><br>

    <form action="/teams" style="text-align: center">
        <input type="submit" value="View Teams" class="button" />
    </form><br>
    <form align="center" action="/addRunnerForm">
        <input type="submit" value="Add A Runner" class="button" />
    </form><br>
    <form align="center" action="/registerUserForm">
        <input type="submit" value="Become A Contributor" class="button" />
    </form>

</body>
