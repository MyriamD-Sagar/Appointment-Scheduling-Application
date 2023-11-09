package main;

import database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class is the Main class, which extends the <code>javaFX Application</code> class.
 *
 * @author Myriam Drouin-Sagar
 */
public class Main extends Application {
    private final ResourceBundle rb = ResourceBundle.getBundle("resources/Languages", Locale.getDefault());

    /**
     * Overrides the start method in the <code>javaFX Application</code> class and loads the first screen (login form).
     *
     * @param loginForm sets the stage for the program
     * @throws Exception tells the compiler that exception might cause this method to suddenly exit
     */
    @Override
    public void start(Stage loginForm) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        loginForm.setTitle(rb.getString("LoginForm"));
        loginForm.setScene(new Scene(root, 400, 400));
        loginForm.show();

    }

    /**
     * Allows the application to begin, and to run the class from an IDE.
     * <p>
     * This method initializes the database connection and calls the <code>launch()</code> method defined in the <code>javaFx Application</code> to launch the application.
     * @param args arguments that launches the application
     * @throws SQLException provides information in case of database access errors
     */
    public static void main(String[] args) throws SQLException {
        DBConnection.openConnection();

        launch(args);


        DBConnection.closeConnection();
    }


}
