package demo;

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

@WebServlet("/LibraryDataServlet")
public class LibraryDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC connection details
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/LibraryDB";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "root";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookId = request.getParameter("bookId");
        String bookTitle = request.getParameter("bookTitle");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String quantity = request.getParameter("quantity");
        String price = request.getParameter("price");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Establish database connection
            Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // SQL query to insert library book data
            PreparedStatement st = con.prepareStatement("INSERT INTO LibraryBooks (bookId, bookTitle, author, genre, quantity, price) VALUES (?, ?, ?, ?, ?, ?)");

            // Setting query parameters
            st.setString(1, bookId);
            st.setString(2, bookTitle);
            st.setString(3, author);
            st.setString(4, genre);
            st.setInt(5, Integer.parseInt(quantity));
            st.setDouble(6, Double.parseDouble(price));

            // Execute update
            st.executeUpdate();

            // Close resources
            st.close();
            con.close();

            // Success response
            out.println("<html><body><div class='alert alert-success'>Book successfully added!</div></body></html>");

        } catch (Exception e) {
            // Error response
            out.println("<html><body><div class='alert alert-danger'>Error occurred: " + e.getMessage() + "</div></body></html>");
        } finally {
            out.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchBookId = request.getParameter("searchBookId");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Establish database connection
            Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // SQL query to search for a book by ID
            PreparedStatement st = con.prepareStatement("SELECT * FROM LibraryBooks WHERE bookId = ?");
            st.setString(1, searchBookId);

            ResultSet rs = st.executeQuery();

            // Start HTML response
            out.println("<html>");
            out.println("<head><title>Book Search Result</title>");
            out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css'></head>");
            out.println("<body>");

            // Check if any record is found
            if (rs.next()) {
                out.println("<div class='container mt-5'>");
                out.println("<div class='alert alert-info'>Book Found</div>");
                // Create a table to display the book details
                out.println("<table class='table table-bordered table-striped'>");
                out.println("<thead><tr><th>Book ID</th><th>Book Title</th><th>Author</th><th>Genre</th><th>Quantity</th><th>Price</th></tr></thead>");
                out.println("<tbody>");
                out.println("<tr>");
                out.println("<td>" + rs.getString("bookId") + "</td>");
                out.println("<td>" + rs.getString("bookTitle") + "</td>");
                out.println("<td>" + rs.getString("author") + "</td>");
                out.println("<td>" + rs.getString("genre") + "</td>");
                out.println("<td>" + rs.getInt("quantity") + "</td>");
                out.println("<td>" + rs.getDouble("price") + "</td>");
                out.println("</tr>");
                out.println("</tbody>");
                out.println("</table>");
                out.println("</div>");
            } else {
                out.println("<div class='container mt-5'>");
                out.println("<div class='alert alert-warning'>No book found with ID: " + searchBookId + "</div>");
                out.println("</div>");
            }

            // Close resources
            rs.close();
            st.close();
            con.close();

            out.println("</body></html>");

        } catch (Exception e) {
            // Error response
            out.println("<html><body><div class='alert alert-danger'>Error occurred: " + e.getMessage() + "</div></body></html>");
        } finally {
            out.close();
        }
    }

    
}
