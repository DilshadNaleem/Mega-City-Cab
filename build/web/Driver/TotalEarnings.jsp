<%@page import="java.util.ArrayList" %>
<%@page import="Driver.TotalEarnings" %>
<html>
<head>
    <title>Total Earnings</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        .error {
            color: red;
            font-weight: bold;
        }
    </style>
</head>

<body>
    <h1>Driver Total Earnings</h1>

    <%-- Display error message if any --%>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
        <p class="error"><%= errorMessage %></p>
    <% } %>

    <%-- Fetch total earnings from request attribute --%>
    <% ArrayList<TotalEarnings> totalEarnings = (ArrayList<TotalEarnings>) request.getAttribute("totalEarnings"); %>
    <% if (totalEarnings == null || totalEarnings.isEmpty()) { %>
        <p>No Earnings Available</p>
    <% } else { %>

    <table>
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
</body>
</html>
