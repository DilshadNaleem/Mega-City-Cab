<%@ page import="java.util.Map" %>
<%@ page import="AServices.CustomerService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Manage Customers</title>
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
            }
            table, th, td {
                border: 1px solid black;
            }
            th, td {
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
        </style>
    </head>
    
    <body>
        <h2>Manage Customers</h2>
        
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
                            <button type="submit" class="" > Delete Account </button>
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
