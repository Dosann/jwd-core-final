package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.strategy.FetchAndCreateStrategy;
import com.epam.jwd.core_final.strategy.impl.CrewMembersFetchAndCreateStrategy;
import com.epam.jwd.core_final.strategy.impl.SpaceshipsFetchAndCreateStrategy;

public enum FileReaderAndCreatorEntity {
    INSTANCE;

    private FetchAndCreateStrategy fetchAndCreateStrategy;
    private String pathName;

    public void setCrewMemberFetchAndCreateStrategy() {
        this.fetchAndCreateStrategy = CrewMembersFetchAndCreateStrategy.getInstance();
    }

    public void setSpaceshipFetchAndCreateStrategy() {
        this.fetchAndCreateStrategy = SpaceshipsFetchAndCreateStrategy.getInstance();
    }

    public void fetchFromFileAndCreateEntities() throws InvalidStateException {
        fetchAndCreateStrategy.fetchFromFileAndCreateEntities(pathName);
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }
}
