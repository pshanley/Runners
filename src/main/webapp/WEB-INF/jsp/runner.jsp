<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Runner Page</title>
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
        <h2>Runner information</h2>
        <span>Name:</span><span>${runner.firstName} ${runner.lastName}</span><br/><br/>
        <span>Instagram Handle:</span><span><a href="https://www.instagram.com/${runner.instagramHandle}">${runner.instagramHandle}</a></span><br/><br/>
        <span>Number of Followers:</span><span>${runner.followersCount}</span><br/><br/>
        <span>Image:</span><span><img alt="" height=40  src="/uploads/${runner.username}" ></span><br/><br/>
        <span>Team:</span><span><a href="/teams?teamName=${runner.team.teamName}">${runner.team.teamName}</a></span><br/><br/>
    </div><br><br>




        <form action="/editRunnerForm" align="center">
            <input type="hidden" name="runnerName" value=${runner.username}>
            <input type="submit" value="Edit ${runner.firstName}&#39;s Profile" />
        </form><br>
    <form action="/" align="center" >
        <input type="submit" value="Back to Runners" />
    </form><br>

</body>
</html>
