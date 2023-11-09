package utils;

import javafx.scene.control.Alert;

/**
 * This class contains only one static method.
 * This class could have been avoided by defining the <code>alertDisplay()</code> method in another already defined class.
 * It was created simply to facilitate the comprehension of the code and for structure preferences.
 *
 * @author Myriam Drouin-Sagar
 */
public class DisplayAlert {

    /**
     * Creates a detailed Alert to avoid redundancy in the code.
     * @param alertType the type of alert to display
     * @param header the header message of the alert
     * @param content the content message of the alert
     * @return an instance of the Alert object
     */
    public static Alert alertDisplay(Alert.AlertType alertType, String header, String content ){
        Alert alert = new Alert(alertType);
        alert.setAlertType(alertType);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }
}
