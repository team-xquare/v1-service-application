package io.github.v1serviceapplication.domain.weekendmeal.domain;

import io.github.v1serviceapplication.global.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@Table(name = "tbl_weekend_meal_apply")
@Entity
public class WeekendMealEntity extends BaseEntity {
    @NotNull
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isApplied;
}
