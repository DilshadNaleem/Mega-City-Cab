<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, Driver.Class.BookingDetails" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.time.LocalDate, java.time.temporal.ChronoUnit" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bookings</title>
    <!-- Bootstrap CSS link -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
            color: #0073bb;
        }
        .overdue {
            color: red;
            font-weight: bold;
        }
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
            margin-left: auto;
            margin-right: auto;
        }
        .back-btn:hover {
            background-color: #5196c1;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h2 class="text-center text-primary">Vehicle Bookings</h2>

        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Booking ID</th>
                    <th>Vehicle Name</th>
                    <th>Booking Date</th>
                    <th>Return Date</th>
                    <th>Booking Time</th>
                    <th>Customer Name</th>
                    <th>Total Fare</th>
                    <th>Rent Per Day</th>
                    <th>Status</th>
                    <th>Driver Email</th>
                    <th>Fine</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <%
                    HttpSession sessionObj = request.getSession();
                    ArrayList<BookingDetails> bookingList = (ArrayList<BookingDetails>) sessionObj.getAttribute("bookingList");

                    if (bookingList != null && !bookingList.isEmpty()) {
                        LocalDate currentDate = LocalDate.now();

                        for (BookingDetails booking : bookingList) {
                            LocalDate returnDate = LocalDate.parse(booking.getReturnDate());
                            long overdueDays = returnDate.isBefore(currentDate) ? ChronoUnit.DAYS.between(returnDate, currentDate) : 0;
                            double fine = overdueDays * 2500;
                %>
                            <tr>
                                <td><%= booking.getUniqueId() %></td>
                                <td><%= booking.getVehicleName() %></td>
                                <td><%= booking.getBookingDate() %></td>
                                <td><%= booking.getReturnDate() %></td>
                                <td><%= booking.getBookingTime() %></td>
                                <td><%= booking.getCustomerName() %></td>
                                <td>Rs. <%= booking.getTotalFare() %></td>
                                <td>Rs. <%= booking.getRentPerDay() %></td>
                                <td><%= booking.getStatus() %></td>
                                <td><%= booking.getDriverEmail() %></td>
                                <td class="<%= (fine > 0) ? "overdue" : "" %>">
                                    <%= (fine > 0) ? "Rs. " + fine : "No Fine" %>
                                </td>
                                <td>
                                    <% if (!booking.getStatus().equals("Completed")) { %>
                                        <form action="/Mega_City/CompleteTripServlet" method="post">
                                            <input type="hidden" name="bookingId" value="<%= booking.getUniqueId() %>">
                                            <button type="submit" class="btn btn-primary">Complete Trip</button>
                                        </form>
                                    <% } else { %>
                                        Completed
                                    <% } %>
                                </td>
                            </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="12" class="text-center">No bookings available.</td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <!-- Back Button -->
        <button class="back-btn" onclick="window.history.back();">Back</button>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
