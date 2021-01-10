package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
public class CrewMemberCriteria extends Criteria<CrewMember> {

    private final Role role;
    private final Rank rank;
    private final Boolean isReadyForNextMissions;

    private CrewMemberCriteria(Builder builder) {
        super(builder);
        this.role = builder.role;
        this.rank = builder.rank;
        this.isReadyForNextMissions = builder.isReadyForNextMissions;
    }

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public Boolean getReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Criteria.Builder<Builder> {

        private Role role;
        private Rank rank;
        private Boolean isReadyForNextMissions;

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public Criteria<CrewMember> build() {
            return new CrewMemberCriteria(this);
        }

        public Builder withRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder withRank(Rank rank) {
            this.rank = rank;
            return this;
        }

        public Builder isReadyForNextMissions(Boolean isReadyForNextMissions) {
            this.isReadyForNextMissions = isReadyForNextMissions;
            return this;
        }
    }
}
