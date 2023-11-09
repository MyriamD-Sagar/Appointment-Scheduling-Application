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
 * Controller class that allows the user to create an appointment object by inserting its data into the database.
 * <p>
 * Implements the <code>Initializable</code> interface.
 *
 * @author Myriam Drouin-Sagar
 */
public class AddAppointmentForm implements Initializable {

    public TextField addAppointmentId;
    public TextField appointmentTitle;
    public TextField appointmentType;
    public TextField appointmentDescription;
    public TextField appointmentLocation;
    public TextField appointmentStartTime;
    public TextField appointmentEndTime;
    public DatePicker startDatePicker;
    public ComboBox<Customer> customerIdCombo;

    public ComboBox<Contact> contactCombo;
    public ComboBox<User> userIdCombo;

    public Button cancelButton;

    public Button saveButton;
    public Contact selectedContact;

    public Customer selectedCustomer;
    public User selectedUser;


    public void onCustomerIdCombo(ActionEvent actionEvent) {
    }

    public void onContactCombo(ActionEvent actionEvent) {
    }

    public void onUserIdCombo(ActionEvent actionEvent) {
    }
    public void onStartDatePicker(ActionEvent actionEvent) {
    }

    /**
     * Calls the <code>returnToAppointmentView()</code> method to redirect user to the <code>AppointmentView</code>.
     * Loads the <code>AppointmentView</code> FXML file.
     * @param actionEvent event generated when the user clicks on the cancelButton and passes as an argument for the <code>returnToAppointmentView()</code> method.
     * @throws IOException from the FMLLoader in the <code>returnToAppointmentView()</code> method
     */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        returnToAppointmentView(actionEvent);

    }

    /**
     * Inserts a new Appointment object into the database, and redirects the user to the <code>AppointmentView</code>.
     * <p>
     * Gets the appointment's information from the text fields, combo boxes and date picker. Only one date picker since Start and End dates must be during same day.
     * <p>
     * Displays appropriate ERROR dialog boxes when values are missing in the text fields or if no selection is made in the combo boxes.
     * <p>
     * Calls the <code>verifyStartEnd()</code>, and the <code>verifyBusiness()</code> methods from the <code>Time</code> class.
     * Displays Error dialog boxes if the appointment's Start and End Times are outside Business Hours or if Start Time is scheduled after End Time (vice-versa).
     * <p>
     * If all the information is correct, the Lambda Expression implements.
     * LAMBDA EXPRESSION EXPLANATION/JUSTIFICATION:
     * This lambda expression filters the list of allAppointments and fills an ObservableList (appointmentByCustomerId) with the Appointments objects that matches the selectedCustomer ID (customerId).
     * <p>
     * This list (appointmentByCustomerId) is then searched to verify there is no Scheduling Conflicts for the selectCustomer.
     * If scheduling conflict is find, a personalized ERROR dialog box is displayed.
     * If no scheduling conflict is find, the appointment is inserted into the database by calling the <code>insertAppointment()</code> method from the <code>DBAppointment</code> class.
     * <p>
     * After the insert, a call to the <code>returnToAppointmentView()</code> method is made to load the <code>AppointmentView</code>.
     *
     * @param actionEvent event generated when user clicks on the saveButton and passed as an argument for the <code>returnToAppointmentView()</code> method
     * @throws SQLException database access errors
     * @throws IOException from the FXMLLoader
     */
    public void onSaveButton(ActionEvent actionEvent) throws SQLException, IOException {
        try {

            String title = appointmentTitle.getText();
            String location = appointmentLocation.getText();
            String description = appointmentDescription.getText();
            String type = appointmentType.getText();
            String starting = appointmentStartTime.getText();
            String ending = appointmentEndTime.getText();
            selectedContact = contactCombo.getSelectionModel().getSelectedItem();
            selectedCustomer = customerIdCombo.getSelectionModel().getSelectedItem();
            selectedUser = userIdCombo.getSelectionModel().getSelectedItem();

            if(selectedContact == null || selectedUser == null || selectedCustomer == null){
                Alert alertCombo = DisplayAlert.alertDisplay(Alert.AlertType.ERROR, "Empty Combo box(es).",
                        "Please select a Contact, User, and Customer to assign to the appointment.");
                alertCombo.showAndWait();
                return;
            }

            if(title.isEmpty() || location.isEmpty() || description.isEmpty() || type.isEmpty() || starting.isEmpty() || ending.isEmpty()){
                Alert emptyFields = DisplayAlert.alertDisplay(Alert.AlertType.ERROR, "Empty Field(s).",
                        "Please enter values in all text fields.");
                emptyFields.showAndWait();
                return;
            }

            LocalTime startTime = LocalTime.parse(starting);
            LocalTime endTime = LocalTime.parse(ending);
            LocalDate startDate =startDatePicker.getValue();
            LocalDate endDate = startDatePicker.getValue();
            LocalDateTime startDT = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDT = LocalDateTime.of(endDate, endTime);

            if(!Time.verifyStartEnd(startDT, endDT)){
                Alert alertError3 = DisplayAlert.alertDisplay(Alert.AlertType.ERROR, "Error in Appointment Scheduling Time", "The appointment End Time must be scheduled after the appointment Start Time.");
                alertError3.showAndWait();
                return;
            }
            if(!Time.verifyBusiness(startDT, endDT)){
                Alert alertError4 = DisplayAlert.alertDisplay(Alert.AlertType.ERROR,"The appointment Scheduling time is outside of Business Hours.",
                        "Business Hours --> 8:00 to 22:00 (Eastern Time)");
                alertError4.showAndWait();
                return;
            }
            int contactId = selectedContact.getContactId();
            int customerId = selectedCustomer.getCustomerId();
            int userId = selectedUser.getUserId();

            ObservableList<Appointment> allAppointments = DBAppointment.getAllAppointments();
            Stream<Appointment> appointmentList = allAppointments.stream().filter(a -> a.getCustomerId() == customerId);
            ObservableList<Appointment> appointmentByCustomerId = appointmentList.collect(Collectors.toCollection(FXCollections:: observableArrayList));

            LocalDateTime aS = startDT;
            LocalDateTime aE = endDT;
            for (Appointment appointment : appointmentByCustomerId) {
                LocalDateTime s = appointment.getStarting();
                LocalDateTime e = appointment.getEnding();
                if (((s.isAfter(aS) || s.isEqual(aS)) && (s.isBefore(aE))) || ((e.isAfter(aS)) && (e.isBefore(aE) || e.isEqual(aE))) || (((s.isBefore(aS) || s.isEqual(aS)) && (e.isAfter(aE) || e.isEqual(aE))))) {
                    Alert alertError5 = DisplayAlert.alertDisplay(Alert.AlertType.ERROR, "Scheduling conflict for the customer --> [" + customerId + "] " + selectedCustomer.getCustomerName(),
                            "Appointment ID: " + appointment.getAppointmentId() +
                                    "\nType: " + appointment.getType() +
                                    "\nDate: " + appointment.getStarting().toLocalDate() +
                                    "\nTime: " + appointment.getStarting().toLocalTime() + " to " + appointment.getEnding().toLocalTime());
                    alertError5.showAndWait();
                    return;
                }
            }
            DBAppointment.insertAppointment(title, description, location, contactId, type, startDT, endDT, customerId, userId);
            returnToAppointmentView(actionEvent);
        }catch(DateTimeParseException d) {
            Alert alertError6 = DisplayAlert.alertDisplay(Alert.AlertType.ERROR, "Invalid Values for Start Time and/or End Time",
                    "Please enter valid values in Time text fields.\nFormat --> 00:00");
            alertError6.showAndWait();
        }

    }

    /**
     * Cancels the <code>AddAppointmentForm</code> and loads the <code>AppointmentView</code>.
     * @param actionEvent event generated when a call to this method is made
     * @throws IOException
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
     * Populates the combo boxes, sets an initial value to the date picker, and sets an initial value for the Start and End Time text fields.
     * <p>
     * The value for the date picker (startDatePicker) is set to the current date.
     * The value for the text field (appointmentStartTime) is set to 8:00 (EST), and the text field (appointmentEndTime) is set to 22:00 (EST).
     * These initial values "helps" the user to enter valid Start and End Time by showing them the correct format and Business Hours.
     * <p>
     * The contactCombo combo box is populated by calling the <code>getAllContacts()</code> method from the <code>DBContact</code> class.
     * The customerIdCombo combo box is populated by calling the <code>getAllCustomers()</code> method from the <code>DBCustomer</code> class.
     * The userIdCombo combo box is populated by calling the <code>getAllUsers()</code> method from the <code>DBUser</code> class.
     * @param url location used to retrieve relative paths for root object
     * @param resourceBundle resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Contact> allContacts = DBContact.getAllContacts();
        ObservableList<Customer> allCustomers = DBCustomer.getAllCustomers();
        ObservableList<User> allUsers = DBUser.getAllUsers();

        contactCombo.setItems(allContacts);
        customerIdCombo.setItems(allCustomers);
        userIdCombo.setItems(allUsers);

        startDatePicker.setValue(LocalDate.now());

        LocalTime st = LocalTime.of(8, 00);
        appointmentStartTime.setText(st.toString());

        LocalTime et = LocalTime.of(22,00);
        appointmentEndTime.setText(et.toString());

    }



}
