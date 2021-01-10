package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.AssignationException;
import com.epam.jwd.core_final.exception.DuplicateEntityException;
import com.epam.jwd.core_final.service.SpaceshipService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SimpleSpaceshipService implements SpaceshipService {
    INSTANCE;

    private final Collection<Spaceship> SPACESHIP_CACHE =
            NassaContext.getInstance().retrieveBaseEntityList(Spaceship.class);

    @Override
    public List<Spaceship> findAllSpaceships() {
        return new ArrayList<>(SPACESHIP_CACHE);
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(Criteria<? extends Spaceship> criteria) {
        if (criteria == null) {
            return findAllSpaceships();
        }
        return filterCriteria(criteria)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<? extends Spaceship> criteria) {
        if (criteria == null) {
            return Optional.empty();
        }
        return filterCriteria(criteria)
                .findFirst();
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) {
        return null;
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship crewMember) throws AssignationException {
        if (crewMember.getReadyForNextMissions()) {
            crewMember.setReadyForNextMissions(false);
        } else {
            throw new AssignationException("Impossible to assign crew member on a mission");
        }
    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws DuplicateEntityException {
        boolean isDuplicateNotContainsInCache = SPACESHIP_CACHE.stream()
                .noneMatch(crewMember -> crewMember.getName().equals(spaceship.getName()));

        if (isDuplicateNotContainsInCache) {
            SPACESHIP_CACHE.add(spaceship);
            return spaceship;
        }
        throw new DuplicateEntityException("Impossible to create a duplicate of spaceship");
    }

    private Stream<Spaceship> filterCriteria(Criteria<? extends Spaceship> criteria) {
        SpaceshipCriteria spaceshipCriteria = (SpaceshipCriteria) criteria;

        return SPACESHIP_CACHE.stream()
                .filter(spaceship -> isIdMatches(spaceship, criteria) &&
                        isNameMatches(spaceship, criteria) &&
                        isCrewMatches(spaceship, spaceshipCriteria) &&
                        isDistanceMatches(spaceship, spaceshipCriteria) &&
                        isPreparationForNextMissionsMatches(spaceship, spaceshipCriteria)
                );
    }

    private boolean isIdMatches(Spaceship spaceship, Criteria<? extends Spaceship> spaceshipCriteria) {
        return spaceshipCriteria.getId() == null || spaceship.getId().equals(spaceshipCriteria.getId());
    }

    private boolean isNameMatches(Spaceship spaceship, Criteria<? extends Spaceship> spaceshipCriteria) {
        return spaceshipCriteria.getName() == null || spaceship.getName().equals(spaceshipCriteria.getName());
    }

    private boolean isCrewMatches(Spaceship spaceship, SpaceshipCriteria spaceshipCriteria) {
        return spaceshipCriteria.getCrew() == null || spaceship.getCrew().equals(spaceshipCriteria.getCrew());
    }

    private boolean isDistanceMatches(Spaceship spaceship, SpaceshipCriteria spaceshipCriteria) {
        return spaceshipCriteria.getFlightDistance() == null ||
                spaceship.getFlightDistance().equals(spaceshipCriteria.getFlightDistance());
    }

    private boolean isPreparationForNextMissionsMatches(Spaceship spaceship, SpaceshipCriteria spaceshipCriteria) {
        return spaceshipCriteria.getReadyForNextMissions() == null ||
                spaceship.getReadyForNextMissions().equals(spaceshipCriteria.getReadyForNextMissions());
    }

}
