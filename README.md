# Appointment-Scheduling-Application

 
The purpose of this application is to create a GUI-based scheduling desktop application following specific business requirements (see *Requirements* section).



Application Version: 1.0

Date: 1/30/2023


## Author's information

Name: Myriam Drouin-Sagar

Email: midrou_10@hotmail.com



## IDE information

IntelliJ IDEA Community Edition 2021.1.3 x64

JDK version: jdk-17.0.1

Java FX version: javafx-sdk-17.0.1



## Driver information

mysql-connector-j-8.0.31


## Requirements 

 1. Create a log-in form with the following capabilities:
    
    - accepts username and password and provides an appropriate error message
    - determines the user’s location (i.e., ZoneId) and displays it in a label on the log-in form
    - displays the log-in form in English or French based on the user’s computer language setting to translate all the text, labels, buttons, and errors on the form
    - automatically translates error control messages into English or French based on the user’s computer language setting
      
 2. Write code that provides the following customer record functionalities:
    
    - Customer records and appointments can be added, updated, and deleted.
      * When deleting a customer record, all of the customer’s appointments must be deleted fi rst, due to foreignkey constraints.*
    - When adding and updating a customer, text fields are used to collect the following data: customer name,address, postal code, and phone number.
      * Customer IDs are auto-generated, and fi rst-level division (i.e., states, provinces) and country data are collected using separate combo boxes.*
      * When updating a customer, the customer data autopopulates in the form.
    - Country and first-level division data is prepopulated in separate combo boxes or lists in the user interface forthe user to choose. The first-level list should be filtered by the user’s selection of a country.
    - All of the original customer information is displayed on the update form.
      * Customer_ID must be disabled
    - All of the field can be updated except for Customer_ID.
    - Customer data is displayed using a TableView, including first-level division data.
      * A list of all the customers andtheir information may be viewed in a TableView, and updates of the data can be performed in text fields on the form.
    - When a customer record is deleted, a custom message should display in the user interface.
  
  3. Add scheduling functionalities to the GUI-based application by doing the following:
     
     a. Write code that enables the user to add, update, and delete appointments. The code should also include the following functionalities:
     
       - A contact name is assigned to an appointment using a drop-down menu or combo box.
       - A custom message is displayed in the user interface with the Appointment_ID and type of appointment canceled.
       - The Appointment_ID is auto-generated and disabled throughout the application.
       - When adding and updating an appointment, record the following data: Appointment_ID, title, description, location, contact, type, start date and time, end date and time, Customer_ID, and User_ID.
       - All of the original appointment information is displayed on the update form in local time zone.
       - All of the appointment fields can be updated except Appointment_ID, which must be disabled.
         
     b. Write code that enables the user to view appointment schedules by month and week using a TableView and allows the user to choose between these two options using tabs or radio buttons for filtering appointments.
        Please include each of the following requirements as columns:
     
       * Appointment_ID
       * Title
       * Description
       * Location
       * Contact
       * Type
       * Start Date and Time
       * End Date and Time
       * Customer_ID
       * User_ID
         
     c. Write code that enables the user to adjust appointment times. While the appointment times should be stored in Coordinated Universal Time (UTC), they should be automatically and consistently updated according to the local time zone set on the user’s computer    wherever appointments are displayed in the application.
     
     d. Write code to implement input validation and logical error checks to prevent each of the following changes when adding or updating information; display a custom message specific for each error check in the user interface:
     
        * scheduling an appointment outside of business hours defined as 8:00 a.m. to 10:00 p.m. ET, including weekends
        * scheduling overlapping appointments for customers
        * entering an incorrect username and password
          
     e. Write code to provide an alert when there is an appointment within 15 minutes of the user’s log-in. A custom message should be displayed in the user interface and include the appointment ID, date, and time. If the user does not have any appointments within 15 minutes of logging in, display a custom message in the user interface indicating there are no upcoming appointments.

     f. Write code that generates accurate information in each of the following reports and will display the reports inthe user interface:
     
       * the total number of customer appointments by type and month
       * a schedule for each contact in your organization that includes appointment ID, title, type and description, start date and time, end date and time, and customer ID
       * an additional report of your choice that is different from the two other required reports in this prompt and from the user log-in date and time stamp that will be tracked in part C

5. Write at least two different lambda expressions to improve your code.

6. Write code that provides the ability to track user activity by recording all user log-in attempts, dates, and time stamps and whether each attempt was successful in a file named login_activity.txt. Append each new record to the existing file, and save to the root folder of the application.
   
7. Provide Javadoc comments.



## Additional report description  

The additional report included in my application displays the total number of customer by country.
The data displayed in the table view is fetched from the database with the customerBycounty() method in the DBReport class.
This method returns an ObservableList of ReportCountry objects (previously define in model class ReportCountry).
Once the user clicks on the "Reports" button from the AppointmentView form, the data can be seen in the table view labeled as "Total of Customers by country".



## How to run the program

The application will be on the form of a zip file.
On the VM, the user must download and extract all files and note the directory where the project resides.
Once this is done, user opens the project from IntelliJ IDEA and makes sure it is set up with the same JDK and javaFX version described above.
The MySQL Connector driver must also aligned with the Driver Information from above in order for the application to connect properly to the database.

When the user runs the program (MyriamApplication configuration which runs main.Main), a Login window will appear requiring to enter a Username and Password.
Once the credentials entered are verified and approved, the user can access the Desktop Scheduling Application.
From the Appointment Scheduling ("main form" of the program), the user can do the following:
- View Appointments Schedule weekly and monthly
- Delete Appointments
- Update Appointments
- Add Appointments
- View Customers -> add, update, delete
- View Reports
- Log out
To close the entire application, simply close the tab.
 
  
  

 







 
