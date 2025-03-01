<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="java.util.ArrayList, AServices.ManageVehicleTypeClass" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vehicle Management</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEJYy6aA7vJzF5ZC8msE5fKhuQ1ZzbyWqZTc+zJv0z7d6RmI0dZp1uNGp5Fts" crossorigin="anonymous">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h1 {
            text-align: center;
            color: #0073bb; /* Apply primary color */
        }
        table {
            width: 100%;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: center;
        }
        th {
            background-color: #5196c1; /* Apply secondary color */
            color: white;
        }
        td {
            background-color: #f9f9f9;
        }
        button {
            background-color: #5196c1; /* Apply secondary color */
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #0073bb; /* Darker on hover */
        }
        .back-btn {
            width: 100%;
            padding: 12px;
            background-color: #ccc;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 20px;
        }
        .back-btn:hover {
            background-color: #aaa;
        }
    </style>
</head>
<body>

    <h1>Manage Vehicle Types</h1>

    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Category Id</th>
                <th>Category Name</th>
                <th>Category Description</th>
                <th>Image</th>
                <th>Actions</th>
            </tr>
        </thead> 
        <tbody>
            <%
                ArrayList<ManageVehicleTypeClass> vehicleList = 
                    (ArrayList<ManageVehicleTypeClass>) request.getAttribute("vehicleList");

                if (vehicleList == null || vehicleList.isEmpty()) {
            %>
            <tr>
                <td colspan="5">No types found.</td>
            </tr>
            <% 
                } else {
                    for (ManageVehicleTypeClass vehicle : vehicleList) {
            %>
            <tr>
                <td><%= vehicle.getId() %></td>
                <td><%= vehicle.getVehicleCategory() %></td>
                <td><%= vehicle.getVehicleDescription() %></td>
                <td>
                    <img src="http://localhost:8080/Mega_City/<%= vehicle.getVehicleImage() %>" width="100" height="100" alt="Vehicle Image">
                </td>
                <td>
                    <form action="/Mega_City/Admin/Edit_VehicleCategory.jsp?vehicleId=<%= vehicle.getId()%>" method="post" style="display:inline;">
                        <input type="hidden" name="vehicleId" value="<%= vehicle.getId()%>">
                        <button type="submit">Edit</button>
                    </form>
                    <form action="/Mega_City/DeleteVehicleTypeServlet" method="post" style="display:inline;">
                        <input type="hidden" name="vehicleId" value="<%= vehicle.getId()%>">
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>

    <!-- Back Button -->
    <button type="button" class="back-btn" onclick="back()">Back</button>

    <script>
        function back() {
            window.history.back();  // JavaScript function to navigate back
        }
    </script>
</body>
</html>
