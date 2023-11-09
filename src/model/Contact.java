package model;

/** This class shapes the data for the Contact.
 *
 * @author Myriam Drouin-Sagar
 */
public class Contact {
    private int contactId; //PK
    private String contactName;
    private String email;

    /**
     * Full constructor of the Contact class.
     * @param contactId unique ID for the contact
     * @param contactName the name of the contact
     * @param email the email address of the contact
     */
    public Contact(int contactId, String contactName, String email){
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Gets the ID value of the contact.
     * @return the id value of the contact
     */
    public int getContactId(){
        return contactId;
    }

    /**
     * Sets the contact ID.
     * @param contactId the contact ID to set
     */
    public void setContactId(int contactId){
        this.contactId = contactId;
    }

    /**
     * Gets the name value of the contact
     * @return the name value of the contact
     */
    public String getContactName(){
        return contactName;
    }

    /**
     * Sets the contact name.
     * @param contactName the contact name to set
     */
    public void setContactName(String contactName){
        this.contactName = contactName;
    }

    /**
     * Gets the email value of the contact.
     * @return the email value of the contact
     */
    public String getEmail(){
        return email;
    }

    /**
     * Sets the contact email.
     * @param email the contact email to set
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Overriding method to represent the contact object as a string for combo boxes.
     * Mainly used to populate combo boxes.
     * @return the contact ID and contact name as a string representation of the contact
     */
    @Override
    public String toString() {
        return ("[" + contactId + "]" + " " + contactName);
    }
}
