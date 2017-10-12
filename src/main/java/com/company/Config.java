package com.company;

public class Config {
    public static String kafkaHost() {
        return System.getenv("WM_KAFKA_HOST");
    }

    public static String graphiteHostName() {
        return "localhost";
    }

    public static Integer graphitePort() {
        return 2004;
    }

    public static Boolean startFromBeginning() {
        return "true".equals(System.getenv("WM_GRAPHITE_FROM_BEGINNING"));
    }
}
