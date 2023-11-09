package model;

/**
 * Shapes the data for the ReportCountry.
 * This class was created to model the data for the report presenting the total number of customer by country.
 *
 * @author Myriam Drouin-Sagar
 */
public class ReportCountry {
    private String countryName;
    private int count;

    /**
     * Full constructor of the ReportCountry class.
     *
     * @param countryName the country name
     * @param count customer count by country
     */
    public ReportCountry(String countryName, int count){
        this.countryName = countryName;
        this.count = count;
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
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Gets the customer count value associated with the country.
     * @return the customer count value associated with the country
     */
    public int getCount(){
        return count;
    }

    /**
     * Sets the customer count by country.
     * @param count the customer count by country to set
     */
    public void setCount(int count) {
        this.count = count;
    }
}
