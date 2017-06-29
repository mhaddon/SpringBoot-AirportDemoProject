# Airport Demo Project
![Travis Tests](https://travis-ci.org/mhaddon/SpringBoot-AirportDemoProject.svg?branch=master)

This project is a simple application to show my skills as a Java developer.

#### Assessment

> 1. Download three CSV files, one for airports, one for runways and another for countries.
>
> 2. Write a web application in Java or Scala that will ask the user for two actions : Query or Reports.
>
> 2.1 Query Option will ask the user for the country name or code and print the airports & runways at each airport. The input can be country code or country name. For bonus points make the test partial/fuzzy. e.g. entering zimb will result in Zimbabwe :)
>
> 2.2 Choosing Reports will print the following:
>
> 10 countries with highest number of airports (with count) and countries with lowest number of airports.
>
> Type of runways (as indicated in "surface" column) per country
>
> Bonus: Print the top 10 most common runway identifications (indicated in "le_ident" column)
>
> Feel free to use any library/framework as necessary but write it as a web application.
>
> Please write the code as if you are writing production code, possibly with tests.

#### Setup

The project attempts to keep setup minimal, you should be able to build the project with:

mvn clean package -Dgroups="com.airportdemo.testcategory.Fast,com.airportdemo.testcategory.Slow"

Then run the project with a simple:

java -jar target/airportdemo-1.0.jar

The project defaults to http://localhost:8082

This was developed with and expected to work with OpenJDK-8, OracleJDK is untested


#### License
This project has been released as FREE software for educational purposes.
```
   SpringBoot-AirportDemoProject  Copyright (C) 2017  Michael Haddon

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU Affero General Public License version 3
   as published by the Free Software Foundation.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU Affero General Public License for more details.

   You should have received a copy of the GNU Affero General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.
```
More permissive licensing may be available on request.
