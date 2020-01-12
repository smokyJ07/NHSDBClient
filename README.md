# NHS Database 

This project creates a centralized database for the use of various medical centres. It succesfully connects general practicioners with their medical centres and patients. Simultaneously, administrators can access and view all the non-private information stored for a specific medical centre. 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Installing

Clone client Git Hub repository to your local machine:

```
https://github.com/smokyJ07/NHSDBServlet.git
```
This by itself is enough to run the program. If desired, the Servlet repository can also be cloned:
```
https://github.com/smokyJ07/NHSDBServlet.git
```
## Test

Different tests and experiments were conducted to examine the performance of different aspects of the servlet and Client methods. 
The tests can be found in the 'Test' folder and 'TestDatabasefunctions.java' file.

## Deployment

'Run' simulation on NHSDBClient IntelliJ file.
Servlet Window with NHS logo message should appear.

## Run
This section guides the user through the different features of the servlet once it is running. 


1. Select 'Log in as Admin'
Insert in fields
- Username: Jonas
- Password: jonas

2. Select 'New patient'
Insert in fields
- Firstname: John
- Lastname: Doe
- Email: john.dow@hotmail.com
- Date of birth: 01/01/1978
- Address: Westminster Palace
- Mobile phone number: 1234-5678
- Full name of the patient's GP: Clara Dhonte

3. Select 'Navigation' -> 'Back to log in'
4. Select 'Log in as GP'
Insert in fields
- Username: clara
- Password: clara

5. Search 
Insert in field
- Patient name: John Doe

6. Select: 'Add Case Record'
Insert in fields
- Title: Headache
- Case Report: Patient presented symptoms of stress and anxiety
Select 'Temporary'
Select 'Add new medication'

Insert in fields
Medication name: Paracetamol
Start date: 01/01/2020
End date: 01/02/2020

7. Select 'Navigation' -> 'Back to patient search'
Insert in fields: 
Patient Name: John Doe
Press 'Search'
Select 'View/Edit case records'

This concludes the demo of how to use the Servlet when its running. 

## Built With

* [Travis CI](https://travis-ci.com/plans) - Hosted continuous integration service
* [Graddle](https://gradle.org/) - Open-source build-automation system
* [Heroku](www.heroku.com/what) - Cloud platform 

## Contributing

Please contact develeppers for contributing. 

## Versioning

For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

**Alejandra Gutierrez** 
**Chloe Orsini** 
**Jonas Rauchhaus** 
**Joao Pereira** 

## Acknowledgments

* Mr. Holloway for patiently teaching all we know
* Our client for trusting us with this task
* The online community for answering our questions promptly and clearly 

