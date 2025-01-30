<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Vehicle</title>
</head>
<body>
    <h1>Add New Vehicle</h1>
   
    
    <form action="/Mega_City/VehicleSaveServlet" method="post" enctype="multipart/form-data">
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
    
    <label for="mileage">Vehicle Color:</label>
    <input type="text" id="color" name="color" required><br><br>
    
    <label for="rent_per_day">Rent per Day:</label>
    <input type="text" id="rent_per_day" name="rent_per_day" required><br><br>
    
    <label for="rent_per_day">Vehicle Type:</label>
    <select id="rent_per_day" name=type required>
    <option value="" disabled selected>Select a vehicle type</option>
    <option value="car">Car</option>
    <option value="bike">Bike</option>
    <option value="truck">Truck</option>
    <option value="bus">Bus</option>
    </select>
    <br><br>

    
    <label for="vehicle_image">Vehicle Image:</label>
    <input type="file" id="vehicle_image" name="vehicle_image" accept="image/*" required><br><br>
    
    <button type="submit">Submit</button>
</form>


</body>
</html>
