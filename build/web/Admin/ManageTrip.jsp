<%@page import="java.util.Map"%>
<%@page import="AServices.ManageTripClass"%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Manage Trips</title>
    <style>
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid black; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>

<body>
    <h2>Manage Trips</h2>

    <form method="get" action="ManageTripServlet">
        <!-- Status Filter -->
        <label for="status">Status:</label>
        <select name="status" id="status">
            <option value="all">All</option>
            <option value="booked">Booked</option>
            <option value="completed">Complete</option>
        </select>

        <!-- Search Filter -->
        
        <button type="submit">Filter</button>
        <label for="search">Search:</label>
        <input type="text" name="search" id="search" placeholder="Enter Details">

    </form>

    <table>
        <thead>
            <tr>
                <th>Unique ID</th>
                <th>Vehicle Name</th>
                <th>Driver Email</th>
                <th>Booking Date</th>
                <th>Return Date</th>
                <th>Returned Date</th>
                <th>Booking Time</th>
                <th>Customer Name</th>
                <th>Customer Email</th>
                <th>Total Fare</th>
                <th>Created At</th>
                <th>Rent Per Day</th>
                <th>Fine</th>
                <th>Status</th>
            </tr>
        </thead>

        <tbody>
            <%
            // Get the manageTrip map from the request attribute
            Map<String, ManageTripClass> managetrip = (Map<String, ManageTripClass>) request.getAttribute("manageTrip");

            // Check if the map is not null and not empty
            if (managetrip != null && !managetrip.isEmpty()) {
                // Iterate through the trips
                for (Map.Entry<String, ManageTripClass> entry : managetrip.entrySet()) {
                    ManageTripClass trip = entry.getValue();
            %>
            <tr>
                <td><%= trip.getUniqueId() %></td>
                <td><%= trip.getVehiclename() %></td>
                <td><%= (trip.getDriverEmail() != null) ? trip.getDriverEmail() : "N/A" %></td>
                <td><%= trip.getBookingdate() %></td>
                <td><%= trip.getReturndate() %></td>
                <td><%= (trip.getReturneddate() == null) ? "Not Returned" : trip.getReturneddate() %></td>
                <td><%= trip.getBookingtime() %></td>
                <td><%= trip.getCustomername() %></td>
                <td><%= trip.getCustomeremail() %></td>
                <td>Rs. <%= trip.getTotalfare() %></td>
                <td><%= trip.getCreatedaat() %></td>
                <td>Rs. <%= trip.getRentperday() %></td>
                <td>
                    <%= (trip.getFine() == null || trip.getFine().equals("0") || trip.getFine().equals("0.0")) 
                        ? "No Fine" 
                        : "Rs. " + trip.getFine() %>
                </td>
                <td><%= trip.getStatus() %></td>
            </tr>
            <% 
                } // End of loop
            } else { 
            %>
            <tr>
                <td colspan="15" style="text-align: center;">No trips found.</td>
            </tr>
            <% 
            } 
            %>
        </tbody>
    </table>
</body>
</html>
