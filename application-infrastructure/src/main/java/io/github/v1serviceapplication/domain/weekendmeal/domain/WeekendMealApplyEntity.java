package io.github.v1serviceapplication.domain.weekendmeal.domain;

import io.github.v1serviceapplication.global.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@Table(name = "tbl_weekend_meal_apply")
@Entity
public class WeekendMealApplyEntity extends BaseEntity {

    @NotNull
    @CreatedDate
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "weekend_meal_id")
    private WeekendMealEntity weekendMeal;

    @NotNull
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isApplied;

    public UUID getMealId() {
        return this.weekendMeal.getId();
    }

    public void updateApplied(boolean isApplied) {
        this.isApplied = isApplied;
    }

}
