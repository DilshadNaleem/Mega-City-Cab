<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Vehicle Details</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        table { width: 80%; margin: 20px auto; }
        th, td { padding: 10px; text-align: left; }
        th { background-color: #f2f2f2; }
        td { background-color: #ffffff; border: 1px solid #ddd; }
        .img-thumbnail { max-width: 100px; max-height: 100px; }
        .no-image { color: red; font-style: italic; }
    </style>
</head>
<body>
    <div class="container">
        <h2 class="text-center">Vehicle Details</h2>

        <!-- Search form -->
        <form method="get" action="VehicleRetrieveServlet" class="form-inline justify-content-center mb-4">
            <div class="form-group">
                <input type="text" name="search" class="form-control" placeholder="Enter vehicle name or brand" value="${searchQuery}" />
            </div>
            <button type="submit" class="btn btn-primary ml-2">Search</button>
        </form>

        <!-- Vehicle details table -->
        <table class="table table-bordered">
            <thead class="thead-light">
                <tr>
                    <th>Vehicle Name</th>
                    <th>Year</th>
                    <th>Brand</th>
                    <th>Condition</th>
                    <th>Mileage</th>
                    <th>Rent Per Day</th>
                    <th>Image</th>
                    <th>Image Path</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="vehicle" items="${vehicleList}">
                    <tr>
                        <td>${vehicle.vehicleName}</td>
                        <td>${vehicle.vehicleYear}</td>
                        <td>${vehicle.brandName}</td>
                        <td>${vehicle.condition}</td>
                        <td>${vehicle.mileage}</td>
                        <td>${vehicle.rentPerDay}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty vehicle.imagePath}">
                                    <img src="${vehicle.imagePath}" alt="Vehicle Image" class="img-thumbnail">
                                </c:when>
                                <c:otherwise>
                                    <span class="no-image">No image available</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${vehicle.imagePath}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
