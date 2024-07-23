import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Hoteldetails {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/HotelDB";
    private static final String JDBC_USER = "root"; // Replace with your MySQL username
    private static final String JDBC_PASSWORD = "Admin"; // Replace with your MySQL password

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // Get user input
            System.out.print("Enter availability status to search (true/false): ");
            boolean isAvailable = scanner.nextBoolean();

            // Fetch and display hotel details based on availability
            String searchHotelSQL = "SELECT * FROM Hotels WHERE is_available = ?";
            PreparedStatement searchStatement = connection.prepareStatement(searchHotelSQL);
            searchStatement.setBoolean(1, isAvailable);
            
            ResultSet resultSet = searchStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No hotels found with the specified availability status.");
            } else {
                while (resultSet.next()) {
                    System.out.println("Hotel ID: " + resultSet.getInt("hotel_id"));
                    System.out.println("Hotel Name: " + resultSet.getString("hotel_name"));
                    System.out.println("Room Number: " + resultSet.getInt("hotel_room_no"));
                    System.out.println("Address: " + resultSet.getString("address"));
                    System.out.println("Available: " + resultSet.getBoolean("is_available"));
                    System.out.println("-----------------------------------");
                }
            }

            // Close the resources
            resultSet.close();
            searchStatement.close();
            connection.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}