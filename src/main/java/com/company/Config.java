package com.company;

public class Config {
    public static String kafkaHost() {
        return getEnvironmentVariable("WM_KAFKA_HOST", "localhost");
    }

    public static String graphiteHostName() {
        return getEnvironmentVariable("WM_GRAPHITE_HOST_NAME", "localhost");
    }

    public static Integer graphitePort() {
        String portString = getEnvironmentVariable("WM_GRAPHITE_PORT", "2004");
        return Integer.parseInt(portString);
    }

    public static Boolean startFromBeginning() {
        return "true".equals(System.getenv("WM_GRAPHITE_FROM_BEGINNING"));
    }

    private static String getEnvironmentVariable(String name, String defaultValue) {
        String value = System.getenv(name);
        if (value == null || "".equals(value)) {
            return defaultValue;
        }

        return value;
    }

}
