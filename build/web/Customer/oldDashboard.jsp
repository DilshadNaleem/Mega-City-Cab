<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%> <!-- -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hyper Zone</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
   
    <style>
        :root {
    --light-blue-1: rgb(214, 235, 239);
    --light-blue-2: rgb(232, 244, 246);
    --medium-blue: rgb(106, 160, 171);
    --darker-blue: rgb(90, 130, 140);
    --shine-overlay: rgba(255, 255, 255, 0.2);
    --font-color: #1e4d50;
    --dark-bg: rgb(45, 45, 45);
    --dark-text: #eee;
    --button-color: #0073bb;
    --button-hover-color: #5196c1;
    --border-radius: 5px;
}

@keyframes gradientAnimation 
    0% {
        background-position: 0% 50%;
    }
    50% {
        background-position: 100% 50%;
    }
    100% {
        background-position: 0% 50%;
    }


@keyframes logoHover 
    0% {
        transform: scale(1) rotate(0deg);
    }
    50% {
        transform: scale(1.05) rotate(5deg);
    }
    100% {
        transform: scale(1) rotate(0deg);
    }


@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(-10px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

@keyframes slideIn {
    from {
        transform: translateX(-100%);
    }
    to {
        transform: translateX(0);
    }
}

/* Reset */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Poppins', sans-serif;
    background: linear-gradient(135deg, var(--light-blue-1), var(--light-blue-2), var(--medium-blue), var(--darker-blue));
    background-size: 400% 400%;
    background-repeat: no-repeat;
    background-attachment: fixed;
    color: var(--font-color);
    animation: gradientAnimation 8s ease infinite;
    position: relative;
}

body::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: var(--shine-overlay);
    pointer-events: none;
    z-index: 1;
}

/* HEADER */
header {
    background: linear-gradient(135deg, var(--light-blue-1), var(--light-blue-2), var(--medium-blue));
    border-bottom: 1px solid #ccc;
    z-index: 10000;
}

.top-bar {
    display: flex;
    justify-content: flex-end;
    padding: 10px 20px;
    background: linear-gradient(135deg, var(--darker-blue), rgb(70, 110, 120));
    color: white;
}

.auth-links {
    display: flex;
}

.auth-links a {
    margin-right: 15px;
    color: white;
    text-decoration: none;
    font-weight: 500;
    transition: color 0.3s ease;
}

.auth-links a:hover {
    text-decoration: underline;
    color: var(--button-hover-color);
}

.auth-links i {
    display: inline-flex;
}

@media screen and (max-width: 768px) {
    .auth-links span {
        display: none;
    }

    .auth-links i {
        display: inline-block;
        font-size: 20px;
        margin-right: 10px;
    }
}

#modeToggle {
    padding: 10px 15px;
    font-size: 16px;
    background: var(--button-color);
    color: white;
    border: none;
    border-radius: var(--border-radius);
    cursor: pointer;
    transition: background-color 0.3s ease, transform 0.2s ease; /* Added transform */
}

#modeToggle:hover {
    background: var(--button-hover-color);
    transform: scale(1.05); /* Scale on hover */
}

/* LOGO AND SEARCH BAR */
.logo-search {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 20px;
    background: linear-gradient(135deg, var(--light-blue-1), var(--light-blue-2)); /* Gradient background */
    border-radius: var(--border-radius); /* Rounded corners */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* Soft shadow */
    transition: background 0.3s ease; /* Smooth background transition */
}

.logo {
    font-size: 30px;
    color: #5d7096;
    text-decoration: none;
    font-weight: bold;
    transition: transform 0.3s ease, color 0.3s ease; /* Added color transition */
}

.logo:hover {
    animation: logoHover 0.5s ease; /* Logo hover animation */
    color: var(--button-color); /* Change color on hover */
}

@keyframes logoHover 
    0% {
        transform: scale(1);
    }
    50% {
        transform: scale(1.1); /* Slight zoom effect */
    }
    100% {
        transform: scale(1); /* Return to original size */
    }


.search-bar {
    display: flex;
    flex-direction: row; /* Ensure elements are in a row */
    align-items: center;
    justify-content: space-between; /* Ensure items are spaced evenly */
    width: 60%; /* Full width of container */
    border: 2px solid #ccc;
    border-radius: 25px;
    overflow: hidden;
    background-color: transparent;
    transition: box-shadow 0.3s ease;
}

.search-bar:hover {
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.search-bar input[type="text"] {
    flex-grow: 2; /* Allow input to take up the most space */
    border: none;
    padding: 10px; /* Reduced padding to fit better */
    font-size: 16px;
    outline: none;
    width: 500px;
    background-color: rgba(255, 255, 255, 0.8);
    transition: border 0.3s ease, background-color 0.3s ease;
}

.search-bar input[type="text"]:focus {
    border: 2px solid var(--button-color);
    background-color: white;
}

.search-bar select {
    border: none;
    padding: 10px;
    background: white;
    font-size: 16px;
    margin: 0 5px;
    cursor: pointer;
    flex-grow: 0; /* Prevent select from taking too much space */
    width: auto; /* Ensure dropdown is only as wide as content */
}

.search-bar button {
    background-color: var(--button-color);
    color: white;
    border: none;
    padding: 10px 20px;
    cursor: pointer;
    transition: background-color 0.3s ease, transform 0.2s ease;
    border-radius: 0 25px 25px 0;
    white-space: nowrap; /* Prevent button text from wrapping */
    flex-shrink: 0; /* Ensure the button doesn't shrink */
}

.search-bar button:hover {
    background-color: var(--button-hover-color);
    transform: scale(1.05);
}

.search-bar button:active {
    transform: scale(0.95);
}

/* MAIN MENU */
.main-menu {
    background: linear-gradient(135deg, var(--darker-blue), rgb(50, 90, 100));
    padding: 10px;
}

.main-menu ul {
    list-style: none;
    display: flex;
}

.main-menu ul li {
    margin-right: 20px;
    position: relative;
    animation: slideIn 0.3s ease; /* Add slide-in animation */
}

.main-menu ul li a {
    text-decoration: none;
    color: white;
    font-weight: 500;
    padding: 10px 15px;
    display: block;
    transition: background-color 0.3s ease, color 0.3s ease;
}

.main-menu ul li a:hover {
    background-color: var(--button-hover-color);
    border-radius: var(--border-radius);
}

.main-menu ul li .dropdown-menu {
    display: none;
    position: absolute;
    top: 100%;
    left: 0;
    background-color: white;
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
    z-index: 1000;
    list-style: none;
    opacity: 0; /* Start hidden */
    transform: translateY(-10px); /* Slide down */
    transition: opacity 0.3s ease, transform 0.3s ease; /* Animation */
}

.main-menu ul li:hover .dropdown-menu {
    display: block;
    opacity: 1; /* Fade in */
    transform: translateY(0); /* Slide down to position */
}

.main-menu ul li .dropdown-menu li {
    position: relative;
}

.main-menu ul li .dropdown-menu li a {
    padding: 10px;
    color: #333;
    white-space: nowrap;
}

.main-menu ul li .dropdown-menu li a:hover {
    background-color: #f4f4f4;
}

       .image-slider {
            width: 100%;               /* Full width */
            height: 400px;             /* Set the height of the images */
            overflow: hidden;          /* Hide the images outside the container */
            position: relative;  
            z-index: 1;/* Relative positioning for absolute children */
        }

        /* Style for the slider images */
        .slider-image {
            width: 100%;               /* Ensure images take up full width of the container */
            height: auto;              /* Maintain aspect ratio */
            object-fit: cover;         /* Crop images to fit */
            position: absolute;        /* Stack images on top of each other */
            top: 0;                    /* Align images at the top */
            transition: left 1s ease, z-index 1s ease; /* Smooth transition for sliding and layering */
        }

        /* Initially, the images will be stacked on top of each other */
        .image-slider img:nth-child(1) {
            left: 0;
            z-index: 2; /* This image will be on top initially */
        }

        .image-slider img:nth-child(2) {
            left: 100%;
            z-index: 1; /* This image will be behind the first one initially */
        }

        /* Style for the slider buttons */
        .slider-button {
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            background-color: rgba(0, 0, 0, 0.5);
            color: white;
            font-size: 24px;
            padding: 10px;
            cursor: pointer;
            border: none;
            border-radius: 5px;
            z-index: 10;
        }

        .prev-button {
            left: 10px;
        }

        .next-button {
            right: 10px;
        }

        /* Content for quotes */
        .slide-content {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            color: white;
            text-align: center;
            z-index: 3;
            display: none; /* Hide content by default */
        }

        .slide-content.active {
            display: block; /* Only display the active slide's content */
        }

        .slide-content h2,
        .slide-content h3,
        .slide-content p {
            margin: 0;
        }

        .shop-btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #ff5722;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            margin-top: 15px;
        }


.prev:hover, .next:hover {
    background-color: #333;
}


.chat-container {
    width: 350px;
    position: fixed;
    bottom: 0;
    right: 20px;
    background-color: #fff;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
    border-radius: 10px 10px 0 0;
    display: none;
    flex-direction: column;
}

.chat-header {
    background-color: #007BFF;
    color: #fff;
    padding: 15px;
    border-radius: 10px 10px 0 0;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.chat-header h3 {
    margin: 0;
    font-size: 16px;
}

.chat-header button {
    background: none;
    border: none;
    color: #fff;
    font-size: 20px;
    cursor: pointer;
}

.chat-body {
    padding: 15px;
    height: 250px;
    overflow-y: auto;
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.message {
    background-color: #f1f1f1;
    padding: 10px;
    border-radius: 5px;
    max-width: 80%;
}

.bot-message {
    align-self: flex-start;
    background-color: #e9ecef;
}

.chat-footer {
    padding: 10px;
    display: flex;
    gap: 10px;
    border-top: 1px solid #ddd;
}

.chat-footer input[type="text"] {
    flex: 1;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ddd;
}

.chat-footer button {
    background-color: #007BFF;
    color: #fff;
    border: none;
    padding: 10px 15px;
    border-radius: 5px;
    cursor: pointer;
    font-size: 18px;
}

#open-chat {
    position: fixed;
    bottom: 20px;
    right: 20px;
    background-color: #007BFF;
    color: #fff;
    border: none;
    padding: 15px;
    border-radius: 50px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
    cursor: pointer;
    font-size: 18px;
    display: flex;
    align-items: center;
    gap: 10px;
}

#open-chat:hover {
    background-color: #0056b3;
}

#chat-container.show {
    display: flex;
    animation: slideUp 0.4s ease-out;
}

@keyframes slideUp {
    from {
        transform: translateY(100%);
    }
    to {
        transform: translateY(0);
    }
}
.category-section {
    text-align: center;
    padding: 40px 20px; /* Spacing around the section */
    width: 100%;
    background-color: rgb(232, 244, 246); /* Light blue background */
    border-radius: 15px; /* Rounded corners for modern look */
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); /* Shadow for depth */
    transition: background-color 0.3s ease; /* Smooth transition for hover */
}
.category-item img:hover {
    transform: scale(1.15) rotate(5deg); /* Slight rotation and zoom on hover */
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); /* Deepened shadow on hover */
}

.category-section h2 {
    font-size: 2.5rem; /* Larger font for the title */
    color: rgb(106, 160, 171); /* Medium blue for title */
    margin-bottom: 30px; /* Spacing below the title */
    font-family: 'Poppins', sans-serif; /* Consistent font family */
    text-shadow: 1px 1px 5px rgba(0, 0, 0, 0.1); /* Subtle shadow for the title */
}

.categories {
    display: flex;
    justify-content: space-around; /* Space items evenly */
    flex-wrap: wrap; /* Allow wrapping for smaller screens */
    gap: 30px; /* Spacing between items */
    padding: 0 20px; /* Side padding */
}

.category-item {
    text-decoration: none; /* Remove underline */
    color: rgb(90, 130, 140); /* Darker shade for text */
    font-family: 'Poppins', sans-serif; /* Consistent font family */
    width: 150px; /* Item width */
    height: 150px;
    border-radius: 75px;
    text-align: center; /* Center text */
    transition: transform 0.3s ease, box-shadow 0.3s ease; /* Transition effects */
}

.category-item:hover {
    transform: scale(1.05); /* Slight zoom effect on hover */
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); /* Enhanced shadow on hover */
}

.category-item img {
    width: 150px; /* Image width */
    height: 150px; /* Set height to create an oval shape */
    border-radius: 75px; /* Half of the height for a perfect oval */
    object-fit: cover; /* Ensure images cover the area without distortion */
    margin-bottom: 15px; /* Spacing below the image */
    transition: transform 0.3s ease, box-shadow 0.3s ease; /* Transition for images */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Initial shadow for images */
}

.category-item img:hover {
    transform: scale(1.15) rotate(5deg); /* Slight rotation and zoom on hover */
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); /* Deepened shadow on hover */
}

.category-item p {
    font-size: 16px; /* Font size for the description */
    color: rgb(90, 130, 140); /* Text color */
    font-weight: 500; /* Bold font for visibility */
    transition: color 0.3s ease; /* Transition for text color */
}

.category-item:hover p {
    color: rgb(106, 160, 171); /* Change text color on hover to medium blue */
    text-shadow: 1px 1px 3px rgba(0, 115, 187, 0.3); /* Subtle text shadow effect */
}
.footer {
    background-color: #f8f8f8;
    padding: 20px;
    font-family: Arial, sans-serif;
    color: #333;
}
body {
    background-color: white;
    color: black;
    font-family: Arial, sans-serif;
}

header {
    text-align: center;
    margin: 20px;
}

button {
    padding: 10px 20px;
    border: none;
    cursor: pointer;
    background-color: #4CAF50;
    color: white;
    font-size: 16px;
    border-radius: 5px;
}

button:hover {
    background-color: #45a049;
}

/* Dark mode styles */

body.dark-mode {
    background-color: #333;
    color: white;

}

button.dark-mode {
    background-color: #555;
}

button i {
    margin-right: 8px; /* Add some space between the icon and the text */
    font-size: 16px;   /* Adjust the size of the icon */
}


/* qr code*/
/* QR Code Categories Section */
.qr-code-categories {
    width: 250px;
    position: fixed;
    left: 0;
    top: 0;
    height: 100%;
    background-color: #f8f8f8;
    padding: 20px;
    overflow-y: auto;
    box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
   
}

.qr-code-categories h2 {
    font-size: 20px;
    margin-bottom: 20px;
    color: #333;
}

.qr-code-category {
    margin-bottom: 15px;
    text-align: left;
}

.qr-code-category img {
    width: 50px;
    height: 50px;
    border-radius: 5px;
    margin-right: 10px;
    vertical-align: middle;
    
}

.qr-code-category p {
    display: inline-block;
    font-size: 14px;
    color: #555;
    margin: 0;
    vertical-align: middle;
}

.qr-code-category:hover {
    background-color: #e9ecef;
    border-radius: 5px;
    padding: 5px;
}

.qr-code-category a {
    text-decoration: none;
    color:�inherit;
}
@media (max-width: 480px) {
    .header .top-bar {
        font-size: 14px;
    }

    .header .search-bar input[type="text"],
    .header .search-bar select,
    .header .search-bar button {
        font-size: 14px;
    }

    .slider-container {
        height: 200px; /* Adjusted for mobile */
    }

    .slider-content h2,
    .slider-content p {
        font-size: 1rem; /* Adjusted for mobile */
    }

    .chat-footer {
        flex-direction: column;
    }

    .chat-footer input[type="text"] {
        margin-bottom: 10px;
    }
}
@media (max-width: 768px) {
    .qr-code-categories {
        width: 200px; /* Adjusted width for smaller screens */
        padding: 15px;
    }

    .qr-code-categories h2 {
        font-size: 18px;
    }

    .qr-code-category img {
        width: 40px; /* Smaller image size */
        height: 40px;
    }

    .qr-code-category p {
        font-size: 12px; /* Smaller font size */
    }
}

@media (max-width: 480px) {
    .qr-code-categories {
        width: 100%;
        max-width: 300px;
        height: auto; /* Adjust height for better fit */
        position: relative; /* Make it flow in the document flow */
        box-shadow: none; /* Remove shadow on very small screens */
        transform: translateX(-100%); /* Hide off-screen by default */
    }

    .qr-code-categories.show {
        transform: translateX(0); /* Show when toggled */
    }

    .qr-code-categories h2 {
        font-size: 16px;
    }

    .qr-code-category img {
        width: 35px; /* Even smaller image size */
        height: 35px;
    }

    .qr-code-category p {
        font-size: 10px; /* Even smaller font size */
    }
}
@media (max-width: 450px) {
    .search-bar {
        flex-direction: column; /* Stack input, select, and button vertically */
    }

    .search-bar {
        flex-direction: column;
    }
}

@media (max-width: 768px) {
    .search-bar {
        width: 100%;
    }

    .search-bar {
        flex-direction: column;/* Adjust font size for medium screens */
    }

    .search-bar button {
        padding: 10px 20px; /* Increased padding for medium screens */
        font-size: 16px; /* Adjust font size for medium screens */
    }
}
/* Sidebar styling */
.sidebar {
    height: 100%;
    width: 0;
    position: fixed;
    top: 0;
    right: 0;
    background: #f0f0f0;
    overflow-x: hidden;
    transition: width 0.4s ease, background 0.4s ease;
    padding-top: 60px;
    z-index: 1000;
    display: none; /* Hide by default */
    border-left: 1px solid #e0e0e0; /* Light border for a subtle edge */
}

/* Remove bullet points from sidebar list */
.sidebar ul {
    list-style-type: none; /* Remove bullet points */
    padding: 0; /* Remove default padding */
    margin: 0; /* Remove default margin */
}

/* Sidebar links */
.sidebar a {
    padding: 16px 24px;
    text-decoration: none;
    font-size: 16px;
    color: #333;
    display: block;
    transition: background-color 0.3s ease, color 0.3s ease, transform 0.3s ease;
    border-radius: 6px; /* Slightly rounded corners */
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; /* Modern font */
}

/* Hover effect for sidebar links */
.sidebar a:hover {
    background-color: #0073bb; /* Primary highlight color */
    color: #ffffff; /* White text on hover */
    transform: translateX(10px); /* Slide effect on hover */
}

/* Close button */
.sidebar .closebtn {
    position: absolute;
    top: 20px;
    right: 20px;
    font-size: 20px;
    color: #333;
    cursor: pointer;
    transition: color 0.3s ease;
}

/* Hover effect for close button */
.sidebar .closebtn:hover {
    background-color: #0073bb; /* Primary highlight color */
    color: #ffffff; /* White text on hover */
     /* Highlight color */
}

/* When the sidebar is open */
.sidebar.open {
    width: 280px;
    display: block; /* Show the sidebar when open */
}

/* When the sidebar is closed */
.sidebar.closed {
    width: 0;
    display: none; /* Hide the sidebar when closed */
}

/* Menu icon */
.menu-icon {
    display: none; /* Hide by default */
    position: fixed;
    top: 15px;
    right: 20px;
    font-size: 30px;
    cursor: pointer;
    z-index: 999;
    color: #0073bb; /* Main menu color */
    transition: color 0.3s ease;
}

/* Hover effect for menu icon */
.menu-icon:hover {
    color: #005a9e; /* Darker color on hover */
}

/* Media query for small screens */
@media screen and (max-width: 768px) {
    /* Show the menu icon */
    .menu-icon {
        display: block;
    }

    /* Adjust the sidebar width for smaller screens */
    .sidebar.open {
        width: 250px;
    }
}

/* Dropdown menu styling */
.sidebar .dropdown {
    position: relative;
}

.sidebar .dropdown-menu {
    display: none; /* Hide dropdown menu by default */
    list-style-type: none; /* Remove bullet points */
    padding: 0;
    margin: 0;
    position: absolute;
    left: 0;
    top: 100%;
    background: #ffffff; /* White background */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Subtle shadow for depth */
    border-radius: 6px; /* Rounded corners */
    z-index: 1000;
    transition: opacity 0.3s ease, transform 0.3s ease; /* Smooth transition */
}

/* Dropdown menu items styling */
.sidebar .dropdown-menu li {
    padding: 0;
}

.sidebar .dropdown-menu a {
    padding: 14px 20px;
    text-decoration: none;
    font-size: 16px;
    color: #333;
    display: block;
    transition: background-color 0.3s ease, color 0.3s ease;
    border-radius: 6px; /* Rounded corners for dropdown items */
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; /* Modern font */
}

/* Hover effect for dropdown menu items */
.sidebar .dropdown-menu a:hover {
    background-color: #0073bb; /* Highlight color on hover */
    color: #ffffff; /* White text on hover */
    transform: translateX(10px); /* Slide effect on hover */
}

/* Show dropdown menu on parent hover */
.sidebar .dropdown:hover .dropdown-menu {
    display: block;
    opacity: 1; /* Ensure it's visible */
    transform: translateY(10px); /* Slight downward transition */
}
@media (max-width: 768px) {
    .main-menu {
        display: none;
    }
}

/* Show the menu on larger screens */
@media (min-width: 769px) {
    .main-menu {
        display: flex;
    }
}
@media (max-width: 480px) {
    .top-bar {
        font-size: 12px; /* Smaller font size for top bar */
    }

    .slider-container {
        height: 200px; /* Adjust slider height */
    }

    .slide-content h2 {
        font-size: 1.2rem; /* Smaller text */
    }

    .slide-content p {
        font-size: 0.9rem; /* Smaller paragraph */
    }

    .chat-footer {
        flex-direction: column; /* Stack input and button vertically */
    }

    .chat-footer input[type="text"] {
        margin-bottom: 10px;
    }

    /* Ensure buttons are still touch-friendly */
    button {
        font-size: 14px;
        padding: 8px 15px;
    }

    /* Hide QR Code categories on very small screens */
    .qr-code-categories {
        display: none;
    }
}

/* Responsive Adjustments for Tablet screens (up to 768px) */
@media (max-width: 768px) {
    .qr-code-categories {
        width: 200px;
        padding: 15px;
    }

    .qr-code-categories h2 {
        font-size: 16px;
    }

    .qr-code-category img {
        width: 40px;
        height: 40px;
    }

    .category-section {
        flex-direction: column; /* Stack categories */
    }

    .categories {
        flex-direction: column;
        align-items: center;
    }

   
}
.auth-links {
    position: relative; /* Position relative for absolute positioning of dropdown */
}

.dropdown-toggle {
    cursor: pointer;
   /* Added padding for a more clickable area */
    display: flex; /* Flexbox for alignment */
    align-items: center; /* Center align items */
    transition: color 0.3s ease; /* Smooth transition for text color */
}

.dropdown-content {
    margin-top: 40px;
    display: none; /* Initially hidden */
    position: absolute;
    background-color: var(--light-blue-1); /* Light blue background */
    min-width: 200px; /* Slightly wider for better readability */
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); /* Softer shadow for a modern look */
    border-radius: 8px; /* Rounded corners */
    z-index: 9999;
    opacity: 0; /* Start with hidden opacity */
    transition: opacity 0.3s ease, transform 0.3s ease; /* Smooth transition for visibility */
    transform: translateY(10px); /* Slightly translate down for effect */
}

.dropdown-toggle:hover + .dropdown-content,
.dropdown-content:hover {
    display: block; /* Show the dropdown */
    opacity: 1; /* Make it visible */
    transform: translateY(0); /* Reset translation */
}

.dropdown-content a {
    color: #333; /* Darker text color for better readability */
    padding: 10px 14px;
    text-decoration: none;
    display: block;
    transition: background-color 0.3s ease, color 0.3s ease; /* Smooth transition for background and text color */
    margin-left: 10px;
}

.dropdown-content a:hover {
    background-color: var(--button-hover-color); /* Use a variable for hover background */
    color: white; /* Change text color on hover */
}

/* Responsive Design for Mobile Devices */
@media (max-width: 768px) {
    .auth-links {
        width: 100%;
        text-align: center;
    }

    .dropdown-content {
        position: static;
        width: 100%;
        box-shadow: none;
    }

    .auth-link {
        display: block;
        padding: 10px;
    }
}
.account-icon {
    display: inline-flex; /* Aligns icon and text in a line */
    align-items: center; /* Centers items vertically */
    padding: 10px 15px; /* Adds padding around the element */
    background-color: var(--light-blue-1); /* Light blue background */
    color: var(--font-color); /* Font color */
    border-radius: var(--border-radius); /* Rounded corners */
    font-size: 16px; /* Font size */
    transition: background-color 0.3s ease, color 0.3s ease; /* Smooth transitions */
    cursor: pointer; /* Changes cursor on hover */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Adds a subtle shadow */
}

.account-icon:hover {
    background-color: var(--button-color); /* Change background on hover */
    color: white; /* Change text color on hover */
}

.account-icon i {
    margin-right: 8px; /* Space between icon and text */
    font-size: 20px; /* Icon size */
    transition: transform 0.3s ease; /* Smooth transition for scaling */
}

.account-icon:hover i {
    transform: scale(1.1); /* Slightly enlarges the icon�on�hover�*/
}

    </style>
</head>
<body>
    <header>
        <div class="top-bar">
            <div class="auth-links">
                <a class="auth-link dropdown-toggle">
                    <span class="account-icon"><i class="fas fa-user"></i> My Account</span>
                    <div class="dropdown-content">
                        <a href="#">Welcome, User</a>
                        <a href="/Mega_City/CustomerEditProfileServlet">Profile</a>
                        <a href="/Mega_City/HistoryServlet">My Orders</a>
                        <a href="/Mega_City/PaymentServlet" > Payments </a>
                        <a href="/Mega_City/Customer/Offers.jsp">Offers</a>
                        <a href="/Mega_City/Customer/FAQs.jsp">FAQs</a>
                        <a href="/Mega_City/Customer/RequestDriver.jsp"> Request as Driver </a>
                        <a href="/Mega_City/Customer/Signin.html">Logout</a>
                    </div>
                </a>
                <a href="#" class="auth-link"><span>Daily Deals</span><i class="fas fa-tag"></i></a>
                <a href="contact.php" class="auth-link"><span>Help & Contact</span><i class="fas fa-question-circle"></i></a>
            </div>
        </div>

        <div class="logo-search">
            <a href="#" class="logo">Mega City Cab</a>
                    </div>

        <nav class="main-menu" id="navbar">
            <ul>
                <li><a href="about.php">About Us</a></li>
             
                <!-- Categories should be dynamically generated here -->
            </ul>
        </nav>

        <div class="menu-icon" onclick="toggleSidebar()">?</div>
        <div class="sidebar" id="sidebar">
            <nav>
                <ul>
                    <a href="javascript:void(0)" class="closebtn" onclick="closeSidebar()">�</a>
                    <li><a href="about.php">About Us</a></li>
                    <li><a href="#">Saved</a></li>
                    <!-- Categories should be dynamically generated here -->
                </ul>
            </nav>
        </div>
    </header>

    
    

 <!-- Image slider container -->
    <div class="image-slider">
        <img src="/Mega_City/Customer/slide1.jpeg" alt="Image 1" class="slider-image">
        <div class="slide-content" id="slide1">
            <h2>Slide 1 Title</h2>
            <h3>Slide 1 Subtitle</h3>
            <p>Slide 1 description goes here.</p>
            <a href="#" class="shop-btn">Shop Now</a>
        </div>

        <img src="/Mega_City/Customer/slider2.jpeg" alt="Image 2" class="slider-image">
        <div class="slide-content" id="slide2">
            <h2>Slide 2 Title</h2>
            <h3>Slide 2 Subtitle</h3>
            <p>Slide 2 description goes here.</p>
            <a href="#" class="shop-btn">Shop Now</a>
        </div>

        <!-- Slider buttons -->
        <button class="slider-button prev-button">&#10094;</button>
        <button class="slider-button next-button">&#10095;</button>
    </div>





    


<div class="category-section">
    <h2>Explore Popular Categories</h2>
    <div class="categories">
        <% 
        // Get the vehicles list from the request attribute
        List<Map<String, String>> vehicles = (List<Map<String, String>>) request.getAttribute("vehicles");

        // Check if vehicles list is null or empty
        if (vehicles == null || vehicles.isEmpty()) {
        %>
            <p>No vehicles found in the database.</p>
        <% 
        } else {
            // Loop through each vehicle and display its information
            for (Map<String, String> vehicle : vehicles) {
        %>
            <!-- The link redirects to Vehicle_Category.jsp with the vehicle name as a query parameter -->
            <a href="/Mega_City/ProductServlet?vehicleType=<%= URLEncoder.encode(vehicle.get("vehicleName"), "UTF-8") %>" class="category-item">
                <div>
                    <h3><%= vehicle.get("vehicleName") %></h3>
                    <img src="http://localhost:8080/Mega_City/<%= vehicle.get("imagePath") %>" alt="<%= vehicle.get("vehicleName") %>">
                </div>
            </a>
        <% 
            } // End of for loop
        }
        %>
    </div>
</div>


    
</body>


<script>
    let currentIndex = 0;
    const images = document.querySelectorAll('.slider-image');
    const slideContents = document.querySelectorAll('.slide-content');
    const prevButton = document.querySelector('.prev-button');
    const nextButton = document.querySelector('.next-button');

    function changeSlide(direction) {
        // Hide the current image by moving it out of view
        images[currentIndex].style.left = direction === 'next' ? '-100%' : '100%';
        images[currentIndex].style.zIndex = 1;  // Move the current image behind
        slideContents[currentIndex].classList.remove('active'); // Hide the current content

        // Move the next image into view
        const nextIndex = (currentIndex + (direction === 'next' ? 1 : -1) + images.length) % images.length;
        images[nextIndex].style.left = '0';
        images[nextIndex].style.zIndex = 2;  // Bring the next image to the front
        slideContents[nextIndex].classList.add('active'); // Show the next content

        // Update current index
        currentIndex = nextIndex;
    }

    // Button click listeners
    prevButton.addEventListener('click', () => changeSlide('prev'));
    nextButton.addEventListener('click', () => changeSlide('next'));

    // Initially show the first image and content
    images[currentIndex].style.left = '0';
    images[currentIndex].style.zIndex = 2;
    slideContents[currentIndex].classList.add('active');

    // Auto change slide every 5 seconds
    setInterval(() => changeSlide('next'), 5000);
</script>



</html>
