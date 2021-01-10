package com.epam.jwd.core_final.strategy;

import com.epam.jwd.core_final.exception.InvalidStateException;

public interface FetchAndCreateStrategy {

    void fetchFromFileAndCreateEntities(String pathName) throws InvalidStateException;
}
