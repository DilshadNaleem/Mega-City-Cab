<%@ page import="java.util.ArrayList" %>
<%@ page import="Driver.Class.BookingView" %>
<html>
<head>
    <title>Booking View</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
  <h1>Bookings</h1>
  
  <%-- Check if the booking list is empty --%>
  <%
      ArrayList<BookingView> bookingViewList = (ArrayList<BookingView>) request.getAttribute("bookingView");
      if (bookingViewList == null || bookingViewList.isEmpty()) {
  %>
      <p>No bookings available.</p>
  <% } else { %>
      <table>
          <thead>
              <tr>
                  <th>Unique ID</th>
                  <th>Vehicle Name</th>
                  <th>Booking Date</th>
                  <th>Return Date</th>
                  <th>Customer Name</th>
                  <th>Fare</th>
                  <th>Rent Per Day</th>
                  <th>Fine</th>
              </tr>
          </thead>
          <tbody>
              <%-- Loop through the booking list and display each booking --%>
              <%
                  for (BookingView booking : bookingViewList) {
              %>
                  <tr>
                      <td><%= booking.getUniqueId() %></td>
                      <td><%= booking.getVehicleName() %></td>
                      <td><%= booking.getBookingDate() %></td>
                      <td><%= booking.getReturnDate() %></td>
                      <td><%= booking.getCustomerName() %></td>
                      <td><%= booking.getTotalFare() %></td>
                      <td><%= booking.getRentPerDay() %></td>
                      <td><%= booking.getFine() %></td>
                  </tr>
              <% } %>
          </tbody>
      </table>
  <% } %>
</body>
</html>
