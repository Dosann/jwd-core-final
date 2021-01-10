package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.exception.UnknownEntityException;
import org.junit.Assert;
import org.junit.Test;

public class RoleTest {

    @Test
    public void resolveRoleById_whenNumberFromOneToFour_thenReturnRole() {
        Role expected = Role.COMMANDER;
        Role actual = Role.resolveRoleById(4);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = UnknownEntityException.class)
    public void resolveRoleById_whenNumberNotInRangeFromOneToFour_thenThrowException() {
        Role expected = Role.PILOT;
        Role actual  = Role.resolveRoleById(-1);
        Assert.assertEquals("There was no exception for id: -1",expected, actual);
    }
}
