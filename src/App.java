import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) throws Exception {
           

     
        try {
            java.sql.Connection connection; 
            connection = DriverManager.getConnection("jdbc:sqlite:restaurant.db");
            System.out.println("Connected to SQLite database.");

            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS User (id INTEGER PRIMARY KEY, name TEXT)");
            statement.execute("CREATE TABLE IF NOT EXISTS Restaurant (id INTEGER PRIMARY KEY, name TEXT, rating INTEGER)");
            System.out.println("Tables created successfully.");
       
            User user = new User("John", 1);
            PreparedStatement insertUserStatement = connection.prepareStatement("INSERT INTO User (id, name) VALUES (?, ?)");
            insertUserStatement.setInt(1, user.getId());
            insertUserStatement.setString(2, user.getName());
            insertUserStatement.executeUpdate();
            System.out.println("User inserted successfully.");

            PreparedStatement selectUserStatement = connection.prepareStatement("SELECT * FROM User WHERE id = ?");
            selectUserStatement.setInt(1, user.getId());
            ResultSet resultSet = selectUserStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("User ID: " + resultSet.getInt("id") + ", Name: " + resultSet.getString("name"));
            }

            PreparedStatement updateUserStatement = connection.prepareStatement("UPDATE User SET name = ? WHERE id = ?");
            updateUserStatement.setString(1, "Mike");
            updateUserStatement.setInt(2, user.getId());
            updateUserStatement.executeUpdate();
            System.out.println("User updated successfully.");

            PreparedStatement deleteUserStatement = connection.prepareStatement("DELETE FROM User WHERE id = ?");
            deleteUserStatement.setInt(1, user.getId());
            deleteUserStatement.executeUpdate();
            System.out.println("User deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    System.out.println("Connection closed.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}
    }

