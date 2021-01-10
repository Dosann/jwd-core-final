package com.epam.jwd.core_final.util.handlers;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.service.impl.SimpleCrewService;
import com.epam.jwd.core_final.service.impl.SimpleMissionService;
import com.epam.jwd.core_final.service.impl.SimpleSpaceshipService;
import com.epam.jwd.core_final.util.ConsoleSamples;
import com.epam.jwd.core_final.util.FlightMissionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class MajorOptionsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MajorOptionsHandler.class);

    private static final Scanner SCANNER = new Scanner(System.in);

    private static Integer fetchAmountOfCrewMembersFromUserInput() {
        System.out.println("How many crew members of spaceship?");
        String userCommand = SCANNER.next();
        return (userCommand.equals("-s") ? null :Integer.parseInt(userCommand));
    }

    private static Map<Role, Short> fetchCrewMembers(int amountOfCrewMembers) {
        System.out.println("Choose role and number of members of role you'd like to have:\n" + "Expected input [1:4]");
        ConsoleSamples.printRolesToChoseFrom();

        Map<Role, Short> mapOfSpaceshipCrew = new HashMap<>();
        for (int i = 0; i < amountOfCrewMembers; i++) {
            String[] roleIds = SCANNER.next().split(":");
            mapOfSpaceshipCrew.put(Role.resolveRoleById(Integer.parseInt(roleIds[0])), Short.parseShort(roleIds[1]));
        }
        LOGGER.info("Map of crew members roles has been fetched");
        return mapOfSpaceshipCrew;
    }

    static Map<Role, Short> fetchMapOfCrewFromUserInput() {
        LOGGER.info("Fetching map of roles from user input...");

        Integer amountOfCrewMembers = fetchAmountOfCrewMembersFromUserInput();
        return (amountOfCrewMembers == null ? null : fetchCrewMembers(amountOfCrewMembers));
    }

    static String fetchEntityNameFromUserInput(String commandToUser) {
        LOGGER.info("Fetching entity name from user input...");

        System.out.println(commandToUser);
        String name = SCANNER.next();
        LOGGER.info("Entity name has been fetched");
        return (name.equals("-s") ? null : name);
    }

    static Long fetchDistanceFromUserInput(String commandToUser) {
        LOGGER.info("Fetching distance from user input...");

        System.out.println(commandToUser);
        String userCommand = SCANNER.next();
        LOGGER.info("Distance has been fetched");
        return (userCommand.equals("-s") ? null : Long.valueOf(userCommand));
    }

    private static Boolean resolvePreparationForNextMission(String userChoice) {
        boolean isReadyForNextMission = true;
        if (userChoice.toLowerCase().equals("no")) {
            isReadyForNextMission = false;
        }
        LOGGER.info("Preparation for next mission has been fetched");
        return isReadyForNextMission;
    }

    static Boolean resolvePreparationForNextMissionFromUserInput(String userChoice) {
        LOGGER.info("Fetching preparation for next mission from user input...");

        return (userChoice.equals("-s") ? null : resolvePreparationForNextMission(userChoice));
    }

    static LocalDate fetchDateFromUserInput(String commandToUser) {
        LOGGER.info("Fetching date from user input...");

        System.out.println(commandToUser);
        String userCommand = SCANNER.next();
        LOGGER.info("Date has been fetched");
        return (userCommand.equals("-s") ? null : LocalDate.parse(userCommand));
    }

    static Role fetchRoleFromUserInput() {
        LOGGER.info("Fetching role from user input...");

        System.out.println("Choose role:");
        ConsoleSamples.printRolesToChoseFrom();

        String userCommand = SCANNER.next();
        LOGGER.info("Role has been fetched");
        return (userCommand.equals("-s") ? null : Role.resolveRoleById(Integer.parseInt(userCommand)));
    }

    static Rank fetchRankFromUserInput() {
        LOGGER.info("Fetching rank from user input...");

        System.out.println("Choose rank:");
        ConsoleSamples.printRanksToChoseFrom();

        String userCommand = SCANNER.next();
        LOGGER.info("Rank has been fetched");
        return (userCommand.equals("-s") ? null : Rank.resolveRankById(Integer.parseInt(userCommand)));
    }

    static MissionResult fetchMissionStatusFromUserInput(String commandToUser) {
        LOGGER.info("Fetching mission status from user input...");

        System.out.println(commandToUser);
        ConsoleSamples.printMissionResultsToChooseFrom();

        String userCommand = SCANNER.next();
        LOGGER.info("Flight mission status has been fetched");
        return (userCommand.equals("-s") ? null : FlightMissionStatus.resolveMissionStatus(Integer.parseInt(userCommand)));
    }

    static Optional<Spaceship> fetchSpaceshipByNameFromUserInput(String commandToUser) {
        LOGGER.info("Fetching spaceship by name from user input...");

        String name = fetchEntityNameFromUserInput(commandToUser);
        Criteria<Spaceship> spaceshipCriteria = SpaceshipCriteria
                .builder()
                .withName(name)
                .build();

        LOGGER.info("Finding spaceship according to user input...");
        return SimpleSpaceshipService.INSTANCE.findSpaceshipByCriteria(spaceshipCriteria);
    }

    static Optional<FlightMission> fetchMissionByNameFromUserInput(String commandToUser) {
        LOGGER.info("Fetching flight mission by name from user input...");

        String name = fetchEntityNameFromUserInput(commandToUser);
        Criteria<FlightMission> flightMissionCriteria = FlightMissionCriteria
                .builder()
                .withName(name)
                .build();

        LOGGER.info("Finding flight mission according to user input...");
        return SimpleMissionService.INSTANCE.findMissionByCriteria(flightMissionCriteria);
    }

    static Optional<CrewMember> fetchCrewMemberByNameFromUserInput(String commandToUser) {
        LOGGER.info("Fetching crew member by name from user input...");

        String name = fetchEntityNameFromUserInput(commandToUser);
        Criteria<CrewMember> crewMemberCriteria = CrewMemberCriteria
                .builder()
                .withName(name)
                .build();

        LOGGER.info("Finding crew member according to user input...");
        return SimpleCrewService.INSTANCE.findCrewMemberByCriteria(crewMemberCriteria);
    }

}
