package CountriesStates;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getCountries")
public class CountryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
        	  Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pavan", "postgres", "root");
            String sql = "SELECT id, name FROM countries";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            out.println("<select id='countryDropdown' onchange='fetchStates()'>");
            out.println("<option value=''>Select Country</option>");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                out.println("<option value='" + id + "'>" + name + "</option>");
            }

            out.println("</select>");

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error fetching countries.</p>");
        }
    }
}
