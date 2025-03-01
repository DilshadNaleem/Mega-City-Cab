
<%@ page import="java.util.ArrayList" %>
<%@page import="Customer.CService.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>

<html>
<head>
    <title>Booking History</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 30px;
        }
        h2 {
            color: #0073bb;
        }
        .alert-info {
            background-color: #5196c1;
            border-color: #5196c1;
            color: white;
        }
        .table th {
            background-color: #0073bb;
            color: white;
        }
        .table td {
            vertical-align: middle;
        }
        .badge {
            font-size: 0.9rem;
            font-weight: bold;
        }
        .btn-danger {
            background-color: #dc3545;
            border-color: #dc3545;
        }
        .btn-danger:hover {
            background-color: #c82333;
            border-color: #c82333;
        }
        .btn-sm {
            padding: 5px 10px;
        }
        .table-responsive {
            margin-top: 20px;
        }
    </style>
</head>
<body class="bg-light">

    <div class="container mt-5">
        <!-- Back Button -->
       

        <h2 class="text-center mb-4">Booking History</h2>

        <%-- Display success or error messages from session --%>
        <% String message = (String) session.getAttribute("message"); 
           if (message != null) { %>
            <div class="alert alert-info alert-dismissible fade show text-center" role="alert">
                <%= message %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <% session.removeAttribute("message"); // Remove message after displaying it %>
        <% } %>

        <div class="table-responsive">
            <table class="table table-bordered table-hover text-center">
                <thead class="table-dark">
                    <tr>
                        <th>Order ID</th>
                        <th>Vehicle Name</th>
                        <th>Booking Date</th>
                        <th>Return Date</th>
                        <th>Booking Time</th>
                        <th>Customer Name</th>
                        <th>Total Fare</th>
                        <th>Rent Per Day</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        ArrayList<OrderHistory> historyList = (ArrayList<OrderHistory>) request.getAttribute("historyList");
                        if (historyList != null && !historyList.isEmpty()) {
                            for (OrderHistory history : historyList) {
                    %>
                    <tr>
                        <td><%= history.getUniqueId() %></td>
                        <td><%= history.getVehicleName() %></td>
                        <td><%= history.getBookingDate() %></td>
                        <td><%= history.getReturnDate() %></td>
                        <td><%= history.getBookingTime() %></td>
                        <td><%= history.getCustomerName() %></td>
                        <td>Rs. <%= history.getTotalFare() %></td>
                        <td>Rs. <%= history.getRentPerDay() %></td>
                        <td>
                            <span class="badge 
                                <%= history.getStatus().equalsIgnoreCase("Completed") ? "bg-success" : 
                                    history.getStatus().equalsIgnoreCase("Pending") ? "bg-warning text-dark" : 
                                    history.getStatus().equalsIgnoreCase("Booked") ? "bg-primary" : 
                                    "bg-danger" %>">
                                <%= history.getStatus() %>
                            </span>
                        </td>
                        <td>
                            <% if (history.getStatus().equalsIgnoreCase("Booked")) { %>
                                <form action="CancelBookingServlet" method="POST">
                                    <input type="hidden" name="bookingId" value="<%= history.getUniqueId() %>">
                                    <button type="submit" class="btn btn-danger btn-sm">Cancel</button>
                                </form>
                            <% } else { %>
                                <span class="text-muted"></span>
                            <% } %>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="10" class="text-center text-muted">No bookings found.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
                 <button class="btn btn-secondary mb-4" onclick="history.back()">Back</button>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
