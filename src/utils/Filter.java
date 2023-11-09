package utils;

import database.DBAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * This class contains methods that filter data for the appointment table view in the <code>AppointmentView</code> controller class.
 *
 * @author Myriam Drouin-Sagar
 */
public class Filter {

    private static final ZoneId localZone = ZoneId.systemDefault();

    /**
     * Verifies if the appointment's date falls within the current week.
     * <p>
     * The current week is from Monday to Sunday.
     * To fall within the current week, the appointment's date must be equal OR after Monday AND equal OR before Sunday.
     * @param date the appointment's date
     * @return <code>true</code> if the appointment's date falls within the current week, and <code>false</code> if not
     */
    public static Boolean weekFilter(LocalDate date) {
        LocalDate monday = LocalDate.now(localZone);
        LocalDate sunday = LocalDate.now(localZone);

        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);

        }
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sunday = sunday.plusDays(1);
        }

        return ((date.isEqual(monday)) || (date.isAfter(monday))) && ((date.isEqual(sunday)) || (date.isBefore(sunday)));
    }

    /**
     * Verifies if the appointment's date falls within the current month.
     * <p>
     * To fall within the current month, the appointment's date (month and year) must be equal to the current month AND current year.
     * @param date the appointment's date
     * @return <code>true</code> if the appointment's date fall within the current month, and <code>false</code> if not
     */
    public static Boolean monthFilter (LocalDate date){
        LocalDate dayMonth = LocalDate.now(localZone);
        return (date.getMonth() == dayMonth.getMonth()) && (date.getYear() == dayMonth.getYear());
    }

    /**
     * Filters the list of all the appointments from the database into a list containing only the appointment(s) falling within the current week.
     * <p>
     * This method calls the <code>weekFilter()</code> method to determine the appointment(s) to add to the <code>weekAppointmentList</code> ObservableList.
     * The ObservableList <code>weekAppointmentList</code> will be utilized to view appointment schedules by week in the <code>AppointmentView</code> controller class.
     * @return the list of appointment(s) falling within the current week
     */
    public static ObservableList<Appointment> weekAppointments() {
        ObservableList<Appointment> allAppointments = DBAppointment.getAllAppointments();
        ObservableList<Appointment> weekAppointmentList = FXCollections.observableArrayList();
        for (Appointment appointment : allAppointments) {
            LocalDate appointmentDate = appointment.getStarting().toLocalDate();
            if (weekFilter(appointmentDate)) {
                weekAppointmentList.add(appointment);
            }
        }
        return weekAppointmentList;

    }

    /**
     * Filters the list of all the appointments from the database into a list that contains the appointment(s) falling within the current month.
     * <p>
     * Calls the <code>monthFiler()</code> method by passing the appointment start date to determine the appointment(s) to add to the <code>monthAppointmentList</code>.
     * This list will be utilized to view appointment schedules by month in the <code>AppointmentView</code> controller class.
     * @return the list of appointment(s) falling withing the current month
     */
    public static ObservableList<Appointment> monthAppointments(){
        ObservableList<Appointment> allAppointments = DBAppointment.getAllAppointments();
        ObservableList<Appointment> monthAppointmentList = FXCollections.observableArrayList();
        for(Appointment appointment : allAppointments){
            LocalDate appointmentDate = appointment.getStarting().toLocalDate();
            if(monthFilter(appointmentDate)){
                monthAppointmentList.add(appointment);
            }
        }
        return monthAppointmentList;
    }
}
