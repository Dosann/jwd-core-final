package com.epam.jwd.core_final.util.handlers;

import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.impl.SimpleCrewService;
import com.epam.jwd.core_final.service.impl.SimpleMissionService;
import com.epam.jwd.core_final.service.impl.SimpleSpaceshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Map;

public class EntityCreationHandler extends MajorOptionsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityCreationHandler.class);

    public static void handleEntityCreation(int subCommand) {
        LOGGER.info("Handling entity creation by user command...");

        if (subCommand == 1) {
            createCrewMemberFromUserInput();
        } else if (subCommand == 2) {
            createSpaceshipFromUserInput();
        } else if (subCommand == 3) {
            createFlightMissionFromUserInput();
        } else {
            LOGGER.error("Impossible to resolve user command");
            throw new IllegalArgumentException("Invalid command");
        }
    }

    private static void createCrewMemberFromUserInput() {
        LOGGER.info("Creating crew member from user input...");

        String name = fetchEntityNameFromUserInput("Input name:");
        Role role = fetchRoleFromUserInput();
        Rank rank = fetchRankFromUserInput();

        SimpleCrewService.INSTANCE.createCrewMember(CrewMemberFactory.getInstance()
                .create(name, role, rank));

        System.out.println("Crew member was created");
        LOGGER.info("Crew member was successfully created");
    }

    private static void createSpaceshipFromUserInput() {
        LOGGER.info("Creating spaceship by user command...");

        String name = fetchEntityNameFromUserInput("Input name:");
        Map<Role, Short> mapOfSpaceshipCrew = fetchMapOfCrewFromUserInput();
        Long distance = fetchDistanceFromUserInput("Input flight distance:");

        SimpleSpaceshipService.INSTANCE.createSpaceship(SpaceshipFactory.getInstance()
                .create(name, mapOfSpaceshipCrew, distance));

        System.out.println("Spaceship was created");
        LOGGER.info("Spaceship was successfully created");
    }

    private static void createFlightMissionFromUserInput() {
        LOGGER.info("Creating flight mission by user command...");

        String name = fetchEntityNameFromUserInput("Input name:");

        LocalDate startDate = fetchDateFromUserInput("Input start date of a mission:\n" +
                "Expected input [year-month-date]");

        LocalDate endDate = fetchDateFromUserInput("Input end date of a mission:\n" +
                "Expected input [year-month-date]");

        Long distance = fetchDistanceFromUserInput("Input flight distance:");

        SimpleMissionService.INSTANCE.createMission(FlightMissionFactory.getInstance()
                .create(name, startDate, endDate, distance));

        System.out.println("Flight mission was created");
        LOGGER.info("Flight mission was successfully created");
    }
}
