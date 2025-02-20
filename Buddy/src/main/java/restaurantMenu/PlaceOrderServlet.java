package restaurantMenu;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/PlaceOrderServlet")
public class PlaceOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerName = request.getParameter("customerName");
        String foodItem = request.getParameter("foodItem");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double price = getPrice(foodItem);
        double totalPrice = price * quantity;

        // Save the order to the database and retrieve the generated order ID
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/buddydb", "postgres", "root")) {
            String query = "INSERT INTO orders (customer_name, food_item, quantity, total_price) VALUES (?, ?, ?, ?) RETURNING order_id";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, customerName);
                stmt.setString(2, foodItem);
                stmt.setInt(3, quantity);
                stmt.setDouble(4, totalPrice);
                
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    request.setAttribute("orderId", orderId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Show the confirmation page with the order ID
        RequestDispatcher dispatcher = request.getRequestDispatcher("/orderConfirmation.jsp");
        dispatcher.forward(request, response);
    }

    private double getPrice(String foodItem) {
        switch (foodItem) {
            case "Pizza":
                return 12.99;
            case "Burger":
                return 8.99;
            case "Pasta":
                return 10.99;
            case "Sushi":
                return 15.99;
            default:
                return 0;
        }
    }
}
