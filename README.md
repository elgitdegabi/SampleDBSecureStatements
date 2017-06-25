# SampleDBSecureStatements #
## Disclaimer ##
This is not a commercial tool. It was development for test purpose only so doesn't have any warranty.

Feel free to test, use and/or modify.

For more details see the LICENSE file.

## Description ##
Use parameters in a JAVA SQL Prepared Statement is a good practice for performance reasons but also is good way to improve the security of your application.

* Imagine that you have the query " SELECT * FROM USER_TEST WHERE USER = " and you add the "user" parameter a the end of the statement in your code to get the user's details.  
* Imagine you expose this service with a restful end-point like: http://localhost:8080/user/info?user=User3  
* The potential problem is that someone could exploit the "bad design", replace the user parameter for http://localhost:8080/user/info?user=User3' OR 'A'='A and get all the details from all users.
* But if you use parameters like " SELECT * FROM USER_TEST WHERE USER = ?" you can solve this security hole in your application (and, of course, improve the performance of your query) 

If you found a better solution for one or more cases or you want to share your best practices with us, please, let me know and I will update this project.
 
## Features/Tips ##
* Includes H2 in memory database (check application.properties for the configuration values). You can access to console by "/h2" end point
* Includes a sample of controller-> service -> DAO flow
* Includes Unit Tests using:
	* Mockito for Spring Boot 1.4 or higher
* Tested with Spring Boot versions:
	* 1.5.4.RELEASE

## End-points ##
### GET end-point: ###
http://localhost:8080/h2

http://localhost:8080/user/info?user=[User Value]

## GitHub repository: ##
### https://github.com/Gabotto/SampleDBSecureStatements ###
## Contact ##
Let me know if you have any problem, comment or new ideas:
#### Wordpress: http://gabelopment.wordpress.com/ ####
#### Email: gabelopment@gmail.com ####

Edited on: 20th June 2017
