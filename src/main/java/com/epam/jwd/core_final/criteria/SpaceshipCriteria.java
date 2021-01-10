package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;

import java.util.Map;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<Spaceship> {

    private final Map<Role, Short> crew;
    private final Long flightDistance;
    private final Boolean isReadyForNextMissions;

    private SpaceshipCriteria(Builder builder) {
        super(builder);
        this.crew = builder.crew;
        this.flightDistance = builder.flightDistance;
        this.isReadyForNextMissions = builder.isReadyForNextMissions;
    }

    public Map<Role, Short> getCrew() {
        return crew;
    }

    public Long getFlightDistance() {
        return flightDistance;
    }

    public Boolean getReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Criteria.Builder<Builder> {

        private Map<Role, Short> crew;
        private Long flightDistance;
        private Boolean isReadyForNextMissions;

        @Override
        protected SpaceshipCriteria.Builder self() {
            return this;
        }

        @Override
        public Criteria<Spaceship> build() {
            return new SpaceshipCriteria(this);
        }

        public Builder withCrew(Map<Role, Short> crew) {
            this.crew = crew;
            return this;
        }

        public Builder withFlightDistance(Long flightDistance) {
            this.flightDistance = flightDistance;
            return this;
        }

        public Builder isReadyForNextMissions(Boolean isReadyForNextMissions) {
            this.isReadyForNextMissions = isReadyForNextMissions;
            return this;
        }
    }
}
