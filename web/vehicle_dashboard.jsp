<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vehicle Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        h1 {
            text-align: center;
            color: #333;
            padding: 20px;
            background-color: #008CBA;
            color: white;
            margin: 0;
        }

        #vehicle-images {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: center;
            padding: 20px;
        }

        #vehicle-images div {
            text-align: center;
            background-color: white;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 15px;
            width: 220px;
            transition: transform 0.3s ease-in-out;
        }

        #vehicle-images div:hover {
            transform: scale(1.05);
        }

        h2 {
            font-size: 18px;
            color: #333;
            margin: 10px 0;
        }

        img {
            width: 200px;
            height: auto;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease-in-out;
        }

        img:hover {
            transform: scale(1.1);
        }

        p {
            text-align: center;
            color: #333;
            font-size: 16px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <h1>Vehicle Dashboard</h1>

    <div id="vehicle-images">
        <% 
            List<Map<String, String>> vehicles = (List<Map<String, String>>) request.getAttribute("vehicles");

            if (vehicles == null || vehicles.isEmpty()) {
        %>
            <p>No vehicles found in the database.</p>
        <% 
            } else {
                for (Map<String, String> vehicle : vehicles) {
        %>
                    <div>
                        <h2><%= vehicle.get("vehicleName") %></h2>
                        <img src="http://localhost:8080/Mega_City/<%= vehicle.get("imagePath") %>" alt="<%= vehicle.get("vehicleName") %>">
                    </div>
        <% 
                }
            }
        %>
    </div>
</body>
</html>
