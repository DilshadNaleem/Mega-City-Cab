<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.HashMap, java.util.Map, AServices.VehicleConfirmClass" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vehicle Management</title>
    <link rel="stylesheet" href="styles.css"> <!-- Add your own CSS file if needed -->
</head>
<body>
    <h1>Manage Vehicles</h1>

    <!-- Displaying the vehicles -->
    <table border="1">
        <thead>
            <tr>
                <th>Vehicle ID </th>
                <th>Vehicle Name</th>
                <th>Brand</th>
                <th>Year</th>
                <th>Condition</th>
                <th>Mileage</th>
                <th>Color</th>
                <th>Rent per Day</th>
                <th>Category</th>
                <th>Driver Email</th>
                <th>Image</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                // Retrieving the HashMap from the request attribute
                HashMap<String, VehicleConfirmClass> vehicleClassconfirm = 
                    (HashMap<String, VehicleConfirmClass>) request.getAttribute("vehicleClassconfirm");

                if (vehicleClassconfirm == null || vehicleClassconfirm.isEmpty()) {
            %>
                <tr>
                    <td colspan="12">No vehicles found.</td>
                </tr>
            <% 
                } else {
                    for (Map.Entry<String, VehicleConfirmClass> entry : vehicleClassconfirm.entrySet()) {
                        VehicleConfirmClass vehicle = entry.getValue();
            %>
                <tr>
                    <td><%= vehicle.getId()%></td>
                    <td><%= vehicle.getVehicleName() %></td>
                    <td><%= vehicle.getBrandName() %></td>
                    <td><%= vehicle.getYear() %></td>
                    <td><%= vehicle.getCondition() %></td>
                    <td><%= vehicle.getMilage() %></td>
                    <td><%= vehicle.getColor() %></td>
                    <td><%= vehicle.getRentPerDay() %></td>
                    <td><%= vehicle.getVehcileCat() %></td>
                    <td><%= vehicle.getDriverEmail() %></td>
                    <td>
                        <img src="http://localhost:8080/Mega_City/<%= vehicle.getImagePath() %>" 
                             alt="Vehicle Image" width="100" height="100">
                    </td>
                    <td><%= vehicle.getStatus() %></td>
                    <td>
                        <% if ("Pending".equals(vehicle.getStatus())) { %>
                            <form action="/Mega_City/ApproveVehicleServlet" method="post" style="display:inline;">
                                <input type="hidden" name="vehicleId" value="<%= vehicle.getId() %>">
                                <button type="submit">Approve</button>
                            </form>
                            <form action="/Mega_City/RejectVehicleServlet" method="post" style="display:inline;">
                                <input type="hidden" name="vehicleId" value="<%= vehicle.getId() %>">
                                <button type="submit">Reject</button>
                            </form>
                        <% } %>
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
