<%@ page import="java.sql.*, java.util.*, DatabaseConnection.DatabaseConnection" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vehicle List</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h1>Vehicle List</h1>
    
    <table border="1">
        <thead>
            <tr>
                <th>Vehicle Name</th>
                <th>Vehicle Year</th>
                <th>Brand Name</th>
                <th>Condition</th>
                <th>Mileage</th>
                <th>Rent per Day</th>
                <th>Vehicle Image</th>
            </tr>
        </thead>
        <tbody>
            <% 
                Connection conn = null;
                Statement stmt = null;
                ResultSet rs = null;

                try {
                    // Database connection
                    conn = DatabaseConnection.getConnection(); 

                    // SQL query to fetch vehicle data
                    String sql = "SELECT id, vehicle_name, vehicle_year, brand_name, vehicle_condition, mileage, rent_per_day, vehicle_image FROM vehicles";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);

                    // Loop through the result set and display data
                    while (rs.next()) {
                        String vehicleId = rs.getString("id");  // Assuming vehicle_id is available in the ResultSet
                        String vehicleName = rs.getString("vehicle_name");
                        String vehicleYear = rs.getString("vehicle_year");
                        String brandName = rs.getString("brand_name");
                        String condition = rs.getString("vehicle_condition");
                        String mileage = rs.getString("mileage");
                        String rentPerDay = rs.getString("rent_per_day");
                        String vehicleImage = rs.getString("vehicle_image");
            %>
            <tr>
                <td><%= vehicleName %></td>
                <td><%= vehicleYear %></td>
                <td><%= brandName %></td>
                <td><%= condition %></td>
                <td><%= mileage %></td>
                <td><%= rentPerDay %></td>
                <td>
                    <% 
                        if (vehicleImage != null && !vehicleImage.isEmpty()) { 
                            String imageUrl = request.getContextPath() + "/vehicle-image?vehicleId=" + vehicleId;
                    %>
                    <!-- The image source points to the vehicle image servlet -->
                    <img src="<%= imageUrl %>" alt="Vehicle Image" width="100" />
                    <% 
                        } else { 
                    %>
                    No Image Available
                    <% 
                        } 
                    %>
                </td>
            </tr>
            <% 
                    }
                } catch (SQLException e) {
                    out.print("<tr><td colspan='7'>Error fetching vehicle data: " + e.getMessage() + "</td></tr>");
                    e.printStackTrace();
                } finally {
                    // Close resources
                    try {
                        if (rs != null) rs.close();
                        if (stmt != null) stmt.close();
                        if (conn != null) conn.close();
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            %>
        </tbody>
    </table>

</body>
</html>
