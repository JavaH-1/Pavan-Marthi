package servletsss;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/jsonGetOrderByNameServlet")
public class jsonGetOrderByNameServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerName = request.getParameter("customerName");

        try {
           
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/buddy_orders", "postgres", "root");

            String sql = "SELECT * FROM orders WHERE customer_name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customerName);

            ResultSet rs = ps.executeQuery();

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();

          
            StringBuilder jsonResponse = new StringBuilder("[");
            boolean hasOrders = false;

            while (rs.next()) {
                hasOrders = true;
                String foodItem = rs.getString("food_item");
                int quantity = rs.getInt("quantity");
                String vegetarian = rs.getString("vegetarian");
                String beverages = rs.getString("beverages");
                double totalPrice = rs.getDouble("total_price");

                jsonResponse.append(String.format(
                    "{\"foodItem\":\"%s\",\"quantity\":%d,\"vegetarian\":\"%s\",\"beverages\":\"%s\",\"totalPrice\":%.2f},",
                    foodItem, quantity, vegetarian, beverages, totalPrice
                ));
            }

            if (hasOrders) {
                jsonResponse.deleteCharAt(jsonResponse.length() - 1); 
            }
            jsonResponse.append("]");

            if (!hasOrders) {
                jsonResponse = new StringBuilder("{\"message\":\"No orders found for " + customerName + "\"}");
            }

            out.print(jsonResponse);
            System.out.println("Order Details JSON: " + jsonResponse);

            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().println("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
            System.err.println("Error JSON: {\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
