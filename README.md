# Appointment-Scheduling-Application

 
The purpose of this application is to create a GUI-based application following a company's requirements.



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
 
  
  

 







 
