package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class queries data from the countries table from the database.
 *
 * @author Myriam Drouin-Sagar
 */
public class DBCountry {
    /**
     * Fetches data from the countries table into a list of all the countries objects.
     *
     * @return the ObservableList of all contacts
     */
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        String sql = "SELECT * from countries";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Country country = new Country(countryId, countryName);
                allCountries.add(country);
            }


        } catch (SQLException e) {
            e.printStackTrace();

        }
        return allCountries;

    }

}






