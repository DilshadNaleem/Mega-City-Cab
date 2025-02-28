<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import = "Customer.SessionHelper" %>
<html>
<head>
    <title>Request as Driver</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .form-container {
            max-width: 500px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            background-color: #f9f9f9;
        }
        .form-container h2 {
            text-align: center;
        }
        .form-container label {
            font-weight: bold;
        }
        .form-container input[type="text"], .form-container textarea {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border-radius: 4px;
            border: 1px solid #ccc;
        }
        .form-container button {
            width: 100%;
            padding: 12px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .form-container button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Request as Driver</h2>

        <form method="POST" action="/Mega_City/RequestDriverServlet">
           

            <!-- Unique ID Field -->
            <label for="unique_id">Unique ID:</label>
            <input type="hidden" id="unique_id" name="unique_id" required />

            <!-- Description Field -->
            <label for="description">Description:</label>
            <textarea id="description" name="description" placeholder = "Request to Promot as Driver " rows="4" readonly></textarea>

            <!-- Submit Button -->
            <button type="submit">Submit Request</button>
        </form>
    </div>
</body>
</html>
