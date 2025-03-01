<%@ page import="java.util.Map" %>
<%@ page import="AServices.CustomerService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Manage Customers</title>
        <style>
            /* Body and Header Styling */
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
            }
            h2 {
                text-align: center;
                color: #5196c1;
            }

            /* Table Styling */
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            table, th, td {
                border: 1px solid #ddd;
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
            tr:nth-child(even) {
                background-color: #f2f2f2;
            }

            /* Action Button Styling */
            .action-btn {
                background-color: #0073bb;
                color: white;
                border: none;
                padding: 8px 15px;
                border-radius: 4px;
                cursor: pointer;
                width: 120px;
                height: 40px;
            }
            .action-btn:hover {
                background-color: #005fa3;
            }

            /* Search Form Styling */
            .search-form {
                display: flex;
                justify-content: center;
                margin-bottom: 20px;
            }
            .search-form input[type="text"] {
                width: 60%;
                padding: 10px;
                font-size: 16px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }
            .search-form button {
                padding: 10px 20px;
                background-color: #5196c1;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
            }
            .search-form button:hover {
                background-color: #005fa3;
            }
        </style>
    </head>
    
    <body>
        <h2>Manage Customers</h2>
        
        <!-- Search Form -->
        <form action="ManageCustomerServlet" method="get" class="search-form">
            <input type="text" name="searchQuery" value="<%= request.getAttribute("searchQuery") != null ? request.getAttribute("searchQuery") : "" %>" placeholder="Search customer..." />
            <button type="submit">Search</button>
        </form>
        
        <!-- Customer Table -->
        <table>
            <thead>
                <tr>
                    <th>Unique ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Contact Number</th>
                    <th>NIC</th>
                    <th>Status</th>
                    <th>Created Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                // Retrieve the 'customerservice' map from the request
                Map<String, CustomerService> customerservice = (Map<String, CustomerService>) request.getAttribute("customerservice");

                // Check if the map is not empty
                if(customerservice != null && !customerservice.isEmpty()) {
                    // Loop through each customer in the map
                    for(Map.Entry<String, CustomerService> entry : customerservice.entrySet()) {
                        CustomerService customer = entry.getValue();
                %>
                <tr>
                    <td><%= customer.getUniqueId() %></td>
                    <td><%= customer.getFirstname() %></td>
                    <td><%= customer.getLastname() %></td>
                    <td><%= customer.getEmail() %></td>
                    <td><%= customer.getContactnumber() %></td>
                    <td><%= customer.getNic() %></td>
                    <td><%= customer.getStatus() %></td>
                    <td><%= customer.getCreatedAt() %></td>
                    <td>
                        <form action ="DeleteCustomerServlet" method="post">
                            <input type="hidden" name="id" value="<%= customer.getUniqueId()%>" />
                            <button type="submit" class="action-btn">Delete Account</button>
                        </form>
                    </td>
                </tr>
                <% 
                    }
                } else {
                %>
                <tr>
                    <td colspan="9">No customers found.</td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </body>
</html>
