package com.company.graphite;

import com.company.Config;
import com.company.kafka.KafkaObservationData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.python.core.*;
import org.python.modules.cPickle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class GraphiteSender {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void send(ConsumerRecords<String, KafkaObservationData> records) {
        try (Socket socket = new Socket(Config.graphiteHostName(), Config.graphitePort()))  {
            PyList list = new PyList();

            records.forEach(record -> {
                addTemperature(record, list);
                addDewPoint(record, list);
                addHumidity(record, list);
                addWindSpeed(record, list);
            });

            PyString payload = cPickle.dumps(list);
            byte[] header = ByteBuffer.allocate(4).putInt(payload.__len__()).array();

            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(header);
            outputStream.write(payload.toBytes());
            outputStream.flush();

        } catch (IOException e) {
            logger.error("Exception thrown writing data to graphite: " + e);
        }
    }

    private void addWindSpeed(ConsumerRecord<String, KafkaObservationData> record, PyList list) {
        addFloatMetric(record, list, "windSpeedMph", record.value().windSpeedMph);
    }

    private void addHumidity(ConsumerRecord<String, KafkaObservationData> record, PyList list) {
        addFloatMetric(record, list, "humidityPercentage", record.value().humidityPercentage);
    }

    private void addDewPoint(ConsumerRecord<String, KafkaObservationData> record, PyList list) {
        addFloatMetric(record, list, "dewpointCelcius", record.value().dewPointCelcius);
    }

    private void addTemperature(ConsumerRecord<String, KafkaObservationData> record, PyList list) {
        addFloatMetric(record, list, "temperatureCelcius", record.value().temperatureCelcius);
    }

    private void addFloatMetric(ConsumerRecord<String, KafkaObservationData> record, List list, String name, String value) {
        if (value == null) {
            // Some values are optional or not giving data due to broken sensors etc
            return;
        }

        LocalDateTime dateTime = LocalDateTime.parse(record.value().dataDate);

        PyString metricName = new PyString(record.topic() + "." + name);
        PyInteger timestamp = new PyInteger((int) dateTime.toEpochSecond(ZoneOffset.UTC));
        PyFloat metricValue = new PyFloat(Double.parseDouble(value));
        PyTuple metric = new PyTuple(metricName, new PyTuple(timestamp, metricValue));
        logMetric(metric);
        list.add(metric);
    }

    private void logMetric(PyTuple metric) {
        logger.info("Added metric: " + metric.toString());
    }
}
