<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Hello Servlet</title>
</head>
<body>
    <h1>Hello from the Servlet!</h1>

    <h2>Vehicle Names:</h2>
    <ul>
        <!-- Loop through the list of vehicle names and display them -->
        <c:forEach var="vehicle" items="${vehicleNames}">
            <li>${vehicle}</li>
        </c:forEach>
    </ul>
</body>
</html>
