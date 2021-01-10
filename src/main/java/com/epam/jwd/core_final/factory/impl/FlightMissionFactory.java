package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.factory.EntityFactory;
import java.time.LocalDate;

public class FlightMissionFactory implements EntityFactory<FlightMission> {

    private static class FlightMissionFactoryHolder {
        private final static FlightMissionFactory INSTANCE = new FlightMissionFactory();
    }

    public static FlightMissionFactory getInstance() {
        return FlightMissionFactory.FlightMissionFactoryHolder.INSTANCE;
    }

    @Override
    public FlightMission create(Object... args) {
        if (args.length == 4) {
            return new FlightMission((String) args[0], (LocalDate) args[1], (LocalDate) args[2], (Long) args[3]);
        }
        throw new IllegalArgumentException("Invalid number of arguments");
    }
}
