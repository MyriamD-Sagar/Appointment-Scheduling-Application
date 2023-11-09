package model;

import java.time.LocalDateTime;

/**
 * This class shapes the data for the Appointment.
 *
 * @author Myriam Drouin-Sagar
 */
public class Appointment {

    private int appointmentId; //PK
    private String title;
    private String description;
    private String location;
    private int contactId; //FK
    private String contactName;
    private String type;
    private LocalDateTime starting;
    private LocalDateTime  ending;
    private int customerId; //FK
    private String customerName;
    private int userId; //FK
    private String userName;

    /**
     * Full constructor of the Appointment class.
     *
     * @param appointmentId  unique ID for the appointment
     * @param title title of the appointment
     * @param description description of the appointment
     * @param location  location of the appointment
     * @param contactId ID of the contact assigned to the appointment
     * @param contactName name of the contact assigned to the appointment
     * @param type type of appointment
     * @param starting start date and time of the appointment
     * @param ending end date and time of the appointment
     * @param customerId ID of the customer associated to the appointment
     * @param customerName name of the customer associated to the appointment
     * @param userId ID of the user assigned to the appointment
     * @param userName name of the user assigned to the appointment
     */
    public Appointment(int appointmentId, String title, String description, String location,int contactId, String contactName, String type, LocalDateTime  starting, LocalDateTime  ending, int customerId, String customerName, int userId, String userName){
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.contactName = contactName;
        this.type = type;
        this.starting = starting;
        this.ending = ending;
        this.customerId = customerId;
        this.customerName = customerName;
        this.userId = userId;
        this.userName = userName;
    }
    /** Default constructor of the Appointment class.*/
    public Appointment(){
    }

    /**
     * Gets the appointment ID value.
     * @return the appointment ID value
     */
    public int getAppointmentId(){
        return appointmentId;
    }

    /**
     * Sets the appointment ID.
     * @param appointmentId the appointment ID to set
     */
    public void setAppointmentId(int appointmentId){
        this.appointmentId = appointmentId;
    }

    /**
     * Gets the title value.
     * @return the title value
     */
    public String getTitle(){
        return title;
    }

    /**
     * Sets the title.
     * @param title the title to set
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Gets the description value.
     * @return the description value
     */
    public String getDescription(){
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the location value.
     * @return the location value
     */
    public String getLocation(){
        return location;
    }

    /**
     * Sets the location.
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the ID value of the customer assigned to the appointment.
     * @return the contact ID value
     */
    public int getContactId(){
        return contactId;
    }

    /**
     * Sets the ID of the contact assigned to the appointment.
     * @param contactId the contact ID to set
     */
    public void setContactId(int contactId){
        this.contactId = contactId;
    }

    /**
     * Gets the name value of the contact assigned to the appointment.
     * @return the contact's name value
     */
    public String getContactName(){
        return contactName;
    }

    /**
     * Sets the name of the contact associated to the appointment.
     * @param contactName the contact's name to set
     */
    public void setContactName(String contactName){
        this.contactName = contactName;
    }

    /**
     * Gets the appointment's type value.
     * @return the appointment's type
     */
    public String getType(){
        return type;
    }

    /**
     * Sets the appointment's type.
     * @param type the appointment's type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the appointment's start (date and time) value.
     * @return the appointment's start value
     */
    public LocalDateTime getStarting(){
        return starting;
    }

    /**
     * Sets the appointment's start (date and time).
     * @param starting the appointment's start date and time to set
     */
    public void setStarting(LocalDateTime  starting){
        this.starting = starting;
    }

    /**
     * Gets the appointment's end (date and time) value.
     * @return the appointment's end date and time value
     */
    public LocalDateTime getEnding(){
        return ending;
    }

    /**
     * Sets the appointment's end (date and time).
     * @param ending the appointment's end date and time to set
     */
    public void setEnding(LocalDateTime  ending){
        this.ending = ending;
    }

    /**
     * Gets the ID value of the customer associated with the appointment.
     * @return the customer ID value
     */
    public int getCustomerId(){
        return customerId;
    }

    /**
     * Sets the ID of the customer associated with the appointment.
     * @param customerId the customer ID to set
     */
    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }

    /**
     * Gets the name value of the customer associated with the appointment.
     * @return the customer's name value
     */
    public String getCustomerName(){
        return customerName;
    }

    /**
     * Sets the name of the customer associated with the appointment.
     * @param customerName the customer's name to set
     */
    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }

    /**
     * Gets the ID value of the user assigned to the appointment.
     * @return the user ID value
     */
    public int getUserId(){
        return userId;
    }

    /**
     * Sets the ID of the user assigned to the appointment.
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the name value for the user assigned to the appointment.
     * @return the user's name value
     */
    public String getUserName(){
        return userName;
    }

    /**
     * Sets the name for the user assigned to the appointment.
     * @param userName the user's name to set
     */
    public void setUserName(String userName){
        this.userName = userName;
    }

    /**
     * Formats the appointment's start date and time value into a string.
     * This method will be called to populate the appointment's table view.
     * @return the appointments start date and time in a string of format (yyyy-MM-dd hh:mm)
     */
    public String getStartingString(){
        return starting.toLocalDate() + " " + starting.toLocalTime();
    }

    /**
     * Formats the appointment's end date and time value into a string.
     * This method will be called to populate the appointment's table view.
     * @return the appointment end date and time in a string format (yyyy-MM-dd hh:mm)
     */
    public String getEndingString(){
        return ending.toLocalDate() + " " + ending.toLocalTime();
    }


}
