package controller;

import database.DBAppointment;
import database.DBUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.LoginFileInterface;
import model.Appointment;
import model.User;
import utils.DisplayAlert;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller class that allows the user to log into the Appointment Scheduling System, and that utilizes Resource Bundle Property files.
 * <p>
 * This class declares a class variable (rb) to load the appropriate Resource bundle class in <code>Resource Bundle 'Languages'</code> according to the user's computer language setting.
 * There is 2 property available for translation: one for English translation (Languages_en.properties) and one for French translation(Languages_fr.properties).
 * <p>
 * From this, the methods in this class can translate the different labels in the Login Form by using the key-value pairs previously defined in the Resource Bundle Property files.
 * <p>
 * Implements the <code>Initializable</code> interface.
 *
 * @author Myriam Drouin-Sagar
 */
public class LoginForm implements Initializable {
    public TextField userNameText;
    public TextField passwordText;
    public Button loginButton;
    public Label usernameLabel;
    public Label passwordLabel;
    public Label welcomeMessage;
    public Label locationLabel;
    private final ResourceBundle rb = ResourceBundle.getBundle("resources/Languages", Locale.getDefault());
    private static final ZoneId localZone = ZoneId.systemDefault();
    private static final String zone = localZone.toString();
    public static User currentUser;

    /**
     * Sets the different labels seen on the user login screen by utilizing the Resource Bundle Property file that matches the user's default language setting.
     * <p>
     * The <code>Initialize()</code> method calls this method and populates the labels with the appropriate key-value pairs defined in the Bundle associate to the user's computer language setting.
     * It also displays the user's location in the locationLabel.
     */
    private void initializeLabels(){
        locationLabel.setText(rb.getString("Location") + ": " + zone);
        welcomeMessage.setText(rb.getString("Welcome"));
        usernameLabel.setText(rb.getString("Username"));
        passwordLabel.setText(rb.getString("Password"));
        loginButton.setText(rb.getString("Login"));

    }

    /**
     * Loads the <code>AppointmentView</code> to redirect the user to the Appointment Scheduling System (if the login is successful), and implements 2 lambda expressions.
     * <p>
     * First, it creates a FileWriter object to append data to a file (login_activity_text), then the fileWriter object is passed as an argument to the <code>PrintWriter()</code> constructor.
     * The PrintWriter object (outputPrinter) will then write data to the file by calling the <code>println()</code> method.
     * <p>
     * LAMBDA EXPRESSION EXPLANATION/JUSTIFICATION:
     * The lambda expression first implements the <code>LoginFileInterface</code> interface by creating an object (message) that will automatically implement the interface.
     * The lambda expression utilizes the outputPrinter object to output the desired data to the file created.
     * Then, the reference variable (message) calls the LoginFileInterface <code>printMessage()</code> abstract methods multiple times with the appropriate arguments.
     * This lambda expression is useful to make sure the appropriate message is appended to the file without writing the String over and over again.
     * <p>
     * Second, If the user is find in the database by calling the <code>findUser()</code> method from the <code>DBUser</code> class, an object of the User (currentUser) is returned.
     * Then, a list of allAppointments is retrieved from the database by calling the <code>getAllAppointment()</code> method.
     * <p>
     * LAMBDA EXPRESSION EXPLANATION/JUSTIFICATION:
     * The second lambda expression of this method filters the list of allAppointments and fills an ObservableList (appointmentByUserId) with the Appointments objects that matches the currentUser ID.
     * This list is then searched to find and display upcoming appointment(s) (within 15 minutes of the user's login) associated with the currentUser.
     * This lambda expression radically reduces the length of the code for this method.
     * <p>
     * @param actionEvent event generated by the loginButton when the user clicks on the button labeled "Login"
     * @throws IOException from the FXMLLoader
     */
    public void onLogin(ActionEvent actionEvent) throws IOException {
        LocalDateTime currentTime = LocalDateTime.now(localZone);
        LocalDateTime currentTimePlus15 = currentTime.plusMinutes(15);
        ObservableList<Appointment> allAppointments = DBAppointment.getAllAppointments();

        String user1 = userNameText.getText();
        String passW1 = passwordText.getText();

        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter outputWriter = new PrintWriter(fileWriter);
        LoginFileInterface message = (login, t, z, name) -> outputWriter.println(login + " at " + t + " (" + z + ") for User: " + name);

            if ((user1.isEmpty()) || (passW1.isEmpty())) {

                message.printMessage("Login Failed", Timestamp.valueOf(currentTime), zone, user1);
                outputWriter.close();

                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle(rb.getString("Error"));
                alertError.setHeaderText(rb.getString("fieldEmpty"));
                alertError.setContentText(rb.getString("fieldEmptyMessage"));
                alertError.showAndWait();
                return;
            }
            currentUser = DBUser.findUser(user1, passW1);
            if (currentUser == null) {

                message.printMessage("Login Failed", Timestamp.valueOf(currentTime), zone, user1);
                outputWriter.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rb.getString("Information"));
                alert.setHeaderText(rb.getString("userNotFound"));
                alert.setContentText(rb.getString("pleaseEnterValid"));
                alert.showAndWait();
                userNameText.clear();
                passwordText.clear();
                return;
            }

        int currentUserId = currentUser.getUserId();
        Stream<Appointment> appointmentList = allAppointments.stream().filter(a -> a.getUserId() == currentUserId);
        ObservableList<Appointment> appointmentByUserId = appointmentList.collect(Collectors.toCollection(FXCollections :: observableArrayList));

        Appointment appointmentUpcoming = null;
        for (Appointment appointment : appointmentByUserId) {
           if((appointment.getStarting().isAfter(currentTime)) && ((appointment.getStarting().isBefore(currentTimePlus15)) || appointment.getStarting().isEqual(currentTimePlus15))) {
                appointmentUpcoming = appointment;
            }
        }
        if (appointmentUpcoming != null) {
            Alert alertInfo = DisplayAlert.alertDisplay(Alert.AlertType.INFORMATION, "You have an upcoming appointment\nwithin the next 15 minutes.",
                    "Appointment ID: " + appointmentUpcoming.getAppointmentId() +
                            "\nDate: " + appointmentUpcoming.getStarting().toLocalDate() +
                            "\nTime: " + appointmentUpcoming.getStarting().toLocalTime() + " To " + appointmentUpcoming.getEnding().toLocalTime());
            alertInfo.showAndWait();
        } else {
            Alert alertInfo2 = DisplayAlert.alertDisplay(Alert.AlertType.INFORMATION, "You have no upcoming appointments\nwithin the next 15 minutes.", null);
            alertInfo2.showAndWait();
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentView.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1310, 600);
        stage.setTitle("Appointments Scheduling");
        stage.setScene(scene);
        stage.show();

        message.printMessage("Login successful", Timestamp.valueOf(currentTime), zone, user1);
        outputWriter.close();
    }

    /**
     * Calls the <code>initializeLabels()</code> method.
     * <p>
     * First method to run in this class when the FXMLLoader calls the <code>LoginForm</code> FXML file.
     *
     * @param url location to retrieve relative paths for root object
     * @param resourceBundle resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeLabels();

    }


}
