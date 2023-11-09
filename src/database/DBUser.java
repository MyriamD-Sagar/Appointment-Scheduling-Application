package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class queries data from the users table from the database.
 *
 * @author Myriam Drouin-Sagar
 */
public class DBUser {
    /**
     * Gets useful data from the users table and creates a list of all users objects.
     *
     * @return ObservableList of all users
     */
    public static ObservableList<User> getAllUsers(){
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        String sql = "SELECT User_ID, User_Name, Password FROM users";
        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                User user = new User(userId, userName, password);
                allUsers.add(user);

            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return allUsers;

    }

    /**
     * Searches for a user in the users tables that matches both the username and password passed as arguments.
     * <p>
     * This method will be used to allow the user into the Desktop Scheduling Application from the <code>LoginForm</code> controller class.
     *
     * @param username username of the user to find
     * @param password password of the user to find
     * @return the current user object if found, and null if no such user exists
     */
   public static User findUser(String username, String password) {
       String sql = "SELECT User_ID, User_Name, Password FROM users WHERE User_Name = ? AND Password = ?;";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String passWord = rs.getString("Password");

                User currentUser = new User(userId,userName,passWord);

                return currentUser;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
