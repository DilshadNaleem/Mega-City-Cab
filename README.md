**Mega City Cab Rental**
This is a software done by M.N.M.Dilshad from ICBT batch Batch 30 BSC in SE 

**Users in this **
1)  Admin
2)  Driver
3)  Customer

Features of Admin 
   1) Manage Vehicle Types.
   2) Manage Staff.
   3) Manage Customer.
   4) View Total Sales.
   5) Proper authentication with OTP.
   6) Recover Password
   7) Manage Driver new vehicle Request.
   8) Search and Filters
   9) Logout

Features Of Customer
  1)  2 step verification with OTP.
  2)  Recover Password.
  3)  Book Vehicles with proper Validations.
  4)  Real time notifcations via email.
  5)  Cancel Bookings.
  6)  Payments such as Cash, Credit, and Debit with individual Interfaces.
  7)  Request for driver Interface.
  8)  Search in many interfaces.
  9)  Logout

  Features of Driver  
  1)  2 step verification with OTP.
  2)  Reocver Password.
  3)  Add new Vehicles.
  4)  view all vehicles
  5)  Complete trip.
  6)  Search Functions.
  7)  Logout.

Automations using Java 
1) Real time notificatios via email.
2) if customer sends a request for driver interface automated driver interface sends to the inbox

Languages Used 
1)  Java for backend
2)  JSP
3)  HTML
4)  CSS
5)  Java Script around 1%.

How to open this 
1) Download this git to your personal pc.
2) Extract It.
3) Use any IDE to open the source code.
4) Run the wamp Server to the back end databases.
5) Configure TomCat or any Glass Fish or any other server as your prefer.
6) Keep the PC online to the email uses. (to send a email we need data)
7) Extract the Database given below in your MYSQL.
8) Enjoy the Rental API Software.


Database Query 
CREATE DATABASE mega_city;

-- Use the newly created database
USE mega_city;

-- Create the 'admin' table
CREATE TABLE admin (
    admin_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    unique_id VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    contact_number VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    NIC VARCHAR(200) NOT NULL,
    status INT NOT NULL
);

-- Create the 'customers' table
CREATE TABLE customers (
    us_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    unique_id VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    contact_number INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status INT NOT NULL,
    nic VARCHAR(255) NOT NULL,
    image BLOB NOT NULL,
    customer_type VARCHAR(255) NOT NULL
);

-- Create the 'driver' table
CREATE TABLE driver (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    unique_id VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    image BLOB NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status INT NOT NULL,
    contact_number INT NOT NULL,
    nic VARCHAR(255) NOT NULL
);

-- Create the 'payments' table
CREATE TABLE payments (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    unique_id VARCHAR(255) NOT NULL,
    order_id VARCHAR(255) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    total_amount VARCHAR(255) NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    payment_status VARCHAR(255) NOT NULL
);

-- Create the 'request_driver' table
CREATE TABLE request_driver (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    unique_id VARCHAR(255) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status INT NOT NULL
)

-- Create the `vehicle_bookings` table
CREATE TABLE vehicle_bookings (
    booking_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    unique_id VARCHAR(255) NOT NULL,
    vehicle_name VARCHAR(255) NOT NULL,
    booking_date DATE NOT NULL,
    return_date DATE NOT NULL,
    returned_date DATE DEFAULT NULL,
    booking_time TIME NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    total_fare VARCHAR(255) NOT NULL,
    booking_date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    rent_per_day VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    fine FLOAT DEFAULT NULL
);

-- Create the `vehicle_types` table
CREATE TABLE vehicle_types (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    unique_id VARCHAR(255) NOT NULL,
    vehicle_cat VARCHAR(255) NOT NULL,
    vehicle_desc VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    vehicle_image VARCHAR(255) NOT NULL
);

-- Create the `vehicles` table
CREATE TABLE vehicles (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    unique_id VARCHAR(255) NOT NULL,
    vehicle_name VARCHAR(255) NOT NULL,
    vehicle_year VARCHAR(4) NOT NULL,
    brand_name VARCHAR(255) NOT NULL,
    vehicle_condition VARCHAR(50) NOT NULL,
    mileage VARCHAR(255) NOT NULL,
    rent_per_day DECIMAL(10, 2) NOT NULL,
    color VARCHAR(255) NOT NULL,
    vehicle_cat VARCHAR(255) NOT NULL,
    vehicle_image_path VARCHAR(255) NOT NULL,
    driver_email VARCHAR(255) DEFAULT NULL,
    status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

Now the system configuration is ready 
!! ................ENJOY ................. !! 

if u have any concerns let me know dilshadnaleem13@gmail.com 
---------- Thank you ---------
  
