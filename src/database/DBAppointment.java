package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * This class defines methods to SELECT, INSERT, UPDATE, and DELETE customer's data from/to the database.
 *
 * @author Myriam Drouin-Sagar
 */
public class DBAppointment {


    /**
     * Fetches data from the tables in the database and creates a list of all the appointments.
     * <p>
     * This query uses JOIN to associate data from 4 different tables (relations) to create an Appointment object (temporary relation).
     * To do so, it utilizes the primary key (Customer_ID) from the appointments table and its foreign keys (Contact_ID, User_ID, and Customer_ID).
     * The ObservableList <code>allAppointments</code> contains Appointment objects.
     * @return an ObservableList of all the appointments
     */
    public static ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Contact_ID, appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, " +
                " contacts.Contact_ID, contacts.Contact_Name, " +
                " users.User_ID, users.User_Name, " +
                " customers.Customer_ID, customers.Customer_Name  FROM appointments " +
                " LEFT JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID " +
                " INNER JOIN users ON appointments.User_ID = users.User_ID " +
                " INNER JOIN customers ON appointments.Customer_ID = customers.Customer_ID " +
                " ORDER BY appointments.Appointment_ID;";

        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID"); //PK
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contactName = rs.getString("Contact_Name");
                int contactId = rs.getInt("Contact_ID"); //FK
                String type = rs.getString("Type");

                Timestamp startDT = (rs.getTimestamp("Start"));
                LocalDateTime starting = startDT.toLocalDateTime();

                Timestamp endDT = (rs.getTimestamp("End"));
                LocalDateTime ending = endDT.toLocalDateTime();

                int customerId = rs.getInt("Customer_ID"); //FK
                String customerName = rs.getString("Customer_Name");
                int userId = rs.getInt("User_ID"); //FK
                String userName = rs.getString("User_Name");
                Appointment appointment = new Appointment(appointmentId, title, description, location, contactId, contactName, type, starting ,ending, customerId, customerName, userId, userName);
                allAppointments.add(appointment);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    /**
     * Inserts an appointment's data into the database in the appointments table.
     *
     * @param title title of the appointment
     * @param description description of the appointment
     * @param location location of the appointment
     * @param contactId the ID of the contact assigned to the appointment
     * @param type the type of the appointment
     * @param starting the appointment start date and time
     * @param ending the appointment end date and time
     * @param customerId the ID of the customer associated to the appointment
     * @param userId the ID of the user assigned to the appointment
     * @return the number of rows inserted into the table (1 or more) or 0 if no insert
     * @throws SQLException database access errors
     */
    public static int insertAppointment(String title, String description, String location, int contactId, String type, LocalDateTime starting, LocalDateTime ending, int customerId, int userId) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2,description);
        ps.setString(3, location);
        ps.setInt(4, contactId);
        ps.setString(5, type);
        Timestamp dts = Timestamp.valueOf(starting);
        ps.setTimestamp(6, dts);
        Timestamp dte = Timestamp.valueOf(ending);
        ps.setTimestamp(7, dte);
        ps.setInt(8, customerId);
        ps.setInt(9, userId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Removes (deletes) all the fields in the appointments table for the record that matches the appointment ID passed as an argument when the method is called.
     * <p>
     * Since only one appointment can match the unique appointment ID, the number of rows affected should be 0 or 1.
     * @param appointmentId the unique ID of the appointment to delete
     * @return the number of rows (records) deleted from the table
     * @throws SQLException database access errors
     */
    public static int deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1,appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Removes (deletes) all the fields in the appointments table for the record that matches the customer ID passed as an argument.
     *
     * @param customerId the ID of the customer assigned to the appointment
     * @return the number of records deleted (0 or more) from the appointments table
     * @throws SQLException database access errors
     */
    public static int deleteAppointmentCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Customer_ID = ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowAffected = ps.executeUpdate();
        return rowAffected;
    }

    /**
     * Updates data in the appointments table for the record that matches the appointment ID.
     *
     * @param appointmentId unique ID of the appointment to update
     * @param title title of the appointment
     * @param description description of the appointment
     * @param location location of the appointment
     * @param contactId ID of the contact assigned to the appointment
     * @param type the type of the appointment
     * @param starting the appointment start date and time
     * @param ending the appointment end date and time
     * @param customerId ID of the customer associated to the appointment
     * @param userId ID of the customer assigned to the appointment
     * @return number of rows updated (0 or 1) from the appointments table
     * @throws SQLException
     */
    public static int updateAppointment(int appointmentId, String title, String description, String location, int contactId, String type, LocalDateTime starting, LocalDateTime ending, int customerId, int userId) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Contact_ID = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ? WHERE Appointment_ID = ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setInt(4,contactId);
        ps.setString(5, type);
        Timestamp dts = Timestamp.valueOf(starting);
        ps.setTimestamp(6, dts);
        Timestamp dte = Timestamp.valueOf(ending);
        ps.setTimestamp(7, dte);
        ps.setInt(8,customerId);
        ps.setInt(9, userId);
        ps.setInt(10, appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }

}










