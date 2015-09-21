==========================================
   Installation
==========================================

1) JDK
2) MongoDB 3.0
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
   Ex:-
	Start command prompt in administrator mode

	cd C:\MongoDB\bin
	mongod --dbpath=C:\MongoDB\data\db

3) In this project there is script 
	Package : com.pharmeasy.movies.db.scripts
	Class : DBPopulatorScript

   Run this script class. 
   This will populate data in mongodb from the files movie.xml and Movie-Sites-SF.csv.
   Both file present in data folder of this project.

4) Copy war file(movies-0.0.1-SNAPSHOT.war) from ...\PharmEasy\target\ to tomcat\webapps\
5) Rename this war file to ROOT.war
6) Start tomcat


==========================================
   API Request Response
==========================================

1) http://localhost:8080/v1/movie/city?name=San+Francisco

  This API list out all movie listed in "San Francisco".

  output Structure :-
    [
	{
        "lastUser": null,
        "creationTime": 0,
        "productionComapny": "Twentieth Century Fox Film Corp.",
        "directors": [
            "Joseph L. Mankiewicz"
        ],
        "writers": [
            "Joseph L. Mankiewicz"
        ],
        "title": "All About Eve",
        "lastUpdated": 0,
        "actors": [
            "Bette Davis",
            "Anne Baxter"
        ],
        "deleted": false,
        "createUser": null,
        "locations": [
            {
                "country": "USA",
                "lastUser": null,
                "locationName": "Curran Theater (445 Geary Street)",
                "creationTime": 0,
                "lattitude": 37.795219,
                "city": "San Francisco",
                "lastUpdated": 0,
                "deleted": false,
                "street": "",
                "createUser": null,
                "_id": "55fec660bafd05143c446b7c",
                "state": "CA",
                "longitude": -122.420782
            }
        ],
        "_id": "55fec662bafd05143c446bf6",
        "realeseYear": 1950
    	}
       

       ...
       ...

    }


2) http://localhost:8080/v1/movie/title?name=All+About+Eve

   This API list out all entry of the title "All About Eve"

   output Structure :-
   [
    {
        "lastUser": null,
        "creationTime": 0,
        "productionComapny": "Twentieth Century Fox Film Corp.",
        "directors": [
            "Joseph L. Mankiewicz"
        ],
        "writers": [
            "Joseph L. Mankiewicz"
        ],
        "title": "All About Eve",
        "lastUpdated": 0,
        "actors": [
            "Bette Davis",
            "Anne Baxter"
        ],
        "deleted": false,
        "createUser": null,
        "locations": [
            {
                "country": "USA",
                "lastUser": null,
                "locationName": "Curran Theater (445 Geary Street)",
                "creationTime": 0,
                "lattitude": 37.795219,
                "city": "San Francisco",
                "lastUpdated": 0,
                "deleted": false,
                "street": "",
                "createUser": null,
                "_id": "55fec660bafd05143c446b7c",
                "state": "CA",
                "longitude": -122.420782
            }
        ],
        "_id": "55fec662bafd05143c446bf6",
        "realeseYear": 1950
     }
    ]


3) http://localhost:8080/v1/search/suggest?s=a

   This is search API. It return 3 type of suggestion node with list of suggestion string.
   (i)   List of all titles which starts with string "a".
   (ii)  List of all locations which starts with string "a".
   (iii) List of all actors which starts with string "a".

  output Structure :-
  [
    {
        "suggestion": [
            "All About Eve",
            "Around the Fire",
            "American Yearbook",
            "A Smile Like Yours ",
            "Another 48 Hours",
            "Age of Adaline",
            "A View to a Kill",
            "After the Thin Man",
            "Alcatraz",
            "Ant-Man",
            "Attack of the Killer Tomatoes",
            "A Smile Like Yours",
            "A Jitney Elopement",
            "American Graffiti",
            "Americana",
            "A Night Full of Rain",
            "Alexander's Ragtime Band",
            "About a Boy"
        ],
        "suggestion_type": "title"
    },
    {
        "suggestion": [
            "Aquatic Park (Jefferson Street)",
            "Alcatraz Island",
            "Aub Zam Zam Bar"
        ],
        "suggestion_type": "location"
    },
    {
        "suggestion": [
            "Anne Baxter",
            "Alyson Hannigan",
            "Adam Sandler",
            "Alec Baldwin",
            "Amy Adams",
            "Alice Faye"
        ],
        "suggestion_type": "actor"
    }
  ]




