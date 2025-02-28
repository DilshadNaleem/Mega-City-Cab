<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, Driver.BookingDetails" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.time.LocalDate, java.time.temporal.ChronoUnit" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bookings</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        .overdue {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <h2>Vehicle Bookings</h2>

    <table>
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
                                    <button type="submit">Complete Trip</button>
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
                    <td colspan="12">No bookings available.</td>
                </tr>
        <%
            }
        %>
    </table>
</body>
</html>
