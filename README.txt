
### Author: Ameya Dhamanaskar

This readme explain's how to use the Zendesk Ticket Viewer for "Zendesk Intern Coding Challenge", 
Here is the link to the GitHub repo. 
https://github.com/nudlesoup/zccticketviewer

Here is the link to the google drive: 
https://drive.google.com/drive/folders/1YHDedILHIh2CI2TWy5VxYVOim-lUgLtC?usp=sharing

# Zendesk CLI Ticket Viewer (To do's)
  ---------------------
Your company needs you to build a Ticket Viewer that will:
* Connect to the Zendesk API
* Request all the tickets for your account
* Display them in a list
* Display individual ticket details
* Page through tickets when more than 25 are returned

# Getting Started (Installation & Guide)
  ----------------
1. Just to run my Zendesk CLI Application(only access to Ameya's Tickets), Download this file : Ameya-zendeskticketviewer.jar, from : https://github.com/nudlesoup/zccticketviewer or on drive: https://drive.google.com/file/d/1m1YK6iro54RyOz1c_Rtm9PGkTDq--KgC/view?usp=sharing.
2. After Downloading the file, go the the downloaded folder where the jar file is downloaded & run the following command :  java -jar Ameya-zendeskticketviewer.jar
3. To run any Zendesk CLI Application(access any user's Zendesk allowed tickets), Download this file : Testers-zendeskticketviewer.jar, from : https://github.com/nudlesoup/zccticketviewer go the the downloaded folder where the jar file is downloaded & run the following 3 arguments command(your personal Zendesk id, Zendesk password and subdomain) :  
java -jar Testers-zendeskticketviewer.jar user_emailid password subdomain


These further instructions are for you to make a copy of this project, set it up and running on your local machine in an IDE like eclipse and check the codebase, Unit Testing and design :)
## Pre-requisites: 
JAVA 8, JDK and JRE and an IDE(Eclipse)
1. Download the project from either GitHub : https://github.com/nudlesoup/zccticketviewer or Drive : https://drive.google.com/drive/folders/1YHDedILHIh2CI2TWy5VxYVOim-lUgLtC?usp=sharing . And extract the zip file to a folder.
2. Open Eclipse IDE, Go to : File -> Open project from file system -> import source choose Directory...(Choose the extracted dir ex : /Users/yourusername/Downloads/zccticketviewer-main)->Finish
3. Setup your environment with your own username, password and subdomain inside : src/resources/config.properties. Currently set to my account.
4. Go to : zccticketviewer-main/src/main/java/ticketview/zenticketviewer/view/Menu.java to run the application. Right Click and run as JAVA Application inside eclipse.
5. For Unit Testing, there are 4 files for unit testing of methods of different class (HttpRequestsTest.java ,JsonParserTest.java, TicketListTest.java, TicketTest.java). Go to -> zccticketviewer-main/src/test/java/ticketview/zenticketviewer/. Right click and run each file as JUnit Test. To run test for each function in the file separately, Open the file for right click on the function names and run JUNIT tests individually.
6. For Running all Unit Tests together, Just execute TestRunner.java as a java application and it runs through all unit tests.
										
# Important Information on class files and the functions :
  --------------------
This project includes the followings files:
- Source files are located under : zccticketviewer-main/src/main/java/ticketview/zenticketviewer/
Inside Source files we have view, controller, and model.
* View has menu.java (This class is the user interface using CLI)
* Controller has HTTPRequests.java (This class responsible for passing HTTP request and return the request response), JsonStringParser.java (This class responsible for parsing a given Json content and return the object parsed with data) and TicketList.java (This class represents tickets list and paging indicator enable paging through the list and provide an API compatible with TicketList)
* Model has Ticket.java (This class represents a single ticket in the system and provide an API compatible with Ticket)

- Test files are located under : zccticketviewer-main/src/test/java/ticketview/zenticketviewer/


Source files is 'src' directory (Menu.java, Ticket.java, TicketList.java, HttpRequests.java, JsonStringParser.java)
Unit tests files (TicketTest.java, TicketListTest.java, HttpRequestsTest.java, JsonParserTest.java)
This README 

# Unit Testing
  ------------
## Running the test:
Check steps 4,5 above.


Super extra instruction to deploy production zendesk ticket viewer jars:
1. Haven maven installed on the terminal, Go to the zccticketviewer-main Folder and run : mvn clean compile assembly:single. You will find the new deployed jar under target named zendeskticketviewer-jar-with-dependencies.jar :)
					
# Build With
  ----------
This tool tested with:
java version: Java 8
Junit version: 4.11
OS: Mac


Contact info: adhamana@asu.edu, https://www.linkedin.com/in/ameyad95, https://nudlesoup.github.io 

