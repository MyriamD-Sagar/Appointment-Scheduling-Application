package controller;

import database.DBCountry;
import database.DBCustomer;
import database.DBFirstDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.FirstDivision;
import utils.DisplayAlert;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller class that allows the user to create a customer object by inserting its data into the database.
 * <p>
 * Implements the <code>Initializable</code> interface.
 *
 * @author Myriam Drouin-Sagar
 */
public class AddCustomerForm implements Initializable {
    public Button cancelButton;
    public TextField addCustomerName;
    public TextField addCustomerAddress;
    public TextField addCustomerPhone;
    public ComboBox<Country> comboCountry;
    public ComboBox<FirstDivision> comboDivision;
    public TextField addCustomerZip;
    public Country selectedCountry;
    public FirstDivision selectedDivision;

    public Button saveButton;

    /**
     * Calls the <code>returnToCustomerView()</code> method to redirect user to the <code>CustomerView</code>.
     *
     * @param actionEvent event generated when user clicks on the cancelButton and passed as an argument for the <code>returnToCustomerView()</code> method.
     * @throws IOException from the FMLLoader in the <code>returnToCustomerView()</code> method
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        returnToCustomerView(actionEvent);
    }

    /**
     * Gets the country selected in the comboCounty combo box and populates the comboDivision combo box by using a Lambda Expression.
     * <p>
     * When the user selects a country in the combo box (comboCountry), it gets the ID of the country.
     * Also, a list of all FistDivision is retrieved by calling the <code>getAllFirstDivision()</code> method from the <code>DBFirstDivision</code> class.
     * <p>
     * LAMBDA EXPLANATION/JUSTIFICATION:
     * The lambda expression filters the ObservableList (allFirstDivisions) and fills an ObservableList (filteredDivisions) with the FirstDivision objects that matches the ID of the country selected.
     * The combo box (comboDivision) is then populated with the filteredDivisions list.
     *
     * @param actionEvent event generated when the user selects a country from the comboCountry combo box (never used)
     */
    public void onComboCountry(ActionEvent actionEvent) {
        selectedCountry = comboCountry.getSelectionModel().getSelectedItem();
        if (selectedCountry != null) {
            int selectedCountryId = selectedCountry.getCountryId();
            ObservableList<FirstDivision> allFirstDivisions = DBFirstDivision.getAllFirstDivision();

            Stream<FirstDivision> filteredDivisions = allFirstDivisions.stream().filter(d -> d.getCountryId() == selectedCountryId);
            comboDivision.setItems(filteredDivisions.collect(Collectors.toCollection(FXCollections ::observableArrayList)));
        }
    }
    public void onComboDivision(ActionEvent actionEvent) {
    }

    /**
     * Inserts a new Customer object into the database, and redirects the user to the <code>CustomerView</code>.
     * <p>
     * Displays appropriate ERROR dialog boxes when values are missing in the text fields or if no selection is made in the combo boxes.
     * If all information is correct, the new Customer object is inserted in the database by calling the <code>insertCustomer()</code> method in the <code>DBCustomer</code> class.
     * <p>
     * After the insert, a call to the <code>returnToCustomerView()</code> method is made to load the <code>CustomerView</code>.
     *
     * @param actionEvent event generated when user clicks on the saveButton and passed as an argument for the <code>returnToCustomerView()</code> method
     * @throws SQLException database access or other errors
     * @throws IOException from the FXMLLoader
     */
    public void onSaveButton(ActionEvent actionEvent) throws SQLException, IOException {
        selectedDivision = comboDivision.getSelectionModel().getSelectedItem();
        if(selectedDivision == null){
            Alert alert = DisplayAlert.alertDisplay(Alert.AlertType.ERROR, "Empty combo box(es).",
                    "Please select a Country, and Division (State/Province)\nto assign to the Customer.");
            alert.showAndWait();
            return;
        }
        int divisionId = selectedDivision.getDivisionId();
        String customerName = addCustomerName.getText();
        String address = addCustomerAddress.getText();
        String phone = addCustomerPhone.getText();
        String postalCode = addCustomerZip.getText();
        if(customerName.isEmpty() || address.isEmpty() || phone.isEmpty() || postalCode.isEmpty()){
            Alert emptyFields = DisplayAlert.alertDisplay(Alert.AlertType.ERROR, "Empty Field(s).",
                    "Please enter values in all text fields.");
            emptyFields.showAndWait();
            return;
        }
        DBCustomer.insertCustomer(customerName, address, postalCode, phone, divisionId);
        returnToCustomerView(actionEvent);
    }

    /**
     * Cancels the <code>AddCustomerForm</code> and loads the <code>CustomerView</code>.
     *
     * @param actionEvent event generated when a call to this method is made
     * @throws IOException from the FMLLoader
     */
    private void returnToCustomerView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 932, 502);
        stage.setTitle("Customer Records");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Populates the comboCountry combo box.
     * <p>
     * First method to run in this class when the FMLLoader calls the <code>AddCustomerForm</code> FXML file.
     * <p>
     * The combo box is populated by calling the <code>getAllCountries()</code> method from the <code>DBCountry</code> class.
     * @param url location used to retrieve relative paths for root object
     * @param resourceBundle resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Country> allCountries = DBCountry.getAllCountries();
        comboCountry.setItems(allCountries);

    }

}







