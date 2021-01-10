package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.MissionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class FlightMissionStatus {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightMissionStatus.class);

    public static MissionResult resolveMissionStatus(int userChoice) {
        MissionResult missionResult;
        switch (userChoice) {
            case 1:
                missionResult = MissionResult.COMPLETED;
                break;
            case 2:
                missionResult = MissionResult.PLANNED;
                break;
            case 3:
                missionResult = MissionResult.IN_PROGRESS;
                break;
            case 4:
                missionResult = MissionResult.CANCELLED;
                break;
            case 5:
                missionResult = MissionResult.FAILED;
                break;
            default:
                LOGGER.error("Impossible to resolve mission status by id");
                throw new IllegalArgumentException("Invalid command");
        }
        LOGGER.info("Mission status was successfully resolved by id");
        return missionResult;
    }

    public static MissionResult generateMissionStatus() {
        LOGGER.info("Generating mission status id...");

        Random random = new Random();
        int generatedMissionResult = random.nextInt(5) + 1;

        LOGGER.info("Mission status id was generated");
        return resolveMissionStatus(generatedMissionResult);
    }
}
