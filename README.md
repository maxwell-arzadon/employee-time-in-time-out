# MidtermProject4_730-900MTh



## Midterm Project: Daily Time Records

CIS-2-9448-IT222(Integrative Technologies Lab)


## Problem Specification

In a small company composed of several employees, a systems administrator has given you the task to
create an RMI application that will allow recording of daily time records (attendance) of employees. This
is how the administrator envisions the system to be created:
    
    1. When an employee comes in for work, he turns his computer on and opens the application to do a "time in" by supplying his/her user name and password and then logging in and clicking on the time-in button. After timing in, he is allowed to close the application.
    
    2. The employee will have to click the “time out” and “time in” upon lunch break and at the end of the day, he is required to "time out".
    
    3. On the side of an employee, he/she can monitor his/her logged records for the day. On the side of the admin panel, he/she can generate a report pertaining to the logged records of all employees by specifying a date range.

## Client Features
1. Create a GUI that requires two values (username and password) with a button to login and a button to
register.
2. Upon successful login, three buttons should be displayed:

    • "Time in" button

    • "Time out" button (One of the buttons should be disabled based on the status of the employee – working, break)

    • "Summary" button (this should show the daily time records with the following columns)
    1. Date
    2. Time in
    3. Time out

## Server Features
1. JSON file/s will be used by the server to store all pertinent information needed in the application
(example: storing of employee credentials, logging of time records, etc.).
2. The GUI on the admin side requires a log in page. Once logged in, the admin will be able to confirm
registration requests coming from the client application. The admin may accept or reject the
registrations from the clients.
Note: The application will not permit the submission of registrations with user names that are
already in the list of confirmed users.
3. Aside from the confirmation of registered users, the admin will be able to see the status of all
employees.
4. The admin will also have the capability to generate report on the accumulated hours rendered by all
employees in a given period of time. The period (start date and end date) can be selected by the
admin. The breakdown of time logs for every employee will have to be presented first prior to the
total hours rendered.