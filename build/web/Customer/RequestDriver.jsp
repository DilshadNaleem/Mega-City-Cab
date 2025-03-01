<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import = "Customer.CService.SessionHelper" %>

<html>
<head>
    <title>Request as Driver</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEJYy6aA7vJzF5ZC8msE5fKhuQ1ZzbyWqZTc+zJv0z7d6RmI0dZp1uNGp5Fts" crossorigin="anonymous">
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
            color: #0073bb; /* Apply your primary color here */
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
            background-color: #5196c1; /* Apply secondary color here */
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .form-container button:hover {
            background-color: #0073bb; /* Darken on hover */
        }
        .back-btn {
            width: 100%;
            padding: 12px;
            background-color: #ccc;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .back-btn:hover {
            background-color: #aaa;
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
            <textarea id="description" name="description" placeholder="Request to Promote as Driver" rows="4" readonly></textarea>

            <!-- Submit Button -->
            <button type="submit">Submit Request</button>
        </form>

        <!-- Back Button -->
        <button type="button" class="back-btn" onclick="back()">Back</button>
    </div>

    <script>
        function back() {
            window.history.back();  // JavaScript function to navigate back to the previous page
        }
    </script>
</body>
</html>
