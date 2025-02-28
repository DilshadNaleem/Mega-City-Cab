<%@page import = "Driver.DatabaseConnection" %>
<%@page import = "java.sql.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Vehicle</title>
</head>
<body>
    <h1>Add New Vehicle</h1>
   
    <form action="/Mega_City/VehicleSaveServlet" method="post" >
        <label for="vehicle_name">Vehicle Name:</label>
        <input type="text" id="vehicle_name" name="vehicle_name" required><br><br>
        
        <label for="vehicle_year">Vehicle Year:</label>
        <input type="text" id="vehicle_year" name="vehicle_year" required><br><br>

        <label for="brand_name">Brand Name:</label>
        <input type="text" id="brand_name" name="brand_name" required><br><br>

        <label for="condition">Condition:</label>
        <input type="text" id="condition" name="condition" required><br><br>

        <label for="mileage">Mileage:</label>
        <input type="text" id="mileage" name="mileage" required><br><br>
        
        <label for="color">Color:</label>
        <input type="text" id="color" name="color" required><br><br>
        
        <label for="type">Vehicle Type:</label>
        <select id="type" name="type" required>
            <% 
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conn = DatabaseConnection.getConnection(); // Get the database connection
                String query = "SELECT vehicle_cat FROM vehicle_types"; // Query to fetch vehicle categories
                stmt = conn.prepareStatement(query);
                rs = stmt.executeQuery();
                
                while (rs.next()) {
                    String categoryName = rs.getString("vehicle_cat"); 
            %>
                    <option value="<%= categoryName %>"><%= categoryName %></option>
            <% 
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException ignore) {}
            }
            %>
        </select>
        <br><br>
        
        <label for="rent_per_day">Rent per Day:</label>
        <input type="text" id="rent_per_day" name="rent_per_day" required><br><br>

        <label for="vehicle_image">Vehicle Image:</label>
        <input type="file" id="vehicle_image" name="vehicle_image" accept="image/*" required><br><br>

        <button type="submit">Submit</button>
    </form>
        
        <a href="/Mega_City/VehicleRetrieveServlet" >View Added Vehicles</a>
</body>
</html>
