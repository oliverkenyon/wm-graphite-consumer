package com.company;

import com.company.kafka.Consumer;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] arguments) {
        // This would probably be set as a comma-delimited list in an environment variable,
        // to allow changing it without code-change or redeploy.
        List<String> areasOfInterest = Arrays.asList(
                "3065",     // Cairn gorm summit
                "3039",     // Bealach na Ba
                "3047",     // Tulloch Bridge
                "3072",     // Cairnwell
                "3080",     // Aboyne
                "3148",     // Glen Ogle
                "3162"      // Eskdalemuir
        );

        Consumer consumer = new Consumer(areasOfInterest);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> consumer.close()));
        consumer.run();

    }

}
