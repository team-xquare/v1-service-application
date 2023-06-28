package io.github.v1serviceapplication.domain.weekendmealcheck.domain;

import io.github.v1serviceapplication.domain.weekendmeal.domain.WeekendMealEntity;
import io.github.v1serviceapplication.global.entity.BaseDateEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@Table(name = "tbl_weekend_meal_check")
@Entity
public class WeekendMealCheckEntity extends BaseDateEntity{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "weekend_meal_id")
    private WeekendMealEntity weekendMeal;

    @NotNull
    @Column(columnDefinition = "TINYINT(1)")
    private boolean isCheck;

    @NotNull
    @Column(columnDefinition = "INT(1)")
    private Integer grade;

    @NotNull
    @Column(columnDefinition = "INT(1)")
    private Integer classNum;

    public UUID getWeekendMealId() {
        return this.weekendMeal.getId();
    }

    public void changeIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
}
