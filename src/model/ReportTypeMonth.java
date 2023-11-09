package model;

import java.time.Month;

/**
 * Shapes the data for the ReportTypeMonth.
 * This class was created to model the data for the report that presents the total number of customer appointments by type and month.
 *
 * @author Myriam Drouin-Sagar
 */
public class ReportTypeMonth {
    private int month;
    private String type;
    private int count;

    /**
     * Full constructor of the ReportTypeMonth class.
     *
     * @param month the month number (number from 1 to 12) of the appointment start date
     * @param type the type of the appointment
     * @param count the appointment count by type and month
     */
    public ReportTypeMonth(int month, String type, int count){
        this.month = month;
        this.type = type;
        this.count = count;
    }

    /**
     * Gets the month number value of the appointment start date.
     * @return the month number value of the appointment start date
     */
    public int getMonth(){
        return month;
    }

    /**
     * Sets the month number of the appointment start date.
     * @param month the month number to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Gets the type value of the appointment.
     * @return the type value of the appointment
     */
    public String getType(){
        return type;
    }

    /**
     * Sets the appointment type.
     * @param type the appointment type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the appointment count value by type and month.
     * @return the appointment count value by type and month
     */
    public int getCount(){
        return count;
    }

    /**
     * Sets the appointment count by type and month.
     * @param count the appointment count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Extracts the textual name from the month enum in Integer value and formats a string.
     * <p>
     * First the instance of Month is obtained from its integer value, then formatted into a string.
     * This method is called to populate the appropriate table view column in the <code>ReportForm</code>.
     * @return the name of the month as a string with the first letter capitalized
     */
    public String getMonthString(){
        String monthString = Month.of(month).toString().toLowerCase();

        return monthString.substring(0,1).toUpperCase() + monthString.substring(1);
    }
}
