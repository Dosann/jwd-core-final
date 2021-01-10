package com.epam.jwd.core_final.strategy.impl;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.impl.SimpleSpaceshipService;
import com.epam.jwd.core_final.strategy.FetchAndCreateStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SpaceshipsFetchAndCreateStrategy implements FetchAndCreateStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpaceshipsFetchAndCreateStrategy.class);

    private static SpaceshipsFetchAndCreateStrategy instance;

    public static SpaceshipsFetchAndCreateStrategy getInstance() {
        if (instance == null) {
            instance = new SpaceshipsFetchAndCreateStrategy();
        }
        return instance;
    }

    @Override
    public void fetchFromFileAndCreateEntities(String pathName) throws InvalidStateException{
        LOGGER.info("Fetching and creating spaceships form files...");

        try (Scanner scanner = new Scanner(new File("src/main/resources/" + pathName))) {
            while (scanner.hasNext()) {
               String attributes = scanner.nextLine();
               if (attributes.startsWith("#")) {
                   continue;
               }
                String[] infoAboutSpaceships = attributes.split(";");

                SimpleSpaceshipService.INSTANCE.createSpaceship(SpaceshipFactory.getInstance()
                        .create(infoAboutSpaceships[0],
                                fetchMapOfCrew(infoAboutSpaceships),
                                Long.valueOf(infoAboutSpaceships[1])
                        )
                );
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("File: " + pathName + " can't be found");
            throw new InvalidStateException("Impossible to fetch from file and create spaceships");
        }
        LOGGER.info("Information about spaceships was loaded successful");
    }

    private Map<Role, Short> fetchMapOfCrew (String[] infoAboutSpaceships) {
        String[] crews = infoAboutSpaceships[2].substring(1, infoAboutSpaceships[2].length() - 1).split(",");
        Map<Role, Short> mapOfSpaceshipCrew = new HashMap<>();
        for (String crew : crews) {
            String[] roleIds = crew.split(":");
            mapOfSpaceshipCrew.put(Role.resolveRoleById(Integer.parseInt(roleIds[0])), Short.parseShort(roleIds[1]));
        }
        return mapOfSpaceshipCrew;
    }
}
