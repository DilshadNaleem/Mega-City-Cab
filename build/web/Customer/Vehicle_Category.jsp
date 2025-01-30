<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <link rel="stylesheet" href="category_item.css">
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


.search-bar {
    display: flex;
    flex-direction: row; /* Ensure elements are in a row */
    align-items: center;
    justify-content: space-between; /* Ensure items are spaced evenly */
    width: 50%; /* Full width of container */
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
    z-index: 1;
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
    transform: scale(1.1); /* Slightly enlarges the icon on hover */
}
.main-container {
    display: flex;
    width: 100%;
    min-height: 100vh;
    background: linear-gradient(to right, #e0f7fa, #ffffff); /* Light gradient background */
}

.filters {
    width: 15%;
    padding: 20px;
    background-color: var(--light-blue-1); /* Use light blue background */
    height: 100vh;
    overflow-y: auto;
    box-shadow: 2px 0 10px rgba(0, 0, 0, 0.15); /* Deeper shadow for depth */
    border-radius: var(--border-radius); /* Rounded corners for filters */
    transition: box-shadow 0.3s ease; /* Smooth shadow transition */
}

/* Add hover effect on filters */
.filters:hover {
    box-shadow: 4px 0 15px rgba(0, 0, 0, 0.2);
}

.filter-toggle {
    display: none; /* Hide by default */
    position: absolute; /* Change to absolute */
    top: 20px; /* 20px from the top */
    left: 20px; /* 20px from the left */
    color: #fff; /* White text for visibility */
    z-index: 100000;
    border: none;
    padding: 10px;
    border-radius: 50%; /* Make it circular */
    font-size: 12px; /* Adjust font size */
    cursor: pointer;
    background-color: var(--button-color); /* Use button color variable */
    transition: background-color 0.3s ease; /* Smooth transition */
}

/* Hover effect for filter toggle */
.filter-toggle:hover {
    background-color: var(--button-hover-color); /* Darker shade on hover */
}

@media (max-width: 768px) {
    .filter-toggle {
        display: block;
        position: absolute;
        top: 20px;
        left: 20px;
        background-color: var(--medium-blue); /* Use medium blue for visibility */
        color: #fff;
        border: none;
        padding: 10px;
        border-radius: 50%;
        width: 40px;
        height: 40px;
        text-align: center;
        line-height: 40px;
        cursor: pointer;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15); /* Shadow for toggle button */
    }

    .filters {
        display: none; /* Hide filters by default on small screens */
        width: 50%; /* Set width to 50% */
        height: 150vh; /* Full height */
        position: relative; /* Fixed position */
        top: 35%; /* Align to the top */
        left: 0; /* Align to the left */
        right: 25%;
        background-color: var(--light-blue-1); /* Light blue background */
        z-index: 1000000; /* Ensure it appears above other elements */
        transition: transform 0.3s ease; /* Smooth transition */
        transform: translateX(-100%); /* Start off-screen */
    }

    .filters.show {
        display: block; /* Show filters when toggled */
        transform: translateX(0); /* Slide in from the left */
        width: 40%;
        background-color: var(--light-blue-1); /* Light blue background */
    }
}

.filters h3 {
    font-size: 20px;
    margin-bottom: 10px;
    color: var(--button-color); /* Use button color for headers */
}

.filters .filter-group h4 {
    font-size: 16px;
    margin-bottom: 5px;
    color: var(--darker-blue); /* Darker blue for subsection headers */
}

.filters .filter-group ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
}

.filters .filter-group ul li {
    margin-bottom: 5px;
}

.filters .filter-group ul li a {
    text-decoration: none;
    color: var(--font-color); /* Use font color variable */
    transition: color 0.3s ease; /* Smooth color transition */
}

/* Hover effect for filter links */
.filters .filter-group ul li a:hover {
    color: var(--button-color); /* Change color on hover */
    text-shadow: 0 0 5px rgba(0, 115, 187, 0.7); /* Text shadow effect */
}

.close-filters {
    display: none; /* Hide by default */
    position: absolute;
    top: 10px;
    right: 10px;
    background: none;
    border: none;
    font-size: 24px; /* Adjust size as needed */
    cursor: pointer;
    color: var(--font-color); /* Change color to font color */
}

@media (max-width: 768px) {
    .close-filters {
        display: block; /* Show on small screens */
        background-color: var(--light-blue-2); /* Light blue background for close button */
        padding: 5px;
        border-radius: var(--border-radius); /* Rounded corners */
    }

    .close-filters:hover {
        background-color: var(--medium-blue); /* Change color on hover */
    }
}

/* Products Section */
.products {
    width: 90%; /* Adjusted to 85% */
    padding: 60px; /* You can adjust the padding as needed */
    
    border-radius: 5px; /* Rounded corners for products section */
    
    transition: box-shadow 0.3s ease; /* Smooth shadow transition */
    
}

/* Add hover effect on products section */


/* Custom Scrollbars */
.filters::-webkit-scrollbar {
    width: 8px;
}

.filters::-webkit-scrollbar-thumb {
    background: #0073bb;
    border-radius: 10px;
}

.filters::-webkit-scrollbar-track {
    background: #f1f1f1;
}

/* Fade-in animation for sections */
@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

/* Apply animation to products */
.products {
    animation: fadeIn 0.5s ease forwards;
}

/* Responsive adjustments */
@media screen and (max-width: 768px) {
    .filters {
        width: 100%; /* Ensure full width on small screens */
        box-shadow: none; /* Remove shadow for a cleaner look */
    }

    .products {
        width: 100%; /* Products also take full width on small screens */
        margin-left: 0;
        padding: 20px; /* Reduce padding on small screens */
    }
}

@media screen and (max-width: 768px) {
  .filters {
      width: 10%; /* Take up full width on small screens */
      height: auto; /* Allow height to adjust based on content */
      box-shadow: none; /* Remove shadow for smaller devices */
  }
  
  .products {
      width: 85%; /* Products also take full width on small screens */
      margin-left: 0;
  }
}
  
.marquee-container {
    overflow: hidden;
    position: relative;
    width: 100%;
    height: 250px;
    margin-bottom: 20px;
    background: linear-gradient(to right, var(--light-blue-1), var(--light-blue-2)); /* Gradient background */
    border-radius: var(--border-radius);
}

/* Marquee Effect */
.marquee {
    display: flex;
    width: calc(100% + 300px); /* Adjust width to accommodate the images */
    padding: 20px;
    border-radius: var(--border-radius);
    animation: slide 10s linear infinite;
}

/* Adjusted Animation Keyframes */


.marquee img {
    width: 300px;
    height: 200px;
    border-radius: var(--border-radius);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Soft shadow for images */
}



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
    --button-hover-color: #005f99; /* Updated hover color */
    --border-radius: 10px; /* Increased border radius */
    --dark-silver: #8b8c8f; /* Dark silver color */
}

/* Product Grid */
#product-grid {
    display: flex; /* Use flexbox for layout */
    flex-wrap: wrap; /* Wrap to the next row when space runs out */
    gap: 60px; /* Space between product cards */
    justify-content: flex-start; /* Align items to the left */
    padding: 20px;
    width: 100%; /* Ensure it takes the full width of its container */
    background-color: var(--light-blue-1);
    border-radius: var(--border-radius);
}

/* Product Card */
.product-card {
    position: relative; /* Necessary for the ::before element to position relative to the card */
    width: 250px; /* Fixed width */
    height: 420px; /* Fixed height */
    background-color: #fff;
    border: 2px solid var(--dark-silver); /* Dark silver border */
    border-radius: var(--border-radius);
    padding: 20px;
    
    text-align: center;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Light shadow */
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    box-sizing: border-box; /* Ensure padding doesn't affect size */
    overflow: hidden; /* Ensure the ::before element doesn't overflow outside the card */
}

.product-card::before {
    content: '';
    position: absolute; /* Use absolute to stay within the card */
    top: 0;
    left: 0;
    width: 100%; /* Full width of the card */
    height: 100%; /* Full height of the card */
    background: linear-gradient(45deg, var(--medium-blue), var(--darker-blue));
    opacity: 0; /* Start with 0 opacity */
    border-radius: var(--border-radius);
    z-index: 0; /* Ensure it's behind the content of the product card */
    transition: opacity 0.4s ease;
}

.product-card:hover::before {
    opacity: 0.6; /* Enhanced shine on hover */
}

.product-card * {
    position: relative; /* Ensure all card content is above the ::before element */
    z-index: 1;
}


/* Adjusting for smaller screens */
@media screen and (max-width: 768px) {
    #product-grid {
        justify-content: center; /* Center the items on smaller screens */
    }

    .product-card {
        width: 100%; /* Make the cards full width on smaller screens */
        height: auto; /* Adjust height accordingly */
    }
}



/* Product Image */
.product-card img {
    width: 180px; /* Increased image width */
    height: 180px; /* Increased image height */
    object-fit: cover;
    margin-bottom: 15px;
    border-radius: var(--border-radius);
    transition: transform 0.3s ease;
    position: relative;
    z-index: 1;
}

.product-card:hover img {
    transform: scale(1.1); /* Enhanced image zoom on hover */
}

/* Product Info */
.product-info {
    margin-top: 10px;
    z-index: 1;
    position: relative;
}

.product-name {
    font-size: 20px; /* Increased font size */
    font-weight: bold;
    color: var(--font-color);
    margin-bottom: 10px;
}

.price {
    font-size: 24px; /* Increased font size */
    color: var(--darker-blue);
    font-weight: bold;
    margin-bottom: 10px;
}

/* Add to Cart Button */
.add-to-cart-btn {
    background-color: red;
    color: #fff;
    padding: 12px 16px; /* Increased padding */
    border: none;
    font-style:bold;
    border-radius: var(--border-radius);
    cursor: pointer;
    transition: background-color 0.3s ease, transform 0.2s ease, box-shadow 0.2s ease;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08); /* Lighter shadow */
    z-index: 1;
}


/* Updated Hover Effect */
.add-to-cart-btn:hover {
    background-color: #005f99; /* New hover color */
    transform: translateY(-3px); /* Increased lift effect */
    box-shadow: 
        0 6px 12px rgba(0, 0, 0, 0.2), /* Soft shadow on hover */
        0 0 10px rgba(255, 255, 255, 0.8), /* Shine effect on borders */
        0 0 20px rgba(255, 255, 255, 0.5); /* More visible shine effect */
}

/* Button Glow on Focus */
.add-to-cart-btn:focus {
    outline: none;
    box-shadow: 
        0 0 8px var(--button-color), 
        0 0 16px var(--button-hover-color); /* Softer glow effect */
}

@media (max-width: 768px) {
    #product-grid {
        grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
    }

    .product-card {
        padding: 15px;
    }

    .product-card img {
        width: 80px;
        height: 80px;
    }

    .product-name {
        font-size: 14px;
    }

    .price {
        font-size: 16px;
    }
}

@media (max-width: 480px) {
    #product-grid {
        grid-template-columns: 1fr;
    }

    .product-card {
        padding: 10px;
    }

    .product-card img {
        width: 70px;
        height: 70px;
    }

    .product-name {
        font-size: 13px;
    }

    .price {
        font-size: 14px;
    }
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
    color: inherit;
}
.sidebar {
    height: 100%;
    width: 0;
    position: fixed;
    top: 0;
    right: 0;
    background:  #f0f0f0;
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
    color: white; /* Highlight color */
    background-color:  #0073bb;
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
    position: absolute; /* Change to absolute positioning */
    top: 2px; /* Position it 15px from the top */
    right: 8px; /* Position it 20px from the right */
    font-size: 30px;
    cursor: pointer;
    z-index: 999;
    color: #0073bb; 
    transition: color 0.3s ease;
}


/* Hover effect for menu icon */
.menu-icon:hover {
    color: #005a9e;
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
@media (max-width: 768px) {
    .main-container {
        flex-direction: column;
    }
    
    .filters,
    .products {
        width: 100%;
    }
    
    .search-bar {
        flex-direction: column;
    }
    
    .marquee-container {
        height: 250px;
    }
    
    .product-card {
        padding: 10px;
    }
    
    .product-card img {
        width: 150px;
        height: 150px;
    }
    
    .chat-container {
        width: 100%;
        right: 0;
    }
}
@media screen and (max-width: 600px) {
    .filter-group h4 {
        font-size: 1.1em;
    }

    .product-grid {
        grid-template-columns: repeat(auto-fill, minmax(130px, 1fr));
    }

    .products h2 {
        font-size: 1.2em;
    }

    .marquee img {
        width: 100%;
        height: 200px;
    }
}

body.dark-mode {
            background-color: #333;
            color: #f4f4f9;
        }
        /* Heading Styles */
        .product-heading {
    font-family: 'Oswald', sans-serif; /* Market feel font */
    font-size: 28px; /* Larger font size */
    font-weight: bold; /* Bold font */
    color: var(--font-color); /* Use your defined font color */
    text-align: center; /* Center alignment */
    margin: 40px 0; /* Margin for spacing */
    position: relative; /* Position for pseudo-elements */
}

/* Highlighted part of the heading */
.highlight {
    color: var(--button-color); /* Different color for standout effect */
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3); /* Stronger shadow for emphasis */
    font-size: 32px; /* Slightly larger for emphasis */
}

/* Underline Effect */
.product-heading::after {
    content: ''; /* Create a pseudo-element */
    display: block;
    width: 60%; /* Width of the underline */
    height: 4px; /* Thickness of the underline */
    background: linear-gradient(90deg, var(--medium-blue), var(--darker-blue)); /* Gradient for the underline */
    margin: 10px auto; /* Center the underline */
    border-radius: 5px; /* Rounded edges */
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Subtle shadow for depth */
}

/* Text Shadow for Depth */
.product-heading {
    text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1); /* Light shadow for depth */
}

   </style>
</head>
<body>
<header>
    <div class="top-bar">
        <div class="auth-links">
            <a class="auth-link dropdown-toggle">
                <span class="account-icon"><i class="fas fa-user"></i> My Account</span>
            </a>
            <div class="dropdown-content">
               
                    <a href="#">Welcome, ></a>
                    <a href="../../../Customer/Customer_profile.php">My Profile</a>
                    <a href="../../../Customer/Customer_Orders.php">My Orders</a>
                    <a href="../../../Customer/Customer_Offfers.php">Offers</a>
                    <a href="../../../Customer/Manage_Address.php">Manage Address</a>
                    <a href="../../../Customer/FAQ.php">FAQs</a>
                    <a href="../../../Customer/Customer_Refund.php">Refund</a>
                    <a href="../../../Customer/customer_signin.html">Logout</a>
                     <a href="#">My Account</a>
                    <a href="../../../Customer/customer_signin.html">Login</a>
                
            </div>
            <a href="#" class="auth-link"><span>Daily Deals </span><i class="fas fa-tag"></i></a>
            <a href="contact.php" class="auth-link"><span>Help & Contact </span><i class="fas fa-question-circle"></i></a>
            <a href="Cart.php" class="auth-link">
                <span>My Cart </span>
                <i class="fas fa-shopping-cart"></i>
            </a>
            
        </div>
    </div>
    
    <div class="logo-search">
        <a href="#" class="logo">Hyper Zone</a>
        <div class="search-bar">
        <form method="GET" action="">
    <input type="text" name="search_query" placeholder="Search for anything" class="search-input" value="">
    <select name="category" class="category-select">
        <option value="">All Categories</option>
                <option value="<?php echo urlencode($categoryName); ?>" 
               
            </option>
       
    </select>

    <select name="subcategory" class="subcategory-select">
        <option value="">All Subcategories</option>
       
            <option value="<?php echo urlencode($subcategory['sub_id']); ?>" 
               
            </option>
      
    </select>

    <button type="submit" class="search-button">Search</button>
</form>
    </div>
    </div>
    <nav class="main-menu" id="navbar">
    <ul>
        <li><a href="about.php">About Us</a></li>
        <li><a href="#">Saved</a></li>
      
            <li class="dropdown">
                <a href="">
                    
                </a>
                <ul class="dropdown-menu">
                   
                        <li>
                            <a href="">
                               
                            </a>
                        </li>
                   
                </ul>
            </li>
      
    </ul>
</nav>


<div class="menu-icon" onclick="toggleSidebar()">?</div>
<div class="sidebar" id="sidebar">
    <nav>
        <ul>
            <a href="javascript:void(0)" class="closebtn" onclick="closeSidebar()">×</a>
            <li><a href="about.php">About Us</a></li>
            <li><a href="#">Saved</a></li>
        
                <li class="dropdown">
                    <a href="category_items.php?category=<?php echo urlencode($categoryName); ?>">
                        
                    </a>
                    <ul class="dropdown-menu">
                       
                            <li>
                                <a href="category_items.php?category=<?php echo urlencode($categoryName); ?>&subcategory=<?php echo urlencode($subcategory); ?>">
                                  
                                </a>
                            </li>
                        
                    </ul>
                </li>
           
        </ul>
    </nav>
</div>

</header>

<div class="main-container">
    <button class="filter-toggle" id="filter-toggle">
        <i class="fas fa-filter"></i>
    </button>
    
    <aside class="filters">
        <button class="close-filters" id="close-filters">&times;</button>
       
        <form method="get" >
        <h4>Filter by Subcategory</h4>
        <div class="filter-group">
            <ul>
               
                    <ul>
                      
                            <li>
                                <a href="?">
                                    
                                </a>
                            </li>
                       
                    </ul>
              
                    <p>No subcategories available for this category.</p>
               
            </ul>
        </div>

        <h3>Filter</h3>
        <div class="filter-group">
            <h4>Show Items By</h4>
            <ul>
                <li><a href="?category=<?php echo urlencode($categoryId); ?>&sort=name_asc" </a></li>
                <li><a href="?category=<?php echo urlencode($categoryId); ?>&sort=name_desc"</a></li>
                <li><a href="?category=<?php echo urlencode($categoryId); ?>&sort=price_asc" </a></li>
                <li><a href="?category=<?php echo urlencode($categoryId); ?>&sort=price_desc" </a></li>
            </ul>
        </div>
            
        </form>
    </aside>
    <section class="products">
    <h2 class="product-heading">Showing Products for <span class="highlight"></span></h2><br><br>
        
    
    
    <br>

    <main>
    <div class="product-grid" id="product-grid">
       
                    
                    <div class="product-card">
                      
                            <img src="" alt="">
                     
                            <img src="path/to/default-image.jpg" alt="Default Image">
                        
                        
                        <div class="product-info">
                            <div class="product-name">
                               
                            </div>
                            <div class="price">
                                <span class="new-price">Rs.</span></div>
                            <div class="quantity-control" style="display: none;"><br>
                                <button class="decrease">-</button>
                                <input type="number" value="1" min="1" class="quantity" />
                                <button class="increase">+</button>
                            </div>
                          
                            
                            <button class="add-to-cart-btn">Add</button>
                            <a href="#" class="add-to-cart" style="display: none;">Add to Cart</a>
                            <div class="save-item"><br>
                                <a href="#" class="save-to-wishlist"
                                   data-item-name="<?php echo htmlspecialchars($item['item_name'] ?? 'Unknown Item'); ?>"
                                   data-image-url="<?php echo htmlspecialchars($item['image'] ?? 'path/to/default-image.jpg'); ?>"
                                   data-saved="false">
                                    <i class="fa fa-heart" style="font-size: 24px; color: #ccc;"></i>
                                </a>
                            </div>
                        </div>
                    </div>
           
            <p>No items found in this category.</p>
       
    </div>
    </main>
</section>
</div>

<br>

       
<style>
    .price {
        font-size: 16px;
        display: flex;
        align-items: center; /* Aligns items vertically */
    }
    .old-price {
        text-decoration: line-through;
        color: #888;
        margin-right: 8px; /* Spacing between old and new price */
    }
    .new-price {
        color: #d9534f; /* Color for the discounted price */
        font-weight: bold;
        margin-left: 4px; /* Space before new price */
    }
    .error-message {
        color: red;
    }
</style>




</main>

    </section>
