<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, java.util.HashSet, java.util.Set" %>
<%@ page import="AServices.StaffManageService" %>

<html>
<head>
    <title>Manage Staff</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .action-btn {
            background-color: red;
            color: white;
            border: none;
            padding: 5px 10px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <h2>Manage Staff</h2>
    
    <!-- Search Form -->
    <form action="ManageStaffServlet" method="get">
        <input type="text" name="searchQuery" value="<%= request.getAttribute("searchQuery") != null ? request.getAttribute("searchQuery") : "" %>" placeholder="Search staff..." />
        <button type="submit">Search</button>
    </form>
    
    <table>
        <thead>
            <tr>
                <th>Unique ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>NIC</th>
                <th>Contact Number</th>
                <th>Status</th>
                <th>Created Date</th>
                <th>Vehicle Name</th>
                <th>Total Earnings</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<StaffManageService> staffList = (List<StaffManageService>) request.getAttribute("staffList");
                Set<String> seenEmails = new HashSet<>(); // Store already displayed emails

                if (staffList != null && !staffList.isEmpty()) {
                    for (StaffManageService staff : staffList) {
                        boolean showDeleteButton = seenEmails.add(staff.getEmail()); // Returns false if email already exists
            %>
                        <tr>
                            <td><%= staff.getUniqueId() %></td>
                            <td><%= staff.getFirstName() %></td>
                            <td><%= staff.getLastName() %></td>
                            <td><%= staff.getEmail() %></td>
                            <td><%= staff.getNic() %></td>
                            <td><%= staff.getContact() %></td>
                            <td>
                                <%
                                    String status = (staff.getStatus().equals("0")) ? "Inactive" : "Active";
                                %>
                                <%= status %>
                            </td>
                            <td><%= staff.getCreatedat()%></td>
                            <td><%= staff.getVehicleName() %></td>
                            <td>Rs. <%= String.format("%.2f", staff.getTotalEarnings()) %></td>
                            <td>
                                <% if (showDeleteButton) { %>
                                    <form action="DeleteStaffServlet" method="post">
                                        <input type="hidden" name="id" value="<%= staff.getUniqueId() %>" />
                                        <button type="submit" class="action-btn">Delete Account</button>
                                    </form>
                                <% } %>
                            </td>
                        </tr>
            <%
                    }
                } else {
            %>
                    <tr>
                        <td colspan="10" style="text-align:center;">No records found</td>
                    </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
