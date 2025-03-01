<%@page language="java" contentType="text/html; charset= UTF-8" pageEncoding="UTF-8"%>
<%@page import = "java.sql.*" %> 
<%@page import = "DatabaseConnection.DatabaseConnection" %>

<% 
String vehicleId = request.getParameter("vehicleId");

String vehicleCategory = "", vehicleDescription = "", vehicleImage= "";


try 
(Connection conn = DatabaseConnection.getConnection())
{
String sql = "SELECT * FROM vehicle_types WHERE unique_id= ?";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setString(1, vehicleId);
ResultSet rs = stmt.executeQuery();

if(rs.next())
{
vehicleCategory = rs.getString("vehicle_cat");
vehicleDescription = rs.getString("vehicle_desc");
vehicleImage = rs.getString("vehicle_image");
}

DatabaseConnection.getConnection();
}

catch(SQLException ex)
{
ex.printStackTrace();
System.out.println("SQL Exception: " + ex.getMessage());
}

catch(Exception ex)
{
ex.printStackTrace();
System.out.println("Exception : " + ex.getMessage() ) ;
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Vehicle</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>

    <h1>Edit Vehicle Type</h1>

    <form action="/Mega_City/EditVehicleTypeServlet" method="post">
        <input type="hidden" name="vehicleId" value="<%= vehicleId %>">

        <label>Category Name:</label>
        <input type="text" name="vehicleCategory" value="<%= vehicleCategory %>" required>

        <label>Category Description:</label>
        <textarea name="vehicleDescription" required><%= vehicleDescription %></textarea>

        <label>Current Image:</label><br>
        <img src="http://localhost:8080/Mega_City/<%= vehicleImage %>" width="100" height="100"><br>

        <label>Upload New Image (Optional):</label>
        <input type="file" name="vehicleImage">

        <button type="submit">Update Vehicle</button>
    </form>

</body>
</html>

    