package database;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstDivision;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class queries data from the first_level_divisions table from the database.
 *
 * @author Myriam Drouin-Sagar
 */
public class DBFirstDivision {
    /**
     * Fetches data from the first_level_divisions table into a list of all FirstDivision objects.
     *
     * @return ObservableList of all FirstDivision
     */
    public static ObservableList<FirstDivision> getAllFirstDivision() {
        ObservableList<FirstDivision> allFirstDivision = FXCollections.observableArrayList();
        String sql = "SELECT Division_ID, Division, Country_ID from first_level_divisions";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                FirstDivision firstDivision = new FirstDivision(divisionId, divisionName, countryId);
                allFirstDivision.add(firstDivision);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return allFirstDivision;

    }

/*
    public static ObservableList<FirstDivision> getAllUSDivision(){
        ObservableList<FirstDivision> USDivision = FXCollections.observableArrayList();
        String sql = "SELECT Division_ID, Division, Country_ID from first_level_divisions where Country_ID = 1;";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryId= rs.getInt("Country_ID");
            FirstDivision firstDivision = new FirstDivision(divisionId, divisionName, countryId);
            USDivision.add(firstDivision);
        }
    } catch (SQLException e) {
        e.printStackTrace();

    }
        return USDivision;
    }

    public static ObservableList<FirstDivision> getAllUKDivision(){
        ObservableList<FirstDivision> UKDivision = FXCollections.observableArrayList();
        String sql = "SELECT Division_ID, Division, Country_ID from first_level_divisions where Country_ID = 2;";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            //ps.setInt(1, 2);

            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId= rs.getInt("Country_ID");
                FirstDivision firstDivision = new FirstDivision(divisionId, divisionName, countryId);
                UKDivision.add(firstDivision);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return UKDivision;
    }
    public static ObservableList<FirstDivision> getAllCADivision(){
        ObservableList<FirstDivision> CADivision = FXCollections.observableArrayList();
        String sql = "SELECT Division_ID, Division, Country_ID from first_level_divisions where Country_ID = 3;";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
           // ps.setInt(1, 3);

            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId= rs.getInt("Country_ID");
                FirstDivision firstDivision = new FirstDivision(divisionId, divisionName, countryId);
                CADivision.add(firstDivision);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return CADivision;
    }
    */


}

