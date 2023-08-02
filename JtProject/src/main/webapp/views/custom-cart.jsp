<%@ page import="com.jtspringproject.JtSpringProject.CustomCartItem" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BestFood</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Pacifico&display=swap" rel="stylesheet">
    <style>

        body {
            font-family: 'Roboto', sans-serif;
            background-color: #F8F9FA;
            display: flex;
            flex-direction: column;
        }

        html, body {
            height: 100%;
        }

        .container {
            flex: 1;
        }

        .bg-image-wrapper {
            background-image: url('../bg.jpg'); /* Set the background image */
            background-size: cover;
            background-repeat: no-repeat;
            background-position: center top;
        }

        .navbar {
            background-color: transparent;
            font-weight: 500;
            font-size: 17px;
        }

        .navbar-brand {
            font-family: 'Pacifico', cursive;
            font-size: 28px;
            color: #fff;
        }

        .navbar-brand:hover {
            font-family: 'Pacifico', cursive;
            font-size: 28px;
            color: #e74c3c;
        }

        .navbar-nav .nav-link {
            color: #fff;
            transition: 0.5s ease;
        }

        .navbar-nav .nav-link:hover {
            color: #e74c3c;
            font-weight: bold;
        }

        .btn-delete {
            background-color: #e74c3c;
            color: #fff;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }

        .btn-action {
            background-color: #2980b9;
            color: #fff;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            margin-right: 10px;
        }

        .btn-action:hover {
            color: #fff;
        }

        .btn-delete:hover {
            color: #fff;
        }

        .empty-cart-message {
            display: none;
            text-align: center;
            font-size: 18px;
            margin-top: 20px;
        }

        #total {
            font-size: 24px;
            text-align: center;
        }

        .footer {
            background-color: #292929;
            color: #fff;
            text-align: center;
            padding: 15px;
            font-family: 'Segoe UI', sans-serif;
            font-size: 14px;
        }

        .footer a {
            color: #fff;
            font-weight: bold;
            text-decoration: none;
            margin: 5px;
        }

        .footer a:hover {
            color: #e74c3c;
        }

    </style>
</head>
<body>
<div class="bg-image-wrapper">
    <nav class="navbar navbar-expand-lg">
        <div class="container">
            <a class="navbar-brand" href="/index">
                BestFood
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/index">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/shop">Shop</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/cart">Cart</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/profileDisplay">Profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</div>

<div class="container">
    <br><br>
    <h1>My Custom Cart</h1>

    <div class="d-flex justify-content-end mb-3">
        <form action="/clearcart" method="get">
            <button type="submit" id="clearCart" class="btn btn-action">Clear cart</button>
        </form>
        <a href="/cart" class="btn btn-action">Show cart</a>
    </div>

    <table class="table" id="cartTable">
        <thead>
        <br>
        <tr>
            <th>Product Name</th>
            <th>Quantity</th>
            <th>Total Price</th>
            <th></th>
        </tr>
        <tr>
            <%-- Get the cartItems ArrayList from the Model object --%>
            <% ArrayList<CustomCartItem> customCartItems = (ArrayList<CustomCartItem>) request.getAttribute("customCartItems"); %>

            <%-- Loop through the cart items and populate the table --%>
            <% for (CustomCartItem item : customCartItems) { %>
        </tr>
        <tr>
            <td id="1"><%= item.getProductName() %></td>
            <td><%= item.getQuantity() %></td>
            <td>$<%= item.getTotalPrice() %></td>
            <td>
                <!-- Add the "Delete" button for each product -->
                <form action="/deletecustom" method="get">
                    <input type="hidden" name="productID" value="<%= item.getProductID() %>">
                    <button type="submit" class="btn btn-delete">Remove</button>
                </form>
            </td>
        </tr>
        <% } %>
        </thead>
        <tbody id="cartItems">
        </tbody>
    </table>
    <br>
    <p class="empty-cart-message" id="emptyCartMessage">Your custom cart is currently empty, add some products to view them here. <br><br><a href="/shop">Go to shop page</a></p>
    <p id="total">Total: $${total}</p>
</div>

<br><br>

<footer class="footer">
    <p>&copy; 2023 BestFood</p>
    <div>
        <a href="/contact">Contact Us</a>
    </div>
</footer>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script>
    const cartTable = document.getElementById("cartTable");
    const clearCartButton = document.getElementById("clearCart");
    const emptyCartMessage = document.getElementById("emptyCartMessage");
    const tdFirst = document.getElementById("1");
    const total = document.getElementById('total');
    if(tdFirst == null) {
        cartTable.style.display = 'none';
        clearCartButton.style.display = 'none';
        emptyCartMessage.style.display = 'block';
        total.style.display = 'none';
        checkOut.style.display = 'none';
    }

</script>
</body>
</html>