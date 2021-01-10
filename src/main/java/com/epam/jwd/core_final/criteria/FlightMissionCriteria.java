package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Spaceship;

import java.time.LocalDate;
import java.util.List;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Long distance;
    private final Spaceship assignedSpaceShift;
    private final List<CrewMember> assignedCrew;
    private final MissionResult missionResult;

    private FlightMissionCriteria(Builder builder) {
        super(builder);
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.distance = builder.distance;
        this.assignedSpaceShift = builder.assignedSpaceShift;
        this.assignedCrew = builder.assignedCrew;
        this.missionResult = builder.missionResult;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Long getDistance() {
        return distance;
    }

    public Spaceship getAssignedSpaceship() {
        return assignedSpaceShift;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public MissionResult getMissionResult() {
        return missionResult;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Criteria.Builder<Builder> {

        private LocalDate startDate;
        private LocalDate endDate;
        private Long distance;
        private Spaceship assignedSpaceShift;
        private List<CrewMember> assignedCrew;
        private MissionResult missionResult;

        @Override
        protected FlightMissionCriteria.Builder self() {
            return this;
        }

        @Override
        public Criteria<FlightMission> build() {
            return new FlightMissionCriteria(this);
        }

        public Builder whenStarts(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder whenEnds(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder whereDistanceIs(Long distance) {
            this.distance = distance;
            return this;
        }

        public Builder withAssignedSpaceship(Spaceship assignedSpaceShift) {
            this.assignedSpaceShift = assignedSpaceShift;
            return this;
        }

        public Builder withAssignedCrew(List<CrewMember> assignedCrew) {
            this.assignedCrew = assignedCrew;
            return this;
        }

        public Builder withMissionResult(MissionResult missionResult) {
            this.missionResult = missionResult;
            return this;
        }
    }
}
