package com.epam.jwd.core_final.util.handlers;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.service.impl.SimpleCrewService;
import com.epam.jwd.core_final.service.impl.SimpleSpaceshipService;
import com.epam.jwd.core_final.util.ConsoleSample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class EntityUpdatingHandler extends MajorOptionsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityUpdatingHandler.class);

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void handleEntityUpdating(int subCommand) {
        LOGGER.info("Handling entity updating by user command...");

        if (subCommand == 1) {
            updateCrewMemberFromUserInput();
        } else if (subCommand == 2) {
            updateSpaceshipFromUserInput();
        } else if (subCommand == 3) {
            updateMissionFromUserInput();
        } else {
            LOGGER.error("Impossible to resolve user command");
            throw new IllegalArgumentException("Invalid command");
        }
    }

    private static void updateCrewMemberFromUserInput() {
        LOGGER.info("Updating crew member from user input...");

        System.out.println("Do you want to rise up the role and rank of a crew member? [Yes / No]");
        String userChoice = SCANNER.next();
        if (userChoice.toLowerCase().equals("yes")) {
            updateRoleAndRankOfCrewMember();
            System.out.println("Crew member details have been updated");
            LOGGER.info("Role and rank have been updated");
        }
        LOGGER.info("No updating happened");
    }

    private static void updateRoleAndRankOfCrewMember() {
        Optional<CrewMember> crewMember = fetchCrewMemberByNameFromUserInput
                ("Choose name of crew member you'd like to update:");

        if (crewMember.isPresent()) {
            SimpleCrewService.INSTANCE.updateCrewMemberDetails(crewMember.get());
        } else {
            System.out.println("There is no such crew member");
        }
    }

    private static void updateSpaceshipFromUserInput() {
        LOGGER.info("Updating spaceship from user input...");

        Optional<Spaceship> spaceship = fetchSpaceshipByNameFromUserInput
                ("Choose name of spaceship you'd like to update:");

        if (spaceship.isPresent()) {
            updateSpaceshipDetails(spaceship.get());
            System.out.println("Spaceship details have been updated");
            LOGGER.info("Spaceship details have been updated");
        } else {
            System.out.println("There is no such spaceship");
        }
    }

    private static void updateSpaceshipDetails(Spaceship spaceship) {
        String userChoice;

        do {
            System.out.println("Choose attributes to update:\nTo exit from this option, press -e");
            ConsoleSample.printSpaceshipAttributesToUpdate();

            userChoice = SCANNER.next();
            updateSpaceshipAttributesAccordingToUserChoice(userChoice, spaceship);
        } while (!userChoice.equals("-e"));
    }

    private static void updateSpaceshipAttributesAccordingToUserChoice(String userChoice, Spaceship spaceship) {
        switch (userChoice) {
            case "1":
                Map<Role, Short> mapOfSpaceshipCrew = fetchMapOfCrewFromUserInput();
                spaceship.setCrew(mapOfSpaceshipCrew);
                break;
            case "2":
                Long distance = fetchDistanceFromUserInput("Choose new flight distance:");
                spaceship.setFlightDistance(distance);
                break;
            case "3":
                System.out.println("Choose whether or not spaceship is prepared for next missions: [Yes / No]");
                Boolean isReadyForNextMissions = resolvePreparationForNextMissionFromUserInput(SCANNER.next());
                spaceship.setReadyForNextMissions(isReadyForNextMissions);
                break;
            default:
                System.out.println("Attribute is missing");
                break;
        }
    }

    private static void updateMissionFromUserInput() {
        LOGGER.info("Updating flight mission from user input...");

        Optional<FlightMission> flightMission = fetchMissionByNameFromUserInput
                ("Choose name of mission you'd like to update:");

        if (flightMission.isPresent()) {
            updateMissionDetails(flightMission.get());
            System.out.println("Flight mission details have been updated");
            LOGGER.info("Flight mission details have been updated");
        } else {
            System.out.println("There is no such flight mission");
        }
    }

    private static void updateMissionDetails(FlightMission flightMission) {
        String userChoice;

        do {
            System.out.println("Choose attributes to update:\nTo exit from this option, press -e");
            ConsoleSample.printMissionAttributesToUpdate();

            userChoice = SCANNER.next();
            updateMissionAttributesAccordingToUserChoice(userChoice, flightMission);
        } while (!userChoice.equals("-e"));
    }

    private static void updateMissionAttributesAccordingToUserChoice(String userChoice, FlightMission flightMission) {
        switch (userChoice) {
            case "1":
                LocalDate startDate = fetchDateFromUserInput("Choose new start date of a mission:\n" +
                        "Expected input [year-month-date]");
                flightMission.setStartDate(startDate);
                break;
            case "2":
                LocalDate endDate = fetchDateFromUserInput("Choose new end date of a mission:\n" +
                        "Expected input [year-month-date]");
                flightMission.setEndDate(endDate);
                break;
            case "3":
                Long distance = fetchDistanceFromUserInput("Choose new distance:");
                flightMission.setDistance(distance);
                break;
            case "4":
                updateMissionSpaceship(flightMission);
                break;
            case "5":
                updateMissionCrew(flightMission);
                break;
            case "6":
                updateMissionStatus(flightMission);
                break;
            default:
                System.out.println("Attribute is missing");
                break;
        }
    }

    private static void updateMissionSpaceship(FlightMission flightMission) {
        Optional<Spaceship> spaceship = fetchSpaceshipByNameFromUserInput("Choose a new spaceship by name:");

        if (spaceship.isPresent()) {
            SimpleSpaceshipService.INSTANCE.assignSpaceshipOnMission(spaceship.get());
            flightMission.setAssignedSpaceship(spaceship.get());
        } else {
            System.out.println("There is no such spaceship");
        }
    }

    private static void updateMissionCrew(FlightMission flightMission) {
        Optional<CrewMember> crewMember = fetchCrewMemberByNameFromUserInput
                ("Choose new crew member by name:");

        if (crewMember.isPresent()) {
            SimpleCrewService.INSTANCE.assignCrewMemberOnMission(crewMember.get());
            System.out.println("Choose crew members you'd like to replace by name:");
            String name = SCANNER.next();
            if (flightMission.getAssignedCrew().removeIf(member -> member.getName().equals(name))) {
                flightMission.getAssignedCrew().add(crewMember.get());
            }
        }
    }

    private static void updateMissionStatus(FlightMission flightMission) {
        MissionResult missionStatusToUpdate = fetchMissionStatusFromUserInput("Choose new mission status:");
        flightMission.setMissionResult(missionStatusToUpdate);
    }
}
