package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.AssignationException;
import com.epam.jwd.core_final.exception.DuplicateEntityException;
import com.epam.jwd.core_final.service.CrewService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SimpleCrewService implements CrewService {
    INSTANCE;

    private final Collection<CrewMember> CREW_MEMBERS_CACHE =
            NassaContext.getInstance().retrieveBaseEntityList(CrewMember.class);

    @Override
    public List<CrewMember> findAllCrewMembers() {
        return new ArrayList<>(CREW_MEMBERS_CACHE);
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria) {
        if (criteria == null) {
            return findAllCrewMembers();
        }
        return filterCriteria(criteria)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) {
        if (criteria == null) {
            return Optional.empty();
        }
        return filterCriteria(criteria)
                .findFirst();
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) {
        long rankId = crewMember.getRank().getId();
        long roleId = crewMember.getRole().getId();

        if (rankId != 4) {
            crewMember.setRank(Rank.resolveRankById((int) ++rankId));
        } if (roleId != 4) {
            crewMember.setRole(Role.resolveRoleById((int) ++roleId));
        }
        return crewMember;
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember) throws AssignationException {
        if (crewMember.getReadyForNextMissions()) {
            crewMember.setReadyForNextMissions(false);
        } else {
            throw new AssignationException("Impossible to assign crew member on a mission");
        }
    }

    @Override
    public CrewMember createCrewMember(CrewMember crewMember) throws DuplicateEntityException {
        boolean isDuplicateNotContainsInCache = CREW_MEMBERS_CACHE.stream()
                .noneMatch(member -> member.getName().equals(crewMember.getName()));

        if (isDuplicateNotContainsInCache) {
            CREW_MEMBERS_CACHE.add(crewMember);
            return crewMember;
        }
        throw new DuplicateEntityException("Impossible to create a duplicate of crew member");
    }

    private Stream<CrewMember> filterCriteria(Criteria<? extends CrewMember> criteria) {
        CrewMemberCriteria crewMemberCriteria = (CrewMemberCriteria) criteria;

        return CREW_MEMBERS_CACHE.stream()
                .filter(crewMember -> isIdMatches(crewMember, criteria) &&
                        isNameMatches(crewMember, criteria) &&
                        isRoleMatches(crewMember, crewMemberCriteria) &&
                        isRankMatches(crewMember, crewMemberCriteria) &&
                        isPreparationForNextMissionsMatches(crewMember, crewMemberCriteria)
                );
    }

    private boolean isIdMatches(CrewMember crewMember, Criteria<? extends CrewMember> crewMemberCriteria) {
        return crewMemberCriteria.getId() == null || crewMember.getId().equals(crewMemberCriteria.getId());
    }

    private boolean isNameMatches(CrewMember crewMember, Criteria<? extends CrewMember> crewMemberCriteria) {
        return crewMemberCriteria.getName() == null || crewMember.getName().equals(crewMemberCriteria.getName());
    }

    private boolean isRoleMatches(CrewMember crewMember, CrewMemberCriteria crewMemberCriteria) {
        return crewMemberCriteria.getRole() == null || crewMember.getRole() == crewMemberCriteria.getRole();
    }

    private boolean isRankMatches(CrewMember crewMember, CrewMemberCriteria crewMemberCriteria) {
        return crewMemberCriteria.getRank() == null || crewMember.getRank() == crewMemberCriteria.getRank();
    }

    private boolean isPreparationForNextMissionsMatches(CrewMember crewMember, CrewMemberCriteria crewMemberCriteria) {
        return crewMemberCriteria.getReadyForNextMissions() == null
                || crewMember.getReadyForNextMissions().equals(crewMemberCriteria.getReadyForNextMissions());
    }
}
