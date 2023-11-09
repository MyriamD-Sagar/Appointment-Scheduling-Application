package main;

import java.sql.Timestamp;

/**
 * Declaration of the functional interface LoginFileInterface that will be implemented by a lambda expression to append data to a text file.
 *
 * @author Myriam Drouin-Sagar
 */
public interface LoginFileInterface {
    /**
     * Abstract method called in the <code>LoginForm</code> controller class.
     * @param login login failed or successful login
     * @param t the time when the user attempt to log in
     * @param z user time zone as a string
     * @param name username of the user
     */
    void printMessage(String login, Timestamp t, String z, String name);
}
