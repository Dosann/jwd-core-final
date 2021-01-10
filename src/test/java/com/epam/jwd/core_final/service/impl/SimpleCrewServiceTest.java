package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.AssignationException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class SimpleCrewServiceTest {

    private static CrewMember crewMember;

    @BeforeClass
    public static void setUp() {
        crewMember = new CrewMember("Rassel", Role.COMMANDER, Rank.CAPTAIN);
    }

    @Test
    public void findAllCrewMembersByCriteria_whenCriteriaIsNull_thenReturnListOfAllMembers() {
        List<CrewMember> expected = new ArrayList<>(NassaContext.getInstance().retrieveBaseEntityList(CrewMember.class));
        List<CrewMember> actual = SimpleCrewService.INSTANCE.findAllCrewMembersByCriteria(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateCrewMemberDetails_whenRoleAndRankEqualsFour_thenNotRiseUpItsIds() {
        CrewMember crewMemberActual = SimpleCrewService.INSTANCE.updateCrewMemberDetails(crewMember);
        Assert.assertEquals(crewMember, crewMemberActual);
    }

    @Test(expected = AssignationException.class)
    public void assignCrewMemberOnMission_whenMemberIsNotReadyForNextMissions_thenThrowException() {
        CrewMember crewMember = Mockito.mock(CrewMember.class);
        Mockito.when(crewMember.getReadyForNextMissions()).thenReturn(false);
        SimpleCrewService.INSTANCE.assignCrewMemberOnMission(crewMember);
        Assert.assertFalse(crewMember.getReadyForNextMissions());
    }

    @Test
    public void assignCrewMemberOnMission_whenMemberIsReadyForNextMissions_thenAssignHimAndReturnFalse() {
        crewMember.setReadyForNextMissions(true);
        SimpleCrewService.INSTANCE.assignCrewMemberOnMission(crewMember);
        Assert.assertFalse(crewMember.getReadyForNextMissions());
    }
}
