# CTTClient

Java Client for CellTrackTech, 
DOF/Birdlife Denmark & CELLULAR TRACKING TECHNOLOGIES (https://www.celltracktech.com/about-us/)
Latest version: 1.1
By Theo Askov @ DOF 2017-07-04

## Requirements

1) An active customer account by Cellular Tracking technologies (CTT) with a number of trackable gps tags included
2) API Token and endpoint url issued by CTT
3) A mysql 5.6+ database or postgres 9.x database and user/schema
4) A Java IDE like IntelliJ to build the executable jar file and Maven 3.3+
5) A server with installed JVM 1.8 (Java 8+) and access to the Internet and NS lookup

Optional: a GeoServer or other WFS server to read the data from the database for map views etc.

## Installation
1) clone this repository and import the project as a Maven project to your IDE.
1a) Let Maven update the dependencies
2) put environment variables (see below) into the run configuration
3) do a test run with a local database
4) Build cttagent-1.x.jar with Maven
5) deploy to the web server
6) create a shell script for scheduled execution of the JAVA program (*.jar)

## Bash script with environment variables
Install a bash-script (e.g cttagent.sh) setting the variables (with capital letters below).
Not shown: Also set JAVA_HOME to the JVM 1.8 if this is not the default

```
#!/bin/bash

# java agent program for retrieving gps data from the company CELLULAR TRACKING TECHNOLOGIES (https://www.celltracktech.com/about-us/)

# cttagent.jar

VERSION=1.1

# build by Maven with dependencies
# Theo Askov @ DOF
# 2017-07-04

DATE=`date +%Y-%m-%d`
LOGFILE=logs/cttagent_$DATE.log

export CTT_API_ENDPOINT= `CTT Domain / API / Version`

# The token is only valid for gps-units licensed to DOF / Birdlife Denmark. 
# The token is the only validation of user of the API, so it MUST NOT be shared with others  

export CTT_API_TOKEN= `Token for your account`

# DATA is stored in postgresql
export DEST_DB_URL=`JDBC URI` (jdbc://... with port no database)
export DEST_DB_USERNAME=user
export DEST_DB_PASSWORD=pass
export DEST_DB_NAME=database (should be `partners`on MySQL)

# date for mounting the GPS tags
export START_DATETIME=2017-07-03T15:00:00

# GPS's are mounted on white-tailed eagles
export EURINGNO=02340

# ------- don't change below this line  ------ #

java -Xms1024M -Xmx2G -jar ./cttagent-$VERSION.jar >> $LOGFILE  2>&1 &

```

### install database table
In postgres create the schema `partners` with the required user as owner.
In mysql create a database `partners`.
In the schema/database run this script (postgresql version):
```
DROP table IF exists gps_observation;
CREATE table gps_observation(
	 id serial,
	 unitid int, -- 9 digit fraction of serial, 41549716
	 euringno varchar(5), -- current species id
	 dt varchar(20), -- String representation og gps_time
	 gps_date varchar(10), -- 2017-06-26
	 gps_time varchar(8), -- 11:46:47
	 geom Geometry,
	 lat Decimal(8,6), --55.123456
	 lon Decimal(8,6), -- 12.442293,
	 hdop Decimal(4,2), -- 1.8
	 fix int, -- 3
	 cog int, -- 49
	 speed Decimal(4,2), -- 0.1
	 alt Decimal(6,2), -- 41.4
	 data_voltage Decimal(4,2), -- 4.07
	 solar_charge Decimal(4,2) -- 6.05
);
```
Some fields are missing.. to be corrected later ...

### quickly test number of observations:

`select count(*) as antal_pr_dag, unitid, gps_date from gps_observation GROUP by gps_date, unitid;`

