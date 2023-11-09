package model;

/**
 * This class shapes the data for the User.
 *
 * @author Myriam Drouin-Sagar
 */
public class User {
    private int userId; //PK
    private String userName;
    private String password;

    /**
     * Full constructor of the User class.
     *
     * @param userId the unique ID value of the user
     * @param userName the username of the user
     * @param password the password associated with the user
     */
    public User(int userId, String userName, String password){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Gets the ID value of the user
     * @return the ID value of the user
     */
    public int getUserId(){
        return userId;
    }

    /**
     * Sets the user ID.
     * @param userId the user ID to set
     */
    public void setUserId(int userId){
        this.userId = userId;
    }

    /**
     * Gets the username value of the user.
     * @return the username value of the user
     */
    public String getUserName(){
        return userName;
    }

    /**
     * Sets the username.
     * @param userName the username to set
     */
    public void setUserName(String userName){
        this.userName = userName;
    }

    /**
     * Gets the password value associated with the user.
     * @return the password value associated with the user
     */
    public String getPassword(){
        return password;
    }

    /**
     * Sets the user password.
     * @param password the user password to set
     */
    public void setPassword(String password){
        this.password = password;

    }

    /**
     * Overrides the <code>toString()</code> method to represent the User as a particular string.
     * This method is used to populate combo boxes.
     * @return the user ID and the username as string representation of the User
     */
    @Override
    public String toString() {
        return ("[" + userId + "]" + " " + userName);
    }
}
