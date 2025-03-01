<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, java.util.HashSet, java.util.Set" %>
<%@ page import="AServices.StaffManageService" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Staff</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEJYy6aA7vJzF5ZC8msE5fKhuQ1ZzbyWqZTc+zJv0z7d6RmI0dZp1uNGp5Fts" crossorigin="anonymous">

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h2 {
            text-align: center;
            color: #5196c1;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #5196c1;
            color: white;
        }
        td {
            background-color: #f9f9f9;
        }
        .action-btn {
            background-color: #0073bb;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 4px;
            cursor: pointer;
            width: 120px; /* Fixed width */
            height: 40px; /* Fixed height */
        }
        .action-btn:hover {
            background-color: #005fa3;
        }
        .back-btn {
            width: 150px;
            height: 50px;
            background-color: #0073bb;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 20px;
            display: block;
            margin-left: auto;
            margin-right: auto;
        }
        .back-btn:hover {
            background-color: #005fa3;
        }
        .search-form {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
        }
        .search-form input {
            width: 60%;
            padding: 10px;
            border: 1px solid #0073bb;
            border-radius: 4px;
            margin-right: 10px;
        }
        .search-form button {
            background-color: #0073bb;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
        }
        .search-form button:hover {
            background-color: #005fa3;
        }
    </style>
</head>
<body>
    <h2>Manage Staff</h2>
    
    <!-- Search Form -->
    <form action="ManageStaffServlet" method="get" class="search-form">
        <input type="text" name="searchQuery" value="<%= request.getAttribute("searchQuery") != null ? request.getAttribute("searchQuery") : "" %>" placeholder="Search staff..." />
        <button type="submit">Search</button>
    </form>
    
    <table class="table table-bordered">
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
                        <td colspan="10" class="text-center">No records found</td>
                    </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <!-- Back Button -->
    <button class="back-btn" onclick="back()">Back</button>

    <script>
        function back() {
            window.history.back();  // JavaScript function to navigate back
        }
    </script>
</body>
</html>
