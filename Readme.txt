==========================================
   Installation
==========================================

1) JDK
2) MongoDB 3.0.X
3) Maven
4) Tomcat
5) Git Repository


==========================================
   Build And Deploy Project
==========================================

1) Build project with maven
	-- mvn clean install

2) Start MongoDB on local machine
	-- change directory to MongoDB\bin
	-- mongod --dbpath=<absolute db directory path of mongo>

3) In this project there is script 
	Package : com.pharmeasy.movies.db.scripts
	Class : DBPopulatorScript

   Run this script class. 
   This will populate data in mongodb from the files movie.xml and Movie-Sites-SF.csv.
   Both file present in data folder of this project.