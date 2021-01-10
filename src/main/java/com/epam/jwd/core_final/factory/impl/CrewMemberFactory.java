package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.factory.EntityFactory;

// do the same for other entities
public class CrewMemberFactory implements EntityFactory<CrewMember> {

    private static class CrewMemberFactoryHolder {
        private final static CrewMemberFactory INSTANCE = new CrewMemberFactory();
    }

    public static CrewMemberFactory getInstance() {
        return CrewMemberFactoryHolder.INSTANCE;
    }

    @Override
    public CrewMember create(Object... args) {
        if (args.length == 3) {
            return new CrewMember((String) args[0], (Role) args[1], (Rank) args[2]);
        }
        throw new IllegalArgumentException("Invalid number of arguments");
    }
}
