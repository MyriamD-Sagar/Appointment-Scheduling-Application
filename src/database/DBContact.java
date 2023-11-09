package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class queries data from the contacts table from the database.
 *
 * @author Myriam Drouin-Sagar
 */
public class DBContact {
    /**
     * Fetches data from the contacts table into a list of all contacts objects.
     *
     * @return the ObservableList of all contacts
     */
    public static ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        String sql = "SELECT * from contacts;";
        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact contact = new Contact(contactId, contactName, email);
                allContacts.add(contact);

            }

        }catch(SQLException e){
            e.printStackTrace();

        }
        return allContacts;
    }

}
