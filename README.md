# Weather Monitor Graphite Consumer

Subscribes to a stream of observation data from Kafka and feeds into Graphite to allow the data to be visualized. The expected data format is that produced by the [wm-producer](../../../wm-producer) project.

## Configuration

The application uses the following settings which should be present as environment variables:

 - WM_KAFKA_HOST: Host and port for kafka. Defaults to "localhost:9092"
 - WM_GRAPHITE_HOST_NAME: Host name only for Graphite, defaults to "localhost"
 - WM_GRAPHITE_PORT: Port for Graphite (pickle protocol), defaults to "2004"
 - WM_GRAPHITE_FROM_BEGINNING: optional, set to "true" to consume from the beginning of the Kafka stream
 
## Build

Cd to project directory: gradle build

## Run

java -jar build/libs/WmGraphiteConsumer-1.0-SNAPSHOT.jar

The consumer will run until stopped, it will shutdown cleanly if interuptted e.g. by Ctrl+C
