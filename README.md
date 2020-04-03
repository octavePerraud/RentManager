


Sujet

	The objective of this program is to be able to manage reservations, a customer list and a 	vehicle list.	
	for each of this object it's possible to create, delete, edit and see the details.
	
	constraint:
		client: - name a lastname at least 3 characters
				- at least 18 years
				- a different email for everyone 
			
		vehicle: - number of seats between 3 and 9
				 - constructeur not null
				 
		reservation: - a vehicle can not be reserved for more than 7 days per reservation
					 - not reserved 30 days in a row

Run the project
	
	1. start a server with: mvn clean install tomcat7:run
		in the project with control terminal
	2. go one the url "http://localhost:8080/RentManager/home"
	
						 

Made with

	1. maven
	2. tomcat7
	3. java 1.8.0
	4. jdk 12.0.2
	5. eclipse JEES
	
	
Test

	the package Controler allows you to manually test the functions
	some tests are automatically run when the server are run.
	

	