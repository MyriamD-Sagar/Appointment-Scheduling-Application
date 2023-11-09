package utils;

import java.time.*;


/**
 * This class contains methods to validate user date and time inputs.
 *
 * @author Myriam Drouin-Sagar
 */
public class Time {
    private static final ZoneId estZone = ZoneId.of("US/Eastern");

    /**
     * Verifies if the appointment's start date and time is before the appointment's end date and time.
     * <p>
     * This method make sure the user cannot enter an appointment time that doesn't make sense chronologically.
     * @param start the appointment's start date and time
     * @param end the appointment's end date and time
     * @return <code>true</code> if start date and time is before the end date and time, and <code>false</code> if equal OR after
     */
    public static Boolean verifyStartEnd (LocalDateTime start, LocalDateTime end){
        return start.isBefore(end);
    }

    /**
     * Verifies if the appointment's time is within the Business Hours.
     * <p>
     * The comparison is made to allow only appointment's Start Time and End Time sets after the business starts (08:00 eastern time), and before the business ends (22:00 eastern time).
     * <p>
     * Since the program can be launch from anywhere, this method gets the user's default <code>ZoneID</code>.
     * Then, the appointment's Start and End date-times entered by the user are converted to the same <code>ZoneID</code> (<code>estZone</code> declared as class variable) as the Business Hours.
     *
     * @param start the appointment's start date-time
     * @param end the appointment's end date-time
     * @return <code>true</code> if the appointment's Start and End times are within the Business Hours, and <code>false</code> if not.
     */
    public static Boolean verifyBusiness(LocalDateTime start, LocalDateTime end){
        ZonedDateTime DTStartZone = ZonedDateTime.of(start,ZoneId.systemDefault());
        ZonedDateTime DTEndZone = ZonedDateTime.of(end,ZoneId.systemDefault());
        ZonedDateTime StartToEst = DTStartZone.withZoneSameInstant(estZone);
        ZonedDateTime EndToEst = DTEndZone.withZoneSameInstant(estZone);

        LocalTime startEstTime = StartToEst.toLocalTime();
        LocalTime endEstTime = EndToEst.toLocalTime();

        LocalTime businessStart = LocalTime.of(8,0,0);
        LocalTime businessEnd = LocalTime.of(22,0,0);
        return !startEstTime.isBefore(businessStart) && !startEstTime.isAfter(businessEnd) && !endEstTime.isBefore(businessStart) && !endEstTime.isAfter(businessEnd);



    }
}


