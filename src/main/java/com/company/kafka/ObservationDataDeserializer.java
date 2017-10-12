package com.company.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class ObservationDataDeserializer implements Deserializer<KafkaObservationData> {

    private final static Logger logger = LoggerFactory.getLogger(ObservationDataDeserializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public KafkaObservationData deserialize(String topic, byte[] data) {
        KafkaObservationData observationData = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            observationData = mapper.readValue(data, KafkaObservationData.class);
        } catch (IOException e) {
            logger.error("Failed to deserialize object: " + data.toString(), e);
        }
        return observationData;
    }

    @Override
    public void close() {

    }
}
