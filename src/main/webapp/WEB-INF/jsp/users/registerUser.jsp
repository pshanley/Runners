<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add a Runner</title>
<link rel="stylesheet" href="/styles.css">
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

              display:inline-block;
              padding:0.4em 2em;
              border:0.16em solid #0096FF;
              background-color: #FFFFFF;
              margin:0 0.2em 0.2em 0;
              box-sizing: border-box;
              text-decoration:none;
              color:#000000;
              text-align:center;
              border-radius: 4px;
              cursor: pointer;
        }
        button:hover{
          box-shadow: 0px 0px 0px 2px #0047AB;
        }
</style>
</head>
<body>
    <div align="center">
        <h2>User Registration</h2>
        <form:form action="registerUser" method="post" modelAttribute="userDTO">
            <form:label path="username">Username:</form:label>
            <form:input path="username"/><br/>

            <form:label path="password">Password:</form:label>
            <form:input type="password" path="password"/><br/>

            <form:label path="matchingPassword">Confirm Password:</form:label>
            <form:input type="password" path="matchingPassword"/><br/>
            <span style="color:red">${error}</span><br>

            <form:button>Add User</form:button>
        </form:form><br>

        <form action="/" style="text-align: center">
            <input type="submit" value="Back to Runners" class="button" />
        </form>
    </div>
</body>
</html>
