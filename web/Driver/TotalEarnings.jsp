<%@page import="java.util.ArrayList" %>
<%@page import="Driver.Class.*" %>
<html>
<head>
    <title>Total Earnings</title>
    <!-- Bootstrap CSS link -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #0073bb;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
            color: #0073bb;
        }
        .error {
            color: red;
            font-weight: bold;
        }
        /* Back Button Styling */
        .back-btn {
            background-color: #0073bb;
            color: white;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            width: 150px;  /* Fixed width */
            height: 40px;  /* Fixed height */
            font-size: 14px;
            display: block;
            margin-top: 20px;
            text-align: center;
        }
        .back-btn:hover {
            background-color: #5196c1;
        }
    </style>
</head>

<body class="bg-light">
    <div class="container mt-5">
        <h1 class="text-center text-primary">Driver Total Earnings</h1>

        <%-- Display error message if any --%>
        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMessage != null) { %>
            <p class="error text-center"><%= errorMessage %></p>
        <% } %>

        <%-- Fetch total earnings from request attribute --%>
        <% ArrayList<TotalEarnings> totalEarnings = (ArrayList<TotalEarnings>) request.getAttribute("totalEarnings"); %>
        <% if (totalEarnings == null || totalEarnings.isEmpty()) { %>
            <p class="text-center">No Earnings Available</p>
        <% } else { %>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Unique ID</th>
                    <th>Vehicle Name</th>
                    <th>Booking Date</th>
                    <th>Returned Date</th>
                    <th>Customer Name</th>
                    <th>Fare</th>
                    <th>Fine</th>
                    <th>Total Amount</th>
                </tr>
            </thead>
            
            <tbody>
                <% for (TotalEarnings earnings : totalEarnings) { %>
                    <tr>
                        <td><%= earnings.getUniqueId() %></td>
                        <td><%= earnings.getVehiclename() %></td>
                        <td><%= earnings.getBookingdate() %></td>
                        <td><%= earnings.getReturneddate() %></td>
                        <td><%= earnings.getCustomername() %></td>
                        <td><%= earnings.getTotalFare() %></td>
                        <td><%= earnings.getFine() %></td>
                        <td><%= earnings.getTotalAmount() %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>

        <% } %>

        <!-- Back Button -->
        <button class="back-btn" onclick="window.history.back();">Back</button>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
