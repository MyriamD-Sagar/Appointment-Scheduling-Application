package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class defines methods that performs SELECT, DELETE, INSERT, and UPDATE operations on the customer's data from/to the database.
 *
 * @author Myriam Drouin-Sagar
 */
public class DBCustomer {
    /**
     * Fetches data from database's tables and creates a list of customer objects.
     * <p>
     * This method queries data from 3 different tables to create a Customer object by using LEFT JOIN.
     * To connect records from the different relations, the primary keys and foreign keys are utilized.
     * @return ObservableList of all customers
     */
    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.phone, customers.Division_ID," +
                " first_level_divisions.Division_ID, first_level_divisions.Division, first_level_divisions.Country_ID," +
                " countries.Country_ID, countries.Country FROM customers" +
                " LEFT JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID" +
                " LEFT JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID";

        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                //object instance
                Customer customer = new Customer(customerId, customerName, address, postalCode, phone, divisionId, divisionName, countryId, countryName);
                allCustomers.add(customer);

            }

        }catch(SQLException e){
            e.printStackTrace();

        }

        return allCustomers;
    }

    /**
     * Inserts customer's data into the customers table in the database.
     *
     * @param customerName the name of the customer
     * @param address the address of the customer
     * @param postalCode the postal code of the customer
     * @param phone the phone number of the customer
     * @param divisionId the ID of the first-level-division where the customer lives
     * @return the number of rows (records) inserted into the table
     * @throws SQLException database access errors
     */
    public static int insertCustomer(String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }

    /**
     * Removes all the fields in the customers table for the customer record that matches the customer ID passed as an argument.
     * <p>
     * Only one record should match this condition since the customer ID is unique, so the number of rows affected is limited to 1 or 0.
     * @param customerId unique ID of the customer record to delete
     * @return the number of rows deleted from the table (0 or 1)
     * @throws SQLException database access errors
     */
    public static int deleteCustomer (int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Updates data in the customers table for the record (customer) that matches the customer ID.
     *
     * @param customerId unique ID of the customer to update
     * @param customerName the name of the customer
     * @param address address of the customer
     * @param postalCode postal code of the customer
     * @param phone phone number of the customer
     * @param divisionId ID of the first-level-division where the customer lives
     * @return number of rows deleted from the customers table (0 or more)
     * @throws SQLException database access errors
     */
    public static int updateCustomer(int customerId, String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);
        ps.setInt(6, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}


