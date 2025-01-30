<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking History</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f4f4;
            font-family: Arial, sans-serif;
        }

        header {
            background-color: #333;
            color: white;
            padding: 20px;
            text-align: center;
        }

        header .logo img {
            width: 50px;
            height: auto;
            margin-bottom: 10px;
        }

        header h1 {
            font-size: 24px;
        }

        .booking-history {
            margin-top: 20px;
        }

        .booking-history table {
            background-color: white;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            padding: 20px;
        }

        .booking-history th {
            background-color: #333;
            color: white;
        }

        .booking-history tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .booking-history tr:hover {
            background-color: #f1f1f1;
        }
    </style>
</head>
<body>

    <header>
        <div class="logo">
            <img src="logo.png" alt="Logo">
        </div>
        <h1>Your Booking History</h1>
    </header>

    <div class="container booking-history">
        <table class="table table-striped table-bordered">
            <thead>
                <tr>
                    <th>Booking ID</th>
                    <th>Customer Name</th>
                    <th>Booking Date</th>
                    <th>Reservation Time</th>
                    <th>Address</th>
                </tr>
            </thead>
            <tbody>
                <!-- Sample booking history data -->
                <tr>
                    <td>#101</td>
                    <td>John Doe</td>
                    <td>2025-01-20</td>
                    <td>9:00 AM - 12:00 PM</td>
                    <td>123 Main St, City</td>
                </tr>
                <tr>
                    <td>#102</td>
                    <td>Jane Smith</td>
                    <td>2025-01-21</td>
                    <td>12:00 PM - 4:00 PM</td>
                    <td>456 Elm St, City</td>
                </tr>
                <tr>
                    <td>#103</td>
                    <td>Michael Brown</td>
                    <td>2025-01-22</td>
                    <td>4:00 PM - 8:00 PM</td>
                    <td>789 Oak St, City</td>
                </tr>
            </tbody>
        </table>
    </div>

    <!-- Bootstrap JS, Popper.js, and jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
