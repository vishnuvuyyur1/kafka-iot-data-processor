# kafka-iot-data-processor
Kafka project <br>
![arc](https://user-images.githubusercontent.com/22782834/88284944-93ab9600-ccee-11ea-9790-5357636d209f.png)
- A pipeline architecture that consists of well-known open source tools to specifically integrate internet of things (IoT) data streams.
- The code demonstrate how to deal with IoT stream data that is generated by device equipped with sensing components(IOT devices) 
   by utilizing open source tools and frameworks to tackle data integration as well as scalable stream processing.  
- The architecture consists of several tools that are chained to build a pipeline. Apache Kafka a
  suitable intermediate tool for the data integration kicks in by triggering a schedule task which send out a value every second mimicing an IOT device as the entry point of published messages, 
- These messages are then forwarded to a Kafka broker running locally in order to fault-tolerant stream processing and parallel topic consumption.
- Kafka Consumer is  the service that will be responsible for reading messages processing them according to the needs of your own business logic which in our case storing them in DB.
- Big Data, for data processing Hadoop is a framework that can process large data sets across clusters; Spark is a unified analytics engine for large scale data processing. Apache storm is also available for big data analytics, data processing and can be combined together but is not considered for the scope of this project development.
- We utilize Spring Data JPA, In memory H2DB to implement the data storage, which consume messages from the Kafka broker. Finally, the produced data of the IoT
  is stored in our DB for further processing.
- The user can query the readings of specific sensor groups

## Tech stack
- Apache kafka 2+
- Java 11
- Spring Boot 2+
- Spring security 
- JPA
- In Memory DB
- Maven build
  
 ## Running Instructions Locally
 Prerequisites:
 - Windows os
 - Download kafka from :https://kafka.apache.org/quickstart#quickstart_download
 - Unzip the folder using 7-zip or winrar
 - Rename the folder to kafka and place the folder in C: drive and the final path looks like C:\kafka
 
 Runnign the project
 - From command prompt Go to  C:\kafka
 - First statr the zookeeper cmd: .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
 - Next start kafka server cmd: .\bin\windows\kafka-server-start.bat .\config\server.properties
  - If you see error in Kafka server such then go to config/server.properties and add this listeners=PLAINTEXT://localhost:9092 is what the broker will use to create server sockets.
 - If you see error in Kafka server such as org.apache.kafka.clients.NetworkClient then go to config/server.properties and add this advertised.listeners=PLAINTEXT://localhost:9092 is what clients will use to connect to the brokers.
 - Next run the spring boot project from an IDE
 
  ## Approach
  - The API call to '/start' endpoint will trigger scheduling which will enable 3 IOT devices to send out a value every second.
  - Until the API call '/stop' is called the IOT devices keep sending out a value every second.
  - The IOT device data is processed in parallel and stored in DB
  - The user can query readings (e.g average/median/max/min values) of specific IOT sensors
  
  ## Limitatin
 - Not a limitation, at this moment the user cannot be able to querying the readings (e.g average/median/max/min values) of specific sensors or groups of sensors for a specific timeframe.
 - Additional features can be added.
 # API Documentation
  Base URL: http://localhost:8080/processor <br>
  Operations:
  
  |No| Operation | Endpoint | Method
|----|---|---|---|
|1| start scheduling  |/start| POST |
|2| stop scheduling | /stop | POST |
|3| get query readings|/iotdata (protected) |GET | 

## 1. start scheduling
- URI: /start
- Method: POST
<br>
Request Body : None <br>
Response Body : Started Scheduling <br>

## 2. stop scheduling
- URI: /stop
- Method: POST
<br>
Request Body : None <br>
Response Body : Stopped Scheduling <br>

## 3. get query readings (protected: Requires authentication)
- URI: /iotdata
- Method: GET
- Authentication type: Basic
- username: admin
- password: password
<br>
Request Body

  |Attributes|Type|Validation | Required |
|----|---|---|---|
|deviceType|ENUM | THERMOSTAT_METER/HEART_METER/CARFUEL_METER| yes|
|queryType|ENUM | AVERAGE/MAX/MIN | yes |

```
{
    "deviceType": "HEART_METER",
    "queryType": "MIN"
}
```
Response 
 |Attributes|Type|
|----|---|
|deviceType|ENUM | 
|queryType|ENUM | 
|queryValue|int | 

```
{
    "queryType": "MIN",
    "deviceType": "HEART_METER",
    "queryValue": "62"
}
```
## Notes:
- The project is a prototype for demo purpose.
- It does requires standardizing.

## Useful commands:
- Create topics<br>
.\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic THERMOSTAT_METER <br>

 .\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic HEART_METER <br>

- List the topics <br>
.\bin\windows\kafka-topics.bat --list --zookeeper localhost:2181 <br>

- Produce data to a topic <br>
.\bin\windows\kafka-console-producer --broker-list localhost:9092 --topic THERMOSTAT_METER <br>

- Consume data from a topic <br>
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic THERMOSTAT_METER  --from-beginning <br>
