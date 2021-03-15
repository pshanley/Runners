<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add a Runner</title>
<style type="text/css">
    label {
        display: inline-block;
        width: 200px;
        margin: 5px;
        text-align: left;
    }
    input[type=text], input[type=password], select {
        width: 200px;
    }
    input[type=radio] {
        display: inline-block;
        margin-left: 45px;
    }
    input[type=checkbox] {
        display: inline-block;
        margin-right: 190px;
    }

    button {
        padding: 10px;
        margin: 10px;
    }
</style>
</head>
<body>
    <div align="center">
        <h2>Add a Team</h2>
        <form:form action="addTeam" method="post" modelAttribute="team">
            <form:label path="teamName">Team Name:</form:label>
            <form:input path="teamName"/><br/>

            <form:button>Add Team</form:button>
        </form:form><br>

        <form action="/teams" style="text-align: center">
            <input type="submit" value="Back to Teams"  />
        </form><br>

        <form action="/" style="text-align: center">
            <input type="submit" value="Home"  />
        </form>
    </div>
</body>
</html>
