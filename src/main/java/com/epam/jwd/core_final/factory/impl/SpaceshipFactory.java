package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.util.Map;

public class SpaceshipFactory implements EntityFactory<Spaceship> {

    private static class SpaceshipFactoryHolder {
        private final static SpaceshipFactory INSTANCE = new SpaceshipFactory();
    }

    public static SpaceshipFactory getInstance() {
        return SpaceshipFactory.SpaceshipFactoryHolder.INSTANCE;
    }

    @Override
    public Spaceship create(Object... args) {
        if (args.length == 3) {
            return new Spaceship((String) args[0], (Map<Role, Short>) args[1], (Long) args[2]);
        }
        throw new IllegalArgumentException("Invalid number of arguments");
    }
}
