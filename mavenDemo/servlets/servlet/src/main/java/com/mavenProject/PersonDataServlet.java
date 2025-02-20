package com.mavenProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/PersonDataServlet")
public class PersonDataServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Initialize Log4j Logger
    private static final Logger logger = LogManager.getLogger(PersonDataServlet.class);

    private Connection connect() throws SQLException {
        try {
            // Explicitly load the MySQL driver
            Class.forName("org.postgresql.Driver");

            // Database connection details
            String url = "jdbc:postgresql://localhost:5432/RetailStore";
            String username = "postgres";
            String password = "root";
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            logger.error("MySQL Driver not found", e);
            throw new SQLException("org.postgresql.Driver", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Get JSON data from request
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            stringBuilder.append(line);
        }

        try {
            JSONObject json = new JSONObject(stringBuilder.toString());
            String name = json.getString("Name");
            int age = json.getInt("Age");
            String phoneNumber = json.getString("PhoneNumber");
            String emailId = json.getString("EmailId");
            String bloodGroup = json.getString("BloodGroup");

            // Database insertion
            try (Connection conn = connect()) {
                String query = "INSERT INTO PersonDataFile (Name, Age, PhoneNumber, EmailId, BloodGroup) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, name);
                    stmt.setInt(2, age);
                    stmt.setString(3, phoneNumber);
                    stmt.setString(4, emailId);
                    stmt.setString(5, bloodGroup);
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        logger.info("Data inserted successfully for name: {}", name);
                        out.println("{\"status\":\"success\", \"message\":\"Data inserted successfully\"}");
                    } else {
                        logger.error("Failed to insert data for name: {}", name);
                        out.println("{\"status\":\"error\", \"message\":\"Failed to insert data\"}");
                    }
                }
            }
        } catch (SQLException | JSONException e) {
            logger.error("Error during POST request: ", e);
            out.println("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Get the 'id' parameter from the request URL
        String id = request.getParameter("id");

        // If 'id' is provided, fetch the specific person's data
        if (id != null) {
            try (Connection conn = connect()) {
                String query = "SELECT * FROM PersonDataFile WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, Integer.parseInt(id));
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id", rs.getInt("id"));
                        jsonObject.put("Name", rs.getString("Name"));
                        jsonObject.put("Age", rs.getInt("Age"));
                        jsonObject.put("PhoneNumber", rs.getString("PhoneNumber"));
                        jsonObject.put("EmailId", rs.getString("EmailId"));
                        jsonObject.put("BloodGroup", rs.getString("BloodGroup"));
                        out.println(jsonObject.toString());
                        logger.info("Fetched data for person with ID: {}", id);
                    } else {
                        out.println("{\"status\":\"error\", \"message\":\"No person found with this ID\"}");
                        logger.warn("No person found with ID: {}", id);
                    }
                }
            } catch (SQLException | JSONException e) {
                logger.error("Error during GET request: ", e);
                out.println("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}");
            }
        } else {
            // If no 'id' is provided, fetch all person data
            try (Connection conn = connect()) {
                String query = "SELECT * FROM PersonDataFile";
                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery(query);

                    JSONArray jsonArray = new JSONArray();
                    while (rs.next()) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id", rs.getInt("id"));
                        jsonObject.put("Name", rs.getString("Name"));
                        jsonObject.put("Age", rs.getInt("Age"));
                        jsonObject.put("PhoneNumber", rs.getString("PhoneNumber"));
                        jsonObject.put("EmailId", rs.getString("EmailId"));
                        jsonObject.put("BloodGroup", rs.getString("BloodGroup"));
                        jsonArray.put(jsonObject);
                    }
                    out.println(jsonArray.toString());
                    logger.info("Fetched all person data.");
                }
            } catch (SQLException | JSONException e) {
                logger.error("Error during GET request for all data: ", e);
                out.println("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}");
            }
        }
    }
}