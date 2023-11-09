package model;

/**
 * This class shapes the data for the Customer.
 *
 * @author Myriam Drouin-Sagar
 */
public class Customer {
    private int customerId; //PK
    private int divisionId; //FK
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private String divisionName;
    private String countryName;
    private int countryId;

    /**
     * Full constructor of the Customer class.
     *
     * @param customerId unique ID for the customer
     * @param customerName name of the customer
     * @param address address of the customer
     * @param postalCode postal code of the customer
     * @param phone phone number of the customer
     * @param divisionId ID of the first-level-division where the customer lives
     * @param divisionName name of the first-level-division where the customer lives
     * @param countryID ID of the country associated with the first-level-division where the customer lives
     * @param countryName name of the country where the customer lives
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phone, int divisionId, String divisionName, int countryID, String countryName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionName = divisionName;
        this.countryName = countryName;
        this.divisionId = divisionId;
        this.countryId = countryID;
    }

    /**
     * Constructor of the Customer class.
     * This constructor is useful when populating combo boxes instead of using the full constructor.
     * <p>
     * Another option would have been to omit this constructor, and simply insert null values (or 0) when instantiating the customer class.
     * @param customerId the unique ID of the customer
     * @param customerName the name of the customer
     */
    public Customer(int customerId, String customerName){
        this.customerId = customerId;
        this.customerName = customerName;
    }

    /**
     * Default constructor.
     */
    public Customer() {

    }

    /**
     * Gets the ID value for the customer.
     * @return the customer ID value
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer ID.
     * @param customerId the customer ID to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the name value of the customer.
     * @return the name value of the customer
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the customer name.
     * @param customerName the customer name to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the address value of the customer.
     * @return the address value of the customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the customer address.
     * @param address the customer address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the postal code value of the customer.
     * @return the postal code value of the customer
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the customer postal code.
     * @param postalCode the customer postal code to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the phone number value of the customer.
     * @return the phone number value of the customer
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the customer phone number.
     * @param phone the customer phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the ID value of the first-level-division associated with the customer.
     * @return the ID value of the division associated with the customer
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the first-level-division ID to associate with the customer.
     * @param divisionId the division ID to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Gets the name value of the first-level-division associated with the customer.
     * @return the name value of the first-level-division associated with the customer
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Sets the first-level-division name to associate with the customer.
     * @param divisionName the division name to set
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Gets the name value of the country associated with the country ID of the customer.
     * @return the name value of the country
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the country name to associate with the customer.
     * @param countryName the country name to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Gets the ID value of the country associated with the first-level-division ID of the customer.
     * @return the ID value of the country
     */
    public int getCountryID() {
        return countryId;
    }

    /**
     * Sets the country ID to associate with the customer.
     * @param countryId the country ID to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Overrides the <code>toString()</code> to represent the customer object as a particular string.
     * This method is mainly used to populate combo boxes.
     * @return the customer ID and the customer name as a string representation of the customer
     */
    @Override
    public String toString() {
        return ("[" + customerId + "]" + " " + customerName);
    }
}
