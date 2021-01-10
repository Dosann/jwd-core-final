package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.MissionResult;
import org.junit.Assert;
import org.junit.Test;


public class FlightMissionStatusTest {

    @Test (expected = IllegalArgumentException.class)
    public void resolveMissionResult_shouldThrowException_whenInvalidArgument() {
        MissionResult expected = MissionResult.COMPLETED;
        MissionResult actual = FlightMissionStatus.resolveMissionStatus(7);
        Assert.assertEquals("For argument 7 wasn't exception:", expected, actual);
    }

    @Test
    public void resolveMissionResult_shouldReturnMissionStatusCancelled_whenArgumentIsFive() {
        MissionResult expected = MissionResult.FAILED;
        MissionResult actual = FlightMissionStatus.resolveMissionStatus(5);
        Assert.assertEquals(expected, actual);
    }
}
