package io.github.v1serviceapplication.domain.weekendmealapply;

import io.github.v1serviceapplication.global.entity.BaseUUIDEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "tbl_weekend_meal_apply")
@Entity
public class WeekendMealApply extends BaseUUIDEntity {
    @NotNull
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isApplied;
}
