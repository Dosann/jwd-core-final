package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.BaseEntity;

/**
 * Should be a builder for {@link BaseEntity} fields
 */
public abstract class Criteria<T extends BaseEntity> {

    private final Long id;
    private final String name;

    Criteria(Builder<?> builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract static class Builder<T extends Builder<T>> {

        Long id;
        String name;

        protected abstract T self();

        public T withId(Long id) {
            this.id = id;
            return self();
        }

        public T withName(String name) {
            this.name = name;
            return self();
        }

        public abstract Criteria<? extends BaseEntity> build();
    }
}
