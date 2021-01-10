package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import org.junit.Assert;
import org.junit.Test;

public class CrewMemberFactoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void create_shouldThrowException_whenArgumentsNotThree() {
        CrewMember crewMember = new CrewMember("BigStar", Role.MISSION_SPECIALIST, Rank.CAPTAIN);
        CrewMember actual = CrewMemberFactory.getInstance().create(2L, "BigStar", Role.MISSION_SPECIALIST, Rank.CAPTAIN);
        Assert.assertEquals("There was no exception for >3 arguments", crewMember, actual);
    }
}
