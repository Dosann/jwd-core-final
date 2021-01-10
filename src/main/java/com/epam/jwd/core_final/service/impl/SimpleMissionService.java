package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.exception.DuplicateEntityException;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.util.FlightMissionStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SimpleMissionService implements MissionService {
    INSTANCE;

    private final Collection<FlightMission> FLIGHT_MISSIONS_CACHE =
            NassaContext.getInstance().retrieveBaseEntityList(FlightMission.class);


    @Override
    public List<FlightMission> findAllMissions() {
        return new ArrayList<>(FLIGHT_MISSIONS_CACHE);
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria) {
        if (criteria == null) {
            return findAllMissions();
        }
        return filterCriteria(criteria)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) {
        if (criteria == null) {
            return Optional.empty();
        }
        return filterCriteria(criteria)
                .findFirst();
    }

    @Override
    public FlightMission updateMissionDetails(FlightMission flightMission) {
        if (flightMission.getMissionResult().equals(MissionResult.FAILED)) {
            flightMission.getAssignedSpaceship().setReadyForNextMissions(false);
            flightMission.getAssignedCrew().forEach(crew -> crew.setReadyForNextMissions(false));
        }
        return flightMission;
    }

    @Override
    public FlightMission createMission(FlightMission flightMission) {
        boolean isDuplicateNotContainsInCache = FLIGHT_MISSIONS_CACHE.stream()
                .noneMatch(crewMember -> crewMember.getName().equals(flightMission.getName()));

        if (isDuplicateNotContainsInCache) {
            FLIGHT_MISSIONS_CACHE.add(flightMission);
            flightMission.setMissionResult(FlightMissionStatus.generateMissionStatus());
            return flightMission;
        }
        throw new DuplicateEntityException("Impossible to create a duplicate of flight mission");
    }

    private Stream<FlightMission> filterCriteria(Criteria<? extends FlightMission> criteria) {
        FlightMissionCriteria flightMissionCriteria = (FlightMissionCriteria) criteria;

        return FLIGHT_MISSIONS_CACHE.stream()
                .filter(mission -> isNameMatches(mission, criteria) &&
                        isStartDateMatches(mission, flightMissionCriteria) &&
                        isEndDateMatches(mission, flightMissionCriteria) &&
                        isDistanceMatches(mission, flightMissionCriteria) &&
                        isAssignedSpaceshipMatches(mission, flightMissionCriteria) &&
                        isAssignedCrewMatches(mission, flightMissionCriteria) &&
                        isMissionResultMatches(mission, flightMissionCriteria)
                );
    }

    private boolean isNameMatches(FlightMission flightMission, Criteria<? extends FlightMission> missionCriteria) {
        return missionCriteria.getName() == null || flightMission.getName().equals(missionCriteria.getName());
    }

    private boolean isStartDateMatches(FlightMission flightMission, FlightMissionCriteria missionCriteria) {
        return missionCriteria.getStartDate() == null
                || flightMission.getStartDate().equals(missionCriteria.getStartDate());
    }

    private boolean isEndDateMatches(FlightMission flightMission, FlightMissionCriteria missionCriteria) {
        return missionCriteria.getEndDate() == null
                || flightMission.getEndDate().equals(missionCriteria.getEndDate());
    }

    private boolean isDistanceMatches(FlightMission flightMission, FlightMissionCriteria missionCriteria) {
        return missionCriteria.getDistance() == null
                || flightMission.getDistance().equals(missionCriteria.getDistance());
    }

    private boolean isAssignedSpaceshipMatches(FlightMission flightMission, FlightMissionCriteria missionCriteria) {
        if (missionCriteria.getAssignedSpaceship() != null) {
            return flightMission.getAssignedSpaceship().getName().equals(missionCriteria.getAssignedSpaceship().getName());
        }
        return true;
    }

    private boolean isAssignedCrewMatches(FlightMission flightMission, FlightMissionCriteria missionCriteria) {
        return missionCriteria.getAssignedCrew() == null
                || flightMission.getAssignedCrew().equals(missionCriteria.getAssignedCrew());
    }

    private boolean isMissionResultMatches(FlightMission flightMission, FlightMissionCriteria missionCriteria) {
        return missionCriteria.getMissionResult() == null
                || flightMission.getMissionResult().equals(missionCriteria.getMissionResult());
    }
}
