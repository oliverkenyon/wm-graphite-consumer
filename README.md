# Weather Monitor Graphite Consumer

Subscribes to a stream of observation data from Kafka and feeds into Graphite to allow the data to be visualized

## Configuration

The application uses the following settings which should be present as environment variables:

 - WM_KAFKA_HOST: Host and port for kafka, e.g. "localhost:9092"
 - WM_GRAPHITE_FROM_BEGINNING: optional, set to "true" to consume from the beginning of the Kafka stream
 
## Build

Cd to project directory: gradle build

## Run

java -jar build/libs/WmGraphiteConsumer-1.0-SNAPSHOT.jar

The consumer will run until stopped, it will shutdown cleanly if interuptted e.g. by Ctrl+C
