package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.ReportCountry;
import model.ReportTypeMonth;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class queries data used to populate the table views in the <code>ReportForm</code> controller class.
 *
 * @author Myriam Drouin-Sagar
 */
public class DBReport {
    /**
     * Fetches data from the appointments table into a list of ReportTypeMonth objects.
     * <p>
     * The list contains the total number of appointments by month and type.
     * This relation is created by utilizing the <code>month()</code> function to get the month part of the start date and time of the appointments.
     * It also uses the <code>count(*)</code> function and <code>GROUP BY</code> to count the number of appointments from the same month and the same type.
     * @return the ObservableList of all ReportTypeMonth objects
     */
    public static ObservableList<ReportTypeMonth> getTypeMonthList(){
        ObservableList<ReportTypeMonth> typeMonthList = FXCollections.observableArrayList();
        String sql = "SELECT month(Start), Type, count(*) FROM appointments GROUP BY Type, month(Start) order by month(Start)";
        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int month = rs.getInt("month(Start)");
                String type = rs.getString("Type");
                int count = rs.getInt("count(*)");
                ReportTypeMonth reportTypeMonth = new ReportTypeMonth(month,type,count);
                typeMonthList.add(reportTypeMonth);
        }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return typeMonthList;
    }

    /**
     * Fetches data from the appointments table into a list of appointments objects for the record(s) that matches the contact ID passed as an argument.
     * <p>
     * The ObservableList <code>allAppointments</code> will be used to populate the table view for the contact schedules in the <code>ReportForm</code> controller class.
     * @param contactID the ID of the contact associated with the appointment(s) to fetch data from
     * @return an ObservableList of appointment(s) that matches the contact ID
     */
    public static ObservableList<Appointment> appointmentByContactId (int contactID){
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT Appointment_ID, Title, Type, Description, Contact_ID, Start, End, Customer_ID FROM appointments WHERE Contact_ID = ?;";
        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1,contactID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                int contactId = rs.getInt("Contact_ID");
                Timestamp startDT = (rs.getTimestamp("Start"));
                LocalDateTime starting = startDT.toLocalDateTime();

                Timestamp endDT = (rs.getTimestamp("End"));
                LocalDateTime ending = endDT.toLocalDateTime();


                int customerId = rs.getInt("Customer_ID");
                Appointment appointment = new Appointment(appointmentId, title, description, null, contactId, null, type, starting, ending, customerId, null, 0, null );
                allAppointments.add(appointment);
            }
        } catch (SQLException e) {
        e.printStackTrace();
        }
        return allAppointments;
    }

    /**
     * Fetches data from tables in the database into a list of ReportCountry object.
     * <p>
     * This method utilizes INNER JOIN to create a relation between the customers and the countries tables.
     * A third table (first_level_divisions aka f_l_d) is acting as a bridge to allow to fetch the country field from the countries table.
     * <p>
     * The Foreign Key of the customers table (Division_ID) connects to the Primary Key of the f_l_d table (Division_ID).
     * Then, the foreign key of the f_l_d table (Country_ID) connects to the Primary Key of the countries table (Country_ID).
     * These relations allow to extract the appropriate country field for each customer.
     * <p>
     * The <code>count(*)</code> function and <code>GROUP BY</code> to return the number of customers by country.
     * @return an ObservableList of all reportByCountry objects
     */
    public static ObservableList<ReportCountry> customerByCountry(){
        ObservableList<ReportCountry> customerByCountryList = FXCollections.observableArrayList();
        String sql = "SELECT countries.Country, count(*) as Total FROM customers INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                " INNER JOIN countries ON countries.Country_ID = first_level_divisions.Country_ID WHERE customers.Division_ID = first_level_divisions.Division_ID " +
                " GROUP BY first_level_divisions.Country_ID ORDER BY count(*);";
        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String countryName = rs.getString("Country");
                int count = rs.getInt("Total");
                ReportCountry reportCountry = new ReportCountry(countryName,count);
                customerByCountryList.add(reportCountry);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerByCountryList;
    }

}
