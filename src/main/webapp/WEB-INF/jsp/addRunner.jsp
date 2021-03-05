<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Registration Form</title>
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
        <h2>User Registration</h2>
        <form:form action="addRunner" method="post" modelAttribute="runner">
            <form:label path="firstName">First Name:</form:label>
            <form:input path="firstName"/><br/>

            <form:label path="lastName">Last Name:</form:label>
            <form:input path="lastName"/><br/>

            <form:label path="instagramHandle">Instagram Handle:</form:label>
            <form:input path="instagramHandle"/><br/>



            <form:button>Add Runner</form:button>
        </form:form>
            <form action="/" style="text-align: center">
                <input type="submit" value="Back to Runners"  />
            </form>
    </div>
</body>
</html>
