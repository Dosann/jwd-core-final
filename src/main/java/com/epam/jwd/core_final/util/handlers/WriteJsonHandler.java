package com.epam.jwd.core_final.util.handlers;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.util.ConsoleSample;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class WriteJsonHandler {

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final Logger LOGGER = LoggerFactory.getLogger(WriteJsonHandler.class);

    public static void handleWritingInJson () {
        LOGGER.info("Handling writing missions in JSON format...");

        System.out.println("Choose how to write flight missions in JSON format:");
        ConsoleSample.printWritingInJsonToChoseFrom();
        int userChoice = SCANNER.nextInt();
        if (userChoice == 1) {
            writeMultipleMissions();
        } else if (userChoice == 2) {
            writeOneMission();
        } else {
            LOGGER.error("Impossible to resolve user command");
            throw new IllegalArgumentException("Invalid command");
        }
    }

    private static void writeOneMission() {
        LOGGER.info("Writing single mission in JSON format...");

        Optional<FlightMission> flightMission = fetchMissionFromUserInput();
        if (flightMission.isPresent()) {
            writeToJson(Collections.singletonList(flightMission.get()));
            System.out.println("Mission has been written: You may look in output file");
            LOGGER.info("Mission has been written in output file");
        } else {
            System.out.println("There is no such flight mission");
        }
    }

    private static void writeToJson(List<FlightMission> flightMission) {
        LOGGER.info("Writing info to JSON file...");

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());

        String path = PropertyReaderUtil.applicationProperties.getMissionsFileName() + ".json";

        try {
            writer.writeValue(new File(path), flightMission);
        } catch (IOException e) {
            LOGGER.error("Exception occurred: {}", e.toString());
        }
        LOGGER.info("Information was successfully written to JSON file");
    }

    private static void writeMultipleMissions() {
        LOGGER.info("Writing multiple missions in JSON format...");

        System.out.println("How many missions you'd like to write in JSON file?");
        int userChoice = SCANNER.nextInt();
        List<FlightMission> flightMissionList = new ArrayList<>(userChoice);
        for (int i = 0; i < userChoice; i++) {
            fetchMissionFromUserInput().ifPresent(flightMissionList::add);
        }
        writeToJson(flightMissionList);
    }

    private static Optional<FlightMission> fetchMissionFromUserInput() {
        return MajorOptionsHandler.fetchMissionByNameFromUserInput
                ("To write in JSON format choose a mission by its name:");
    }
}
