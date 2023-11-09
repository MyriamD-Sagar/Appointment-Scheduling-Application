package model;


/**
 * This class shapes the data for the Country.
 *
 * @author Myriam Drouin-Sagar
 */
public class Country {
    private int countryId; // PK
    private String countryName;

    /**
     * Full constructor of the Country class.
     *
     * @param countryId unique ID for the country
     * @param countryName the name of the country
     */
    public Country(int countryId, String countryName){
        this.countryId =countryId;
        this.countryName =countryName;}

    /**
     * Gets the ID value of the country.
     * @return the ID value of the country
     */
    public int getCountryId(){
        return countryId;
    }

    /**
     * Sets the country ID.
     * @param countryId the country ID to set
     */
    public void setCountryId(int countryId){ //might not need
        this.countryId = countryId;
    }

    /**
     * Gets the name value of the country.
     * @return the name value of the country
     */
    public String getCountryName(){
        return countryName;
    }

    /**
     * Sets the country name.
     * @param countryName the country name to set
     */
    public void setCountryName(String countryName){ // might not need
        this.countryName = countryName;
    }

    /**
     * Overrides the <code>toString()</code> method to represent the country object as a string.
     * Mainly used to populate combo boxes.
     * @return the country ID and contact name as a string representation of the country
     */
    @Override
    public String toString(){
        return("[" + countryId + "]" + " " + countryName);
    }

}
