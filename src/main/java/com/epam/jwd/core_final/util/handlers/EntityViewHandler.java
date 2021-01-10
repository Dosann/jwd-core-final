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
import com.epam.jwd.core_final.util.ConsoleSample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class EntityViewHandler extends MajorOptionsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityViewHandler.class);

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void handleEntityView (int subCommand) {
        LOGGER.info("Handling entity view by user command...");

        if (subCommand == 1) {
            viewCrewMemberAccordingToUserInput();
        } else if (subCommand == 2) {
            viewSpaceshipAccordingToUserInput();
        } else if (subCommand == 3) {
            viewFlightMissionAccordingToUserInput();
        } else {
            LOGGER.error("Impossible to resolve user command");
            throw new IllegalArgumentException("Invalid command");
        }
    }

    private static void viewCrewMemberAccordingToUserInput() {
        LOGGER.info("Viewing crew member according to user choice...");

        ConsoleSample.printEntityViewToChoseFrom();
        int viewChoice = SCANNER.nextInt();
        if (viewChoice == 1) {
            SimpleCrewService.INSTANCE.findAllCrewMembers().forEach(System.out::println);
        } else if (viewChoice == 2) {
            System.out.println("Let's build a criteria to find crew members\n" +
                    "If you don't want any field to be filled in criteria, you may skip it [enter -s]");
            viewCrewMemberByUserCriteria();
        }
    }

    private static void viewCrewMemberByUserCriteria() {
        LOGGER.info("Building a criteria for crew member to find according to user choice...");

        String name = fetchEntityNameFromUserInput("Choose name:");
        Role role = fetchRoleFromUserInput();
        Rank rank = fetchRankFromUserInput();

        System.out.println("Choose whether or not crew member is ready for next mission: [Yes / No]");
        String userCommand = SCANNER.next();
        Boolean isReadyForNextMission = resolvePreparationForNextMissionFromUserInput(userCommand);

        Criteria<CrewMember> crewMemberCriteria = CrewMemberCriteria
                .builder()
                .withName(name)
                .withRole(role)
                .withRank(rank)
                .isReadyForNextMissions(isReadyForNextMission)
                .build();

        SimpleCrewService.INSTANCE.findAllCrewMembersByCriteria(crewMemberCriteria).forEach(System.out::println);
    }

    private static void viewSpaceshipAccordingToUserInput() {
        LOGGER.info("Viewing spaceship according to user choice...");

        ConsoleSample.printEntityViewToChoseFrom();
        int viewChoice = SCANNER.nextInt();
        if (viewChoice == 1) {
            SimpleSpaceshipService.INSTANCE.findAllSpaceships().forEach(System.out::println);
        } else if (viewChoice == 2) {
            System.out.println("Let's build a criteria to find spaceships\n" +
                    "If you don't want any field to be filled in criteria, you may skip it [press <Enter>]");
            viewSpaceshipByUserCriteria();
        }
    }

    private static void viewSpaceshipByUserCriteria() {
        LOGGER.info("Building a criteria for spaceship to find according to user choice...");

        String name = fetchEntityNameFromUserInput("Choose name:");
        String userCommand;

        System.out.println("Choose crew:");
        Map<Role, Short> mapOfSpaceshipCrew = fetchMapOfCrewFromUserInput();

        Long distance = fetchDistanceFromUserInput("Choose distance:");

        System.out.println("Choose whether or not spaceship is ready for next mission: [Yes / No]");
        userCommand = SCANNER.next();
        Boolean isReadyForNextMission = resolvePreparationForNextMissionFromUserInput(userCommand);

        Criteria<Spaceship> spaceshipCriteria = SpaceshipCriteria
                .builder()
                .withName(name)
                .withCrew(mapOfSpaceshipCrew)
                .withFlightDistance(distance)
                .isReadyForNextMissions(isReadyForNextMission)
                .build();

        SimpleSpaceshipService.INSTANCE.findAllSpaceshipsByCriteria(spaceshipCriteria).forEach(System.out::println);
    }

    private static void viewFlightMissionAccordingToUserInput() {
        LOGGER.info("Viewing flight mission according to user choice...");

        ConsoleSample.printEntityViewToChoseFrom();
        int viewChoice = SCANNER.nextInt();
        if (viewChoice == 1) {
            SimpleMissionService.INSTANCE.findAllMissions().forEach(System.out::println);
        } else if (viewChoice == 2) {
            System.out.println("Let's build a criteria to find flight missions\n" +
                    "If you don't want any field to be filled in criteria, you may skip it [press <Enter>]");
            viewFlightMissionByUserCriteria();
        }
    }

    private static void viewFlightMissionByUserCriteria() {
        LOGGER.info("Building a criteria for flight mission to find according to user choice...");

        String name = fetchEntityNameFromUserInput("Choose name:");

        LocalDate startDate = fetchDateFromUserInput("Choose start date:\n" +
                "Expected input [year-month-date]");

        LocalDate endDate = fetchDateFromUserInput("Choose end date:\n" +
                "Expected input [year-month-date]");

        Long distance = fetchDistanceFromUserInput("Choose distance:");

        Optional<Spaceship> spaceshipProbablyFound = fetchSpaceshipByNameFromUserInput
                ("Choose assigned spaceship by name:");

        Spaceship spaceship = null;
        if (spaceshipProbablyFound.isPresent()) {
            spaceship = spaceshipProbablyFound.get();
        }

        System.out.println("How many crew members?");
        int amountOfCrewMembers = SCANNER.nextInt();
        List<CrewMember> crewMembers = new ArrayList<>();
        for (int i = 0; i < amountOfCrewMembers; i++){
            Optional<CrewMember> crewMemberProbablyFound = fetchCrewMemberByNameFromUserInput
                    ("Choose assigned crew member by name:");
            if (crewMemberProbablyFound.isPresent()) {
                crewMembers.add(crewMemberProbablyFound.get());
            } else {
                System.out.println("There is no such crew member");
            }
        }

        MissionResult missionResult = fetchMissionStatusFromUserInput("Choose mission status:");

        Criteria<FlightMission> flightMissionCriteria = FlightMissionCriteria
                .builder()
                .withName(name)
                .whenStarts(startDate)
                .whenEnds(endDate)
                .whereDistanceIs(distance)
                .withAssignedCrew(crewMembers)
                .withAssignedSpaceship(spaceship)
                .withMissionResult(missionResult)
                .build();

        SimpleMissionService.INSTANCE.findAllMissionsByCriteria(flightMissionCriteria).forEach(System.out::println);
    }

}
