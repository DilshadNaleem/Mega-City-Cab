<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello Servlet Example</title>
    <style>
        /* Basic styling for the page */
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
            text-align: center;
        }

        h1 {
            color: #333;
            font-size: 3em;
            margin-top: 50px;
        }

        h2 {
            color: #555;
            font-size: 2em;
            margin-top: 20px;
        }

        /* Style for the container of the content */
        .container {
            margin-top: 100px;
        }

        /* Style the message */
        .message {
            padding: 15px;
            background-color: #e0e0e0;
            border-radius: 8px;
            color: #333;
            font-size: 1.5em;
            margin-top: 20px;
        }
    </style>
</head>
<body>

    <div class="container">
        <h1>Hello</h1>
        <div class="message">
            <h2>${message}</h2>
        </div>
    </div>

</body>
</html>
