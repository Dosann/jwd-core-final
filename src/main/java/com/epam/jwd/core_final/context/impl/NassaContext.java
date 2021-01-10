package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.util.PropertyReaderUtil;

import java.util.ArrayList;
import java.util.Collection;

// todo
public class NassaContext implements ApplicationContext {

    private static NassaContext instance;

    public static NassaContext getInstance() {
        if (instance == null) {
            instance = new NassaContext();
        }
        return instance;
    }

    // no getters/setters for them
    private final Collection<CrewMember> crewMembers = new ArrayList<>();
    private final Collection<Spaceship> spaceships = new ArrayList<>();
    private final Collection<FlightMission> flightMissions = new ArrayList<>();

    private FileReaderAndCreatorEntity fileReaderAndCreator = FileReaderAndCreatorEntity.INSTANCE;

    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        if (tClass.equals(CrewMember.class)) {
            return (Collection<T>) crewMembers;
        } else if (tClass.equals(Spaceship.class)) {
            return (Collection<T>) spaceships;
        } else {
            return (Collection<T>) flightMissions;
        }
    }

    /**
     * You have to read input files, populate collections
     * @throws InvalidStateException
     */
    @Override
    public void init() throws InvalidStateException {
        PropertyReaderUtil.loadProperties();
        initCrewMembers();
        initSpaceships();
    }

    private void initCrewMembers() throws InvalidStateException {
        fileReaderAndCreator.setPathName(PropertyReaderUtil.applicationProperties.getInputRootDir() + "/"
                + PropertyReaderUtil.applicationProperties.getCrewFileName());
        fileReaderAndCreator.setCrewMemberFetchAndCreateStrategy();
        fileReaderAndCreator.fetchFromFileAndCreateEntities();
    }

    private void initSpaceships() throws InvalidStateException {
        fileReaderAndCreator.setPathName(PropertyReaderUtil.applicationProperties.getInputRootDir() + "/"
                + PropertyReaderUtil.applicationProperties.getSpaceshipsFileName());
        fileReaderAndCreator.setSpaceshipFetchAndCreateStrategy();
        fileReaderAndCreator.fetchFromFileAndCreateEntities();
    }
}
