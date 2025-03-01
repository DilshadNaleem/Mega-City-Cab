<%@page import="java.util.Map" %>
<%@page import="java.util.HashMap" %>
<%@page import="Customer.CService.*" %>

<html>
<head>
    <title>Edit Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            width: 50%;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        h1 {
            text-align: center;
        }
        .error {
            color: red;
            text-align: center;
        }
        label {
            font-weight: bold;
            display: block;
            margin-top: 10px;
        }
        input, button {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
            background-color: #28a745;
            color: white;
            font-size: 16px;
            cursor: pointer;
            border: none;
            margin-top: 15px;
        }
        button:hover {
            background-color: #218838;
        }
    </style>
</head>

<body>
    <div class="container">
        <h1>Edit Profile</h1>

        <%-- Display error message --%>
        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMessage != null) { %>
            <p class="error"><%= errorMessage %></p>
        <% } %>

        <%-- Display success message as an alert --%>
        <% String successMessage = (String) request.getAttribute("successMessage"); %>
        <% if (successMessage != null) { %>
            <script>
                alert("<%= successMessage %>");
            </script>
        <% } %>

        <%
            Map<String, EditProfile> profileDetails = (HashMap<String, EditProfile>) request.getAttribute("profileDetails");
            EditProfile profile = null;
            
            if (profileDetails != null && !profileDetails.isEmpty()) {
                profile = profileDetails.values().iterator().next(); // Get the first profile object
            }

            if (profile == null) {
        %>
            <p class="error">No profile details available.</p>
        <% } else { %>

        <form action="/Mega_City/CustomerUpdateProfileServlet" method="post">
            <label>First Name:</label>
            <input type="text" name="firstName" value="<%= profile.getFirstName() %>" required>

            <label>Last Name:</label>
            <input type="text" name="lastName" value="<%= profile.getLastName() %>" required>

            <label>Email:</label>
            <input type="email" name="email" value="<%= profile.getEmail() %>" readonly>

            <label>Contact Number:</label>
            <input type="text" name="contactNumber" value="<%= profile.getContactnumber() %>" required>

            <label>NIC:</label>
            <input type="text" name="nic" value="<%= profile.getNic() %>" readonly>

            <label>Status:</label>
            <input type="text" name="status" value="<%= profile.getStatus() %>" readonly>

            <button type="submit">Update Profile</button>
        </form>

        <% } %>
    </div>
</body>
</html>
