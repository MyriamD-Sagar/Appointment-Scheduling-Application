package controller;

import database.DBAppointment;
import database.DBContact;
import database.DBCustomer;
import database.DBUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import utils.DisplayAlert;
import utils.Time;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller class that allows the user to update an appointment's information in the database.
 *
 * <p>
 * Implements the <code>Initializable</code> interface.
 */
public class UpdateAppointmentForm implements Initializable {
    public TextField updateAppointmentId;
    public TextField appointmentTitle;
    public TextField appointmentType;
    public TextField appointmentDescription;
    public TextField appointmentLocation;
    public TextField appointmentStartTime;
    public TextField appointmentEndTime;
    public ComboBox<Customer> customerIdCombo;
    public ComboBox<Contact> contactCombo;
    public ComboBox<User> userIdCombo;
    public DatePicker startDatePicker;
    public Button cancelButton;
    public Button saveButton;
    public Appointment selectedAppointment;
    public Contact selectedContact;
    public User selectedUser;
    public Customer selectedCustomer;

    public void onCustomerIdCombo(ActionEvent actionEvent) {
    }

    public void onContactCombo(ActionEvent actionEvent) {
    }

    public void onUserIdCombo(ActionEvent actionEvent) {
    }

    public void onStartDatePicker(ActionEvent actionEvent) {
    }

    /**
     * Calls the <code>returnToAppointmentView()</code> method to redirect the user to the <code>AppointmentView</code>.
     * <p>
     * Loads the <code>AppointmentView</code> FXML file.
     * @param actionEvent event generated when the user clicks on the cancelButton and passes as an argument for the <code>returnToAppointmentView()</code> method.
     * @throws IOException from the FMLLoader in the <code>returnToAppointmentView()</code> method
     */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        returnToAppointmentView(actionEvent);

    }

    /**
     * Updates appointment's information into the database, and redirects the user to the <code>AppointmentView</code>.
     * <p>
     * Gets the appointment's information from the text fields, combo boxes and date picker. Only one date picker since Start and End dates must be during same day.
     * <p>
     * Displays appropriate ERROR dialog boxes when values are missing in the text fields.
     * <p>
     * Calls the <code>verifyStartEnd()</code>, and the <code>verifyBusiness()</code> methods from the <code>Time</code> class.
     * Displays Error dialog boxes if the appointment's Start and End Times are outside Business Hours or if Start Time is scheduled after End Time (vice-versa).
     * <p>
     * If all the information is correct, the Lambda Expression implements.
     * LAMBDA EXPRESSION EXPLANATION/JUSTIFICATION:
     * This lambda expression filters the list of allAppointments and fills an ObservableList (appointmentByCustomerId) with the Appointments objects that matches the selectedCustomer ID (customerId).
     * <p>
     * This list (appointmentByCustomerId) is then searched to verify there is no Scheduling Conflicts for the selectCustomer.
     * If scheduling conflict is find, a custom ERROR dialog box is displayed.
     * If no scheduling conflict is find, the appointment is inserted into the database by calling the <code>updateAppointment()</code> method from the <code>DBAppointment</code> class.
     * <p>
     * After the insert, a call to the <code>returnToAppointmentView()</code> method is made to load the <code>AppointmentView</code>.
     * @param actionEvent event generated when user clicks on the saveButton and passed as an argument for the <code>returnToAppointmentView()</code> method
     * @throws SQLException database access errors
     * @throws IOException from the FXMLLoader
     */
    public void onSaveButton(ActionEvent actionEvent) throws SQLException, IOException {
        try{
            String title = appointmentTitle.getText();
            String location = appointmentLocation.getText();
            String type = appointmentType.getText();
            String description = appointmentDescription.getText();
            String starting = appointmentStartTime.getText();
            String ending = appointmentEndTime.getText();
            selectedContact = contactCombo.getSelectionModel().getSelectedItem();
            selectedUser = userIdCombo.getSelectionModel().getSelectedItem();
            selectedCustomer = customerIdCombo.getSelectionModel().getSelectedItem();

            if(title.isEmpty() || location.isEmpty() || description.isEmpty() || type.isEmpty() || starting.isEmpty() || ending.isEmpty()){
                Alert emptyFields = DisplayAlert.alertDisplay(Alert.AlertType.ERROR, "Empty Field(s).",
                        "Please enter values in all text fields.");
                emptyFields.showAndWait();
                return;
            }
            LocalTime startTime = LocalTime.parse(starting);
            LocalTime endTime = LocalTime.parse(ending);
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = startDatePicker.getValue();
            LocalDateTime startDT = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDT = LocalDateTime.of(endDate, endTime);

            if(!Time.verifyStartEnd(startDT, endDT)){
                Alert alertError1 = DisplayAlert.alertDisplay(Alert.AlertType.ERROR, "Error in Appointment Scheduling Time", "The appointment End Time must be scheduled after the appointment Start Time.");
                alertError1.showAndWait();
                return;
            }
            if(!Time.verifyBusiness(startDT, endDT)){
                Alert alertError4 = DisplayAlert.alertDisplay(Alert.AlertType.ERROR,"The appointment Scheduling time is outside of Business Hours.",
                        "Business Hours --> 8:00 to 22:00 (Eastern Time)");
                alertError4.showAndWait();
                return;
            }

            int contactId = selectedContact.getContactId();
            int userId = selectedUser.getUserId();
            int customerId = selectedCustomer.getCustomerId();
            int appointmentId = selectedAppointment.getAppointmentId();

            ObservableList<Appointment> allAppointments = DBAppointment.getAllAppointments();
            Stream<Appointment> appointmentList = allAppointments.stream().filter(a -> a.getCustomerId() == customerId);
            ObservableList<Appointment> appointmentByCustomerId = appointmentList.collect(Collectors.toCollection(FXCollections:: observableArrayList));

            LocalDateTime aS = startDT;
            LocalDateTime aE = endDT;
            for (Appointment appointment : appointmentByCustomerId) {
                if (appointment.getAppointmentId() != selectedAppointment.getAppointmentId()) {
                    LocalDateTime s = appointment.getStarting();
                    LocalDateTime e = appointment.getEnding();
                    if (((s.isAfter(aS) || s.isEqual(aS)) && (s.isBefore(aE))) || ((e.isAfter(aS)) && (e.isBefore(aE) || e.isEqual(aE))) || ((s.isBefore(aS) || s.isEqual(aS)) && (e.isAfter(aE) || e.isEqual(aE)))) {
                        Alert alertError2 = DisplayAlert.alertDisplay(Alert.AlertType.ERROR, "Scheduling conflict for the customer --> [" + customerId + "] " + selectedCustomer.getCustomerName(),
                                "Appointment ID: " + appointment.getAppointmentId() +
                                        "\nType: " + appointment.getType() +
                                        "\nDate: " + appointment.getStarting().toLocalDate() +
                                        "\nTime: " + appointment.getStarting().toLocalTime() + " to " + appointment.getEnding().toLocalTime());
                        alertError2.showAndWait();
                        return;
                    }
                }
            }
            DBAppointment.updateAppointment(appointmentId, title, description, location, contactId, type, startDT, endDT, customerId, userId);
            returnToAppointmentView(actionEvent);
        }catch(DateTimeParseException e){
            Alert alertError3 = DisplayAlert.alertDisplay(Alert.AlertType.ERROR, "Invalid Values for Start Time and/or End Time",
                    "Please enter valid values in Time text fields.\nFormat --> 00:00");
            alertError3.showAndWait();
        }

    }

    /**
     * Cancels the <code>UpdateAppointmentForm</code> and loads the <code>AppointmentView</code>.
     * @param actionEvent event generated when a call to this method is made
     * @throws IOException from the FMLLoader
     */
    private void returnToAppointmentView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1260, 600);
        stage.setTitle("Appointments Scheduling");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Populates the combo boxes and text fields with the appropriate information associated with the selectedAppointment in the <code>AppointmentView</code>.
     * <p>
     * First method to run in this class when the FMLLoader calls the <code>UpdateCustomerForm</code> FXML file.
     * <p>
     * The contactCombo combo box is populated by calling the <code>getAllContacts()</code> method from the <code>DBContact</code> class.
     * The customerIdCombo combo box is populated by calling the <code>getAllCustomers()</code> method from the <code>DBCustomer</code> class.
     * The userIdCombo combo box is populated by calling the <code>getAllUsers()</code> method from the <code>DBUser</code> class.
     * <p>
     * The values displayed in the combo boxes are set by creating instances of Contact, Customer, and User objects with the selectedAppointment information.
     * @param url location used to retrieve relative paths for root object
     * @param resourceBundle resources used to localize the root object
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedAppointment = AppointmentView.getSelectedAppointment();

        ObservableList<Contact> allContacts = DBContact.getAllContacts();
        ObservableList<Customer> allCustomers = DBCustomer.getAllCustomers();
        ObservableList<User> allUsers = DBUser.getAllUsers();

        contactCombo.setItems(allContacts);
        contactCombo.setValue(new Contact(selectedAppointment.getContactId(), selectedAppointment.getContactName(), null));

        customerIdCombo.setItems(allCustomers);
        customerIdCombo.setValue(new Customer(selectedAppointment.getCustomerId(), selectedAppointment.getCustomerName()));

        userIdCombo.setItems(allUsers);
        userIdCombo.setValue(new User(selectedAppointment.getUserId(), selectedAppointment.getUserName(), null));

        appointmentTitle.setText(selectedAppointment.getTitle());
        appointmentDescription.setText(selectedAppointment.getDescription());
        appointmentType.setText(selectedAppointment.getType());
        appointmentLocation.setText(selectedAppointment.getLocation());
        startDatePicker.setValue(selectedAppointment.getStarting().toLocalDate());

        appointmentStartTime.setText(String.valueOf(selectedAppointment.getStarting().toLocalTime()));
        appointmentEndTime.setText(String.valueOf(selectedAppointment.getEnding().toLocalTime()));




    }
}
