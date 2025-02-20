package servletsss;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/jsonPlaceOrderServlet")
public class jsonPlaceOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerName = request.getParameter("customerName");
        String foodItem = request.getParameter("foodItem");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String vegetarian = request.getParameter("vegetarian");
        String beverages = request.getParameter("beverages");
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));

        try {
            
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/buddy_orders", "postgres", "root");
            String sql = "INSERT INTO orders (customer_name, food_item, quantity, vegetarian, beverages, total_price) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, foodItem);
            ps.setInt(3, quantity);
            ps.setString(4, vegetarian);
            ps.setString(5, beverages);
            ps.setDouble(6, totalPrice);

            int result = ps.executeUpdate();
       
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            		if (result > 0) {
            	     String jsonResponse = String.format(
                    "{\"status\":\"success\",\"message\":\"Order placed successfully\",\"customerName\":\"%s\",\"foodItem\":\"%s\",\"quantity\":%d,\"vegetarian\":\"%s\",\"beverages\":\"%s\",\"totalPrice\":%.2f}",
                    customerName, foodItem, quantity, vegetarian, beverages, totalPrice
                );
  
                out.print(jsonResponse);

                System.out.println("Order JSON: " + jsonResponse);
           //     response.sendRedirect("json-orderDetails.html?customerName=" + customerName);
            } else {
                String errorResponse = "{\"status\":\"error\",\"message\":\"Failed to place the order. Please try again.\"}";
                out.print(errorResponse);
                System.out.println("Order Failed JSON: " + errorResponse);
            }

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
