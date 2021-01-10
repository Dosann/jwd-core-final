package com.epam.jwd.core_final.strategy.impl;

import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.service.impl.SimpleCrewService;
import com.epam.jwd.core_final.strategy.FetchAndCreateStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CrewMembersFetchAndCreateStrategy implements FetchAndCreateStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrewMembersFetchAndCreateStrategy.class);

    private static CrewMembersFetchAndCreateStrategy instance;

    public static CrewMembersFetchAndCreateStrategy getInstance() {
        if (instance == null) {
            instance = new CrewMembersFetchAndCreateStrategy();
        }
        return instance;
    }

    @Override
    public void fetchFromFileAndCreateEntities(String pathName) throws InvalidStateException{
        LOGGER.info("Fetching and creating crew members form files...");

        try (Scanner scanner = new Scanner(new File("src/main/resources/" + pathName))) {
            scanner.nextLine();
            scanner.useDelimiter(";");

            while (scanner.hasNext()) {
                String attributes = scanner.next();
                String[] infoAboutCrewMembers = attributes.split(",");

                SimpleCrewService.INSTANCE.createCrewMember(CrewMemberFactory.getInstance()
                        .create(infoAboutCrewMembers[1],
                                Role.resolveRoleById(Integer.parseInt(infoAboutCrewMembers[0])),
                                Rank.resolveRankById(Integer.parseInt(infoAboutCrewMembers[2]))
                        )
                );
            }

        } catch (FileNotFoundException e) {
            LOGGER.error("File:" + pathName + " can't be found");
            throw new InvalidStateException("Impossible to fetch from file and create crew members");
        }
        LOGGER.info("Information about crew members was loaded successful");
    }
}
