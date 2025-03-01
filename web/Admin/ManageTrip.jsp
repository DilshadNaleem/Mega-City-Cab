<%@page import="java.util.Map"%>
<%@page import="AServices.ManageTripClass"%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Manage Trips</title>
    <!-- Bootstrap CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <style>
        /* Custom Styles */
        table {
            width: 100%;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #5196c1;
            color: white;
        }
        td {
            background-color: #f9f9f9;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        /* Back Button Styling */
        .back-btn {
            background-color: #0073bb;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            width: 150px;
            height: 50px;
            display: block;
            margin: 30px auto;
        }
        .back-btn:hover {
            background-color: #005fa3;
        }

        /* Filter Form Styling */
        .filter-form {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
        }
        .filter-form select, .filter-form input {
            margin-right: 10px;
            padding: 10px;
            border-radius: 4px;
        }
        .filter-form button {
            padding: 10px 20px;
            background-color: #5196c1;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .filter-form button:hover {
            background-color: #005fa3;
        }
    </style>
</head>

<body>
    <h2 class="text-center text-primary">Manage Trips</h2>

    <form method="get" action="ManageTripServlet" class="filter-form">
        <!-- Status Filter -->
        <label for="status" class="mr-2">Status:</label>
        <select name="status" id="status" class="form-control">
            <option value="all">All</option>
            <option value="booked">Booked</option>
            <option value="completed">Complete</option>
        </select>

        <!-- Search Filter -->
        <label for="search" class="mr-2">Search:</label>
        <input type="text" name="search" id="search" class="form-control" placeholder="Enter Details">

        <button type="submit" class="btn btn-primary">Filter</button>
    </form>

    <table class="table table-bordered">
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
                <td colspan="14" style="text-align: center;">No trips found.</td>
            </tr>
            <% 
            } 
            %>
        </tbody>
    </table>

    <!-- Back Button -->
    <button class="back-btn" onclick="history.back()">Go Back</button>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
