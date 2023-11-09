package model;

/**
 * This class shapes the data for the FirstDivision.
 *
 * @author Myriam Drouin-Sagar
 */
public class FirstDivision {
    private int divisionId; //PK
    private String divisionName;
    private int countryId; //FK

    /**
     * Full constructor of the FirstDivision class.
     *
     * @param divisionId the unique first-level-division ID
     * @param divisionName the name of the first-level-division
     * @param countryId the country ID associated with the first-level-division
     */
    public FirstDivision(int divisionId, String divisionName, int countryId){
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    /**
     * Gets the ID value of the first-level-division.
     * @return the id value of the first-level-division
     */
    public int getDivisionId(){

        return divisionId;
    }

    /**
     * Sets the first-level-division ID.
     * @param divisionId the first-level-division to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Gets the name value of the first-level-division.
     * @return the name value of the first-level-division
     */
    public String getDivisionName(){
        return divisionName;
    }

    /**
     * Sets the first-level-division name.
     * @param divisionName the first-level-division name to set
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Gets the ID value of the country associated with the first-level-division.
     * @return the id value of the country
     */
    public int getCountryId(){
        return countryId;
    }

    /**
     * Sets the country ID to associated with the first-level-division.
     * @param countryId the country ID to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Overrides the <code>toString()</code> method to represent the FirstDivision as a chosen string.
     * Mainly used to populate combo boxes in the <code>AddCustomerForm</code> and the <code>UpdateCustomerForm</code>.
     * @return the first-level-division ID and name as a string representation of the FirstDivision
     */
    @Override
    public String toString(){
        return "[" + divisionId + "]" + " " + divisionName;
    }
}
