"# mqtt_client" 

cd subscribe_client
mvn clean package
cd target
java -jar SubMsg-1.0-SNAPSHOT.jar


cd publish_client
mvn clean package
cd target
java -jar PubMsg-1.0-SNAPSHOT.jar

