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
import model.Customer;
import model.FirstDivision;
import utils.DisplayAlert;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller class that allows the user to update a customer's information in the database.
 * <p>
 * Implements the <code>Initializable</code> interface.
 *
 * @author Myriam Drouin-Sagar
 */
public class UpdateCustomerForm implements Initializable {
    public TextField updateCustomerId;
    public TextField updateCustomerName;
    public TextField updateCustomerAddress;
    public TextField updateCustomerPhone;
    public TextField updateCustomerZip;
    public ComboBox<Country> comboCountry;
    public ComboBox<FirstDivision> comboDivision;

    public Button saveButton;

    public Button cancelButton;

    public Customer selectedCustomer;
    public Country selectedCountry;
    public FirstDivision selectedDivision;


    /**
     * Gets the country selected in the comboCounty combo box and populates the comboDivision combo box by using a Lambda Expression.
     * <p>
     * If the user makes a different country selection in the combo box (comboCountry), it gets the ID of the country to populate the comboDivision combo box with the appropriate data.
     * A list of all FistDivision is retrieved by calling the <code>getAllFirstDivision()</code> method from the <code>DBFirstDivision</code> class.
     * <p>
     * LAMBDA EXPLANATION/JUSTIFICATION:
     * The lambda expression filters the ObservableList (allFirstDivisions) and fills an ObservableList (filteredDivisions) with the FirstDivision objects that matches the ID of the selectedCountry.
     * The combo box (comboDivision) is then populated with the filteredDivisions list.
     * @param actionEvent event generated when the user selects a country from the comboCountry combo box (never used)
     */
    public void onComboCountry(ActionEvent actionEvent) {
        selectedCountry = comboCountry.getSelectionModel().getSelectedItem();
        comboDivision.setValue(null);
        int selectedCountryId = selectedCountry.getCountryId();

        ObservableList<FirstDivision> allFirstDivisions = DBFirstDivision.getAllFirstDivision();
        Stream<FirstDivision> filteredDivisions = allFirstDivisions.stream().filter(d -> d.getCountryId() == selectedCountryId);
        comboDivision.setItems(filteredDivisions.collect(Collectors.toCollection(FXCollections ::observableArrayList)));

    }

   public void onComboDivision(ActionEvent actionEvent) {
    }

    /**
     * Calls the <code>returnToCustomerView()</code> method to redirect the user to the <code>CustomerView</code>.
     * @param actionEvent event generated when user clicks on the cancelButton and passed as an argument for the <code>returnToCustomerView()</code>
     * @throws IOException from the FMLLoader in the <code>returnToCustomerView</code> method
     */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
       returnToCustomerView(actionEvent);

    }

    /**
     * Updates customer's information into the database, and redirects the user to the <code>CustomerView</code>.
     * <p>
     * Displays appropriate error message when values are missing in the text fields or if no selection is made in the combo boxes.
     * If all information is correct, the customer's information is updated in the database by calling the <code>updateCustomer()</code> method in the <code>DBCustomer</code> class.
     * After the insert, a call to the <code>returnToCustomerView()</code> is made to load the <code>CustomerView</code> FXML file.
     *
     * @param actionEvent event generated when user clicks on the saveButton and passed as an argument for the <code>returnToCustomerView()</code> method
     * @throws SQLException database access or other errors
     * @throws IOException from the FXMLLoader
     */
    public void onSaveButton(ActionEvent actionEvent) throws SQLException, IOException {
        selectedDivision = comboDivision.getSelectionModel().getSelectedItem();
        if(selectedDivision == null){
            Alert alert = DisplayAlert.alertDisplay(Alert.AlertType.ERROR, "Empty combo box.",
                    "Please select a Division (State/Province)\nto assign to the Customer.");
            alert.showAndWait();
            return;
        }
        int divisionId = selectedDivision.getDivisionId();
        int customerId = selectedCustomer.getCustomerId();
        String customerName = updateCustomerName.getText();
        String address = updateCustomerAddress.getText();
        String phone = updateCustomerPhone.getText();
        String postalCode = updateCustomerZip.getText();
        if(customerName.isEmpty() || address.isEmpty() || phone.isEmpty() || postalCode.isEmpty()){
            Alert emptyFields = DisplayAlert.alertDisplay(Alert.AlertType.ERROR, "Empty Field(s).",
                    "Please enter values in all text fields.");
            emptyFields.showAndWait();
            return;
        }
        DBCustomer.updateCustomer(customerId, customerName, address, postalCode, phone, divisionId);
        returnToCustomerView(actionEvent);

    }

    /**
     * Cancels the <code>UpdateCustomerForm</code> and loads the <code>CustomerView</code>.
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
     * Populates the combo boxes and text fields with the appropriate information associated with the selectedCustomer in the <code>CustomerView</code>.
     * <p>
     * First method to run in this class when the FMLLoader calls the <code>UpdateCustomerForm</code> FXML file.
     * <p>
     * The combo box (comboCountry) is populated by calling the <code>getAllCountries()</code> method from the <code>DBCountry</code> class.
     * The values displayed in the combo boxes are set by creating instances of Country and FirstDivision objects with the selectedCustomer information.
     * <p>
     * A list of all FistDivision is retrieved by calling the <code>getAllFirstDivision()</code> method from the <code>DBFirstDivision</code> class.
     * <p>
     * LAMBDA EXPLANATION/JUSTIFICATION:
     * The lambda expression filters the ObservableList (allFirstDivisions) and fills an ObservableList (filteredDivisions) with the FirstDivision objects that matches the country ID of the selectedCustomer.
     * The combo box (comboDivision) is then populated with the filteredDivisions list.
     * @param url location used to retrieve relative paths for root object
     * @param resourceBundle resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Country> allCountries = DBCountry.getAllCountries();
        ObservableList<FirstDivision> allFirstDivisions = DBFirstDivision.getAllFirstDivision();

        selectedCustomer = CustomerView.getSelectedCustomer();
        int selectedCountryId = selectedCustomer.getCountryID();

        updateCustomerId.setText(String.valueOf(selectedCustomer.getCustomerId()));
        updateCustomerName.setText(selectedCustomer.getCustomerName());
        updateCustomerAddress.setText(selectedCustomer.getAddress());
        updateCustomerZip.setText(selectedCustomer.getPostalCode());
        updateCustomerPhone.setText(selectedCustomer.getPhone());

        comboCountry.setValue(new Country(selectedCountryId, selectedCustomer.getCountryName()));
        comboCountry.setItems(allCountries);

        comboDivision.setValue(new FirstDivision(selectedCustomer.getDivisionId(), selectedCustomer.getDivisionName(), selectedCountryId));

        Stream<FirstDivision> filteredDivisions = allFirstDivisions.stream().filter(d -> d.getCountryId() == selectedCountryId);
        comboDivision.setItems(filteredDivisions.collect(Collectors.toCollection(FXCollections ::observableArrayList)));



    }
}
