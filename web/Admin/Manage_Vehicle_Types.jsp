<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="java.util.ArrayList, Admin.ManageVehicleTypeClass" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vehicle Management</title>
    <link rel="stylesheet" href="styles.css"> <!-- Add your own CSS file if needed -->
</head>
<body>
    <h1>Manage Vehicle Types</h1>
    <table border="1">
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
                <td colspan="4"> No types found. </td>
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
                    <img src="http://localhost:8080/Mega_City/<%= vehicle.getVehicleImage() %>" width="100" height="100">
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
</body>
</html>
